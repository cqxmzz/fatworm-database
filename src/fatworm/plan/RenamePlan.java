package fatworm.plan;

import java.util.LinkedList;

import fatworm.database.Schema;
import fatworm.scan.RenameScan;
import fatworm.scan.Scan;

public class RenamePlan implements Plan
{
	Plan plan;

	Plan fatherPlan;

	String name;

	public RenamePlan(Plan p, String newName)
	{
		plan = p;
		plan.setFather(this);
		name = newName;
	}

	@Override
	public Scan open()
	{
		Scan scan = plan.open();
		return new RenameScan(scan, name);
	}

	@Override
	public Schema schema()
	{
		return plan.schema().rename(name);
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
		return fatherPlan;
	}

	@Override
	public void setFather(Plan plan)
	{
		fatherPlan = plan;
	}

	@Override
	public String tostring()
	{
		return "rename " + name + " " + plan.tostring();
	}
}
