package fatworm.scan;

import fatworm.types.Type;

public interface UpdateScan extends Scan
{
	public boolean setVal(String tblname, String fldname, Type type);

	public int setVal(String fldname, Type type);

	public int delete();

	//public void moveToBottom();
}
