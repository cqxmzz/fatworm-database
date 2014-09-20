package fatworm.scan;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.LinkedList;

import fatworm.database.Schema;
import fatworm.record.Record;
import fatworm.types.DECIMAL;
import fatworm.types.INT;
import fatworm.types.Type;

public class GroupScan implements Scan
{
	Schema schema;

	Scan scan;

	String colname;

	String tblname;

	HashMap<Type, Record> map;

	LinkedList<Type> keys;

	int now = -1;

	int size;

	public GroupScan(Schema s, Scan sc, String groupColName, String groupTableName)
	{
		schema = s;
		scan = sc;
		map = new HashMap<Type, Record>();
		colname = groupColName;
		tblname = groupTableName;
		scan.beforeFirst();
		keys = new LinkedList<Type>();
		boolean flag = true;
		if (scan.next() == false)
		{
			flag = false;
			LinkedList<Type> vals = new LinkedList<Type>();
			for (int i = 0; i < schema.getNames().size(); ++i)
			{
				String st = schema.getName(i);
				if (st.startsWith("count("))
					vals.add(new INT(0));
				else if (st.startsWith("max("))
					vals.add(null);
				else if (st.startsWith("min("))
					vals.add(null);
				else if (st.startsWith("sum("))
					vals.add(null);
				else if (st.startsWith("avg("))
					vals.add(null);
				else
				{
					vals.add(scan.getVal(st));
				}
			}
			map.put(null, new Record(vals, schema));
			keys.add(null);
		}
		scan.beforeFirst();
		while (scan.next())
		{
			if (colname != null)
			{
				Type type = scan.getVal(tblname, colname);
				for (Type t : keys)
				{
					if (t.equalTo(type))
					{
						type = t;
						break;
					}
				}
				if (!map.containsKey(type))
				{
					LinkedList<Type> vals = new LinkedList<Type>();
					for (int i = 0; i < schema.getNames().size(); ++i)
					{
						String st = schema.getName(i);
						if (st.startsWith("count("))
							vals.add(new INT(1));
						else if (st.startsWith("max("))
							vals.add(scan.getVal(st.substring(4, st.length() - 1)));
						else if (st.startsWith("min("))
							vals.add(scan.getVal(st.substring(4, st.length() - 1)));
						else if (st.startsWith("sum("))
						{
							Object object = scan.getVal(st.substring(4, st.length() - 1)).getValue();
							if (object instanceof Integer) vals.add(new DECIMAL(((Integer) object).floatValue()));
							if (object instanceof Float) vals.add(new DECIMAL((Float) object));
							if (object instanceof BigDecimal) vals.add(new DECIMAL((BigDecimal) object));
						}
						else if (st.startsWith("avg("))
							vals.add(null);
						else
						{
							vals.add(scan.getVal(st));
						}
					}
					map.put(type, new Record(vals, schema));
					keys.add(type);
				}
				else
				{
					Record record = map.get(type);
					for (int i = 0; i < schema.getNames().size(); ++i)
					{
						String st = schema.getName(i);
						if (st.startsWith("count("))
							record.setValue(i, new INT(((INT) record.getValue(i)).getINT() + 1));
						else if (st.startsWith("max("))
						{
							if (scan.getVal(st.substring(4, st.length() - 1)).greaterThan(record.getValue(i)))
								record.setValue(i, scan.getVal(st.substring(4, st.length() - 1)));
							else
								record.setValue(i, record.getValue(i));
						}
						else if (st.startsWith("min("))
						{
							if (scan.getVal(st.substring(4, st.length() - 1)).lessThan(record.getValue(i)))
								record.setValue(i, scan.getVal(st.substring(4, st.length() - 1)));
							else
								record.setValue(i, record.getValue(i));
						}
						else if (st.startsWith("sum("))
						{
							Object object = scan.getVal(st.substring(4, st.length() - 1)).getValue();
							if (object instanceof Integer) record.setValue(i, record.getValue(i).add(new DECIMAL(((Integer) object).floatValue())));
							if (object instanceof Float) record.setValue(i, record.getValue(i).add(new DECIMAL((Float) object)));
							if (object instanceof BigDecimal) record.setValue(i, record.getValue(i).add(new DECIMAL((BigDecimal) object)));
						}
					}
				}
			}
			else
			{
				if (map.size() == 0)
				{
					LinkedList<Type> vals = new LinkedList<Type>();
					for (int i = 0; i < schema.getNames().size(); ++i)
					{
						String st = schema.getName(i);
						if (st.startsWith("count("))
							vals.add(new INT(1));
						else if (st.startsWith("max("))
							vals.add(scan.getVal(st.substring(4, st.length() - 1)));
						else if (st.startsWith("min("))
							vals.add(scan.getVal(st.substring(4, st.length() - 1)));
						else if (st.startsWith("sum("))
						{
							Object object = scan.getVal(st.substring(4, st.length() - 1)).getValue();
							if (object instanceof Integer) vals.add(new DECIMAL(((Integer) object).floatValue()));
							if (object instanceof Float) vals.add(new DECIMAL((Float) object));
							if (object instanceof BigDecimal) vals.add(new DECIMAL((BigDecimal) object));
						}
						else if (st.startsWith("avg("))
							vals.add(null);
						else
						{
							vals.add(scan.getVal(st));
						}
					}
					map.put(null, new Record(vals, schema));
					keys.add(null);
				}
				else
				{
					Record record = map.get(null);
					for (int i = 0; i < schema.getNames().size(); ++i)
					{
						String st = schema.getName(i);
						if (st.startsWith("count("))
							record.setValue(i, new INT(((INT) record.getValue(i)).getINT() + 1));
						else if (st.startsWith("max("))
						{
							if (scan.getVal(st.substring(4, st.length() - 1)).greaterThan(record.getValue(i)))
								record.setValue(i, scan.getVal(st.substring(4, st.length() - 1)));
							else
								record.setValue(i, record.getValue(i));
						}
						else if (st.startsWith("min("))
						{
							if (scan.getVal(st.substring(4, st.length() - 1)).lessThan(record.getValue(i)))
								record.setValue(i, scan.getVal(st.substring(4, st.length() - 1)));
							else
								record.setValue(i, record.getValue(i));
						}
						else if (st.startsWith("sum("))
						{
							Object object = scan.getVal(st.substring(4, st.length() - 1)).getValue();
							if (object instanceof Integer) record.setValue(i, record.getValue(i).add(new DECIMAL(((Integer) object).floatValue())));
							if (object instanceof Float) record.setValue(i, record.getValue(i).add(new DECIMAL((Float) object)));
							if (object instanceof BigDecimal) record.setValue(i, record.getValue(i).add(new DECIMAL((BigDecimal) object)));
						}
					}
				}
			}
		}
		if (flag) for (Record record : map.values())
		{
			for (int i = 0; i < schema.getNames().size(); ++i)
			{
				String st = schema.getName(i);
				if (st.startsWith("avg("))
				{
					int count = ((INT) (record.getValue("count(" + st.substring(4, st.length())))).getINT();
					Type type = record.getValue("sum(" + st.substring(4, st.length())).devideBy(count);
					record.setValue(i, type);
				}
			}
		}
		size = map.size();
	}

	@Override
	public void beforeFirst()
	{
		now = -1;
	}

	@Override
	public boolean next()
	{
		if (now < size - 1)
		{
			now++;
			return true;
		}
		return false;
	}

	@Override
	public void close()
	{
	}

	@Override
	public Type getVal(String fldname)
	{
		return map.get(keys.get(now)).getValue(fldname);
	}

	@Override
	public Record getRecord()
	{
		return map.get(keys.get(now));
	}

	@Override
	public Type getVal(String tblname, String fldname)
	{
		return map.get(keys.get(now)).getValue(fldname, tblname);
	}

	@Override
	public Type getFirstVal()
	{
		return map.get(keys.get(now)).getValue(0);
	}

	@Override
	public int getRecordLength()
	{
		return schema.getColumns().size();
	}

	@Override
	public Type getVal(int i)
	{
		return map.get(keys.get(now)).getValue(i);
	}
}
