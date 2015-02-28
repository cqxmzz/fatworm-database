package fatworm.scan;

import java.util.ArrayList;

import fatworm.FatwormDB;
import fatworm.database.Table;
import fatworm.record.Record;
import fatworm.types.Type;

public class TableScan implements UpdateScan
{
	private Table table;

	// in RAM
	// ArrayList<Record> values;
	ArrayList<Integer> places;
	//TODO change this to a linked list, and iterate through it using a curser.

	int now = -1;

	Record record;

	int size;

	int count = 0;

	public TableScan(Table t)
	{
		now = -1;
		table = t;
		// values = table.map;
		places = table.places;
		// size = values.size();
		size = places.size();
	}

	public int insert(Record r)
	{
		// values.add(r);
		places.add(table.tail);
		table.tail = FatwormDB.bufferMgr().insert(table.name, r);
		return table.tail; 
	}

	public int delete()
	{
		// values.remove(now);
		// now--;
		// FatwormDB.bufferMgr().delete(table.name, places.get(now));
		int ret = places.get(now);
		places.remove(places.get(now));
		now--;
		return ret;
	}

	@Override
	public void beforeFirst()
	{
		now = -1;
		count = 0;
	}

	@Override
	public boolean next()
	{
		// if (now < values.size() - 1 && now < size - 1 && count < size)
		if (now < places.size() - 1 && now < size - 1 && count < size)
		{
			now++;
			count++;
			return true;
		}
		return false;
	}

	@Override
	public void close()
	{
		// buffer save on disk on connection close
	}

	@Override
	public Type getVal(String fldname)
	{
		if (now == -1)
		{
			return null;
		}
		int i = table.getSchema().getNames().lastIndexOf(fldname);
		if (i >= 0) return getRecord().getValue(i);
		return null;
	}

	public int setVal(String fldname, Type type)
	{
		getRecord().setValue(table.getSchema().getNames().lastIndexOf(fldname), type);
		FatwormDB.bufferMgr().write(table.name, places.get(now), record);
		return places.get(now);
	}

	@Override
	public Record getRecord()
	{
		record = FatwormDB.bufferMgr().get(table.name, places.get(now));
		return record;
	}

	@Override
	public Type getVal(String tblname, String fldname)
	{
		if (tblname == null) return getVal(fldname);
		for (int i = 0; i < getRecordLength(); ++i)
		{
			String tbln = table.getSchema().getTableName(i);
			String coln = table.getSchema().getName(i);
			if (tbln.equals(tblname) && fldname.equals(coln))
			{
				return getVal(i);
			}
		}
		return null;
	}

	@Override
	public boolean setVal(String tblname, String fldname, Type type)
	{
		for (int i = 0; i < getRecordLength(); ++i)
		{
			String tbln = table.getSchema().getTableName(i);
			String coln = table.getSchema().getName(i);
			if (tbln.equals(tblname) && fldname.equals(coln))
			{
				getRecord().setValue(i, type);
			}
		}
		FatwormDB.bufferMgr().write(table.name, places.get(now), record);
		return false;
	}

	@Override
	public Type getFirstVal()
	{
		return getRecord().getValue(0);
	}

	@Override
	public int getRecordLength()
	{
		return table.getSchema().getColumns().size();
	}

	@Override
	public Type getVal(int i)
	{
		return getRecord().getValue(i);
	}

//	@Override
//	public void moveToBottom()
//	{
//		// values.add(values.get(now));
//		// values.remove(now);
//		// now--;
//		places.add(table.tail);
//		table.tail = FatwormDB.bufferMgr().insert(table.name, FatwormDB.bufferMgr().get(table.name, places.get(now)));
//		// FatwormDB.bufferMgr().delete(table.name, places.get(now));
//		places.remove(places.get(now));
//		now--;
//	}

	public Record getRecordFromIndex(Integer integer)
	{
		Record record = FatwormDB.bufferMgr().get(table.name, places.get(integer));
		return record;
	}
}
