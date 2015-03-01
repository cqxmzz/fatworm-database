package fatworm.metadata;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.LinkedList;

import fatworm.FatwormDB;
import fatworm.database.DataBase;
import fatworm.database.Table;
import fatworm.index.Index;

public class MetadataMgr implements Serializable
{
	public LinkedList<String> allDataBase;

	public String currentDB = null;

	public static DataBase currentDataBase = null;
	
	public static int tempTableNum = -1;

	public MetadataMgr()
	{
	}

	public DataBase get(String n)
	{
		File file = new File(FatwormDB.fileMgr().dbName + "/" + n + "/" + "DBdata");
		if (!file.exists())
		{
			return null;
		}
		try
		{
			FileInputStream ins = new FileInputStream(FatwormDB.fileMgr().dbName + "/" + n + "/" + "DBdata");
			ObjectInputStream ooi = new ObjectInputStream(ins);
			DataBase m = (DataBase) (ooi.readObject());
			ooi.close();
			return m;
		} catch (Exception e)
		{
			e.printStackTrace();
			return null;
		}
	}

	public void put(String n, DataBase dataBase, boolean flush)
	{
		try
		{
			if (flush)
				FatwormDB.bufferMgr().flushAll();
			else
				FatwormDB.bufferMgr().writeAll();
			FileOutputStream fos = new FileOutputStream(FatwormDB.fileMgr().dbName + "/" + n + "/" + "DBdata");
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(dataBase);
			oos.flush();
			oos.close();
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}