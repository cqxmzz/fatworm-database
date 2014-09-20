package fatworm.plan.index;

import java.util.LinkedList;

import fatworm.database.Schema;
import fatworm.index.Index;
import fatworm.plan.Plan;
import fatworm.scan.IndexSelectScan;
import fatworm.scan.Scan;
import fatworm.types.Type;

public class IndexSelectPlan implements Plan
{
	private Plan plan;

	Plan fatherPlan;

	String column;

	Type leftType;

	Type rightType;

	Index index;

	public IndexSelectPlan(Plan p, String string, Type constantType, Type constantType2, Index index)
	{
		plan = p;
		plan.setFather(this);
		column = string;
		leftType = constantType;
		rightType = constantType2;
		this.index = index;
	}

	@Override
	public Scan open()
	{
		Scan scan = plan.open();
		return new IndexSelectScan(scan, column, leftType, rightType, index);
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
		return "IndexSelect from " + plan.tostring() + " where " + leftType + rightType;
	}
}
