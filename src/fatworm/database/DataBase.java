package fatworm.database;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Stack;
import java.util.concurrent.ConcurrentHashMap;

import fatworm.FatwormDB;
import fatworm.FatwormException;
import fatworm.metadata.MetadataMgr;
import fatworm.scan.Scan;

public class DataBase implements Serializable
{
	public String name;

	public ConcurrentHashMap<String, Table> tables = new ConcurrentHashMap<String, Table>();

	public transient ThreadLocal<Stack<Scan>> openedScans = new ThreadLocal<Stack<Scan>>();

	public DataBase(String n)
	{
		name = n;
		FatwormDB.mdMgr().put(n, this, true);
		openedScans.set(new Stack<Scan>());
	}

	static public void createDataBase(String n)
	{
		MetadataMgr.readLock.lock();
		FatwormDB.fileMgr().dropDatabasePath(n);
		FatwormDB.fileMgr().newDatabasePath(n);
		FatwormDB.mdMgr().allDataBase.add(n);
		MetadataMgr.readLock.unlock();
		FatwormDB.mdMgr().put(n, new DataBase(n), true);
	}

	static public void useDataBase(String n) throws FatwormException
	{
		if (!FatwormDB.mdMgr().allDataBase.contains(n))
			throw new FatwormException("No Such DB");
		if (getDataBase() != null && DataBase.getDataBase().name.equals(n))
			return;
		if (getDataBase() != null)
		{
			FatwormDB.mdMgr().put(getDataBase().name, getDataBase(), true);
		}
		if (!FatwormDB.mdMgr().openedDataBases.containsKey(n))
		{
			setDataBase(FatwormDB.mdMgr().get(n));
			FatwormDB.mdMgr().openedDataBases.put(n, getDataBase());
		}
		else
		{
			setDataBase(FatwormDB.mdMgr().openedDataBases.get(n));
		}
		FatwormDB.fileMgr().setDatabaseName(n);
		getDataBase().openedScans = new ThreadLocal<Stack<Scan>>();
		DataBase.getDataBase().openedScans.set(new Stack<Scan>());
	}

	static public DataBase getDataBase()
	{
		if (FatwormDB.mdMgr().currentDataBase == null)
			return null;
		return FatwormDB.mdMgr().currentDataBase.get();
	}

	static public void setDataBase(DataBase db)
	{
		if (db == null)
			FatwormDB.mdMgr().currentDataBase = null;
		if (FatwormDB.mdMgr().currentDataBase == null) 
			FatwormDB.mdMgr().currentDataBase = new ThreadLocal<DataBase>();
		FatwormDB.mdMgr().currentDataBase.set(db);
	}

	static public void dropDataBase(String n)
	{
		if (!FatwormDB.mdMgr().allDataBase.contains(n)) 
			return;
		MetadataMgr.readLock.lock();
		FatwormDB.fileMgr().dropDatabasePath(n);
		FatwormDB.mdMgr().allDataBase.remove(n);
		FatwormDB.mdMgr().openedDataBases.remove(n);
		if (getDataBase() != null && getDataBase().name.equals(n))
			setDataBase(null);
		MetadataMgr.readLock.unlock();
	}

//	private void clearDataBase()
//	{
//		while (tables.size() != 0)
//		{
//			Iterator<Table> it = tables.values().iterator();
//			Table table = it.next();
//			table.dropTable(this);
//		}
//		FatwormDB.fileMgr().dropDatabasePath(name);
//	}

	public void addTable(Table table)
	{
		MetadataMgr.readLock.lock();
		tables.put(table.name, table);
		MetadataMgr.readLock.unlock();
	}

	public void removeTable(Table table)
	{
		MetadataMgr.readLock.lock();
		tables.remove(table.name);
		MetadataMgr.readLock.unlock();
	}

	static public boolean include(String name)
	{
		if (FatwormDB.mdMgr().allDataBase.contains(name)) return true;
		return false;
	}

	public boolean includeTable(String name)
	{
		if (tables.containsKey(name)) return true;
		return false;
	}

	public Table getTable(String name)
	{
		return tables.get(name);
	}

	public Stack<Scan> getOpenedScans()
	{
		return openedScans.get();
	}
}
