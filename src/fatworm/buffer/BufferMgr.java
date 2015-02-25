package fatworm.buffer;

import java.nio.ByteBuffer;
import java.util.LinkedList;
import java.util.Random;
import java.util.TreeMap;

import fatworm.FatwormDB;
import fatworm.database.DataBase;
import fatworm.database.Schema;
import fatworm.file.Block;
import fatworm.record.Record;
import fatworm.types.BOOLEAN;
import fatworm.types.CHAR;
import fatworm.types.DATETIME;
import fatworm.types.DECIMAL;
import fatworm.types.FLOAT;
import fatworm.types.INT;
import fatworm.types.TIMESTAMP;
import fatworm.types.Type;
import fatworm.types.VARCHAR;

public class BufferMgr
{
	static Buffer[] buffers;

	TreeMap<String, Integer> findTree = new TreeMap<String, Integer>();

	Random rand = new Random();

	public BufferMgr()
	{
		buffers = new Buffer[FatwormDB.BUFFER_SIZE];
		for (int i = 0; i < FatwormDB.BUFFER_SIZE; i++)
		{
			buffers[i] = new Buffer();
		}
	}

	public void flushAll()
	{
		LinkedList<String> list = new LinkedList<String>();
		for (String i : findTree.keySet())
		{
			list.add(i);
		}
		for (String i : list)
			buffers[findTree.get(i)].flush();
	}
	
	public void writeAll()
	{
		LinkedList<String> list = new LinkedList<String>();
		for (String i : findTree.keySet())
		{
			list.add(i);
		}
		for (String i : list)
			buffers[findTree.get(i)].write();
	}

	Buffer getLRUBuffer(Block blk)
	{
		int i = rand.nextInt(FatwormDB.BUFFER_SIZE - 1);
		if (buffers[i].dirty)
		{
			buffers[i].flush();
		}
		buffers[i].assignToBlock(blk);
		findTree.put(blk.toString(), i);
		return buffers[i];
	}

	public Buffer getBuffer(Block blk)
	{
		if (findTree.containsKey(blk.toString()))
		{
			return buffers[findTree.get(blk.toString())];
		}
		Buffer ret = getLRUBuffer(blk);
		return ret;
	}

	public void deleteRecord(Buffer buf)
	{
		buf.setInt(0, 0);
	}

	public Buffer append(String filename, ByteBuffer bb)
	{
		if (FatwormDB.fileMgr().size(filename) == 0)
		{
			Block blk = new Block(filename, 1);
			FatwormDB.fileMgr().write(blk, bb);
			Buffer ret = getBuffer(blk);
			ret.setInt(0, 1);
			return ret;
		}
		else
		{
			Block blk = new Block(filename, FatwormDB.fileMgr().size(filename));
			FatwormDB.fileMgr().write(blk, bb);
			Buffer ret = getBuffer(blk);
			ret.setInt(0, 1);
			return ret;
		}
	}

	public Buffer Tempappend(String filename, ByteBuffer bb)
	{
		if (FatwormDB.fileMgr().Tempsize(filename) == 0)
		{
			Block blk = new Block(filename, 1);
			FatwormDB.fileMgr().Tempwrite(blk, bb);
			Buffer ret = getBuffer(blk);
			ret.setInt(0, 1);
			return ret;
		}
		else
		{
			Block blk = new Block(filename, FatwormDB.fileMgr().Tempsize(filename));
			FatwormDB.fileMgr().Tempwrite(blk, bb);
			Buffer ret = getBuffer(blk);
			ret.setInt(0, 1);
			return ret;
		}
	}

	public Buffer getHead(String filename)
	{
		Buffer head = getBuffer(new Block(filename, 0));
		if (FatwormDB.fileMgr().size(filename) == 0)
		{
			head.setInt(0, 0);
		}
		return head;
	}

	public Buffer getNextRecord(Buffer buf)
	{
		Block blk = buf.block();
		while (blk.blkNumber() < FatwormDB.fileMgr().size(blk.fileName()))
		{
			Buffer ret = getBuffer(new Block(blk.fileName(), blk.blkNumber() + 1));
			if (ret.getInt(0) == 1) return ret;
			blk = ret.block();
		}
		return null;
	}

	public int insert(String name, Record r) // return new tail
	{
		return write(name, DataBase.getDataBase().getTable(name).tail, r);
	}

