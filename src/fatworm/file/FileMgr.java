package fatworm.file;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import fatworm.FatwormDB;
import fatworm.database.Table;

public class FileMgr
{
	private File dbDirectory;

	public String dbName;

	String databaseName;

	private ConcurrentHashMap<String, RandomAccessFile> openFiles = new ConcurrentHashMap<String, RandomAccessFile>();

	public FileMgr(String dbname)
	{
		dbName = dbname;
		dbDirectory = new File(dbname);
		dbDirectory.mkdir();
	}

	public void newMetadata(String n)
	{
		File metadataDirectory = new File(dbDirectory.getAbsolutePath(), "metadata");
	}

	public void newDatabasePath(String n)
	{
		dbDirectory = new File(dbName + "/" + n);
		dbDirectory.mkdir();
	}

	public void dropDatabasePath(String n)
	{
		dbDirectory = new File(dbName + "/" + n);
		for (RandomAccessFile file : openFiles.values())
		{
			try
			{
				file.close();
			} catch (IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		openFiles.clear();
		System.gc();
		deleteDir(dbDirectory);
	}

	private static boolean deleteDir(File dir)
	{
		if (dir.isDirectory())
		{
			String[] children = dir.list();
			for (int i = 0; i < children.length; i++)
			{
				boolean success = deleteDir(new File(dir, children[i]));
				if (!success)
				{
					return false;
				}
			}
		}
		return dir.delete();
	}

	public void setDatabaseName(String name)
	{
		databaseName = name;
	}

	public String getFileName(Table table)
	{
		return dbName + "/" + databaseName + "/" + table.name;
	}

	public void newTable(String n)
	{
		try
		{
			dbDirectory = new File(dbName + "/" + databaseName, n);
			dbDirectory.createNewFile();
		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	public void newTempTable(String n)
	{
		try
		{
			dbDirectory = new File(dbName, n);
			dbDirectory.createNewFile();
		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	public void dropTable(String n)
	{
		dbDirectory = new File(dbName + "/" + databaseName, n);
		dbDirectory.delete();
	}

	private FileChannel getFile(String filename)
	{
		File dbTable = new File(dbName + "/" + databaseName, filename);
		RandomAccessFile f;
		if (openFiles.containsKey(dbTable.getAbsolutePath()))
		{
			f = openFiles.get(dbTable.getAbsolutePath());
		}
		else
		{
			try
			{
				f = new RandomAccessFile(dbTable, "rws");
				openFiles.put(dbTable.getAbsolutePath(), f);
			} catch (FileNotFoundException e)
			{
				f = null;
				e.printStackTrace();
			}
		}
		FileChannel fc = f.getChannel();
		return fc;
	}

	private FileChannel getTempFile(String filename)
	{
		File dbTable = new File(dbName, filename);
		RandomAccessFile f;
		if (openFiles.containsKey(dbTable.getAbsolutePath()))
		{
			f = openFiles.get(dbTable.getAbsolutePath());
		}
		else
		{
			try
			{
				f = new RandomAccessFile(dbTable, "rws");
				openFiles.put(dbTable.getAbsolutePath(), f);
			} catch (FileNotFoundException e)
			{
				f = null;
				e.printStackTrace();
			}
		}
		FileChannel fc = f.getChannel();
		return fc;
	}

	public void write(Block blk, ByteBuffer bb)
	{
		try
		{
			bb.rewind();
			FileChannel fc = getFile(blk.fileName());
			fc.write(bb, blk.blkNumber() * FatwormDB.BLOCK_SIZE);
		} catch (Exception e)
		{
			throw new RuntimeException("cannot write block" + blk);
		}
	}

	public void Tempwrite(Block blk, ByteBuffer bb)
	{
		try
		{
			bb.rewind();
			FileChannel fc = getTempFile(blk.fileName());
			fc.write(bb, blk.blkNumber() * FatwormDB.BLOCK_SIZE);
		} catch (IOException e)
		{
			throw new RuntimeException("cannot write block" + blk);
		}
	}

	void read(Block blk, ByteBuffer bb)
	{
		try
		{
			bb.clear();
			FileChannel fc = getFile(blk.fileName());
			fc.read(bb, blk.blkNumber() * FatwormDB.BLOCK_SIZE);
		} catch (IOException e)
		{
			throw new RuntimeException("cannot read block " + blk);
		}
	}

	public int size(String filename)
	{
		try
		{
			FileChannel fc = getFile(filename);
			return (int) (fc.size() / FatwormDB.BLOCK_SIZE);
		} catch (IOException e)
		{
			throw new RuntimeException("cannot access " + filename);
		}
	}

	public int Tempsize(String filename)
	{
		try
		{
			FileChannel fc = getTempFile(filename);
			return (int) (fc.size() / FatwormDB.BLOCK_SIZE);
		} catch (IOException e)
		{
			throw new RuntimeException("cannot access " + filename);
		}
	}
}
