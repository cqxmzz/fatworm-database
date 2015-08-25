package fatworm.parser;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.antlr.runtime.tree.CommonTree;

import fatworm.FatwormDB;
import fatworm.FatwormException;
import fatworm.database.*;
import fatworm.expr.*;
import fatworm.plan.*;
import fatworm.pred.*;
import fatworm.types.*;

public class TreeTraverse
{
	public void printTree(CommonTree t, int indent) // default indent = 1;
	{
		if (t != null)
		{
			StringBuffer sb = new StringBuffer(indent);
			System.out.println(sb.toString() + t.getText().toString());
			for (int i = 0; i < indent; i++)
				sb = sb.append("   ");
			for (int i = 0; i < t.getChildCount(); i++)
			{
				System.out.println(sb.toString() + t.getChild(i).getText());
				printSubTree((CommonTree) t.getChild(i), indent + 1);
			}
		}
	}

	public void printSubTree(CommonTree t, int indent)
	{
		if (t != null)
		{
			StringBuffer sb = new StringBuffer(indent);
			for (int i = 0; i < indent; i++)
				sb = sb.append("   ");
			for (int i = 0; i < t.getChildCount(); i++)
			{
				System.out.println(sb.toString() + t.getChild(i).getText());
				printSubTree((CommonTree) t.getChild(i), indent + 1);
			}
		}
	}

