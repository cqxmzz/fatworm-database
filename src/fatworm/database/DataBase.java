package fatworm.database;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Stack;

import fatworm.FatwormDB;
import fatworm.scan.Scan;

public class DataBase implements Serializable
{
	public String name;

	public HashMap<String, Table> tables = new HashMap<String, Table>();

	public transient Stack<Scan> openedScans = new Stack<Scan>();

	public DataBase(String n)
	{
		name = n;
		FatwormDB.mdMgr().put(n, this, true);
	}

	static public void createDataBase(String n)
	{
		FatwormDB.fileMgr().dropDatabasePath(n);
		FatwormDB.fileMgr().newDatabasePath(n);
		FatwormDB.mdMgr().allDataBase.add(n);
		FatwormDB.mdMgr().put(n, new DataBase(n), true);
	}

	static public void useDataBase(String n)
	{
		if (FatwormDB.mdMgr().currentDataBase == null)
		{
			FatwormDB.mdMgr().currentDataBase = FatwormDB.mdMgr().get(n);
			FatwormDB.fileMgr().setDatabaseName(n);
			FatwormDB.mdMgr().currentDB = n;
			FatwormDB.mdMgr().currentDataBase.openedScans = new Stack<Scan>();
		}
		if (!FatwormDB.mdMgr().currentDataBase.name.equals(n))
		{
			FatwormDB.mdMgr().put(FatwormDB.mdMgr().currentDataBase.name, FatwormDB.mdMgr().currentDataBase, true);
			FatwormDB.fileMgr().setDatabaseName(n);
			FatwormDB.mdMgr().currentDB = n;
			FatwormDB.mdMgr().currentDataBase = FatwormDB.mdMgr().get(n);
			FatwormDB.mdMgr().currentDataBase.openedScans = new Stack<Scan>();
		}
	}

	static public DataBase getDataBase()
	{
		return FatwormDB.mdMgr().currentDataBase;
	}

	static public void dropDataBase(String n)
	{
		if (!FatwormDB.mdMgr().allDataBase.contains(n)) return;
		FatwormDB.fileMgr().dropDatabasePath(n);
		FatwormDB.mdMgr().allDataBase.remove(n);
		if (FatwormDB.mdMgr().currentDataBase != null && FatwormDB.mdMgr().currentDataBase.name.equals(n))
		{
			FatwormDB.mdMgr().currentDataBase = null;
			FatwormDB.mdMgr().currentDB = null;
		}
	}

	private void clearDataBase()
	{
		while (tables.size() != 0)
		{
			Iterator<Table> it = tables.values().iterator();
			Table table = it.next();
			table.dropTable(this);
		}
		FatwormDB.fileMgr().dropDatabasePath(name);
	}

	public void addTable(Table table)
	{
		tables.put(table.name, table);
	}

	public void removeTable(Table table)
	{
		tables.remove(table.name);
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
		return openedScans;
	}
}
