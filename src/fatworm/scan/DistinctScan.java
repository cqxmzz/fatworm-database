package fatworm.scan;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.TreeSet;

import fatworm.database.Record;
import fatworm.types.Type;

public class DistinctScan implements Scan
{
	Scan scan;

	// in RAM
	@SuppressWarnings("unchecked")
	TreeSet<Record> values = new TreeSet<Record>(new Sorter());

	ArrayList<Record> list = new ArrayList<Record>();

	int now = -1;

	int size;

	@SuppressWarnings("rawtypes")
	class Sorter implements Comparator
	{
		public int compare(Object obj1, Object obj2)
		{
			Record point1 = (Record) obj1;
			Record point2 = (Record) obj2;
			for (int i = 0; i < scan.getRecordLength(); ++i)
			{
				if (point1.getValue(i).greaterThan(point2.getValue(i))) return 1;
				if (point1.getValue(i).lessThan(point2.getValue(i))) return -1;
			}
			return 0;
		}
	}

	public DistinctScan(Scan s)
	{
		scan = s;
		scan.beforeFirst();
		while (scan.next())
		{
			if (!values.contains(scan.getRecord()))
			{
				values.add(scan.getRecord());
				list.add(scan.getRecord());
			}
		}
		size = list.size();
	}

	@Override
	public void beforeFirst()
	{
		now = -1;
	}

	@Override
	public boolean next()
	{
		if (now < size - 1)
		{
			now++;
			return true;
		}
		return false;
	}

	@Override
	public void close()
	{
		// TODO Auto-generated method stub
	}

	@Override
	public Type getVal(String fldname)
	{
		return getRecord().getValue(fldname);
	}

	@Override
	public int getRecordLength()
	{
		return scan.getRecordLength();
	}

	@Override
	public Type getVal(int i)
	{
		return getRecord().getValue(i);
	}

	@Override
	public Type getFirstVal()
	{
		return getRecord().getValue(0);
	}

	@Override
	public Record getRecord()
	{
		return list.get(now);
	}

	@Override
	public Type getVal(String tblname, String fldname)
	{
		return getRecord().getValue(fldname, tblname);
	}
}