	public Record get(String name, int integer)
	{
		Schema sche = DataBase.getDataBase().getTable(name).getSchema();
		Block block = new Block(name, integer / FatwormDB.BLOCK_SIZE);
		Buffer buffer = getBuffer(block);
		LinkedList<Type> list = new LinkedList<Type>();
		int top = integer % FatwormDB.BLOCK_SIZE;
		for (int i = 0; i < sche.getColumns().size(); ++i)
		{
			Type type = sche.getIndex(i).getType();
			int isnull = buffer.getInt(top);
			if (isnull == 1)
			{
				list.add(type);
				top += (5 + type.length());
				continue;
			}
			top += 5;
			int temp = type.length();
			if (type instanceof BOOLEAN)
			{
				type = new BOOLEAN(buffer.getString(top));
			}
			if (type instanceof CHAR)
			{
				String string = buffer.getString(top);
				try
				{
					type = new CHAR(((CHAR) type).getCapacity(), string);
				} catch (Exception e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (type instanceof DATETIME)
			{
				String string = buffer.getString(top);
				type = new DATETIME(string);
			}
			if (type instanceof VARCHAR)
			{
				String string = buffer.getString(top);
				try
				{
					type = new VARCHAR(((VARCHAR) type).getCapacity(), string);
				} catch (Exception e)
				{
					e.printStackTrace();
				}
			}
			if (type instanceof DECIMAL)
			{
				String string = buffer.getString(top);
				type = new DECIMAL(((DECIMAL) type).getEffective(), ((DECIMAL) type).getDecimalSetting(), string);
			}
			if (type instanceof FLOAT)
			{
				type = new FLOAT(buffer.getFloat(top));
			}
			if (type instanceof INT)
			{
				type = new INT(buffer.getInt(top));
			}
			if (type instanceof TIMESTAMP)
			{
				String string = buffer.getString(top);
				type = new TIMESTAMP(string);
			}
			list.add(type);
			top += temp;
		}
		return new Record(list, sche);
	}

	public int write(String name, int integer, Record record)
	{
		Schema sche = DataBase.getDataBase().getTable(name).getSchema();
		int top = integer % FatwormDB.BLOCK_SIZE;
		Block block;
		Buffer buffer;
		//TODO don't compute record length every time, use schema instead, and schema can cache the length
		if (top + record.length() >= FatwormDB.BLOCK_SIZE)
		{
			block = new Block(name, integer / FatwormDB.BLOCK_SIZE + 1);
			buffer = getBuffer(block);
			integer = integer - integer % FatwormDB.BLOCK_SIZE + FatwormDB.BLOCK_SIZE;
			top = 0;
		}
		else
		{
			block = new Block(name, integer / FatwormDB.BLOCK_SIZE);
			buffer = getBuffer(block);
			integer = integer += record.length();
		}
		for (int i = 0; i < sche.getColumns().size(); ++i)
		{
			Type type = record.getValue(i);
			if (type.isNULL())
			{
				buffer.setInt(top, 1);
				top += (5 + type.length());
				continue;
			}
			else
			{
				buffer.setInt(top, 0);
				top += 5;
			}
			if (type instanceof BOOLEAN)
			{
				if (type.toString().startsWith("f")) buffer.setString(top, "f");
				if (type.toString().startsWith("t")) buffer.setString(top, "t");
			}
			if (type instanceof CHAR)
			{
				buffer.setString(top, type.toString());
			}
			if (type instanceof DATETIME)
			{
				buffer.setString(top, type.toString());
			}
			if (type instanceof VARCHAR)
			{
				buffer.setString(top, type.toString());
			}
			if (type instanceof DECIMAL)
			{
				buffer.setString(top, type.toString());
			}
			if (type instanceof FLOAT)
			{
				buffer.setFloat(top, ((FLOAT) type).getFLOAT());
			}
			if (type instanceof INT)
			{
				buffer.setInt(top, ((INT) type).getINT());
			}
			if (type instanceof TIMESTAMP)
			{
				buffer.setString(top, type.toString());
			}
			top += sche.getColumns().get(i).getType().length();
		}
		if (integer % FatwormDB.BLOCK_SIZE + record.length() >= FatwormDB.BLOCK_SIZE)
		{
			integer = integer - integer % FatwormDB.BLOCK_SIZE + FatwormDB.BLOCK_SIZE;
		}
		return integer;
	}
}
