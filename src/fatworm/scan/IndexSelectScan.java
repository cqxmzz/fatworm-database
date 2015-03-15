package fatworm.scan;

import java.util.ArrayList;

import fatworm.database.Record;
import fatworm.index.Index;
import fatworm.types.Type;

public class IndexSelectScan implements Scan
{
	String column;

	Type leftType;

	Type rightType;

	Index index;

	TableScan scan;

	ArrayList<Integer> records = null;

	int now = -1;

	public IndexSelectScan(TableScan scan, String column, Type leftType, Type rightType, Index index2)
	{
		this.scan = scan;
		this.column = column;
		this.leftType = leftType;
		this.rightType = rightType;
		this.index = index2;
		index.between(leftType, rightType);
	}

	@Override
	public void beforeFirst()
	{
		index.between(leftType, rightType);
		now = -1;
		records = null;
	}

	@Override
	public boolean next()
	{
		if (leftType.greaterThan(rightType)) return false;
		while (records == null || now >= records.size() - 1)
		{
			if (!index.next()) return false;
			records = (ArrayList<Integer>) index.getRecords();
			now = -1;
		}
		now++;
		return true;
	}

	@Override
	public void close()
	{
		index.beforeFirst();
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
		return getVal(0);
	}

	@Override
	public Record getRecord()
	{
		return scan.getRecordFromPlace(records.get(now));
	}

	@Override
	public Type getVal(String tblname, String fldname)
	{
		return getRecord().getValue(fldname, tblname);
	}
}
