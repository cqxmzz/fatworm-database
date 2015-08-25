package fatworm.driver;

import java.sql.ResultSet;

import fatworm.FatwormDB;

public class FatwormStatement extends Stat
{
	private static FatwormResultSet fatwormResultSet = null;

	public boolean execute(String sql)
	{
		setResultSet(null);
		if (!FatwormDB.parser.parse(sql))
		{
			return false;
		}
		try
		{
			FatwormDB.planner().execute();
			return true;
		} catch (Exception e)
		{
			e.printStackTrace();
			return false;
		}
	}

	public ResultSet getResultSet()
	{
		return fatwormResultSet;
	}

	static public void setResultSet(ResultSet r)
	{
		fatwormResultSet = (FatwormResultSet) r;
	}
}
