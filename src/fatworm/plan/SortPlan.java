package fatworm.plan;

import java.util.LinkedList;
import java.util.List;

import fatworm.database.Schema;
import fatworm.scan.Scan;
import fatworm.scan.SortScan;

public class SortPlan implements Plan
{
	List<String> col_name;

	List<String> tbl_name;

	List<Boolean> ascent;

	Plan fatherPlan = null;

	Plan plan;

	public SortPlan(Plan p, List<Boolean> a, List<String> c, List<String> t)
	{
		plan = p;
		plan.setFather(this);
		ascent = a;
		col_name = c;
		tbl_name = t;
	}

	@Override
	public Scan open()
	{
		return new SortScan(col_name, tbl_name, ascent, plan.open(), plan.schema());
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
		return "Sort " + plan.tostring();
	}
}
