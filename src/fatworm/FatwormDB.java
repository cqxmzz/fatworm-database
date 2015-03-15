package fatworm;

import java.util.Stack;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;

import fatworm.buffer.BufferMgr;
import fatworm.database.DataBase;
import fatworm.file.FileMgr;
import fatworm.metadata.MetadataMgr;
import fatworm.parser.Parse;
import fatworm.plan.Planner;
import fatworm.scan.Scan;

public class FatwormDB
{
	//parameters
	/* gratuity: page */
	/* page size = block size */
	public static int BUFFER_SIZE = 1024;

	public static int BLOCK_SIZE = 8 * 1024 * 8;

	public static boolean durability = false;
	
	public static int MAX_TEMP_TABLE_COUNT = 256;
	
	//workers
	private static ThreadLocal<Planner> planner;

	private static FileMgr fm;

	private static BufferMgr bm;

	private static MetadataMgr mdm;
	
	public static Parse parser = new Parse();

	public static void init(String dirname) throws Exception
	{
		if (planner == null)
			initPlanner();
		if (fm == null)
			initFileMgr(dirname);
		if (mdm == null)
			initMetadataMgr(dirname);
		if (bm == null)
			initBufferMgr(dirname);
	}

	public static void initPlanner()
	{
		planner = new ThreadLocal<Planner>();
		planner.set(new Planner());
	}

	public static void initBufferMgr(String dirname)
	{
		bm = new BufferMgr();
	}

	public static void initFileMgr(String dirname)
	{
		fm = new FileMgr(dirname);
	}

	public static void initMetadataMgr(String dirname) throws Exception
	{
		mdm = new MetadataMgr();
		mdm.init();
	}

	public static FileMgr fileMgr()
	{
		return fm;
	}

	public static BufferMgr bufferMgr()
	{
		return bm;
	}

	public static MetadataMgr mdMgr()
	{
		return mdm;
	}

	public static void setMdMgr(MetadataMgr m)
	{
		mdm = m;
		if (m == null)
		{
			mdm = new MetadataMgr();
			mdm.allDataBase = new CopyOnWriteArraySet<String>();
			mdm.openedDataBases = new ConcurrentHashMap<String, DataBase>();
		}
		if (DataBase.getDataBase() != null)
		{
			FatwormDB.fileMgr().setDatabaseName(DataBase.getDataBase().name);
			DataBase.getDataBase().openedScans = new ThreadLocal<Stack<Scan>>();
			DataBase.getDataBase().openedScans.set(new Stack<Scan>());
			mdm.openedDataBases = new ConcurrentHashMap<String, DataBase>();
		}
	}

	public static Planner planner()
	{
		return planner.get();
	}
}
