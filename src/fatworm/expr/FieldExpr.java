package fatworm.expr;

import java.util.Stack;

import fatworm.database.DataBase;
import fatworm.database.Schema;
import fatworm.scan.Scan;
import fatworm.types.Type;

public class FieldExpr implements Expr
{
	public String tableName;

	public String fieldName;

	public FieldExpr(String field)
	{
		tableName = null;
		fieldName = field;
	}

	public FieldExpr(String table, String field)
	{
		tableName = table;
		fieldName = field;
	}

	@Override
	public Type getValue(Scan s)
	{
		Stack<Scan> scanStack = DataBase.getDataBase().getOpenedScans();
		if (s == null && !scanStack.isEmpty()) s = scanStack.peek();
		int size = scanStack.size();
		boolean flag = true;
		for (int i = size - 1; i >= 0; i--)
		{
			Scan scan = scanStack.get(i);
			if (scan != s && flag)
			{
				continue;
			}
			flag = false;
			if (tableName == null)
			{
				Type ret = scan.getVal(fieldName);
				if (ret != null) return ret;
			}
			else
			{
				Type ret = scan.getVal(tableName, fieldName);
				if (ret != null) return ret;
			}
		}
		return null;
	}

	@Override
	public Type getType(Schema s)
	{
		int ret = -1;
		for (int i = 0; i < s.getColumns().size(); ++i)
		{
			if (tableName == null)
			{
				if (fieldName.equals(s.getNames().get(i)))
				{
					ret = i;
					break;
				}
			}
			else
			{
				if (fieldName.equals(s.getNames().get(i)) && tableName.equals(s.getTableName(i)))
				{
					ret = i;
					break;
				}
			}
		}
		if (ret == -1)
		{
			int size = DataBase.getDataBase().getOpenedScans().size();
			Stack<Scan> scanStack = DataBase.getDataBase().getOpenedScans();
			for (int i = size - 1; i >= 0; i--)
			{
				Scan scan = scanStack.get(i);
				if (tableName == null)
				{
					Type re = scan.getVal(fieldName);
					if (re != null)
					{
						Type r = re.clone();
						r.setNULL();
						return r;
					}
				}
				else
				{
					Type re = scan.getVal(tableName, fieldName);
					if (re != null)
					{
						Type r = re.clone();
						r.setNULL();
						return r;
					}
				}
			}
			return null;
		}
		return s.getColumns().get(ret).getType();
	}

	@Override
	public boolean hasFunc()
	{
		return false;
	}

	public String getTableName()
	{
		return tableName;
	}

	public String getFieldName()
	{
		return fieldName;
	}

	public String toString()
	{
		if (tableName == null)
		{
			return fieldName;
		}
		else
		{
			return tableName + "." + fieldName;
		}
	}

	@Override
	public boolean notNeed(Schema s)
	{
		for (int i = 0; i < s.getColumns().size(); ++i)
		{
			if (tableName != null && tableName.equals(s.getTableName(i)))
			{
				return false;
			}
			if (tableName == null && fieldName != null && fieldName.equals(s.getName(i)))
			{
				return false;
			}
		}
		return true;
	}

	@Override
	public boolean isConst()
	{
		return false;
	}
}
