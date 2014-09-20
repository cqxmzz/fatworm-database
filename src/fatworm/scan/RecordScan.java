package fatworm.scan;

import fatworm.record.Record;
import fatworm.types.Type;

public class RecordScan implements Scan
{
	boolean beforeFirst = true;

	private Record record;

	public RecordScan(Record r)
	{
		record = r;
	}

	@Override
	public void beforeFirst()
	{
		beforeFirst = true;
	}

	@Override
	public boolean next()
	{
		if (beforeFirst)
		{
			beforeFirst = false;
			return true;
		}
		return false;
	}

	@Override
	public void close()
	{
		beforeFirst = false;
	}

	@Override
	public Type getVal(String fldname)
	{
		int i = record.schema().getNames().indexOf(fldname);
		if (i > 0) return record.getValue(i);
		return null;
	}

	public Record getRecord()
	{
		return record;
	}

	@Override
	public Type getVal(String tblname, String fldname)
	{
		return getVal(fldname);
	}

	@Override
	public Type getFirstVal()
	{
		return record.getValue(0);
	}

	@Override
	public int getRecordLength()
	{
		return record.getValues().size();
	}

	@Override
	public Type getVal(int i)
	{
		return record.getValue(i);
	}
}
