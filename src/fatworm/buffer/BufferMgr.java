package fatworm.buffer;

import java.util.LinkedList;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import fatworm.FatwormDB;
import fatworm.FatwormException;
import fatworm.database.DataBase;
import fatworm.database.Record;
import fatworm.database.Schema;
import fatworm.file.Block;
import fatworm.types.*;

public class BufferMgr
{
	static Buffer[] buffers;

	// filename+blockNum => index in findTree
	ConcurrentSkipListMap<String, Integer> findTree = new ConcurrentSkipListMap<String, Integer>();
	
	ConcurrentLinkedQueue<Integer> lruQueue = new ConcurrentLinkedQueue<Integer>();
	
	private ReentrantReadWriteLock rwLock = new ReentrantReadWriteLock();

	protected Lock readLock = rwLock.readLock();

	protected Lock writeLock = rwLock.writeLock();

	public BufferMgr()
	{
		buffers = new Buffer[FatwormDB.BUFFER_SIZE];
		for (int i = 0; i < FatwormDB.BUFFER_SIZE; i++)
		{
			buffers[i] = new Buffer();
			lruQueue.add(i);
		}
	}

	public void flushAll()
	{
		writeLock.lock();
		LinkedList<String> list = new LinkedList<String>();
		for (String i : findTree.keySet())
		{
			list.add(i);
		}
		for (String i : list)
			buffers[findTree.get(i)].flush();
		writeLock.unlock();
	}

	public void writeAll()
	{
		readLock.lock();
		LinkedList<String> list = new LinkedList<String>();
		for (String i : findTree.keySet())
		{
			list.add(i);
		}
		for (String i : list)
			buffers[findTree.get(i)].write();
		readLock.unlock();
	}

	private Buffer getLRUBuffer(Block blk)
	{
		int i = lruQueue.poll();
		Block block = buffers[i].block;
		if (block != null)
			findTree.remove(block.toString());
		buffers[i].assignToBlock(blk);
		findTree.put(blk.toString(), i);
		lruQueue.add(i);
		return buffers[i];
	}

	synchronized private Buffer getBufferAndHoldReadLock(Block blk)
	{
		Buffer buffer = getBuffer(blk);
		readLock.lock();
		while (buffer.block != null && !buffer.block.equals(blk))
		{
			readLock.unlock();
			buffer = getBuffer(blk);
			readLock.lock();
		}
		return buffer;
	}

	synchronized private Buffer getBuffer(Block blk)
	{
		writeLock.lock();
		String st = blk.toString();
		if (findTree.containsKey(st))
		{
			int i = findTree.get(st);
			lruQueue.remove(i);
			lruQueue.add(i);
			writeLock.unlock();
			return buffers[findTree.get(blk.toString())];
		}
		Buffer ret = getLRUBuffer(blk);
		writeLock.unlock();
		return ret;
	}

	public Record get(String name, int place)
	{
		Schema sche = DataBase.getDataBase().getTable(name).getSchema();
		Block block = new Block(name, place / FatwormDB.BLOCK_SIZE);
		Buffer buffer = getBufferAndHoldReadLock(block);
		buffer.readLock.lock();
		LinkedList<Type> list = new LinkedList<Type>();
		int top = place % FatwormDB.BLOCK_SIZE;
		for (int i = 0; i < sche.getColumns().size(); ++i)
		{
			Type type = sche.getColumn(i).getType();
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
				} catch (FatwormException e)
				{
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
				} catch (FatwormException e)
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
		buffer.readLock.unlock();
		readLock.unlock();
		return new Record(list, sche);
	}

	public int write(String name, int place, Record record)
	{
		Schema sche = DataBase.getDataBase().getTable(name).getSchema();
		int top = place % FatwormDB.BLOCK_SIZE;
		Block block;
		Buffer buffer = null;
		int recordLength = record.length();
		if (top + recordLength >= FatwormDB.BLOCK_SIZE)
		{
			block = new Block(name, place / FatwormDB.BLOCK_SIZE + 1);
			place = place - place % FatwormDB.BLOCK_SIZE + FatwormDB.BLOCK_SIZE;
			top = 0;
		}
		else
		{
			block = new Block(name, place / FatwormDB.BLOCK_SIZE);
			place = place += recordLength;
		}
		buffer = getBufferAndHoldReadLock(block);
		buffer.writeLock.lock();
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
		if (place % FatwormDB.BLOCK_SIZE + recordLength >= FatwormDB.BLOCK_SIZE)
		{
			place = place - place % FatwormDB.BLOCK_SIZE + FatwormDB.BLOCK_SIZE;
		}
		if (FatwormDB.durability)
			buffer.write();
		buffer.writeLock.unlock();
		readLock.unlock();
		return place;
	}
}
