package fatworm.scan;

import fatworm.database.DataBase;
import fatworm.pred.Predicate;
import fatworm.record.Record;
import fatworm.types.Type;

public class SelectScan implements UpdateScan
{
	private Scan scan;

	private Predicate predicate;

	public SelectScan(Scan s, Predicate pred)
	{
		scan = s;
		DataBase.getDataBase().getOpenedScans().push(scan);
		predicate = pred;
	}

	@Override
	public void beforeFirst()
	{
		scan.beforeFirst();
	}

	@Override
	public boolean next()
	{
		while (scan.next())
		{
			if (predicate.isSatisfied(scan)) return true;
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
		return scan.getVal(fldname);
	}

	public int setVal(String fldname, Type type)
	{
		if (scan instanceof UpdateScan) return ((UpdateScan) scan).setVal(fldname, type);
		return -1;
	}

	@Override
	public Record getRecord()
	{
		return scan.getRecord();
	}

	public int delete()
	{
		if (scan instanceof UpdateScan) 
			return ((UpdateScan) scan).delete();
		return -1;
	}

	@Override
	public Type getVal(String tblname, String fldname)
	{
		return scan.getVal(tblname, fldname);
	}

	@Override
	public boolean setVal(String tblname, String fldname, Type type)
	{
		return setVal(tblname, fldname, type);
	}

	@Override
	public Type getFirstVal()
	{
		return scan.getFirstVal();
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

//	@Override
//	public void moveToBottom()
//	{
//		if (scan instanceof UpdateScan) ((UpdateScan) scan).moveToBottom();
//	}
}
