package fatworm.plan;

import java.util.LinkedList;

import fatworm.database.Schema;
import fatworm.pred.CopPred;
import fatworm.scan.ProductEqualSelectScan;
import fatworm.scan.Scan;

public class ProductEqualSelectPlan implements Plan
{
	Plan lftPlan;

	Plan rgtPlan;

	CopPred copPred;

	Plan father = null;

	public ProductEqualSelectPlan(Plan lftPlan, Plan rgtPlan, CopPred copPred)
	{
		this.lftPlan = lftPlan;
		this.rgtPlan = rgtPlan;
		this.copPred = copPred;
		lftPlan.setFather(this);
		rgtPlan.setFather(this);
	}

	@Override
	public Scan open()
	{
		return new ProductEqualSelectScan(lftPlan.open(), rgtPlan.open(), copPred);
	}

	@Override
	public Schema schema()
	{
		return lftPlan.schema().add(rgtPlan.schema());
	}

	@Override
	public LinkedList<Plan> son()
	{
		LinkedList<Plan> ret = new LinkedList<Plan>();
		ret.add(lftPlan);
		ret.add(rgtPlan);
		return ret;
	}

	@Override
	public void setSon(LinkedList<Plan> plans)
	{
		lftPlan = plans.get(0);
		rgtPlan = plans.get(1);
	}

	@Override
	public Plan father()
	{
		return father;
	}

	@Override
	public void setFather(Plan plan)
	{
		father = plan;
	}

	@Override
	public String tostring()
	{
		return "ProductEqualSelectPlan(" + lftPlan.tostring() + ", " + rgtPlan.tostring() + ", " + copPred.toString() + ")";
	}
}
