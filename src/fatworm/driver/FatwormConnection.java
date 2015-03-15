package fatworm.driver;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import fatworm.FatwormDB;

public class FatwormConnection extends Conn
{
	public FatwormConnection(String url, Properties prop) throws Exception
	{
		FatwormDB.init(url);
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
		} catch (Exception e)
		{
			e.printStackTrace();
			throw new SQLException(e);
		}
	}
}
