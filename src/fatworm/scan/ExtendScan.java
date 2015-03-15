package fatworm.scan;

import java.util.LinkedList;
import java.util.List;

import fatworm.database.DataBase;
import fatworm.database.Record;
import fatworm.database.Schema;
import fatworm.expr.Expr;
import fatworm.types.Type;

public class ExtendScan implements Scan
{
	Scan scan;

	List<Expr> exprs;

	Schema schema;

	public ExtendScan(Scan s, List<Expr> es, Schema sc)
	{
		scan = s;
		DataBase.getDataBase().getOpenedScans().push(scan);
		exprs = es;
		schema = sc;
	}

	@Override
	public void beforeFirst()
	{
		scan.beforeFirst();
	}

	@Override
	public boolean next()
	{
		if (scan.next())
		{
			return true;
		}
		return false;
	}

	@Override
	public void close()
	{
		scan.close();
		DataBase.getDataBase().getOpenedScans().pop();
	}

	@Override
	public Type getVal(String fldname)
	{
		int n = schema.getColumns().size();
		for (int i = n - 1; i >= 0; --i)
		{
			if (schema.getName(i).equals(fldname)) return getVal(i);
		}
		return null;
	}

	@Override
	public int getRecordLength()
	{
		return schema.getColumns().size();
	}

	@Override
	public Type getVal(int i)
	{
		int n = scan.getRecordLength();
		if (i < n)
			return scan.getVal(i);
		else
			return exprs.get(i - n).getValue(scan);
	}

	@Override
	public Type getFirstVal()
	{
		return getVal(0);
	}

	@Override
	public Record getRecord()
	{
		LinkedList<Type> values = new LinkedList<Type>();
		for (int i = 0; i < getRecordLength(); ++i)
		{
			values.add(getVal(i));
		}
		return new Record(values, schema);
	}

	@Override
	public Type getVal(String tblname, String fldname)
	{
		if (tblname == null) return getVal(fldname);
		int n = schema.getColumns().size();
		for (int i = n - 1; i >= 0; --i)
		{
			if (schema.getTableName(i) != null && schema.getTableName(i).equals(tblname) && schema.getName(i).equals(fldname)) return getVal(i);
		}
		return null;
	}
}
