package fatworm.index;

import java.io.Serializable;
import java.util.List;

import fatworm.types.Type;

public interface Index extends Serializable
{
	public void drop();

	public void beforeFirst();

	public boolean next();

	public void insert(Type dataval, Integer integer);

	public void delete(Type dataval, Integer integer);

	List<Integer> getRecords();

	void between(Type type1, Type type2);

	String getColumn();
}
