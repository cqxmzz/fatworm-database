package fatworm.scan;

import fatworm.types.Type;

public interface UpdateScan extends Scan
{
	public boolean setVal(String tblname, String fldname, Type type);

	public boolean setVal(String fldname, Type type);

	public void delete();

	public void moveToBottom();
}
