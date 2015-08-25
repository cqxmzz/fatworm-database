package fatworm.driver;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import fatworm.FatwormDB;
import fatworm.FatwormException;

public class FatwormConnection extends Conn
{
	public FatwormConnection(String url, Properties prop) throws Exception
	{
		if (FatwormDB.CONNECTION_COUNT == FatwormDB.MAX_CONNECTION)
			throw new FatwormException("Too many connection");
		FatwormDB.init(url);
		FatwormDB.CONNECTION_COUNT++;
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
			FatwormDB.mdMgr().save();
			FatwormDB.CONNECTION_COUNT--;
		} catch (Exception e)
		{
			e.printStackTrace();
			throw new SQLException(e);
		}
	}
}
