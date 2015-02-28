package fatworm.driver;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Properties;
import java.util.Stack;

import fatworm.FatwormDB;
import fatworm.database.Table;
import fatworm.index.Index;
import fatworm.metadata.MetadataMgr;
import fatworm.scan.Scan;

public class FatwormConnection extends Conn
{
	public FatwormConnection(String url, Properties prop) throws IOException, ClassNotFoundException
	{
		FatwormDB.init(url);
		File file = new File(FatwormDB.fileMgr().dbName + "/" + "metadata");
		if (!file.exists())
		{
			file.createNewFile();
			FatwormDB.setMdMgr(null);
		}
		else
		{
			try
			{
				FileInputStream ins = new FileInputStream(FatwormDB.fileMgr().dbName + "/" + "metadata");
				//TODO store metadata two times for crash protection
				ObjectInputStream ooi = new ObjectInputStream(ins);
				MetadataMgr m = (MetadataMgr) (ooi.readObject());
				FatwormDB.setMdMgr(m);
				ooi.close();
				if (MetadataMgr.currentDataBase == null)
				{
					MetadataMgr.currentDataBase = FatwormDB.mdMgr().get(FatwormDB.mdMgr().currentDB);
					FatwormDB.fileMgr().setDatabaseName(FatwormDB.mdMgr().currentDB);
				}
				FatwormDB.mdMgr().currentDataBase.openedScans = new Stack<Scan>();
			} catch (Exception e)
			{
				file.delete();
				file.createNewFile();
				FatwormDB.setMdMgr(null);
			}
		}
	}

	public Statement createStatement() throws SQLException
	{
		try
		{
			return new FatwormStatement();
		} catch (Exception e)
		{
			e.printStackTrace();
			throw new SQLException(e);
		}
	}

	public void close() throws SQLException
	{
		try
		{
			FatwormDB.bufferMgr().writeAll();
			FileOutputStream fos = new FileOutputStream(FatwormDB.fileMgr().dbName + "/" + "metadata");
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(FatwormDB.mdMgr());
			oos.flush();
			oos.close();
			if (FatwormDB.mdMgr().currentDB != null)
			{
				FatwormDB.mdMgr().put(FatwormDB.mdMgr().currentDataBase.name, FatwormDB.mdMgr().currentDataBase, true);
			}
			//TODO close random access files
		} catch (Exception e)
		{
			e.printStackTrace();
			throw new SQLException(e);
		}
	}
}
