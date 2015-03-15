package fatworm.plan;

import java.util.LinkedList;
import java.util.concurrent.ConcurrentHashMap;

import fatworm.FatwormDB;
import fatworm.database.DataBase;
import fatworm.database.Schema;
import fatworm.database.Table;
import fatworm.driver.FatwormResultSet;
import fatworm.driver.FatwormStatement;
import fatworm.expr.Expr;
import fatworm.expr.FieldExpr;
import fatworm.index.Index;
import fatworm.plan.index.IndexDeletePlan;
import fatworm.plan.index.IndexInsertPlan;
import fatworm.plan.index.IndexSelectPlan;
import fatworm.plan.index.IndexUpdatePlan;
import fatworm.pred.AndPred;
import fatworm.pred.CopPred;
import fatworm.pred.InPred;
import fatworm.pred.IndexInPred;
import fatworm.pred.OrPred;
import fatworm.pred.Predicate;

public class Planner
{
	private Plan rootPlan;

	public Planner()
	{
	}

	public int execute()
	{
		try
		{
			if (DataBase.getDataBase() != null && DataBase.getDataBase().getOpenedScans() != null) DataBase.getDataBase().getOpenedScans().clear();
			if (rootPlan == null)
			{
				FatwormDB.mdMgr().save();
				return 0;
			}
			if (rootPlan instanceof InsertPlan)
			{
				int ret = ((InsertPlan) rootPlan).execute();
				if (FatwormDB.durability)
				{
					if (DataBase.getDataBase() != null)
					{
						FatwormDB.mdMgr().put(DataBase.getDataBase().name, DataBase.getDataBase(), false);
					}
				}
				return ret;
			}
			if (rootPlan instanceof DeletePlan)
			{
				int ret = ((DeletePlan) rootPlan).execute();
				if (FatwormDB.durability)
				{
					if (DataBase.getDataBase() != null)
					{
						FatwormDB.mdMgr().put(DataBase.getDataBase().name, DataBase.getDataBase(), false);
					}
					FatwormDB.bufferMgr().writeAll();
				}
				return ret;
			}
			if (rootPlan instanceof UpdatePlan)
			{
				int ret = ((UpdatePlan) rootPlan).execute();
				if (FatwormDB.durability)
				{
					FatwormDB.bufferMgr().writeAll();
				}
				return ret;
			}
			FatwormStatement.setResultSet(new FatwormResultSet(rootPlan.open(), rootPlan.schema()));
			FatwormDB.bufferMgr().writeAll();
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return 1;
	}

	private Plan devideWhereOr(Plan plan)
	{
		if (plan == null) return null;
		if (plan instanceof SelectPlan || plan instanceof DistinctPlan || plan instanceof ExtendPlan || plan instanceof GroupPlan || plan instanceof ProjectPlan || plan instanceof RenamePlan || plan instanceof SortPlan || plan instanceof ProductPlan)
		{
			LinkedList<Plan> tmp = new LinkedList<Plan>();
			for (Plan plan1 : plan.son())
			{
				tmp.add(devideWhereOr(plan1));
			}
			plan.setSon(tmp);
		}
		if (plan instanceof SelectPlan)
		{
			LinkedList<Predicate> preds = devidePredOr(((SelectPlan) plan).predicate);
			Plan sonPlan = plan.son().get(0);
			for (Predicate pred : preds)
			{
				sonPlan = new SelectPlan(sonPlan, pred);
			}
			sonPlan.setFather(plan.father());
			if (plan.father() != null)
			{
				LinkedList<Plan> tmp = plan.father().son();
				tmp.set(0, sonPlan);
				plan.father().setSon(tmp);
			}
			return sonPlan;
		}
		return plan;
	}

	private LinkedList<Predicate> devidePredOr(Predicate pred)
	{
		LinkedList<Predicate> t1 = new LinkedList<Predicate>();
		LinkedList<Predicate> t2 = new LinkedList<Predicate>();
		LinkedList<Predicate> ret = new LinkedList<Predicate>();
		if (pred instanceof OrPred)
		{
			t1.addAll(devidePredAnd(((OrPred) pred).lftPredicate));
			t2.addAll(devidePredAnd(((OrPred) pred).rgtPredicate));
			for (Predicate p1 : t1)
			{
				for (Predicate p2 : t2)
				{
					ret.add(new OrPred(p1, p2));
				}
			}
		}
		else
		{
			ret.add(pred);
		}
		return ret;
	}

	public LinkedList<Predicate> devidePredAnd(Predicate pred)
	{
		LinkedList<Predicate> ret = new LinkedList<Predicate>();
		if (pred instanceof AndPred)
		{
			ret.addAll(devidePredAnd(((AndPred) pred).lftPredicate));
			ret.addAll(devidePredAnd(((AndPred) pred).rgtPredicate));
		}
		else
		{
			ret.add(pred);
		}
		return ret;
	}

	public Plan devideWhereAnd(Plan plan)
	{
		if (plan == null) return null;
		if (plan instanceof SelectPlan || plan instanceof DistinctPlan || plan instanceof ExtendPlan || plan instanceof GroupPlan || plan instanceof ProjectPlan || plan instanceof RenamePlan || plan instanceof SortPlan || plan instanceof ProductPlan)
		{
			LinkedList<Plan> tmp = new LinkedList<Plan>();
			for (Plan plan1 : plan.son())
			{
				tmp.add(devideWhereAnd(plan1));
			}
			plan.setSon(tmp);
		}
		if (plan instanceof SelectPlan)
		{
			LinkedList<Predicate> preds = devidePredAnd(((SelectPlan) plan).predicate);
			Plan sonPlan = plan.son().get(0);
			for (Predicate pred : preds)
			{
				sonPlan = new SelectPlan(sonPlan, pred);
			}
			sonPlan.setFather(plan.father());
			if (plan.father() != null)
			{
				LinkedList<Plan> tmp = plan.father().son();
				tmp.set(0, sonPlan);
				plan.father().setSon(tmp);
			}
			return sonPlan;
		}
		return plan;
	}

	public LinkedList<Plan> findSelectPlan(Plan plan)
	{
		LinkedList<Plan> ret = new LinkedList<Plan>();
		if (plan == null)
		{
			return ret;
		}
		if (plan instanceof ProductPlan)
		{
			ret.addAll(findSelectPlan(plan.son().get(0)));
			ret.addAll(findSelectPlan(plan.son().get(1)));
			return ret;
		}
		if (plan instanceof DistinctPlan || plan instanceof ExtendPlan || plan instanceof GroupPlan || plan instanceof ProjectPlan || plan instanceof RenamePlan || plan instanceof SortPlan)
		{
			if (!plan.son().isEmpty())
			{
				ret.addAll(findSelectPlan(plan.son().get(0)));
			}
			return ret;
		}
		if (plan instanceof SelectPlan)
		{
			ret.add(plan);
			if (!plan.son().isEmpty())
			{
				ret.addAll(findSelectPlan(plan.son().get(0)));
			}
		}
		return ret;
	}

	public Plan pushCopSelectPlan(Plan plan)
	{
		LinkedList<Plan> list = findSelectPlan(plan);
		for (Plan plan1 : list)
		{
			if (plan1 instanceof SelectPlan)
			{
				SelectPlan selectPlan = ((SelectPlan) plan1);
				selectPlan.pushDown();
			}
		}
		return plan;
	}
	
//	public Plan optimiseGroupSelectPlan(Plan plan)
//	{
//		if (plan instanceof ProjectPlan || plan instanceof ExtendPlan)
//		{
//			plan.son().set(0, optimiseGroupSelectPlan(plan.son().get(0)));
//		}
//		if (plan instanceof GroupPlan && plan.son().get(0) instanceof SelectPlan)
//		{
//			Predicate predicate = ((SelectPlan)(plan.son().get(0))).predicate;
//			if (predicate instanceof CopPred)
//			{
//				Expr lftExpr = ((CopPred)predicate).lftExpr;
//				if (lftExpr instanceof FieldExpr)
//				{
//					FieldExpr fieldExpr = (FieldExpr)lftExpr;
//					if (fieldExpr.fieldName.equals(((GroupPlan)plan).groupColName));
//					{
//						
//					}
//				}
//			}
//		}
//	}

	private Plan optimiseProductSelectEquals(Plan plan)
	{
		if (plan == null) return null;
		if (plan instanceof SelectPlan || plan instanceof DistinctPlan || plan instanceof ExtendPlan || plan instanceof GroupPlan || plan instanceof ProjectPlan || plan instanceof RenamePlan || plan instanceof SortPlan || plan instanceof ProductPlan)
		{
			LinkedList<Plan> tmp = new LinkedList<Plan>();
			for (Plan plan1 : plan.son())
			{
				tmp.add(optimiseProductSelectEquals(plan1));
			}
			plan.setSon(tmp);
		}
		if (plan instanceof SelectPlan && plan.son().get(0) instanceof ProductPlan && ((SelectPlan) plan).predicate instanceof CopPred && ((CopPred) (((SelectPlan) plan).predicate)).operator.equals("=") && ((CopPred) (((SelectPlan) plan).predicate)).lftExpr instanceof FieldExpr && ((CopPred) (((SelectPlan) plan).predicate)).rgtExpr instanceof FieldExpr)
		{
			CopPred copPred = (CopPred) (((SelectPlan) plan).predicate);
			FieldExpr lftExpr = (FieldExpr) (copPred).lftExpr;
			FieldExpr rgtExpr = (FieldExpr) (copPred).rgtExpr;
			Plan lftPlan = plan.son().get(0).son().get(0);
			Plan rgtPlan = plan.son().get(0).son().get(1);
			if (lftPlan instanceof RenamePlan && ((RenamePlan) lftPlan).name.equals(lftExpr.tableName) || rgtPlan instanceof RenamePlan && ((RenamePlan) rgtPlan).name.equals(rgtExpr.tableName))
			{
				Plan newplan = new ProductEqualSelectPlan(lftPlan, rgtPlan, copPred);
				LinkedList<Plan> tmp = plan.father().son();
				tmp.set(0, newplan);
				plan.father().setSon(tmp);
				plan = newplan;
			}
			else if (rgtPlan instanceof RenamePlan && ((RenamePlan) rgtPlan).name.equals(lftExpr.tableName) || lftPlan instanceof RenamePlan && ((RenamePlan) lftPlan).name.equals(rgtExpr.tableName))
			{
				copPred = new CopPred(rgtExpr, lftExpr, "=");
				Plan newplan = new ProductEqualSelectPlan(lftPlan, rgtPlan, copPred);
				LinkedList<Plan> tmp = plan.father().son();
				tmp.set(0, newplan);
				plan.father().setSon(tmp);
				plan = newplan;
			}
			else if (rgtPlan instanceof TablePlan && ((TablePlan) rgtPlan).table.name.equals(lftExpr.tableName) || lftPlan instanceof TablePlan && ((TablePlan) lftPlan).table.name.equals(rgtExpr.tableName))
			{
				copPred = new CopPred(rgtExpr, lftExpr, "=");
				Plan newplan = new ProductEqualSelectPlan(lftPlan, rgtPlan, copPred);
				LinkedList<Plan> tmp = plan.father().son();
				tmp.set(0, newplan);
				plan.father().setSon(tmp);
				plan = newplan;
			}
			else if (lftPlan instanceof TablePlan && ((TablePlan) lftPlan).table.name.equals(lftExpr.tableName) || rgtPlan instanceof TablePlan && ((TablePlan) rgtPlan).table.name.equals(rgtExpr.tableName))
			{
				Plan newplan = new ProductEqualSelectPlan(lftPlan, rgtPlan, copPred);
				LinkedList<Plan> tmp = plan.father().son();
				tmp.set(0, newplan);
				plan.father().setSon(tmp);
				plan = newplan;
			}
		}
		return plan;
	}

	private Plan getIndexSelect(Plan plan)
	{
		if (plan == null) return null;
		if (plan instanceof SelectPlan || plan instanceof DistinctPlan || plan instanceof ExtendPlan || plan instanceof GroupPlan || plan instanceof ProjectPlan || plan instanceof RenamePlan || plan instanceof SortPlan || plan instanceof ProductPlan)
		{
			LinkedList<Plan> tmp = new LinkedList<Plan>();
			for (Plan plan1 : plan.son())
			{
				tmp.add(getIndexSelect(plan1));
			}
			plan.setSon(tmp);
		}
		if (plan instanceof SelectPlan)
		{
			Plan sonPlan = plan.son().get(0);
			if (sonPlan instanceof SelectPlan)
			{
				Plan sonsonPlan = sonPlan.son().get(0);
				if (sonsonPlan instanceof TablePlan)
				{
					Predicate pred1 = ((SelectPlan) plan).predicate;
					Predicate pred2 = ((SelectPlan) sonPlan).predicate;
					ConcurrentHashMap<String, Index> indexes = ((TablePlan) sonsonPlan).table.indexes;
					if (pred1 instanceof CopPred && pred2 instanceof CopPred)
					{
						CopPred cop1 = (CopPred) pred1;
						CopPred cop2 = (CopPred) pred2;
						if (cop1.lftExpr instanceof FieldExpr && cop1.rgtExpr.isConst() && cop2.lftExpr instanceof FieldExpr && cop2.rgtExpr.isConst() && cop1.operator.equals("<=") && cop2.operator.equals(">="))
						{
							FieldExpr exp1 = (FieldExpr) cop1.lftExpr;
							FieldExpr exp2 = (FieldExpr) cop2.lftExpr;
							{
								String string = exp1.fieldName;
								if (exp2.fieldName.equals(string))
								{
									for (Index index : indexes.values())
									{
										if (index.getColumn().equals(string))
										{
											plan = new IndexSelectPlan((TablePlan)sonsonPlan, string, cop2.rgtExpr.getValue(null), cop1.rgtExpr.getValue(null), index);
										}
									}
								}
							}
						}
					}
				}
			}
		}
		return plan;
	}

	public void createInsertPlan(Table t, Plan p)
	{
		rootPlan = new IndexInsertPlan(t, p);
	}

	public void createUpdatePlan(Table t, Predicate pred, LinkedList<Expr> exprs, Schema s) throws Exception
	{
		rootPlan = new IndexUpdatePlan(t, pred, exprs, s);
	}

	public void createDeletePlan(Table t, Predicate p) throws Exception
	{
		rootPlan = new IndexDeletePlan(t, p);
	}

	private Plan indexOptimizeInPred(Plan plan)
	{
		if (plan == null) return null;
		if (plan instanceof SelectPlan || plan instanceof DistinctPlan || plan instanceof ExtendPlan || plan instanceof GroupPlan || plan instanceof ProjectPlan || plan instanceof RenamePlan || plan instanceof SortPlan || plan instanceof ProductPlan)
		{
			LinkedList<Plan> tmp = new LinkedList<Plan>();
			for (Plan plan1 : plan.son())
			{
				tmp.add(indexOptimizeInPred(plan1));
			}
			plan.setSon(tmp);
		}
		if (plan instanceof SelectPlan)
		{
			Predicate pred = ((SelectPlan) plan).predicate;
			if (pred instanceof InPred)
			{
				InPred inPred = (InPred) pred;
				if (inPred.subqueryPlan instanceof ProjectPlan)
				{
					ProjectPlan projectPlan = (ProjectPlan) inPred.subqueryPlan;
					if (projectPlan.son().get(0) instanceof ExtendPlan && projectPlan.exprs.get(0) instanceof FieldExpr)
					{
						FieldExpr expr = (FieldExpr) projectPlan.exprs.get(0);
						ExtendPlan extendPlan = (ExtendPlan) projectPlan.son().get(0);
						if (extendPlan.son().get(0) instanceof TablePlan)
						{
							TablePlan tablePlan = (TablePlan) extendPlan.son().get(0);
							for (Index index : tablePlan.table.indexes.values())
							{
								if (index.getColumn().equals(expr.fieldName))
								{
									((SelectPlan) plan).predicate = new IndexInPred(expr, index);
									return plan;
								}
							}
						}
					}
				}
			}
		}
		return plan;
	}

	public void setPlan(Plan plan)
	{
		// propergate those optimization into subqueries. no hurry, this is not that urgent
		plan = devideWhereOr(plan);
		plan = devideWhereAnd(plan);
		plan = pushCopSelectPlan(plan);
		plan = getIndexSelect(plan);
		plan = optimiseProductSelectEquals(plan);
		plan = indexOptimizeInPred(plan);
		// if (plan != null) System.out.println(plan.tostring());
		rootPlan = plan;
	}
}
