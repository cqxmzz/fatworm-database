package fatworm.database;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

import fatworm.expr.Expr;
import fatworm.expr.FieldExpr;

@SuppressWarnings("serial")
public class Schema implements Serializable
{
	private LinkedList<Column> columns;

	private LinkedList<String> tableNames;

	private LinkedList<String> names;
	
	int length = -1;

	public Schema()
	{
		columns = new LinkedList<Column>();
		tableNames = new LinkedList<String>();
		names = new LinkedList<String>();
	}

	public Schema(LinkedList<Column> cols, LinkedList<String> t, LinkedList<String> n)
	{
		columns = cols;
		tableNames = t;
		names = n;
	}

	public LinkedList<Column> getColumns()
	{
		return columns;
	}

	public void setColumns(LinkedList<Column> columns)
	{
		this.columns = columns;
	}

	public Column getColumn(int i)
	{
		return columns.get(i);
	}

	public String getName(int i)
	{
		return names.get(i);
	}

	public LinkedList<String> getNames()
	{
		return names;
	}

	public LinkedList<String> getTableNames()
	{
		return tableNames;
	}

	public String getTableName(int i)
	{
		return tableNames.get(i);
	}

	public void setTableName(int i, String s)
	{
		tableNames.set(i, s);
	}

	public int getIndex(String fldname, String tblname)
	{
		if (tblname == null) return getIndex(fldname);
		for (int i = 0; i < columns.size(); ++i)
		{
			String tbln = getTableName(i);
			String coln = getName(i);
			if (tbln.equals(tblname) && fldname.equals(coln))
			{
				return i;
			}
		}
		return -1;
	}

	public int getIndex(String fldName)
	{
		return getNames().indexOf(fldName);
	}

	public void add(Column c, String s1, String s2)
	{
		length = -1;
		columns.add(c);
		tableNames.add(s1);
		names.add(s2);
	}

	public Schema add(Schema schema)
	{
		Schema ret = new Schema();
		for (int i = 0; i < getColumns().size(); ++i)
		{
			ret.add(getColumns().get(i), getTableName(i), getName(i));
		}
		for (int j = 0; j < schema.getColumns().size(); ++j)
		{
			boolean flag = false;
			for (int i = 0; i < getColumns().size(); ++i)
			{
				if (tableNames.get(i).equals(schema.tableNames.get(j)) && names.get(i).equals(schema.names.get(j))) flag = true;
			}
			if (!flag)
			{
				ret.add(schema.getColumns().get(j), schema.getTableName(j), schema.getName(j));
			}
		}
		return ret;
	}

	public Schema rename(String name)
	{
		Schema ret = new Schema();
		for (int i = 0; i < getColumns().size(); ++i)
		{
			ret.add(getColumns().get(i), name, getName(i));
		}
		return ret;
	}

	public List<Integer> project(List<Expr> e, List<String> ns)
	{
		List<Integer> ret = new LinkedList<Integer>();
		for (int i = 0; i < ns.size(); ++i)
		{
			Expr expr = e.get(i);
			String name = ns.get(i);
			for (int j = columns.size() - 1; j >= 0; --j)
			{
				if (name == null)
				{
					if (expr instanceof FieldExpr)
					{
						if (tableNames.get(j) == null || ((FieldExpr) expr).getTableName() == null)
						{
							if (names.get(j).equals(((FieldExpr) expr).getFieldName()))
							{
								ret.add(j);
								break;
							}
						}
						else
						{
							if (names.get(j).equals(((FieldExpr) expr).getFieldName()) && tableNames.get(j).equals(((FieldExpr) expr).getTableName()))
							{
								ret.add(j);
								break;
							}
						}
					}
					else
					{
						if (names.get(j).equals(expr.toString()))
						{
							ret.add(j);
							break;
						}
					}
				}
				else
				{
					if (name.equals(names.get(j)))
					{
						ret.add(j);
						break;
					}
				}
			}
		}
		return ret;
	}

	public Schema extend(List<Expr> e, List<String> names)
	{
		Schema ret = new Schema();
		for (int i = 0; i < getColumns().size(); ++i)
		{
			ret.add(getColumns().get(i), getTableName(i), getName(i));
		}
		for (int i = 0; i < names.size(); ++i)
		{
			Expr expr = e.get(i);
			Column column = new Column(expr.getType(this), false, false, false, null);
			if (expr instanceof FieldExpr)
			{
				if (names.get(i) != null)
					ret.add(column, ((FieldExpr) expr).getTableName(), names.get(i));
				else
					ret.add(column, ((FieldExpr) expr).getTableName(), ((FieldExpr) expr).getFieldName());
			}
			else
			{
				if (names.get(i) != null)
					ret.add(column, null, names.get(i));
				else
					ret.add(column, null, expr.toString());
			}
		}
		return ret;
	}

	public int length()
	{
		if (length != -1)
			return length;
		int temp = 0;
		for (Column c : getColumns())
		{
			temp += (c.getType().length() + 5);
		}
		length = temp;
		return temp;
	}
}
