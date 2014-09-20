package fatworm.record;

import java.io.Serializable;
import java.nio.ByteBuffer;
import java.sql.Timestamp;
import java.util.Iterator;
import java.util.LinkedList;

import fatworm.FatwormDB;
import fatworm.database.Column;
import fatworm.database.Schema;
import fatworm.database.Table;
import fatworm.types.BOOLEAN;
import fatworm.types.CHAR;
import fatworm.types.DATETIME;
import fatworm.types.DECIMAL;
import fatworm.types.FLOAT;
import fatworm.types.INT;
import fatworm.types.TIMESTAMP;
import fatworm.types.Type;
import fatworm.types.VARCHAR;

public class Record implements Serializable
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
					throw new Exception("autoincrement should on primary key");
				} catch (Exception e)
				{
					e.printStackTrace();
				}
			}
			if (column.autoIncrement() && !(column.getType() instanceof INT))
			{
				try
				{
					throw new Exception("autoincrement should on INT");
				} catch (Exception e)
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
			if (schema.getIndex(i).getType() instanceof CHAR)
			{
				((VARCHAR) p).setCapacity(((CHAR) (schema.getIndex(i).getType())).getCapacity());
			}
			else
				((VARCHAR) p).setCapacity(((VARCHAR) (schema.getIndex(i).getType())).getCapacity());
		}
		if (p instanceof CHAR)
		{
			if (schema.getIndex(i).getType() instanceof VARCHAR)
			{
				((CHAR) p).setCapacity(((VARCHAR) (schema.getIndex(i).getType())).getCapacity());
			}
			else
				((CHAR) p).setCapacity(((CHAR) (schema.getIndex(i).getType())).getCapacity());
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

	public Record(ByteBuffer bb, Schema sche)
	{
		LinkedList<Type> list = new LinkedList<Type>();
		int top = 12;
		for (int i = 0; i < sche.getColumns().size(); ++i)
		{
			Type type = sche.getIndex(i).getType();
			bb.position(top);
			top += 4;
			int place = bb.getInt();
			if (place == -1)
			{
				list.add(null);
				continue;
			}
			bb.position(place);
			if (bb.getChar() == 'n')
			{
				type = type.clone();
				list.add(type);
				continue;
			}
			bb.position(place);
			if (type instanceof BOOLEAN)
			{
				type = new BOOLEAN(bb.getChar());
			}
			if (type instanceof CHAR)
			{
				int len = bb.getInt();
				byte[] byteval = new byte[len];
				bb.get(byteval);
				try
				{
					type = new CHAR(((CHAR) type).getCapacity(), "'" + (new String(byteval)) + "'");
				} catch (Exception e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (type instanceof DATETIME)
			{
				int len = bb.getInt();
				byte[] byteval = new byte[len];
				bb.get(byteval);
				type = new DATETIME("'" + (new String(byteval)) + "'");
			}
			if (type instanceof VARCHAR)
			{
				int len = bb.getInt();
				byte[] byteval = new byte[len];
				bb.get(byteval);
				try
				{
					type = new VARCHAR(((VARCHAR) type).getCapacity(), "'" + (new String(byteval)) + "'");
				} catch (Exception e)
				{
					e.printStackTrace();
				}
			}
			if (type instanceof DECIMAL)
			{
				int len = bb.getInt();
				byte[] byteval = new byte[len];
				bb.get(byteval);
				type = new DECIMAL(((DECIMAL) type).getEffective(), ((DECIMAL) type).getDecimalSetting(), new String(byteval));
			}
			if (type instanceof FLOAT)
			{
				type = new FLOAT(bb.getFloat());
			}
			if (type instanceof INT)
			{
				type = new INT(bb.getInt());
			}
			if (type instanceof TIMESTAMP)
			{
				int len = bb.getInt();
				byte[] byteval = new byte[len];
				bb.get(byteval);
				type = new TIMESTAMP("'" + (new String(byteval)) + "'");
			}
			list.add(type);
		}
		schema = sche;
		values = list;
	}

	public ByteBuffer toByteBuffer()
	{
		ByteBuffer bb = ByteBuffer.allocateDirect(FatwormDB.BLOCK_SIZE);
		int end = FatwormDB.BLOCK_SIZE;
		int top = 12;
		for (Type type : values)
		{
			if (type == null)
			{
				bb.putInt(top, -1);
				top += 4;
				continue;
			}
			end = end - type.length();
			bb.putInt(top, end);
			top += 4;
			if (type.isNULL())
			{
				bb.putChar(end, 'n');
				continue;
			}
			if (type instanceof BOOLEAN)
			{
				if (type.toString().startsWith("f")) bb.putChar(end, 'f');
				if (type.toString().startsWith("t")) bb.putChar(end, 't');
			}
			if (type instanceof CHAR)
			{
				bb.putInt(end, type.toString().length());
				bb.position(end + 4);
				bb.put(type.toString().getBytes());
			}
			if (type instanceof DATETIME)
			{
				bb.putInt(end, type.toString().length());
				bb.position(end + 4);
				bb.put(type.toString().getBytes());
			}
			if (type instanceof VARCHAR)
			{
				bb.putInt(end, type.toString().length());
				bb.position(end + 4);
				bb.put(type.toString().getBytes());
			}
			if (type instanceof DECIMAL)
			{
				bb.putInt(end, type.toString().length());
				bb.position(end + 4);
				bb.put(type.toString().getBytes());
			}
			if (type instanceof FLOAT)
			{
				bb.putFloat(end, ((FLOAT) type).getFLOAT());
			}
			if (type instanceof INT)
			{
				bb.putInt(end, ((INT) type).getINT());
			}
			if (type instanceof TIMESTAMP)
			{
				bb.putInt(end, type.toString().length());
				bb.position(end + 4);
				bb.put(type.toString().getBytes());
			}
		}
		return bb;
	}

	public Record rename(String name)
	{
		Schema s = schema.rename(name);
		return new Record(values, s);
	}

	public int length()
	{
		int ret = 0;
		for (Column c : schema.getColumns())
		{
			ret += (c.getType().length() + 5);
		}
		return ret;
	}
}
