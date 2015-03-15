package fatworm.scan;

import fatworm.database.Record;
import fatworm.types.Type;

public class ProductScan implements Scan
{
	Scan scan1;

	Scan scan2;

	public ProductScan(Scan s1, Scan s2)
	{
		scan1 = s1;
		scan2 = s2;
		scan1.next();
	}

	@Override
	public void beforeFirst()
	{
		scan1.beforeFirst();
		scan1.next();
		scan2.beforeFirst();
	}

	@Override
	public boolean next()
	{
		if (scan2.next())
			return true;
		else
		{
			scan2.beforeFirst();
			return scan2.next() && scan1.next();
		}
	}

	@Override
	public void close()
	{
		scan1.close();
		scan2.close();
	}

	@Override
	public Type getVal(String fldname)
	{
		Type type = scan1.getVal(fldname);
		if (type != null)
			return type;
		else
			return scan2.getVal(fldname);
	}

	@Override
	public Type getVal(String tblname, String fldname)
	{
		Type type = scan2.getVal(tblname, fldname);
		if (type != null)
			return type;
		else
			return scan1.getVal(tblname, fldname);
	}

	@Override
	public Record getRecord()
	{
		return scan1.getRecord().add(scan2.getRecord());
	}

	@Override
	public Type getFirstVal()
	{
		return scan1.getFirstVal();
	}

	@Override
	public int getRecordLength()
	{
		return scan1.getRecordLength() + scan2.getRecordLength();
	}

	@Override
	public Type getVal(int i)
	{
		int temp = scan1.getRecordLength();
		if (i < temp)
			return scan1.getVal(i);
		else
		{
			return scan2.getVal(i - temp);
		}
	}
}
