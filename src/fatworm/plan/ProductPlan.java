package fatworm.plan;

import java.util.LinkedList;

import fatworm.database.Schema;
import fatworm.scan.ProductScan;
import fatworm.scan.Scan;

public class ProductPlan implements Plan
{
	Plan fatherPlan = null;

	Plan plan1;

	Plan plan2;

	public ProductPlan(Plan p1, Plan p2)
	{
		plan1 = p1;
		plan2 = p2;
		plan1.setFather(this);
		plan2.setFather(this);
	}

	@Override
	public Scan open()
	{
		return new ProductScan(plan1.open(), plan2.open());
	}

	@Override
	public Schema schema()
	{
		return plan1.schema().add(plan2.schema());
	}

	@Override
	public LinkedList<Plan> son()
	{
		LinkedList<Plan> ret = new LinkedList<Plan>();
		ret.add(plan1);
		ret.add(plan2);
		return ret;
	}

	@Override
	public void setSon(LinkedList<Plan> plans)
	{
		plan1 = plans.get(0);
		plan2 = plans.get(1);
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
		return "Product(" + plan1.tostring() + ", " + plan2.tostring() + ")";
	}
}
