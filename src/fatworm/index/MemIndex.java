package fatworm.index;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Scanner;
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

	TreeMap<Type, ArrayList<Integer>> map;

	// don't store records, store indexes(places)
	transient Iterator<Type> iterator;

	Type currentType;

	@SuppressWarnings("unchecked")
	public MemIndex(String n, Table t, String c, boolean u)
	{
		name = n;
		table = t;
		column = c;
		unique = u;
		map = new TreeMap<Type, ArrayList<Integer>>(new MyComp());
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
	public ArrayList<Integer> getRecords()
	{
		return map.get(currentType);
	}

	@Override
	public void insert(Type dataval, Integer integer)
	{
		if (!map.containsKey(dataval))
		{
			map.put(dataval, new ArrayList<Integer>());
		}
		map.get(dataval).add(integer);
	}

	@Override
	public void delete(Type dataval, Integer integer)
	{
		ArrayList<Integer> list = map.get(dataval);
		if (list != null) 
			for (int i = 0; i < list.size(); ++i)
			{
				if (integer == list.get(i))
				{
					map.get(dataval).remove(i);
					return;
				}
			}
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
