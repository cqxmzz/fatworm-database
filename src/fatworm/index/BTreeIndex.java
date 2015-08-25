package fatworm.index;

import java.util.LinkedList;

import fatworm.database.Column;
import fatworm.database.Table;
import fatworm.types.Type;

//TODO Btree
@SuppressWarnings("serial")
public class BTreeIndex implements Index
{
	public BTreeIndex(String name, Table table, Column column, boolean unique)
	{
		// TODO Auto-generated constructor stub
	}

	@Override
	public void drop()
	{
		// TODO Auto-generated method stub
	}

	@Override
	public void beforeFirst()
	{
		// TODO Auto-generated method stub
	}

	@Override
	public boolean next()
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void insert(Type dataval, Integer integer)
	{
		// TODO Auto-generated method stub
	}

	@Override
	public void delete(Type dataval, Integer integer)
	{
		// TODO Auto-generated method stub
	}

	@Override
	public LinkedList<Integer> getRecords()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getColumn()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void between(Type type1, Type type2)
	{
		// TODO Auto-generated method stub
	}
}
