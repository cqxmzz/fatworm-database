package fatworm.plan.index;

import fatworm.FatwormException;
import fatworm.database.Record;
import fatworm.database.Table;
import fatworm.index.Index;
import fatworm.metadata.MetadataMgr;
import fatworm.plan.DeletePlan;
import fatworm.plan.Plan;
import fatworm.plan.SelectPlan;
import fatworm.pred.Predicate;
import fatworm.scan.UpdateScan;

public class IndexDeletePlan extends DeletePlan
{
	public IndexDeletePlan(Table t, Predicate p) throws FatwormException
	{
		super(t, p);
	}

	public int execute() throws FatwormException
	{
		Plan p;
		UpdateScan scan;
		if (predicate != null)
		{
			p = new SelectPlan(plan, predicate);
			scan = (UpdateScan) p.open();
		}
		else
		{
			p = plan;
			scan = (UpdateScan) p.open();
		}
		int count = 0;
		MetadataMgr.readLock.lock();
		while (scan.next())
		{
			Record record = scan.getRecord();
			int deletePlace = scan.delete();
			count++;
			for (Index index : table.indexes.values())
			{
				if (index != null)
				{
					index.delete(record.getValue(index.getColumn()), deletePlace);
				}
			}
		}
		scan.close();
		MetadataMgr.readLock.unlock();
		return count;
	}
}
