package fatworm.database;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.concurrent.ConcurrentHashMap;

import fatworm.FatwormDB;
import fatworm.index.Index;
import fatworm.index.MemIndex;
import fatworm.metadata.MetadataMgr;
import fatworm.record.Record;

public class Table implements Serializable
{
	//TODO not thread safe
	public LinkedList<Integer> places = new LinkedList<Integer>();
	
	//TODO not thread safe
	public PriorityQueue<Integer> emptyList = new PriorityQueue<Integer>();
	
	private DataBase dataBase;

	public Schema schema;

	private String primaryKey = null;

	public String name;

	public int tail;

	public ConcurrentHashMap<String, Index> indexes = new ConcurrentHashMap<String, Index>();
	
	public Table(String n)
	{
		name = n;
	}

	static public Table createTable(String n, Schema s)
	{
		MetadataMgr.readLock.lock();
		Table table = new Table(n);
		table.setDataBase(DataBase.getDataBase());
		table.schema = s;
		DataBase.getDataBase().addTable(table);
		FatwormDB.fileMgr().newTable(n);
		DataBase.getDataBase().getTable(n).places = new LinkedList<Integer>();
		DataBase.getDataBase().getTable(n).tail = 0;
		MetadataMgr.readLock.unlock();
		return table;
	}

	static public void dropTable(String n)
	{
		Table table = DataBase.getDataBase().tables.get(n);
		table.dropTable(DataBase.getDataBase());
	}

	/*
	 * the index of primary key is: ""
	 */
	public void setPrimaryKey(String col)
	{
		primaryKey = col;
	}

	public String getPrimaryKey()
	{
		return primaryKey;
	}

	public void dropTable(DataBase d)
	{
		this.clearTable();
		d.removeTable(this);
	}

	private void clearTable()
	{
		FatwormDB.fileMgr().dropTable(name);
	}

	public DataBase getDataBase()
	{
		return dataBase;
	}

	public void setDataBase(DataBase dataBase)
	{
		this.dataBase = dataBase;
	}

	public HashMap<String, Column> getColumns()
	{
		HashMap<String, Column> map = new HashMap<String, Column>();
		for (int i = 0; i < schema.getNames().size(); ++i)
			map.put(schema.getNames().get(i), schema.getColumns().get(i));
		return map;
	}

	public Schema getSchema()
	{
		return schema;
	}

	public void createIndex(String name, String column, boolean unique)
	{
		MetadataMgr.readLock.lock();
		Index index = new MemIndex(name, this, column, unique);
		indexes.put(name, index);
		for (int i : places)
		{
			Record record = FatwormDB.bufferMgr().get(this.name, i);
			if (record != null) index.insert(record.getValue(column), i);
		}
		MetadataMgr.readLock.unlock();
	}

	public void dropIndex(String name)
	{
		MetadataMgr.readLock.lock();
		indexes.get(name).drop();
		indexes.remove(name);
		MetadataMgr.readLock.unlock();
	}

	public Index getIndex(String s)
	{
		return indexes.get(s);
	}
}