	// This code here is ugly as shit, I should have used visitor pattern for parsing, but I don't have time to fix it
	@SuppressWarnings("unchecked")
	void traverse(CommonTree tree)
	{
		try
		{
			FatwormDB.planner().setPlan(null);
			// statement -> database_statement -> CREATE_DATABASE db_name
			if (tree.getType() == FatwormLexer.CREATE_DATABASE)
			{
				// db_name -> name -> ID
				CommonTree child0 = (CommonTree) tree.getChild(0);
				if (child0.getType() == FatwormLexer.ID)
				{
					String db_name = child0.toString().toLowerCase();
					if (DataBase.include(db_name))
					{
						throw new FatwormException("can't create, already have that database");
					}
					else
					{
						DataBase.createDataBase(db_name);
					}
				}
			}
			// statement -> database_statement -> USE_DATABASE db_name
			if (tree.getType() == FatwormLexer.USE_DATABASE)
			{
				// db_name -> name -> ID
				CommonTree child0 = (CommonTree) tree.getChild(0);
				if (child0.getType() == FatwormLexer.ID)
				{
					String db_name = child0.toString().toLowerCase();
					if (DataBase.include(db_name))
					{
						DataBase.useDataBase(db_name);
					}
					else
					{
						throw new FatwormException("can't use, don't have that database");
					}
				}
			}
			// statement -> table_statement -> DROP_DATABASE db_name
			if (tree.getType() == FatwormLexer.DROP_DATABASE)
			{
				// db_name -> name -> ID
				CommonTree child0 = (CommonTree) tree.getChild(0);
				if (child0.getType() == FatwormLexer.ID)
				{
					String db_name = child0.toString().toLowerCase();
					FatwormDB.bufferMgr().writeAll();
					if (DataBase.include(db_name))
					{
						DataBase.dropDataBase(db_name);
					}
					else
					{
						FatwormDB.fileMgr().dropDatabasePath(db_name);
					}
				}
			}
			// statement -> table_statement -> CREATE_TABLE tbl_name
			// create_definition*
			if (tree.getType() == FatwormLexer.CREATE_TABLE)
			{
				List<CommonTree> childTrees0 = tree.getChildren();
				String tbl_name = null;
				LinkedList<Column> cols = new LinkedList<Column>();
				LinkedList<String> colNameStrings = new LinkedList<String>();
				LinkedList<String> tblNameStrings = new LinkedList<String>();
				String primaryKey = null;
				for (CommonTree child0 : childTrees0)
				{
					// tbl_name -> name -> ID
					if (child0.getType() == FatwormLexer.ID)
					{
						tbl_name = child0.toString().toLowerCase();
						if (DataBase.getDataBase().includeTable(tbl_name))
						{
							throw new FatwormException("can't create, already have that table");
						}
					}
					// create_definition -> CREATE_DEFINITION col_name data_type
					// column_definition_suffix*
					if (child0.getType() == FatwormLexer.CREATE_DEFINITION)
					{
						List<CommonTree> childTrees1 = child0.getChildren();
						String name = null;
						Type type = null;
						boolean notNull = false;
						boolean isNull = false;
						boolean autoIncrement = false;
						boolean hasDefault = false;
						String constValue = null;
						for (CommonTree child1 : childTrees1)
						{
							// col_name -> tbl_name '.'^ name (will not happen)
							// col_name -> name -> ID
							if (child1.getType() == FatwormLexer.ID)
							{
								name = child1.toString().toLowerCase();
								if (colNameStrings.contains(name))
								{
									throw new FatwormException("can't create, duplicate column name");
								}
							}
							// column_definition_suffix -> AUTO_INCREMENT
							if (child1.getType() == FatwormLexer.AUTO_INCREMENT)
							{
								autoIncrement = true;
							}
							// column_definition_suffix -> NOT? NULL
							if (child1.getType() == FatwormLexer.NULL)
							{
								// column_definition_suffix -> NULL
								if (child1.getChildCount() == 0)
								{
									if (notNull)
									{
										throw new FatwormException("can't create, not null- null conflict");
									}
									else
									{
										isNull = true;
									}
								}
								else
								{
									CommonTree child2 = (CommonTree) child1.getChild(0);
									if (child2 != null)
									{
										// column_definition_suffix -> NOT NULL
										if (isNull)
										{
											throw new FatwormException("can't create, not null- null conflict");
										}
										else
										{
											notNull = true;
										}
									}
								}
							}
							// column_definition_suffix -> DEFAULT CONST_VALUE
							if (child1.getType() == FatwormLexer.DEFAULT)
							{
								hasDefault = true;
								CommonTree child2 = (CommonTree) child1.getChild(0);
								if (child2.getType() == FatwormLexer.NULL)
								{
									type.setNULL();
								}
								else
								{
									type.setNotNULL();
								}
								String cv = child2.getText();
								// CONST_VALUE -> ?
								constValue = cv;
							}
							if (child1.getType() == FatwormLexer.BOOLEAN || child1.getType() == FatwormLexer.CHAR || child1.getType() == FatwormLexer.DATETIME || child1.getType() == FatwormLexer.DECIMAL || child1.getType() == FatwormLexer.FLOAT || child1.getType() == FatwormLexer.INT || child1.getType() == FatwormLexer.TIMESTAMP || child1.getType() == FatwormLexer.VARCHAR)
							{
								type = getType(child1);
							}
						}
						Column c = new Column(type, notNull, autoIncrement, hasDefault, constValue);
						cols.add(c);
						colNameStrings.addLast(name);
						tblNameStrings.addLast(tbl_name);
					}
					// create_definition -> PRIMARY_KEY col_name
					if (child0.getType() == FatwormLexer.PRIMARY_KEY)
					{
						// col_name -> tbl_name '.'^ name (will not happen)
						CommonTree child1 = (CommonTree) child0.getChild(0);
						// col_name -> name -> ID
						if (child1.getType() == FatwormLexer.ID)
						{
							primaryKey = child1.getText().toLowerCase();
						}
					}
				}
				Table table = Table.createTable(tbl_name, new Schema(cols, tblNameStrings, colNameStrings));
				table.setPrimaryKey(primaryKey);
			}
			// statement -> table_statement -> DROP_TABLE tbl_name*
			if (tree.getType() == FatwormLexer.DROP_TABLE)
			{
				List<CommonTree> childTrees0 = tree.getChildren();
				for (CommonTree child0 : childTrees0)
				{
					// tbl_name -> name -> ID
					if (child0.getType() == FatwormLexer.ID)
					{
						if (DataBase.getDataBase().includeTable(child0.getText().toLowerCase()))
						{
							Table.dropTable(child0.getText().toLowerCase());
						}
						else
						{
							// throw new
							// FatwormException("can't drop, don't have that table");
						}
					}
				}
			}
			// statement -> insert_statement -> INSERT_VALUES tbl_name
			// values_clause (no func in value)
			try
			{
				if (tree.getType() == FatwormLexer.INSERT_VALUES)
				{
					Table t = null;
					Record r = null;
					List<CommonTree> childTrees0 = tree.getChildren();
					String tbl_name = "";
					for (CommonTree child0 : childTrees0)
					{
						// tbl_name -> name -> ID
						if (child0.getType() == FatwormLexer.ID)
						{
							tbl_name = child0.toString().toLowerCase();
							t = DataBase.getDataBase().getTable(tbl_name);
							r = new Record(t);
						}
						// values_clause -> VALUES value value*
						if (child0.getType() == FatwormLexer.VALUES)
						{
							Iterator<String> it = DataBase.getDataBase().getTable(tbl_name).getSchema().getNames().iterator();
							List<CommonTree> childTrees1 = child0.getChildren();
							for (CommonTree child1 : childTrees1)
							{
								String s = it.next();
								Type type = getValue(child1, t.getColumns().get(s));
								r.setValue(s, type);
							}
						}
					}
					FatwormDB.planner().createInsertPlan(t, new RecordPlan(r));
				}
				// statement -> insert_statement -> INSERT_COLUMNS tbl_name
				// col_name
				// col_name* values_clause (no func in value)
				if (tree.getType() == FatwormLexer.INSERT_COLUMNS)
				{
					Table t = null;
					Record r = null;
					List<CommonTree> childTrees0 = tree.getChildren();
					// tbl_name -> name -> ID
					CommonTree child0 = childTrees0.get(0);
					if (child0.getType() == FatwormLexer.ID)
					{
						String tbl_name = child0.toString().toLowerCase();
						t = DataBase.getDataBase().getTable(tbl_name);
						r = new Record(t);
					}
					List<String> cols = new LinkedList<String>();
					String col_name = null;
					for (int i = 1; i < childTrees0.size(); ++i)
					{
						child0 = childTrees0.get(i);
						// col_name -> tbl_name '.'^ name (will not happen)
						// col_name -> name -> ID
						if (child0.getType() == FatwormLexer.ID)
						{
							col_name = child0.toString().toLowerCase();
							cols.add(col_name);
						}
						// values_clause -> VALUES value value*
						if (child0.getType() == FatwormLexer.VALUES)
						{
							Iterator<String> it = cols.iterator();
							List<CommonTree> childTrees1 = child0.getChildren();
							for (CommonTree child1 : childTrees1)
							{
								Type ty = getValue(child1, t.getColumns().get(it.next()));
								r.setValue(col_name, ty);
							}
						}
					}
					FatwormDB.planner().createInsertPlan(t, new RecordPlan(r));
				}
			} catch (FatwormException e)
			{
				e.printStackTrace();
			}
			// statement -> insert_statement -> INSERT_SUBQUERY tbl_name
			// subquery
			if (tree.getType() == FatwormLexer.INSERT_SUBQUERY)
			{
				List<CommonTree> childTrees0 = tree.getChildren();
				Table t = null;
				Plan plan = null;
				for (CommonTree child0 : childTrees0)
				{
					// tbl_name -> name -> ID
					if (child0.getType() == FatwormLexer.ID)
					{
						String tbl_name = child0.toString().toLowerCase();
						t = DataBase.getDataBase().getTable(tbl_name);
					}
					// subquery -> select_statement
					else
					{
						plan = traverseSelect(child0);
					}
				}
				FatwormDB.planner().createInsertPlan(t, plan);
			}
			// statement -> delete_statement -> DELETE^ tbl_name where_clause?
			if (tree.getType() == FatwormLexer.DELETE)
			{
				Table t = null;
				Predicate pred = null;
				List<CommonTree> childTrees0 = tree.getChildren();
				for (CommonTree child0 : childTrees0)
				{
					// tbl_name -> name -> ID
					if (child0.getType() == FatwormLexer.ID)
					{
						String tbl_name = child0.toString().toLowerCase();
						t = DataBase.getDataBase().getTable(tbl_name);
					}
					// where_clause -> WHERE^ where_condition
					if (child0.getType() == FatwormLexer.WHERE)
					{
						pred = traverseWhere((CommonTree) child0.getChild(0));
					}
				}
				FatwormDB.planner().createDeletePlan(t, pred);
			}
			// statement -> update_statement -> UPDATE^ tbl_name update_pair
			// update_pair* where_clause?
			if (tree.getType() == FatwormLexer.UPDATE)
			{
				Table t = null;
				Predicate pred = null;
				Column column = null;
				LinkedList<Column> columns = new LinkedList<Column>();
				List<CommonTree> childTrees0 = tree.getChildren();
				LinkedList<String> colNameStrings = new LinkedList<String>();
				LinkedList<String> tblNameStrings = new LinkedList<String>();
				LinkedList<Expr> exprs = new LinkedList<Expr>();
				String tbl_name = null;
				for (CommonTree child0 : childTrees0)
				{
					// tbl_name -> name -> ID
					if (child0.getType() == FatwormLexer.ID)
					{
						tbl_name = child0.toString().toLowerCase();
						t = DataBase.getDataBase().getTable(tbl_name);
					}
					// update_pair -> UPDATE_PAIR col_name value
					if (child0.getType() == FatwormLexer.UPDATE_PAIR)
					{
						List<CommonTree> childTrees1 = child0.getChildren();
						CommonTree child1 = childTrees1.get(0);
						// col_name -> tbl_name '.'^ name (will not happen)
						// col_name -> name -> ID
						String name = null;
						if (child1.getType() == FatwormLexer.ID)
						{
							name = child1.toString().toLowerCase();
							column = t.getColumns().get(name);
							colNameStrings.add(name);
							tblNameStrings.add(tbl_name);
						}
						columns.add(column);
						child1 = childTrees1.get(1);
						Expr expr = getExpr(child1);
						exprs.add(expr);
					}
					// where_clause -> WHERE^ where_condition
					if (child0.getType() == FatwormLexer.WHERE)
					{
						pred = traverseWhere((CommonTree) child0.getChild(0));
					}
				}
				Schema s = new Schema(columns, tblNameStrings, colNameStrings);
				FatwormDB.planner().createUpdatePlan(t, pred, exprs, s);
			}
			// statement -> index_statement -> CREATE_INDEX index_name tbl_name
			// col_name
			if (tree.getType() == FatwormLexer.CREATE_INDEX)
			{
				List<CommonTree> childTrees0 = tree.getChildren();
				CommonTree child0 = (CommonTree) childTrees0.get(0);
				String idx_name = null;
				String tbl_name = null;
				String name = null;
				// index_name -> name -> ID
				if (child0.getType() == FatwormLexer.ID)
				{
					idx_name = child0.toString().toLowerCase();
				}
				child0 = (CommonTree) childTrees0.get(1);
				// tbl_name -> name -> ID
				if (child0.getType() == FatwormLexer.ID)
				{
					tbl_name = child0.toString().toLowerCase();
				}
				child0 = (CommonTree) childTrees0.get(2);
				// col_name -> tbl_name '.'^ name (will not happen)
				// col_name -> name -> ID
				if (child0.getType() == FatwormLexer.ID)
				{
					name = child0.toString().toLowerCase();
				}
				Table table = DataBase.getDataBase().getTable(tbl_name);
				table.createIndex(idx_name, name, false);
			}
			// statement -> index_statement -> CREATE_UNIQUE_INDEX index_name
			// tbl_name col_name
			if (tree.getType() == FatwormLexer.CREATE_UNIQUE_INDEX)
			{
				List<CommonTree> childTrees0 = tree.getChildren();
				CommonTree child0 = (CommonTree) childTrees0.get(0);
				String idx_name = null;
				String tbl_name = null;
				String name = null;
				// index_name -> name -> ID
				if (child0.getType() == FatwormLexer.ID)
				{
					idx_name = child0.toString().toLowerCase();
				}
				child0 = (CommonTree) childTrees0.get(1);
				// tbl_name -> name -> ID
				if (child0.getType() == FatwormLexer.ID)
				{
					tbl_name = child0.toString().toLowerCase();
				}
				child0 = (CommonTree) childTrees0.get(2);
				// col_name -> tbl_name '.'^ name (will not happen)
				// col_name -> name -> ID
				if (child0.getType() == FatwormLexer.ID)
				{
					name = child0.toString().toLowerCase();
				}
				Table table = DataBase.getDataBase().getTable(tbl_name);
				table.createIndex(idx_name, name, true);
			}
			// statement -> index_statement -> DROP_INDEX index_name tbl_name
			if (tree.getType() == FatwormLexer.DROP_INDEX)
			{
				List<CommonTree> childTrees0 = tree.getChildren();
				CommonTree child0 = (CommonTree) childTrees0.get(0);
				String idx_name = null;
				String tbl_name = null;
				// index_name -> name -> ID
				if (child0.getType() == FatwormLexer.ID)
				{
					idx_name = child0.toString().toLowerCase();
				}
				child0 = (CommonTree) childTrees0.get(1);
				// tbl_name -> name -> ID
				if (child0.getType() == FatwormLexer.ID)
				{
					tbl_name = child0.toString().toLowerCase();
				}
				Table table = DataBase.getDataBase().getTable(tbl_name);
				table.dropIndex(idx_name);
			}
			// statement -> select_statement -> SELECT select_expr*
			// select_suffix*
			// statement -> select_statement -> SELECT_DISTINCT select_expr*
			// select_suffix*
			if (tree.getType() == FatwormLexer.SELECT || tree.getType() == FatwormLexer.SELECT_DISTINCT)
			{
				FatwormDB.planner().setPlan(traverseSelect(tree));
			}
		} catch (FatwormException e)
		{
			e.printStackTrace();
		}
	}

