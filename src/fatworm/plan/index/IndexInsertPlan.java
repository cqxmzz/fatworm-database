package fatworm.plan.index;

import fatworm.database.Table;
import fatworm.index.Index;
import fatworm.plan.InsertPlan;
import fatworm.plan.Plan;
import fatworm.plan.TablePlan;
import fatworm.record.Record;
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

	public int execute() throws Exception
	{
		plan = new TablePlan(table);
		TableScan tableScan = (TableScan) plan.open();
		Scan scan = plan2.open();
		while (scan.next())
		{
			Record record = scan.getRecord();
			for (int j = 0; j < record.getValues().size(); ++j)
			{
				if (record.getValue(j) instanceof CHAR && plan.schema().getIndex(j).getType() instanceof VARCHAR)
				{
					Type type = plan.schema().getIndex(j).getType();
					record.setValue(j, new VARCHAR(((VARCHAR) type).getCapacity(), "'" + ((CHAR) record.getValue(j)).getCHAR() + "'"));
					record.schema().getIndex(j).setType(type);
				}
			}
			record.setSchema(plan.schema());
			tableScan.insert(record);
			for (Index index : table.indexes.values())
			{
				if (index != null)
				{
					index.insert(record.getValue(index.getColumn()), record);
				}
			}
		}
		return 1;
	}
}
