package fatworm.scan;

import fatworm.FatwormDB;
import fatworm.database.Record;
import fatworm.database.Table;
import fatworm.types.Type;

public class TableScan implements UpdateScan
{
	protected Table table;

	Record record;
	int now;
	int placeNow;
	int length;
	
	public TableScan(Table t)
	{
		table = t;
		placeNow = -1;
		now = -1;
		length = table.places.size();
	}

	//return the place inserted
	public int insert(Record r)
	{
		Integer place;
		synchronized (table.emptyList)
		{
			if (table.emptyList.isEmpty())
			{
				int oldTail = table.tail;
				table.places.add(oldTail);
				int tail = FatwormDB.bufferMgr().insert(table.name, r, oldTail);
				if (table.tail < tail)
					table.tail = tail;
				return oldTail;
			}
			place = table.emptyList.first();
			table.emptyList.remove(place);
			table.places.add(place);
			int tail = FatwormDB.bufferMgr().insert(table.name, r, place);
			if (table.tail < tail)
				table.tail = tail;
		}
		return place; 
	}

	public int delete()
	{
		int ret = -1;
		synchronized (table.emptyList)
		{
			if (++now < length)
			{
				table.places.remove(now);
				--now;
				--length;
				placeNow = table.places.get(now);
				table.emptyList.add(ret);
			}
		}
		return ret;
	}

	@Override
	public void beforeFirst()
	{
		placeNow = -1;
		now = -1;
		record = null;
	}

	@Override
	public boolean next()
	{
		++now;
		if (now < length)
		{
			placeNow = table.places.get(now);
			record = FatwormDB.bufferMgr().get(table.name, placeNow);
			return true;	
		}
		return false;
	}

	@Override
	public void close()
	{
		// buffer will be saved on disk on connection close
	}

	public int size()
	{
		return length;
	}
	
	@Override
	public Type getVal(String fldname)
	{
		if (placeNow == -1)
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
		FatwormDB.bufferMgr().write(table.name, placeNow, record);
		return placeNow;
	}

	@Override
	public Record getRecord()
	{
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
		FatwormDB.bufferMgr().write(table.name, placeNow, record);
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

	public Record getRecordFromPlace(Integer place)
	{
		Record record = FatwormDB.bufferMgr().get(table.name, place);
		return record;
	}
	
	public void setRecordFromPlace(Integer place, Record record)
	{
		FatwormDB.bufferMgr().insert(table.name, record, place);
	}
}
