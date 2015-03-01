package fatworm.scan;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.ListIterator;

import apple.laf.JRSUIConstants.Size;

import com.sun.org.apache.bcel.internal.generic.RETURN;

import fatworm.FatwormDB;
import fatworm.database.Table;
import fatworm.record.Record;
import fatworm.types.Type;

public class TableScan implements UpdateScan
{
	protected Table table;

	Record record;

	ListIterator<Integer> iterator;
	
	int placeNow;
	
	public TableScan(Table t)
	{
		table = t;
		placeNow = -1;
		iterator = table.places.listIterator();
	}

	//return the place inserted
	public int insert(Record r)
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
		int place = table.emptyList.poll();
		table.places.add(place);
		int tail = FatwormDB.bufferMgr().insert(table.name, r, place);
		if (table.tail < tail)
			table.tail = tail;
		return place; 
	}

	public int delete()
	{
		int ret = -1;
		if (iterator.hasNext())
		{
			ret = iterator.previous();
			iterator.remove();
		}
		return ret;
	}

	@Override
	public void beforeFirst()
	{
		placeNow = -1;
		iterator = table.places.listIterator();
		record = null;
	}

	@Override
	public boolean next()
	{
		boolean ret = iterator.hasNext();
		if (ret)
		{
			placeNow = iterator.next();
			record = FatwormDB.bufferMgr().get(table.name, placeNow);
		}
		return ret;
	}

	@Override
	public void close()
	{
		// buffer save on disk on connection close
	}

	public int size()
	{
		return table.places.size();
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
