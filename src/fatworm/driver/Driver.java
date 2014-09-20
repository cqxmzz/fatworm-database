package fatworm.driver;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.DriverPropertyInfo;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.Properties;
import java.util.logging.Logger;

public class Driver implements java.sql.Driver
{
	public static final String PREFIX = "jdbc:fatworm:";
	static
	{
		try
		{
			DriverManager.registerDriver(new Driver());
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
	}

	public Connection connect(String url, Properties prop) throws SQLException
	{
		try
		{
			if (!isValidURL(url)) throw new SQLException("invalid database address: " + url);
			url = url.trim();
			url = url.substring(13);
			System.setProperty("java.util.Arrays.useLegacyMergeSort", "true");
			return new FatwormConnection(url, prop);
		} catch (Exception e)
		{
			e.printStackTrace();
			throw new SQLException(e);
		}
	}

	private boolean isValidURL(String url)
	{
		return url != null && url.toLowerCase().startsWith(PREFIX);
	}

	@Override
	public boolean acceptsURL(String url) throws SQLException
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int getMajorVersion()
	{
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getMinorVersion()
	{
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public DriverPropertyInfo[] getPropertyInfo(String url, Properties info) throws SQLException
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean jdbcCompliant()
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Logger getParentLogger() throws SQLFeatureNotSupportedException
	{
		// TODO Auto-generated method stub
		return null;
	}
}
