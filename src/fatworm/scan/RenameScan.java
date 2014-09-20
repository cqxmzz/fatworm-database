package fatworm.scan;

import fatworm.record.Record;
import fatworm.types.Type;

public class RenameScan implements Scan
{
	Scan scan;

	String name;

	public RenameScan(Scan s, String name2)
	{
		scan = s;
		name = name2;
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
		return scan.getVal(fldname);
	}

	@Override
	public int getRecordLength()
	{
		return scan.getRecordLength();
	}

	@Override
	public Type getVal(int i)
	{
		return scan.getVal(i);
	}

	@Override
	public Type getFirstVal()
	{
		return scan.getFirstVal();
	}

	@Override
	public Record getRecord()
	{
		return scan.getRecord().rename(name);
	}

	@Override
	public Type getVal(String tblname, String fldname)
	{
		if (tblname == null)
		{
			return getVal(fldname);
		}
		if (tblname.equals(name)) return scan.getVal(fldname);
		return null;
	}
}
