package fatworm.index;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.TreeMap;

import fatworm.database.Table;
import fatworm.file.Block;
import fatworm.record.Record;
import fatworm.types.Type;

public class MemIndex implements Index, Serializable
{
	@SuppressWarnings("rawtypes")
	class MyComp implements Comparator, Serializable
	{
		public int compare(Object a, Object b)
		{
			Type ta, tb;
			ta = (Type) a;
			tb = (Type) b;
			if (ta.greaterThan(tb)) return 1;
			if (ta.lessThan(tb)) return -1;
			return 0;
		}
		// no need to override equals
	}

	String name;

	Table table;

	public String column;

	boolean unique;

	TreeMap<Type, ArrayList<Record>> map;

	transient Iterator<Type> iterator;// TODO

	Type currentType;

	@SuppressWarnings("unchecked")
	public MemIndex(String n, Table t, String c, boolean u)
	{
		name = n;
		table = t;
		column = c;
		unique = u;
		map = new TreeMap<Type, ArrayList<Record>>(new MyComp());
		iterator = null;
	}

	@Override
	public void drop()
	{
		map.clear();
	}

	@Override
	public void beforeFirst()
	{
		iterator = map.keySet().iterator();
	}

	@Override
	public boolean next()
	{
		if (iterator.hasNext())
		{
			currentType = iterator.next();
			return true;
		}
		return false;
	}

	@Override
	public ArrayList<Record> getRecords()
	{
		return map.get(currentType);
	}

	@Override
	public void insert(Type dataval, Record record)
	{
		if (!map.containsKey(dataval))
		{
			map.put(dataval, new ArrayList<Record>());
		}
		map.get(dataval).add(record);
	}

	@Override
	public void delete(Type dataval, Record record)
	{
		ArrayList<Record> list = map.get(dataval);
		if (list != null) for (Record r : list)
		{
			if (r.equals(record))
			{
				map.get(dataval).remove(r);
				return;
			}
		}
	}

	@Override
	public Block getDataRid()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getColumn()
	{
		return column;
	}

	@Override
	public void between(Type type1, Type type2)
	{
		if (type1.greaterThan(type2)) return;
		iterator = map.subMap(type1, true, type2, true).keySet().iterator();
	}
}
