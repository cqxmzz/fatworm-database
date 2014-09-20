package fatworm.file;

import java.nio.ByteBuffer;

import fatworm.FatwormDB;

public class Page
{
	private byte[] bytes;

	static private ByteBuffer contents = ByteBuffer.allocateDirect(FatwormDB.BLOCK_SIZE);

	private FileMgr filemgr;

	public void flush()
	{
		bytes = new byte[FatwormDB.BLOCK_SIZE];
		contents = ByteBuffer.allocateDirect(FatwormDB.BLOCK_SIZE);
	}

	public Page()
	{
		bytes = new byte[FatwormDB.BLOCK_SIZE];
		filemgr = FatwormDB.fileMgr();
	}

	public void read(Block blk)
	{
		filemgr.read(blk, contents);
		contents.position(0);
		contents.get(bytes);
	}

	public void write(Block blk)
	{
		contents.position(0);
		contents.put(bytes);
		filemgr.write(blk, contents);
	}

	public static void putInt(byte[] bb, int num, int index)
	{
		for (int i = 0; i < 4; i++)
		{
			bb[index + i] = (byte) (num >>> (24 - i * 8));
		}
	}

	public static int getInt(byte[] data, int offset)
	{
		int num = 0;
		for (int i = offset; i < offset + 4; ++i)
		{
			num <<= 8;
			num |= (data[i] & 0xff);
		}
		return num;
	}

	public static void putFloat(byte[] bb, float x, int index)
	{
		int l = Float.floatToIntBits(x);
		for (int i = 0; i < 4; i++)
		{
			bb[index + i] = new Integer(l).byteValue();
			l = l >> 8;
		}
	}

	public static float getFloat(byte[] b, int index)
	{
		int l;
		l = b[index + 0];
		l &= 0xff;
		l |= ((long) b[index + 1] << 8);
		l &= 0xffff;
		l |= ((long) b[index + 2] << 16);
		l &= 0xffffff;
		l |= ((long) b[index + 3] << 24);
		return Float.intBitsToFloat(l);
	}

	public int getInt(int offset)
	{
		return getInt(bytes, offset);
	}

	public void setInt(int offset, int val)
	{
		putInt(bytes, val, offset);
	}

	public float getFloat(int offset)
	{
		return getFloat(bytes, offset);
	}

	public void setFloat(int offset, float val)
	{
		putFloat(bytes, val, offset);
	}

	public String getString(int offset)
	{
		int len = getInt(offset);
		byte[] byteval = new byte[len];
		for (int i = 0; i < len; ++i)
		{
			byteval[i] = bytes[offset + 4 + i];
		}
		return new String(byteval);
	}

	public void setString(int offset, String val)
	{
		byte[] byteval = val.getBytes();
		setInt(offset, byteval.length);
		for (int i = 0; i < byteval.length; ++i)
		{
			bytes[offset + 4 + i] = byteval[i];
		}
	}
}