	// for insert and update
	private Type getValue(CommonTree valueTree, Column c) throws FatwormException
	{
		if (valueTree.getType() == FatwormLexer.NULL)
		{
			if (c.autoIncrement()) return null;
			Type type = c.getType().clone();
			type.setNULL();
			return type;
		}
		if (valueTree.getType() == FatwormLexer.DEFAULT)
		{
			return null;
		}
		if (c.notNull() && c.getType().isNULL())
		{
			throw new FatwormException("Insert null on NOTNULL colum");
		}
		if (c.autoIncrement() && c.getType() instanceof INT)
		{
			Expr expr = getExpr(valueTree);
			Type type = expr.getValue(null);
			if (type instanceof INT)
			{
				if (((INT) type).getINT() > c.getAutoIncrementValue())
				{
					c.setAutoIncrementValue(((INT) type).getINT() + 1);
					return new INT(((INT) type).getINT());
				}
				if (((INT) type).getINT() < c.getAutoIncrementValue())
				{
					c.setAutoIncrementValue(c.getAutoIncrementValue() - 1);
					return new INT(((INT) type).getINT());
				}
			}
		}
		if (c.getType() instanceof BOOLEAN)
		{
			return new BOOLEAN(valueTree.toString().toLowerCase());
		}
		if (c.getType() instanceof CHAR)
		{
			return new CHAR(((CHAR) c.getType()).getCapacity(), valueTree.toString());
		}
		if (c.getType() instanceof DATETIME)
		{
			return new DATETIME(valueTree.toString().toLowerCase());
		}
		if (c.getType() instanceof TIMESTAMP)
		{
			return new TIMESTAMP(valueTree.toString().toLowerCase());
		}
		if (c.getType() instanceof VARCHAR)
		{
			return new VARCHAR(((VARCHAR) c.getType()).getCapacity(), valueTree.toString());
		}
		if (c.getType() instanceof DECIMAL && (valueTree.getType() == FatwormLexer.INTEGER_LITERAL || valueTree.getType() == FatwormLexer.FLOAT_LITERAL))
		{
			return new DECIMAL(((DECIMAL) (c.getType())).getEffective(), ((DECIMAL) (c.getType())).getDecimalSetting(), valueTree.toString());
		}
		Expr expr = getExpr(valueTree);
		Type type = expr.getValue(null);
		BigDecimal bd = null;
		if (type instanceof INT)
		{
			bd = new BigDecimal(((INT) type).getINT());
		}
		if (type instanceof FLOAT)
		{
			bd = new BigDecimal(((FLOAT) type).getFLOAT());
		}
		if (type instanceof DECIMAL)
		{
			bd = ((DECIMAL) type).getDECIMAL();
		}
		if (c.getType() instanceof INT)
		{
			return new INT(bd.intValue());
		}
		if (c.getType() instanceof DECIMAL)
		{
			return new DECIMAL(((DECIMAL) c.getType()).getEffective(), ((DECIMAL) c.getType()).getDecimalSetting(), bd.toString());
		}
		if (c.getType() instanceof FLOAT)
		{
			return new FLOAT(bd.floatValue());
		}
		return null;
	}

