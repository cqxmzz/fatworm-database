package fatworm.scan;

import java.util.LinkedList;
import java.util.List;

import fatworm.database.Schema;
import fatworm.record.Record;
import fatworm.types.Type;

public class ProjectScan implements Scan
{
	Scan scan;

	List<Integer> places;

	Schema schema;

	public ProjectScan(Scan s, List<Integer> p, Schema sche)
	{
		scan = s;
		places = p;
		schema = sche;
	}

	@Override
	public void beforeFirst()
	{
		scan.beforeFirst();
	}

	@Override
	public boolean next()
	{
		return scan.next();
	}

	@Override
	public void close()
	{
		scan.close();
	}

	@Override
	public Type getVal(String fldname)
	{
		List<String> fldname_lst = schema.getNames();
		int i = fldname_lst.indexOf(fldname);
		if (i == -1) return null;
		return getVal(i);
	}

	@Override
	public int getRecordLength()
	{
		return places.size();
	}

	@Override
	public Type getVal(int i)
	{
		return scan.getVal(places.get(i));
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
			if (schema.getName(i).equals(fldname) && schema.getTableName(i).equals(tblname)) return getVal(i);
		}
		return null;
	}
}
