package fatworm.scan;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import fatworm.record.Record;
import fatworm.types.Type;

public class SortScan implements Scan
{
	List<String> col_name;

	List<String> tbl_name;

	List<Boolean> ascent;

	Scan scan;

	//TODO don't sort in RAM! Use a temp table
	//It will be just O(N) 
	ArrayList<Record> values;

	int now = -1;

	int size;

	@SuppressWarnings("rawtypes")
	class Sorter implements Comparator
	{
		List<String> cols;

		List<String> tbls;

		List<Boolean> ascs;

		public Sorter(List<String> c, List<String> t, List<Boolean> a)
		{
			cols = c;
			tbls = t;
			ascs = a;
		}

		public int compare(Object obj1, Object obj2)
		{
			Record point1 = (Record) obj1;
			Record point2 = (Record) obj2;
			// System.out.println(point1.getValues());
			// System.out.println(point1.schema().getNames());
			// System.out.println(point1.schema().getTableNames());
			for (int i = 0; i < ascs.size(); ++i)
			{
				if (ascs.get(i))
				{
					if (point1.getValue(cols.get(i), tbls.get(i)).greaterThan(point2.getValue(cols.get(i), tbls.get(i))))
					{
						return 1;
					}
					if (point1.getValue(cols.get(i), tbls.get(i)).lessThan(point2.getValue(cols.get(i), tbls.get(i))))
					{
						return 0;
					}
				}
				else
				{
					if (point1.getValue(cols.get(i), tbls.get(i)).lessThan(point2.getValue(cols.get(i), tbls.get(i))))
					{
						return 1;
					}
					if (point1.getValue(cols.get(i), tbls.get(i)).greaterThan(point2.getValue(cols.get(i), tbls.get(i))))
					{
						return 0;
					}
				}
			}
			return 0;
		}
	}

	@SuppressWarnings("unchecked")
	public SortScan(List<String> c, List<String> t, List<Boolean> a, Scan s)
	{
		col_name = c;
		tbl_name = t;
		ascent = a;
		scan = s;
		scan.beforeFirst();
		values = new ArrayList<Record>();
		while (scan.next())
		{
			values.add(scan.getRecord());
		}
		size = values.size();
		// sort
		Collections.sort(values, new Sorter(col_name, tbl_name, ascent));
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
		return values.get(now).getValue(fldname);
	}

	@Override
	public int getRecordLength()
	{
		return scan.getRecordLength();
	}

	@Override
	public Type getVal(int i)
	{
		return values.get(now).getValue(i);
	}

	@Override
	public Type getFirstVal()
	{
		return values.get(now).getValue(0);
	}

	@Override
	public Record getRecord()
	{
		return values.get(now);
	}

	@Override
	public Type getVal(String tblname, String fldname)
	{
		return values.get(now).getValue(fldname, tblname);
	}
}
