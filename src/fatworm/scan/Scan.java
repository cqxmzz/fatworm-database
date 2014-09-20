package fatworm.scan;

import fatworm.record.Record;
import fatworm.types.Type;

public interface Scan
{
	/**
	 * Positions the scan before its first record.
	 */
	public void beforeFirst();

	/**
	 * Moves the scan to the next record.
	 * 
	 * @return false if there is no next record
	 */
	public boolean next();

	/**
	 * Closes the scan and its subscans, if any.
	 */
	public void close();

	/**
	 * Returns the value of the specified field in the current record. The value
	 * is expressed as a Constant.
	 * 
	 * @param fldname
	 *            the name of the field
	 * @return the value of that field, expressed as a Constant.
	 */
	public Type getVal(String fldname);

	public int getRecordLength();

	public Type getVal(int i);

	public Type getFirstVal();

	/**
	 * Returns the current record for insert. The value is expressed as a
	 * InsertRecord.
	 */
	public Record getRecord();

	Type getVal(String tblname, String fldname);
}
