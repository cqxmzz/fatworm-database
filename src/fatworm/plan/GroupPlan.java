package fatworm.plan;

import java.util.LinkedList;
import java.util.List;

import fatworm.database.Column;
import fatworm.database.Schema;
import fatworm.expr.Expr;
import fatworm.expr.FieldExpr;
import fatworm.expr.FuncExpr;
import fatworm.scan.GroupScan;
import fatworm.scan.Scan;
import fatworm.types.DECIMAL;
import fatworm.types.FLOAT;
import fatworm.types.INT;
import fatworm.types.Type;

public class GroupPlan implements Plan
{
	String groupColName;

	String groupTableName;

	Plan fatherPlan = null;

	Plan plan;

	Schema schema;

	public GroupPlan(String groupTbl, String groupCol, Plan p)
	{
		groupColName = groupCol;
		groupTableName = groupTbl;
		plan = p;
		plan.setFather(this);
		setSchema();
	}

	public GroupPlan(String groupTbl, String groupCol, Plan p, List<Expr> exprs)
	{
		groupColName = groupCol;
		groupTableName = groupTbl;
		plan = p;
		plan.setFather(this);
		setSchema(exprs);
	}

	@Override
	public Scan open()
	{
		return new GroupScan(schema(), plan.open(), groupColName, groupTableName);
	}

	void setSchema(List<Expr> exprs)
	{
		Schema ret = new Schema();
		for (Expr expr : exprs)
		{
			if (expr instanceof FieldExpr)
			{
				int i = plan.schema().getIndex(((FieldExpr) expr).fieldName, ((FieldExpr) expr).tableName);
				ret.add(plan.schema().getColumns().get(i), plan.schema().getTableName(i), plan.schema().getName(i));
			}
			if (expr instanceof FuncExpr)
			{
				int i = plan.schema().getIndex(((FuncExpr) expr).name);
				if (((FuncExpr) expr).func.equals("sum"))
				{
					ret.add(new Column(new DECIMAL(100), false, false, false, null), plan.schema().getTableName(i), "sum(" + plan.schema().getName(i) + ")");
				}
				else if (((FuncExpr) expr).func.equals("avg"))
				{
					ret.add(new Column(new DECIMAL(100), false, false, false, null), plan.schema().getTableName(i), "sum(" + plan.schema().getName(i) + ")");
					ret.add(new Column(new DECIMAL(100), false, false, false, null), plan.schema().getTableName(i), "avg(" + plan.schema().getName(i) + ")");
					ret.add(new Column(new INT(), false, false, false, null), plan.schema().getTableName(i), "count(" + plan.schema().getName(i) + ")");
				}
				else if (((FuncExpr) expr).func.equals("count"))
				{
					ret.add(new Column(new INT(), false, false, false, null), plan.schema().getTableName(i), expr.toString());
				}
				else
				{
					ret.add(plan.schema().getColumns().get(i), plan.schema().getTableName(i), expr.toString());
				}
			}
		}
		schema = ret;
	}

	void setSchema()
	{
		if (groupColName != null)
		{
			Schema ret = new Schema();
			for (int i = 0; i < plan.schema().getColumns().size(); ++i)
			{
				if (plan.schema().getName(i) == groupColName)
				{
					ret.add(plan.schema().getColumns().get(i), null, groupColName);
					continue;
				}
				Type t = plan.schema().getColumns().get(i).getType();
				ret.add(plan.schema().getColumns().get(i), plan.schema().getTableName(i), plan.schema().getName(i));
				ret.add(new Column(new INT(), false, false, false, null), plan.schema().getTableName(i), "count(" + plan.schema().getName(i) + ")");
				if (t instanceof INT || t instanceof FLOAT || t instanceof DECIMAL)
				{
					ret.add(new Column(new DECIMAL(100), false, false, false, null), plan.schema().getTableName(i), "sum(" + plan.schema().getName(i) + ")");
					ret.add(new Column(new DECIMAL(100), false, false, false, null), plan.schema().getTableName(i), "avg(" + plan.schema().getName(i) + ")");
				}
				ret.add(plan.schema().getColumns().get(i), plan.schema().getTableName(i), "max(" + plan.schema().getName(i) + ")");
				ret.add(plan.schema().getColumns().get(i), plan.schema().getTableName(i), "min(" + plan.schema().getName(i) + ")");
			}
			schema = ret;
			return;
		}
		else
		{
			Schema ret = new Schema();
			for (int i = 0; i < plan.schema().getColumns().size(); ++i)
			{
				Type t = plan.schema().getColumns().get(i).getType();
				ret.add(plan.schema().getColumns().get(i), plan.schema().getTableName(i), plan.schema().getName(i));
				ret.add(new Column(new INT(), false, false, false, null), plan.schema().getTableName(i), "count(" + plan.schema().getName(i) + ")");
				if (t instanceof INT || t instanceof FLOAT || t instanceof DECIMAL)
				{
					ret.add(new Column(new DECIMAL(100), false, false, false, null), plan.schema().getTableName(i), "sum(" + plan.schema().getName(i) + ")");
					ret.add(new Column(new DECIMAL(100), false, false, false, null), plan.schema().getTableName(i), "avg(" + plan.schema().getName(i) + ")");
				}
				ret.add(plan.schema().getColumns().get(i), plan.schema().getTableName(i), "max(" + plan.schema().getName(i) + ")");
				ret.add(plan.schema().getColumns().get(i), plan.schema().getTableName(i), "min(" + plan.schema().getName(i) + ")");
			}
			schema = ret;
			return;
		}
	}

	@Override
	public Schema schema()
	{
		return schema;
	}

	@Override
	public LinkedList<Plan> son()
	{
		LinkedList<Plan> ret = new LinkedList<Plan>();
		ret.add(plan);
		return ret;
	}

	@Override
	public void setSon(LinkedList<Plan> plans)
	{
		plan = plans.get(0);
	}

	@Override
	public Plan father()
	{
		return fatherPlan;
	}

	@Override
	public void setFather(Plan plan)
	{
		fatherPlan = plan;
	}

	@Override
	public String tostring()
	{
		return "Group " + plan.tostring();
	}
}
