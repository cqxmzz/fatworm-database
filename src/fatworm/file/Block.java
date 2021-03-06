package fatworm.file;

public class Block
{
	private String fileName;

	private int blkNumber;

	public Block(String fileName, int blkNum)
	{
		this.fileName = fileName;
		this.blkNumber = blkNum;
	}

	public String fileName()
	{
		return fileName;
	}

	public int blkNumber()
	{
		return blkNumber;
	}

	public boolean equals(Object obj)
	{
		if (obj instanceof Block)
		{
			Block blk = (Block) obj;
			return fileName.equals(blk.fileName) && blkNumber == blk.blkNumber;
		}
		return false;
	}

	public String toString()
	{
		return "[file " + fileName + ", block " + blkNumber + "]";
	}
}
