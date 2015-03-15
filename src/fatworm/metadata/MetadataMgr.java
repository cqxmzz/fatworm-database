package fatworm.metadata;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import fatworm.FatwormDB;
import fatworm.database.DataBase;

@SuppressWarnings("serial")
public class MetadataMgr implements Serializable
{
	//read lock for reading/writing meta data in memory, every data structure on disk is synchronized
	//write lock for writing/reading meta data on disk
	
	public static transient Lock readLock;
	public static transient Lock writeLock;
	
	public transient ThreadLocal<DataBase> currentDataBase;
	
	public transient ConcurrentHashMap<String, DataBase> openedDataBases = new ConcurrentHashMap<String, DataBase>();
	
	public CopyOnWriteArraySet<String> allDataBase;
	
	public Integer tempTableNum = -1;

	public MetadataMgr()
	{
		createLocks();
	}
	
	private void createLocks()
	{
		 ReentrantReadWriteLock rwLock = new ReentrantReadWriteLock();
		 readLock = rwLock.readLock();
		 writeLock = rwLock.writeLock();
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
			writeLock.lock();
			FileInputStream ins = new FileInputStream(FatwormDB.fileMgr().dbName + "/" + n + "/" + "DBdata");
			ObjectInputStream ooi = new ObjectInputStream(ins);
			DataBase m = (DataBase) (ooi.readObject());
			ooi.close();
			return m;
		} catch (Exception e)
		{
			e.printStackTrace();
			return null;
		}finally{
			writeLock.unlock();
		}
	}

	public void put(String n, DataBase dataBase, boolean flush)
	{
		try
		{
			writeLock.lock();
			if (flush)
				FatwormDB.bufferMgr().writeAll();
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
		}finally{
			writeLock.unlock();
		}
	}

	public void init() throws Exception
	{
		if (allDataBase != null)
			return;
		try
		{
			File file = new File(FatwormDB.fileMgr().dbName + "/" + "metadata1");
			File file2 = new File(FatwormDB.fileMgr().dbName + "/" + "metadata2");
			if (!file.exists() && !file2.exists())
			{
				file.createNewFile();
				FatwormDB.setMdMgr(null);
			}
			else
			{
				try
				{
					openMdMgr(FatwormDB.fileMgr().dbName + "/" + "metadata1");
				} catch (Exception e)
				{
					try
					{
						openMdMgr(FatwormDB.fileMgr().dbName + "/" + "metadata2");
					} catch (Exception e1)
					{
						file.delete();
						file.createNewFile();
						file2.delete();
						file2.createNewFile();
						FatwormDB.setMdMgr(null);
					}
				}
			}
		} catch (Exception e)
		{
			throw e;
		}
	}	
	
	public void save() throws Exception
	{
		try
		{
			writeLock.lock();
			saveMdMgr(FatwormDB.fileMgr().dbName + "/" + "metadata1");
			saveMdMgr(FatwormDB.fileMgr().dbName + "/" + "metadata2");
		} catch (Exception e)
		{
			throw e;
		}finally
		{
			writeLock.unlock();
		}
	}
	
	public void openMdMgr(String string) throws Exception
	{
		FileInputStream ins = new FileInputStream(string);
		ObjectInputStream ooi = new ObjectInputStream(ins);
		MetadataMgr m = (MetadataMgr) (ooi.readObject());
		ooi.close();
		createLocks();
		m.openedDataBases = new ConcurrentHashMap<String, DataBase>();
		FatwormDB.setMdMgr(m);
	}
	
	// it is guaranteed that all changes made to the file by
	// RandomAccessFile
	// will have been written to that device.
	// don't need to close or flush them
	public void saveMdMgr(String string) throws Exception
	{
		FatwormDB.bufferMgr().writeAll();
		FileOutputStream fos = new FileOutputStream(string);
		ObjectOutputStream oos = new ObjectOutputStream(fos);
		oos.writeObject(FatwormDB.mdMgr());
		oos.flush();
		oos.close();
		if (DataBase.getDataBase() != null)
			FatwormDB.mdMgr().put(DataBase.getDataBase().name, DataBase.getDataBase(), true);
	}
}