package fatworm.expr;

import java.util.Stack;

import fatworm.database.DataBase;
import fatworm.database.Schema;
import fatworm.scan.Scan;
import fatworm.types.Type;

public class FuncExpr implements Expr
{
	public String func;

	public String table = null;

	public String name;

	public FuncExpr(String f, String n)
	{
		func = f;
		table = null;
		name = n;
	}

	public FuncExpr(String f, String t, String n)
	{
		func = f;
		table = t;
		name = n;
	}

	@Override
	public Type getValue(Scan s)
	{
		int size = DataBase.getDataBase().getOpenedScans().size();
		Stack<Scan> scanStack = DataBase.getDataBase().getOpenedScans();
		boolean flag = true;
		for (int i = size - 1; i >= 0; i--)
		{
			Scan scan = scanStack.get(i);
			if (scan != s && flag)
			{
				continue;
			}
			flag = false;
			Type ret;
			if (table == null)
				ret = scan.getVal(func + "(" + name + ")");
			else
				ret = scan.getVal(table, func + "(" + name + ")");
			if (ret != null) return ret;
		}
		return null;
	}

	@Override
	public Type getType(Schema s)
	{
		int ret = -1;
		if (table == null)
		{
			for (int i = 0; i < s.getColumns().size(); ++i)
			{
				if ((func + "(" + name + ")").equals(s.getNames().get(i)))
				{
					ret = i;
					break;
				}
			}
		}
		else
		{
			for (int i = 0; i < s.getColumns().size(); ++i)
			{
				if (table.equals(s.getTableName(i)) && (func + "(" + name + ")").equals(s.getNames().get(i)))
				{
					ret = i;
					break;
				}
			}
		}
		if (ret == -1)
		{
			try
			{
				throw new Exception("can't find this field in the select-expr");
			} catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		return s.getColumns().get(ret).getType();
	}

	@Override
	public boolean hasFunc()
	{
		return true;
	}

	public String toString()
	{
		if (table == null)
			return func + "(" + name + ")";
		else
			return func + "(" + name + ")";
	}

	@Override
	public boolean notNeed(Schema s)
	{
		return false;
	}

	@Override
	public boolean isConst()
	{
		return false;
	}
}
