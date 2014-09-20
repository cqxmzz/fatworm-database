package fatworm.scan;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import fatworm.expr.FieldExpr;
import fatworm.pred.CopPred;
import fatworm.record.Record;
import fatworm.types.Type;

public class ProductEqualSelectScan implements Scan
{
	ArrayList<Record> values1;

	ArrayList<Record> values2;

	int size1;

	int size2;

	int now1 = 0;

	int now2 = -1;

	int last2 = -1;

	int i1;

	int i2;

	CopPred copPred;

	FieldExpr lftExpr;

	FieldExpr rgtExpr;

	Scan scan1;

	Scan scan2;

	@SuppressWarnings("rawtypes")
	class Sorter implements Comparator
	{
		int i;

		public Sorter(int i)
		{
			this.i = i;
		}

		public int compare(Object obj1, Object obj2)
		{
			Record point1 = (Record) obj1;
			Record point2 = (Record) obj2;
			if (point1.getValue(i).greaterThan(point2.getValue(i)))
				return 1;
			else
				return 0;
		}
	}

	@SuppressWarnings("unchecked")
	public ProductEqualSelectScan(Scan scan1, Scan scan2, CopPred copPred)
	{
		this.scan1 = scan1;
		this.scan2 = scan2;
		this.copPred = copPred;
		lftExpr = (FieldExpr) copPred.lftExpr;
		rgtExpr = (FieldExpr) copPred.rgtExpr;
		values1 = new ArrayList<Record>();
		values2 = new ArrayList<Record>();
		while (scan1.next())
		{
			values1.add(scan1.getRecord());
		}
		while (scan2.next())
		{
			values2.add(scan2.getRecord());
		}
		size1 = values1.size();
		size2 = values2.size();
		// sort
		i1 = values1.get(0).getIndex(lftExpr.fieldName, lftExpr.tableName);
		i2 = values2.get(0).getIndex(rgtExpr.fieldName, rgtExpr.tableName);
		Collections.sort(values1, new Sorter(i1));
		Collections.sort(values2, new Sorter(i2));
		scan1.close();
		scan2.close();
	}

	@Override
	public void beforeFirst()
	{
		now1 = 0;
		now2 = -1;
		last2 = -1;
	}

	private Type thisVal1()
	{
		return values1.get(now1).getValue(i1);
	}

	private Type thisVal2()
	{
		return values2.get(now2).getValue(i2);
	}

	private Type nextVal1()
	{
		return values1.get(now1 + 1).getValue(i1);
	}

	private Type nextVal2()
	{
		return values2.get(now2 + 1).getValue(i2);
	}

	@Override
	public boolean next()
	{
		if (size1 == 0) return false;
		if (size2 == 0) return false;
		if (now1 == size1 - 1 && now2 == size2 - 1) return false;
		if (now2 == -1)
		{
			now2++;
			last2 = now2;
			if (thisVal1().equalTo(thisVal2())) return true;
		}
		if (thisVal1().notEqual(thisVal2()))
		{
			while (thisVal1().notEqual(thisVal2()))
			{
				while (thisVal1().greaterThan(thisVal2()))
				{
					now2++;
					if (now2 == size2) return false;
					last2 = now2;
				}
				while (thisVal1().lessThan(thisVal2()))
				{
					now1++;
					if (now1 == size1) return false;
				}
			}
			return true;
		}
		if (now2 < size2 - 1 && thisVal2().equalTo(nextVal2()))
		{
			now2++;
			return true;
		}
		if (now1 < size1 - 1)
		{
			if (thisVal1().notEqual(nextVal1()))
			{
				now1++;
				now2++;
				last2 = now2;
				if (now1 == size1) return false;
				if (now2 == size2) return false;
				if (thisVal1().notEqual(thisVal2()))
				{
					while (thisVal1().notEqual(thisVal2()))
					{
						while (thisVal1().greaterThan(thisVal2()))
						{
							now2++;
							if (now2 == size2) return false;
							last2 = now2;
						}
						while (thisVal1().lessThan(thisVal2()))
						{
							now1++;
							if (now1 == size1) return false;
						}
					}
					return true;
				}
				else
					return true;
			}
			else
			{
				now2 = last2;
				now1++;
				if (now1 == size1) return false;
				return true;
			}
		}
		return false;
	}

	@Override
	public void close()
	{
	}

	@Override
	public Type getVal(String fldname)
	{
		return getRecord().getValue(fldname);
	}

	@Override
	public int getRecordLength()
	{
		return scan1.getRecordLength() + scan2.getRecordLength();
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
		return values1.get(now1).add(values2.get(now2));
	}

	@Override
	public Type getVal(String tblname, String fldname)
	{
		return getRecord().getValue(fldname, tblname);
	}
}
