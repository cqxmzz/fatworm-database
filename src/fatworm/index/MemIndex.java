package fatworm.index;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.concurrent.ConcurrentSkipListMap;

import fatworm.database.Table;
import fatworm.types.Type;

@SuppressWarnings("serial")
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

	ConcurrentSkipListMap<Type, ArrayList<Integer>> map;

	transient ThreadLocal<Iterator<Type>> iterator;

	Type currentType;

	@SuppressWarnings("unchecked")
	public MemIndex(String n, Table t, String c, boolean u)
	{
		name = n;
		table = t;
		column = c;
		unique = u;
		map = new ConcurrentSkipListMap<Type, ArrayList<Integer>>(new MyComp());
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
		iterator = new ThreadLocal<Iterator<Type>>();
		iterator.set(map.keySet().iterator());
	}

	@Override
	public boolean next()
	{
		if (iterator.get().hasNext())
		{
			currentType = iterator.get().next();
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
		iterator = new ThreadLocal<Iterator<Type>>();
		iterator.set(map.subMap(type1, true, type2, true).keySet().iterator());
	}
}