	private Expr getExpr(CommonTree tree) throws FatwormException
	{
		if (tree.getType() == 105 || tree.getType() == 108 || tree.getType() == 109 || (tree.getType() == 111 && tree.getChildCount() == 2) || tree.getType() == 113) return new BinaryExpr(getExpr((CommonTree) tree.getChild(0)), getExpr((CommonTree) tree.getChild(1)), tree.toString());
		if (tree.getType() == 111 && tree.getChildCount() == 1)
		{
			return new NegativeExpr(getExpr((CommonTree) tree.getChild(0)));
		}
		if (tree.getType() == FatwormLexer.SELECT && tree.getChildCount() == 1)
		{
			return getExpr((CommonTree) tree.getChild(0));
		}
		if (tree.getType() == FatwormLexer.SELECT || tree.getType() == FatwormLexer.SELECT_DISTINCT)
		{
			return new SubqueryExpr(traverseSelect(tree));
		}
		if (tree.getType() == FatwormLexer.SUM || tree.getType() == FatwormLexer.MAX || tree.getType() == FatwormLexer.MIN || tree.getType() == FatwormLexer.COUNT || tree.getType() == FatwormLexer.AVG)
		{
			if (tree.getChild(0).getType() == 112)
			{
				return new FuncExpr(tree.toString().toLowerCase(), tree.getChild(0).getChild(0).toString().toLowerCase(), tree.getChild(0).getChild(1).toString().toLowerCase());
			}
			return new FuncExpr(tree.toString().toLowerCase(), tree.getChild(0).toString().toLowerCase());
		}
		if (tree.getType() == 112)
		{
			return new FieldExpr(tree.getChild(0).toString().toLowerCase(), tree.getChild(1).toString().toLowerCase());
		}
		if (tree.getType() == FatwormLexer.ID)
		{
			return new FieldExpr(tree.toString().toLowerCase());
		}
		if (tree.getType() == FatwormLexer.INTEGER_LITERAL)
		{
			return new ConstantExpr(new INT(tree.toString()));
		}
		if (tree.getType() == FatwormLexer.FLOAT_LITERAL)
		{
			return new ConstantExpr(new FLOAT(tree.toString()));
		}
		if (tree.getType() == FatwormLexer.STRING_LITERAL)
		{
			return new ConstantExpr(new CHAR(tree.toString().length() - 2, tree.toString()));
		}
		if (tree.getType() == FatwormLexer.TRUE)
		{
			return new ConstantExpr(new BOOLEAN(true));
		}
		if (tree.getType() == FatwormLexer.FALSE)
		{
			return new ConstantExpr(new BOOLEAN(false));
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	private Plan traverseSelect(CommonTree tree) throws FatwormException
	{
		boolean distinct = false;
		if (tree.getType() == FatwormLexer.SELECT) distinct = false;
		if (tree.getType() == FatwormLexer.SELECT_DISTINCT) distinct = true;
		List<CommonTree> childTrees0 = tree.getChildren();
		LinkedList<Plan> fromPlans = new LinkedList<Plan>();// before from
		Plan fromPlan = null;// after from
		// find FROM tbl_ref*
		for (CommonTree child0 : childTrees0)
		{
			if (child0.getType() == FatwormLexer.FROM)
			{
				List<CommonTree> childTrees1 = child0.getChildren();
				for (CommonTree child1 : childTrees1)
				{
					if (child1.getType() == FatwormLexer.ID)
					{
						fromPlans.add(new TablePlan(DataBase.getDataBase().getTable(child1.toString().toLowerCase())));
					}
					if (child1.getType() == FatwormLexer.AS)
					{
						CommonTree leftChild = (CommonTree) child1.getChild(0);
						CommonTree rightChild = (CommonTree) child1.getChild(1);
						String newName = rightChild.toString().toLowerCase();
						if (leftChild.getType() == FatwormLexer.ID)
						{
							Plan leftPlan = new TablePlan(DataBase.getDataBase().getTable(leftChild.toString().toLowerCase()));
							fromPlans.add(new RenamePlan(leftPlan, newName));
						}
						else
						{
							Plan leftPlan = traverseSelect(leftChild);
							fromPlans.add(new RenamePlan(leftPlan, newName));
						}
					}
				}
				Plan lastPlan = fromPlans.get(0);
				for (int i = 1; i < fromPlans.size(); ++i)
				{
					lastPlan = new ProductPlan(lastPlan, fromPlans.get(i));
				}
				fromPlan = lastPlan;
				break;
			}
		}
		// from plan might be null if there is no from clause
		// find where
		Plan wherePlan;
		Predicate wherePred = null;
		for (CommonTree child0 : childTrees0)
		{
			if (child0.getType() == FatwormLexer.WHERE)
			{
				wherePred = traverseWhere((CommonTree) child0.getChild(0));
			}
		}
		if (wherePred != null)
			wherePlan = new SelectPlan(fromPlan, wherePred);
		else
			// there is no where clause
			wherePlan = fromPlan;
		// where plan might be null if there is no from clause
		// find group
		boolean haveGroup = false;
		String groupTableName = null;
		String groupColName = null;
		List<Expr> exprs = new ArrayList<Expr>();
		List<String> asNames = new ArrayList<String>();
		for (CommonTree child0 : childTrees0)
		{
			if (child0.getType() == FatwormLexer.GROUP)
			{
				haveGroup = true;
				CommonTree child1 = (CommonTree) child0.getChild(0);
				if (child1.getType() == 112)
				{
					groupTableName = child1.getChild(0).toString().toLowerCase();
					groupColName = child1.getChild(1).toString().toLowerCase();
				}
				if (child1.getType() == FatwormLexer.ID)
				{
					groupColName = child1.toString().toLowerCase();
				}
				break;
			}
		}
		// select
		boolean hasFunc = false;
		for (CommonTree child0 : childTrees0)
		{
			if (child0.getType() == FatwormLexer.FROM) break;
			if (child0.getType() == 108 && child0.getChildCount() == 0)
			{
				Schema schema = wherePlan.schema();
				for (int i = 0; i < schema.getColumns().size(); ++i)
				{
					exprs.add(new FieldExpr(schema.getTableName(i), schema.getName(i)));
					asNames.add(null);
				}
				continue;
			}
			if (child0.getType() == FatwormLexer.AS)
			{
				Expr e = getExpr((CommonTree) child0.getChild(0));
				if (e.hasFunc()) hasFunc = true;
				exprs.add(e);
				asNames.add(child0.getChild(1).toString().toLowerCase());
				if (e instanceof FieldExpr && groupColName != null && groupColName.equals(child0.getChild(1).toString().toLowerCase()))
				{
					groupTableName = ((FieldExpr) e).getTableName();
					groupColName = ((FieldExpr) e).getFieldName();
				}
			}
			if (child0.getType() != FatwormLexer.AS && (child0.getType() != 108 || child0.getChildCount() != 0) && child0.getType() != FatwormLexer.FROM)
			{
				Expr e = getExpr((CommonTree) child0);
				if (e.hasFunc()) hasFunc = true;
				exprs.add(e);
				asNames.add(null);
			}
		}
		Plan groupPlan = null;
		Plan extendPlan = null;
		Plan projectPlan = null;
		if (haveGroup)
		{
			boolean hasHaving = false;
			for (CommonTree child0 : childTrees0)
			{
				if (child0.getType() == FatwormLexer.HAVING)
				{
					hasHaving = true;
					break;
				}
			}
			if (asNames.contains(groupColName) || groupColName == null)
			{
				groupColName = ((FieldExpr) (exprs.get(asNames.lastIndexOf(groupColName)))).getFieldName();
				groupTableName = ((FieldExpr) (exprs.get(asNames.lastIndexOf(groupColName)))).getTableName();
			}
			if (!hasHaving)
			{
				for (Expr expr : exprs)
				{
					if (!(expr instanceof FieldExpr) && !(expr instanceof FuncExpr))
					{
						groupPlan = new GroupPlan(groupTableName, groupColName, wherePlan);
					}
				}
				if (groupPlan == null) groupPlan = new GroupPlan(groupTableName, groupColName, wherePlan, exprs);
			}
			if (groupPlan == null) groupPlan = new GroupPlan(groupTableName, groupColName, wherePlan);
			extendPlan = new ExtendPlan(groupPlan, exprs, asNames);
			// having
			Predicate havingPred = null;
			for (CommonTree child0 : childTrees0)
			{
				if (child0.getType() == FatwormLexer.HAVING)
				{
					havingPred = traverseWhere((CommonTree) child0.getChild(0));
					extendPlan = new SelectPlan(extendPlan, havingPred);
					break;
				}
			}
		}
		else
		{
			if (hasFunc)
			{
				for (Expr expr : exprs)
				{
					if (!(expr instanceof FieldExpr) && !(expr instanceof FuncExpr))
					{
						groupPlan = new GroupPlan(null, null, wherePlan);
					}
				}
				if (groupPlan == null) groupPlan = new GroupPlan(null, null, wherePlan, exprs);
			}
			else
				groupPlan = wherePlan;
			extendPlan = new ExtendPlan(groupPlan, exprs, asNames);
		}
		// order
		Plan sortPlan = null;
		List<String> cols = new LinkedList<String>();
		List<String> tbls = new LinkedList<String>();
		List<Boolean> as = new LinkedList<Boolean>();
		for (CommonTree child0 : childTrees0)
		{
			if (child0.getType() == FatwormLexer.ORDER)
			{
				List<CommonTree> childTrees1 = child0.getChildren();
				for (CommonTree child1 : childTrees1)
				{
					if (child1.getType() == 112)
					{
						cols.add(child1.getChild(1).toString().toLowerCase());
						tbls.add(child1.getChild(0).toString().toLowerCase());
						as.add(new Boolean(true));
					}
					if (child1.getType() == FatwormLexer.ASC)
					{
						if (child1.getChild(0).getType() == 112)
						{
							cols.add(child1.getChild(0).getChild(1).toString().toLowerCase());
							tbls.add(child1.getChild(0).getChild(0).toString().toLowerCase());
							as.add(new Boolean(true));
						}
						else
						{
							cols.add(child1.getChild(0).toString().toLowerCase());
							tbls.add(null);
							as.add(new Boolean(true));
						}
					}
					if (child1.getType() == FatwormLexer.DESC)
					{
						if (child1.getChild(0).getType() == 112)
						{
							cols.add(child1.getChild(0).getChild(1).toString().toLowerCase());
							tbls.add(child1.getChild(0).getChild(0).toString().toLowerCase());
							as.add(new Boolean(false));
						}
						else
						{
							cols.add(child1.getChild(0).toString().toLowerCase());
							tbls.add(null);
							as.add(new Boolean(false));
						}
					}
					if (child1.getType() == FatwormLexer.ID)
					{
						cols.add(child1.toString().toLowerCase());
						tbls.add(null);
						as.add(new Boolean(true));
					}
				}
			}
		}
		if (cols.size() == 0)
			sortPlan = extendPlan;
		else
			sortPlan = new SortPlan(extendPlan, as, cols, tbls);
		// project
		projectPlan = new ProjectPlan(sortPlan, exprs, asNames);
		// distinct
		Plan distinctPlan;
		if (distinct)
		{
			distinctPlan = new DistinctPlan(projectPlan);
		}
		else
		{
			distinctPlan = projectPlan;
		}
		return distinctPlan;
	}

	private Predicate traverseWhere(CommonTree tree) throws FatwormException
	{
		if (tree.getType() == FatwormLexer.OR)
		{
			CommonTree lftChild = (CommonTree) tree.getChild(0);
			CommonTree rgtChild = (CommonTree) tree.getChild(1);
			return new OrPred(traverseWhere(lftChild), traverseWhere(rgtChild));
		}
		if (tree.getType() == FatwormLexer.AND)
		{
			CommonTree lftChild = (CommonTree) tree.getChild(0);
			CommonTree rgtChild = (CommonTree) tree.getChild(1);
			return new AndPred(traverseWhere(lftChild), traverseWhere(rgtChild));
		}
		if (tree.getType() >= 114 && tree.getType() <= 119)
		{
			CommonTree lftChild = (CommonTree) tree.getChild(0);
			CommonTree rgtChild = (CommonTree) tree.getChild(1);
			return new CopPred(getExpr(lftChild), getExpr(rgtChild), tree.getText());
		}
		if (tree.getType() == FatwormLexer.NOT_EXISTS)
		{
			CommonTree lftChild = (CommonTree) tree.getChild(0);
			return new NotExistsPred(traverseSelect(lftChild));
		}
		if (tree.getType() == FatwormLexer.EXISTS)
		{
			CommonTree lftChild = (CommonTree) tree.getChild(0);
			return new ExistsPred(traverseSelect(lftChild));
		}
		if (tree.getType() == FatwormLexer.IN)
		{
			CommonTree lftChild = (CommonTree) tree.getChild(0);
			CommonTree rgtChild = (CommonTree) tree.getChild(1);
			return new InPred(getExpr(lftChild), traverseSelect(rgtChild));
		}
		if (tree.getType() == FatwormLexer.ANY)
		{
			CommonTree lftChild = (CommonTree) tree.getChild(0);
			CommonTree rgtChild = (CommonTree) tree.getChild(1);
			CommonTree thirdChild = (CommonTree) tree.getChild(2);
			return new AnyPred(getExpr(lftChild), rgtChild.getText(), traverseSelect(thirdChild));
		}
		if (tree.getType() == FatwormLexer.ALL)
		{
			CommonTree lftChild = (CommonTree) tree.getChild(0);
			CommonTree rgtChild = (CommonTree) tree.getChild(1);
			CommonTree thirdChild = (CommonTree) tree.getChild(2);
			return new AllPred(getExpr(lftChild), rgtChild.getText(), traverseSelect(thirdChild));
		}
		return null;
	}

	// data_type -> ?
	@SuppressWarnings("unchecked")
	private Type getType(CommonTree tree)
	{
		// data_type -> INT
		if (tree.getType() == FatwormLexer.INT)
		{
			return new INT();
		}
		// data_type -> FLOAT
		if (tree.getType() == FatwormLexer.FLOAT)
		{
			return new FLOAT();
		}
		// data_type -> CHAR INTEGER_LITERAL
		if (tree.getType() == FatwormLexer.CHAR)
		{
			CommonTree child2 = (CommonTree) tree.getChild(0);
			String cap = child2.getText();
			return new CHAR(Integer.parseInt(cap));
		}
		// data_type -> DATETIME
		if (tree.getType() == FatwormLexer.DATETIME)
		{
			return new DATETIME();
		}
		// data_type -> BOOLEAN
		if (tree.getType() == FatwormLexer.BOOLEAN)
		{
			return new BOOLEAN();
		}
		// data_type -> DECIMAL INTEGER_LITERAL INTEGER_LITERAL
		if (tree.getType() == FatwormLexer.DECIMAL)
		{
			List<CommonTree> childTrees2 = tree.getChildren();
			// data_type -> DECIMAL INTEGER_LITERAL INTEGER_LITERAL
			if (childTrees2.size() == 2)
			{
				CommonTree child2 = childTrees2.get(0);
				String cap = child2.getText();
				child2 = childTrees2.get(1);
				String dec = child2.getText();
				return new DECIMAL(Integer.parseInt(cap), Integer.parseInt(dec));
			}
			else
			// data_type -> DECIMAL INTEGER_LITERAL
			{
				CommonTree child2 = childTrees2.get(0);
				String cap = child2.getText();
				return new DECIMAL(Integer.parseInt(cap));
			}
		}
		// data_type -> TIMESTAMP
		if (tree.getType() == FatwormLexer.TIMESTAMP)
		{
			return new TIMESTAMP();
		}
		// data_type -> VARCHAR INTEGER_LITERAL
		if (tree.getType() == FatwormLexer.VARCHAR)
		{
			CommonTree child2 = (CommonTree) tree.getChild(0);
			String cap = child2.getText();
			return new VARCHAR(Integer.parseInt(cap));
		}
		return null;
	}
}
