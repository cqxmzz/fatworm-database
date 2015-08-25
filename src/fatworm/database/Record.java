package fatworm.database;

import java.nio.ByteBuffer;
import java.sql.Timestamp;
import java.util.Iterator;
import java.util.LinkedList;

import fatworm.FatwormDB;
import fatworm.FatwormException;
import fatworm.types.*;

public class Record
{
	private LinkedList<Type> values = new LinkedList<Type>();

	private Schema schema;

	public Record(Table t)
	{
		schema = t.getSchema();
		Iterator<String> iterator = schema.getNames().iterator();
		for (Column column : schema.getColumns())
		{
			String s = iterator.next();
			Type type = column.getType();
			if (column.autoIncrement() && (t.getPrimaryKey() == null || !t.getPrimaryKey().equals(s)))
			{
				try
				{
					throw new FatwormException("autoincrement should on primary key");
				} catch (FatwormException e)
				{
					e.printStackTrace();
				}
			}
			if (column.autoIncrement() && !(column.getType() instanceof INT))
			{
				try
				{
					throw new FatwormException("autoincrement should on INT");
				} catch (FatwormException e)
				{
					e.printStackTrace();
				}
			}
			if (column.hasDefault())
			{
				type = column.getConstValue();
			}
			else if (column.getType().isTIMESTAMP())
			{
				type = new TIMESTAMP(new Timestamp(System.currentTimeMillis()));
			}
			if (column.autoIncrement())
			{
				type = new INT(column.getAutoIncrementCount());
			}
			values.add(type);
		}
	}

	public Record(LinkedList<Type> vals, Schema ret)
	{
		schema = ret;
		values = vals;
	}

	public LinkedList<Type> getValues()
	{
		return values;
	}

	public Type getValue(int i)
	{
		return values.get(i);
	}

	public Type getValue(String s)
	{
		int i = schema.getNames().indexOf(s);
		if (i == -1) return null;
		return values.get(i);
	}

	public void setSchema(Schema s)
	{
		schema = s;
	}

	public Type getValue(String fldname, String tblname)
	{
		if (tblname == null) return getValue(fldname);
		for (int i = 0; i < values.size(); ++i)
		{
			String tbln = schema.getTableName(i);
			String coln = schema.getName(i);
			if (tbln.equals(tblname) && fldname.equals(coln))
			{
				return getValue(i);
			}
		}
		return null;
	}

	public int getIndex(String fldname, String tblname)
	{
		if (tblname == null) return getIndex(fldname);
		for (int i = 0; i < values.size(); ++i)
		{
			String tbln = schema.getTableName(i);
			String coln = schema.getName(i);
			if (tbln.equals(tblname) && fldname.equals(coln))
			{
				return i;
			}
		}
		return -1;
	}

	public int getIndex(String fldName)
	{
		return schema.getNames().indexOf(fldName);
	}

	public void setValue(int i, Type p)
	{
		if (p instanceof VARCHAR)
		{
			if (schema.getColumn(i).getType() instanceof CHAR)
			{
				((VARCHAR) p).setCapacity(((CHAR) (schema.getColumn(i).getType())).getCapacity());
			}
			else
				((VARCHAR) p).setCapacity(((VARCHAR) (schema.getColumn(i).getType())).getCapacity());
		}
		if (p instanceof CHAR)
		{
			if (schema.getColumn(i).getType() instanceof VARCHAR)
			{
				((CHAR) p).setCapacity(((VARCHAR) (schema.getColumn(i).getType())).getCapacity());
			}
			else
				((CHAR) p).setCapacity(((CHAR) (schema.getColumn(i).getType())).getCapacity());
		}
		values.set(i, p);
	}

	public void setValue(String s, Type p)// only for insert
	{
		if (p == null) return;
		if (p.isNULL()) return;
		int i = schema.getNames().indexOf(s);
		values.set(i, p);
	}

	public Schema schema()
	{
		return schema;
	}

	public Record add(Record record)
	{
		Schema ret = new Schema();
		LinkedList<Type> vals = new LinkedList<Type>();
		for (int i = 0; i < schema.getColumns().size(); ++i)
		{
			ret.add(schema.getColumns().get(i), schema.getTableName(i), schema.getName(i));
			vals.add(values.get(i));
		}
		for (int j = 0; j < record.schema.getColumns().size(); ++j)
		{
			boolean flag = false;
			for (int i = 0; i < schema.getColumns().size(); ++i)
			{
				if (schema.getTableName(i).equals(record.schema.getTableName(j)) && schema.getName(i).equals(record.schema.getName(j))) flag = true;
			}
			if (!flag)
			{
				ret.add(record.schema.getColumns().get(j), record.schema.getTableName(j), record.schema.getName(j));
				vals.add(record.getValue(j));
			}
		}
		return new Record(vals, ret);
	}

	public Record rename(String name)
	{
		Schema s = schema.rename(name);
		return new Record(values, s);
	}

	public int length()
	{
		return schema.length();
	}
}
