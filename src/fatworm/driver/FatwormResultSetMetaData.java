package fatworm.driver;

import java.sql.Types;

import fatworm.database.Schema;
import fatworm.types.BOOLEAN;
import fatworm.types.CHAR;
import fatworm.types.DATETIME;
import fatworm.types.DECIMAL;
import fatworm.types.FLOAT;
import fatworm.types.INT;
import fatworm.types.TIMESTAMP;
import fatworm.types.Type;
import fatworm.types.VARCHAR;

public class FatwormResultSetMetaData extends ResuSetMetaData
{
	Schema schema;

	public FatwormResultSetMetaData(Schema s)
	{
		schema = s;
	}

	public int getColumnCount()
	{
		if (schema.getColumns() == null) return 0;
		return schema.getColumns().size();
	}

	public int getColumnType(int columnIndex)
	{
		Type type = schema.getIndex(columnIndex - 1).getType();
		if (type instanceof BOOLEAN) return Types.BOOLEAN;
		if (type instanceof CHAR) return Types.CHAR;
		if (type instanceof DATETIME) return Types.TIMESTAMP;
		if (type instanceof DECIMAL) return Types.DECIMAL;
		if (type instanceof FLOAT) return Types.FLOAT;
		if (type instanceof INT) return Types.INTEGER;
		if (type instanceof TIMESTAMP) return Types.TIMESTAMP;
		if (type instanceof VARCHAR) return Types.VARCHAR;
		return 0;
	}
}
