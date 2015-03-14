package fatworm.plan;

import java.util.LinkedList;

import fatworm.FatwormDB;
import fatworm.database.Schema;
import fatworm.database.Table;
import fatworm.metadata.MetadataMgr;
import fatworm.pred.Predicate;
import fatworm.scan.Scan;
import fatworm.scan.UpdateScan;

public class DeletePlan implements Plan
{
	protected Table table;// to be deleted

	protected Plan plan;// to be deleted

	protected Predicate predicate;

	public DeletePlan(Table t, Predicate p) throws Exception
	{
		table = t;
		plan = new TablePlan(table);
		plan.setFather(this);
		predicate = p;
	}

	public int execute() throws Exception
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
			scan.delete();
			count++;
		}
		scan.close();
		MetadataMgr.readLock.unlock();
		return count;
	}

	@Override
	public Scan open()
	{
		return plan.open();
	}

	@Override
	public Schema schema()
	{
		return plan.schema();
	}

	@Override
	public LinkedList<Plan> son()
	{
		LinkedList<Plan> ret = new LinkedList<Plan>();
		ret.add(plan);
		return ret;
	}

	@Override
	public void setSon(LinkedList<Plan> plans)
	{
		plan = plans.get(0);
	}

	@Override
	public Plan father()
	{
		return null;
	}

	@Override
	public void setFather(Plan plan)
	{
	}

	@Override
	public String tostring()
	{
		return "Delete " + plan.tostring() + " where " + predicate.toString();
	}
}
