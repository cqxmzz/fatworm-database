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
import fatworm.parser.FatwormParser.bool_expr_return;
import fatworm.scan.Scan;

public class FatwormConnection extends Conn
{
	public FatwormConnection(String url, Properties prop) throws IOException, ClassNotFoundException
	{
		FatwormDB.init(url);
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
			saveMdMgr(FatwormDB.fileMgr().dbName + "/" + "metadata1");
			saveMdMgr(FatwormDB.fileMgr().dbName + "/" + "metadata2");
		} catch (Exception e)
		{
			e.printStackTrace();
			throw new SQLException(e);
		}
	}

	public void saveMdMgr(String string) throws Exception
	{
		FatwormDB.bufferMgr().writeAll();
		FileOutputStream fos = new FileOutputStream(string);
		ObjectOutputStream oos = new ObjectOutputStream(fos);
		oos.writeObject(FatwormDB.mdMgr());
		oos.flush();
		oos.close();
		if (FatwormDB.mdMgr().currentDB != null)
		{
			FatwormDB.mdMgr().put(FatwormDB.mdMgr().currentDataBase.name, FatwormDB.mdMgr().currentDataBase, true);
		}
		// it is guaranteed that all changes made to the file by RandomAccessFile
		// will have been written to that device.
		// don't need to close or flush them
	}

	public void openMdMgr(String string) throws Exception
	{
		FileInputStream ins = new FileInputStream(string);
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
	}
}
