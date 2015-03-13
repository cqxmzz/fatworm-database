package fatworm.scan;

import fatworm.types.Type;

public interface UpdateScan extends Scan
{
	public boolean setVal(String tblname, String fldname, Type type);

	public int setVal(String fldname, Type type);

	public int delete();

	//public void moveToBottom();
	
	//TODO add concurrency, not only within query(don't crash the iterator), also among query(delete? update?)
	//So maybe we need a list of all the opened scans in DB for concurrency
}
