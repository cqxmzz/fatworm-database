package fatworm.scan;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import antlr.MakeGrammar;

import com.sun.org.apache.xalan.internal.xsltc.compiler.Template;

import fatworm.FatwormDB;
import fatworm.database.DataBase;
import fatworm.database.Schema;
import fatworm.database.Table;
import fatworm.metadata.MetadataMgr;
import fatworm.record.Record;
import fatworm.types.Type;

public class SortScan implements Scan
{
	List<String> col_name;

	List<String> tbl_name;

	List<Boolean> ascent;
	
	ArrayList<Integer> sort_index = new ArrayList<Integer>();

	Scan scan;

	int now = -1;

	int size;
	
	TableScan tableScan;

	@SuppressWarnings("rawtypes")
	class Sorter implements Comparator
	{
		List<String> cols;

		List<String> tbls;

		List<Boolean> ascs;

		public Sorter(List<String> c, List<String> t, List<Boolean> a, TableScan tableScan)
		{
			cols = c;
			tbls = t;
			ascs = a;
		}

		public int compare(Object obj1, Object obj2)
		{
			Integer point1 = (Integer) obj1;
			Integer point2 = (Integer) obj2;
			Record r1 = FatwormDB.bufferMgr().get(tableScan.table.name, point1);
			Record r2 = FatwormDB.bufferMgr().get(tableScan.table.name, point2); 			
			for (int i = 0; i < ascs.size(); ++i)
			{
				if (ascs.get(i))
				{
					if (r1.getValue(cols.get(i), tbls.get(i)).greaterThan(r2.getValue(cols.get(i), tbls.get(i))))
					{
						return 1;
					}
					if (r1.getValue(cols.get(i), tbls.get(i)).lessThan(r2.getValue(cols.get(i), tbls.get(i))))
					{
						return 0;
					}
				}
				else
				{
					if (r1.getValue(cols.get(i), tbls.get(i)).lessThan(r2.getValue(cols.get(i), tbls.get(i))))
					{
						return 1;
					}
					if (r1.getValue(cols.get(i), tbls.get(i)).greaterThan(r2.getValue(cols.get(i), tbls.get(i))))
					{
						return 0;
					}
				}
			}
			return 0;
		}
	}

	@SuppressWarnings("unchecked")
	public SortScan(List<String> c, List<String> t, List<Boolean> a, Scan s, Schema sc)
	{
		col_name = c;
		tbl_name = t;
		ascent = a;
		
		scan = s;
		scan.beforeFirst();
		//TODO make counting of the tempTableNum synchronized
		Table table = Table.createTable("TEMP_" + MetadataMgr.tempTableNum, sc);
		MetadataMgr.tempTableNum++;
		tableScan = new TableScan(table);
		while (scan.next())
		{
			tableScan.insert(scan.getRecord());
		}
		size = tableScan.size();
		sort_index.addAll(tableScan.table.places);
		Collections.sort(sort_index, new Sorter(col_name, tbl_name, ascent, tableScan));
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
		FatwormDB.mdMgr();
		MetadataMgr.tempTableNum++;
	}

	@Override
	public Type getVal(String fldname)
	{
		return tableScan.getRecordFromPlace(sort_index.get(now)).getValue(fldname);
	}

	@Override
	public int getRecordLength()
	{
		return scan.getRecordLength();
	}

	@Override
	public Type getVal(int i)
	{
		return tableScan.getRecordFromPlace(sort_index.get(now)).getValue(i);
	}

	@Override
	public Type getFirstVal()
	{
		return tableScan.getRecordFromPlace(sort_index.get(now)).getValue(0);
	}

	@Override
	public Record getRecord()
	{
		return tableScan.getRecordFromPlace(sort_index.get(now));
	}

	@Override
	public Type getVal(String tblname, String fldname)
	{
		return tableScan.getRecordFromPlace(sort_index.get(now)).getValue(fldname, tblname);
	}
}
