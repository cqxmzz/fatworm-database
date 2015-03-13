package fatworm.driver;

import java.sql.ResultSet;

import fatworm.FatwormDB;
import fatworm.parser.Parse;

public class FatwormStatement extends Stat
{
	private static FatwormResultSet fatwormResultSet = null;

	public boolean execute(String sql)
	{
		setResultSet(null);
		//TODO for parsing and planing, just make it synchronized
		//TODO because it is not the bottleneck, it will be fast
		Parse parser = new Parse();
		if (!parser.parse(sql))
		{
			return false;
		}
		try
		{
			//TODO but not for executing
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
