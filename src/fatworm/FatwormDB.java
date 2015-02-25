package fatworm;

import java.util.LinkedList;
import java.util.Stack;

import fatworm.buffer.BufferMgr;
import fatworm.file.FileMgr;
import fatworm.metadata.MetadataMgr;
import fatworm.plan.Planner;
import fatworm.scan.Scan;

public class FatwormDB
{
	//parameters
	public static int BUFFER_SIZE = 1024;

	public static int BLOCK_SIZE = 4096*16;

	public static boolean durability = false;
	
	//workers
	private static Planner planner;

	private static FileMgr fm;

	private static BufferMgr bm;

	private static MetadataMgr mdm;

	public static void init(String dirname)
	{
		initPlanner();
		initMetadataMgr(dirname);
		initFileMgr(dirname);
		initBufferMgr(dirname);
	}

	public static void initPlanner()
	{
		planner = new Planner();
	}

	public static void initBufferMgr(String dirname)
	{
		bm = new BufferMgr();
	}

	public static void initFileMgr(String dirname)
	{
		fm = new FileMgr(dirname);
	}

	public static void initMetadataMgr(String dirname)
	{
		mdm = new MetadataMgr();
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
			mdm.allDataBase = new LinkedList<String>();
		}
		if (mdm.currentDataBase != null)
		{
			FatwormDB.fileMgr().setDatabaseName(mdm.currentDataBase.name);
			mdm.currentDataBase.openedScans = new Stack<Scan>();
		}
	}

	public static Planner planner()
	{
		return planner;
	}
}
