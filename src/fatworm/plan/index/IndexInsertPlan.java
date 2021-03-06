package fatworm.plan.index;

import fatworm.FatwormException;
import fatworm.database.Record;
import fatworm.database.Table;
import fatworm.index.Index;
import fatworm.metadata.MetadataMgr;
import fatworm.plan.InsertPlan;
import fatworm.plan.Plan;
import fatworm.plan.TablePlan;
import fatworm.scan.Scan;
import fatworm.scan.TableScan;
import fatworm.types.CHAR;
import fatworm.types.Type;
import fatworm.types.VARCHAR;

public class IndexInsertPlan extends InsertPlan
{
	public IndexInsertPlan(Table t, Plan p)
	{
		super(t, p);
	}

	public int execute() throws FatwormException
	{
		plan = new TablePlan(table);
		TableScan tableScan = (TableScan) plan.open();
		Scan scan = plan2.open();
		MetadataMgr.readLock.lock();
		while (scan.next())
		{
			Record record = scan.getRecord();
			for (int j = 0; j < record.getValues().size(); ++j)
			{
				if (record.getValue(j) instanceof CHAR && plan.schema().getColumn(j).getType() instanceof VARCHAR)
				{
					Type type = plan.schema().getColumn(j).getType();
					record.setValue(j, new VARCHAR(((VARCHAR) type).getCapacity(), "'" + ((CHAR) record.getValue(j)).getCHAR() + "'"));
					record.schema().getColumn(j).setType(type);
				}
			}
			record.setSchema(plan.schema());
			int place = tableScan.insert(record);
			for (Index index : table.indexes.values())
			{
				if (index != null)
				{
					index.insert(record.getValue(index.getColumn()), place);
				}
			}
		}
		scan.close();
		MetadataMgr.readLock.unlock();
		return 1;
	}
}
