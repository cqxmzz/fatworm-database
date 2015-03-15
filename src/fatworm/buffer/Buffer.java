package fatworm.buffer;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import fatworm.FatwormDB;
import fatworm.file.Block;
import fatworm.file.Page;

public class Buffer
{
	Page page = new Page();

	Block block = null;

	boolean dirty = false;
	
	ReentrantReadWriteLock rwLock = new ReentrantReadWriteLock();
	protected Lock readLock = rwLock.readLock();
	protected Lock writeLock = rwLock.writeLock();

	public Buffer()
	{
	}

	public int getInt(int offset)
	{
		return page.getInt(offset);
	}

	public void setInt(int offset, int val)
	{
		page.setInt(offset, val);
		dirty = true;
	}

	public String getString(int offset)
	{
		return page.getString(offset);
	}

	public void setString(int offset, String val)
	{
		page.setString(offset, val);
		dirty = true;
	}

	public float getFloat(int offset)
	{
		return page.getFloat(offset);
	}

	public void setFloat(int offset, float val)
	{
		page.setFloat(offset, val);
		dirty = true;
	}

	public Block block()
	{
		return block;
	}

	void flush()
	{
		if (block != null && dirty)
		{
			page.write(block);
		}
		if (block != null) FatwormDB.bufferMgr().findTree.remove(block.toString());
		block = null;
		page.flush();
		dirty = false;
	}
	
	void write()
	{
		if (block != null && dirty)
		{
			page.write(block);
		}
		dirty = false;
	}

	void assignToBlock(Block b)
	{
		if (block != null && !b.equals(block))
		{
			flush();
		}
		block = b;
		page.read(block);
	}
}
