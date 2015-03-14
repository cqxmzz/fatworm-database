package fatworm.scan;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.LinkedList;

import fatworm.FatwormDB;
import fatworm.database.DataBase;
import fatworm.database.Schema;
import fatworm.database.Table;
import fatworm.metadata.MetadataMgr;
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

	HashMap<Type, Integer> map;

	LinkedList<Type> keys;

	int now = -1;

	int size;
	
	TableScan tableScan;
	
	Table tempTable;

	public GroupScan(Schema s, Scan sc, String groupColName, String groupTableName)
	{
		schema = s;
		scan = sc;
		map = new HashMap<Type, Integer>();
		colname = groupColName;
		tblname = groupTableName;
		scan.beforeFirst();
		keys = new LinkedList<Type>();
		boolean flag = true;
		
		synchronized (FatwormDB.mdMgr().tempTableNum)
		{
			tempTable = Table.createTable("TEMP_" + FatwormDB.mdMgr().tempTableNum, schema);
			FatwormDB.mdMgr().tempTableNum = (FatwormDB.mdMgr().tempTableNum + 1) % FatwormDB.MAX_TEMP_TABLE_COUNT;
		}
		tableScan = new TableScan(tempTable);
		
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
			int place = tableScan.insert(new Record(vals, schema));
			map.put(null, place);
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
					int place = tableScan.insert(new Record(vals, schema));
					map.put(type, place);
					keys.add(type);
				}
				else
				{
					Record record = tableScan.getRecordFromPlace(map.get(type));
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
					tableScan.setRecordFromPlace(map.get(type), record);
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
						{
							Object object = scan.getVal(st.substring(4, st.length() - 1)).getValue();
							if (object instanceof Integer) vals.add(new DECIMAL(((Integer) object).floatValue()));
							if (object instanceof Float) vals.add(new DECIMAL((Float) object));
							if (object instanceof BigDecimal) vals.add(new DECIMAL((BigDecimal) object));
						}
						else
						{
							vals.add(scan.getVal(st));
						}
					}
					int place = tableScan.insert(new Record(vals, schema));
					map.put(null, place);
					keys.add(null);
				}
				else
				{
					Record record = tableScan.getRecordFromPlace(map.get(null));
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
					tableScan.setRecordFromPlace(map.get(null), record);
				}
			}
		}
		if (flag) for (Integer integer : map.values())
		{
			Record record = tableScan.getRecordFromPlace(integer);
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
			tableScan.setRecordFromPlace(integer, record);
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
		tableScan.close();
		DataBase.getDataBase().removeTable(tableScan.table);
	}

	@Override
	public Type getVal(String fldname)
	{
		return getRecord().getValue(fldname);
	}

	@Override
	public Record getRecord()
	{
		return tableScan.getRecordFromPlace(map.get(keys.get(now)));
	}

	@Override
	public Type getVal(String tblname, String fldname)
	{
		return getRecord().getValue(fldname, tblname);
	}

	@Override
	public Type getFirstVal()
	{
		return getRecord().getValue(0);
	}

	@Override
	public int getRecordLength()
	{
		return schema.getColumns().size();
	}

	@Override
	public Type getVal(int i)
	{
		return getRecord().getValue(i);
	}
}
