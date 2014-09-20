package fatworm.driver;

import java.sql.ResultSetMetaData;

import fatworm.database.Schema;
import fatworm.scan.Scan;
import fatworm.types.Type;

public class FatwormResultSet extends ResuSet
{
	Scan scan;

	Schema schema;

	public FatwormResultSet()
	{
		scan = null;
		schema = null;
	}

	public FatwormResultSet(Scan sc, Schema s)
	{
		scan = sc;
		schema = s;
	}

	public ResultSetMetaData getMetaData()
	{
		return new FatwormResultSetMetaData(schema);
	}

	public void beforeFirst()
	{
		scan.beforeFirst();
	}

	public Object getObject(int columnIndex)
	{
		Type type = scan.getVal(columnIndex - 1);
		return type.getValue();
	}

	public boolean next()
	{
		return scan.next();
	}
}
