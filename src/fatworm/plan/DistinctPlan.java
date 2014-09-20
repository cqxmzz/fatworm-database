package fatworm.plan;

import java.util.LinkedList;

import fatworm.database.Schema;
import fatworm.scan.DistinctScan;
import fatworm.scan.Scan;

public class DistinctPlan implements Plan
{
	Plan plan;

	Plan fatherPlan = null;

	public DistinctPlan(Plan p)
	{
		plan = p;
		plan.setFather(this);
	}

	@Override
	public Scan open()
	{
		return new DistinctScan(plan.open());
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
		return "Distinct" + plan.tostring();
	}
}
