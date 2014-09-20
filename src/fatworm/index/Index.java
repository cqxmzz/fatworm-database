package fatworm.index;

import java.util.List;

import fatworm.file.Block;
import fatworm.record.Record;
import fatworm.types.Type;

public interface Index
{
	public void drop();

	public void beforeFirst();

	public boolean next();

	public Block getDataRid();

	public void insert(Type dataval, Record record);

	public void delete(Type dataval, Record record);

	List<Record> getRecords();

	void between(Type type1, Type type2);

	String getColumn();
}
