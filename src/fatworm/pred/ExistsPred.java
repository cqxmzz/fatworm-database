package fatworm.pred;

import fatworm.database.Schema;
import fatworm.plan.Plan;
import fatworm.scan.Scan;

public class ExistsPred implements Predicate
{
	Plan subqueryPlan;

	Scan subqueryScan = null;

	public ExistsPred(Plan sub)
	{
		subqueryPlan = sub;
	}

	@Override
	public boolean isSatisfied(Scan scan)
	{
		if (subqueryScan == null)
		{
			subqueryScan = subqueryPlan.open();
		}
		subqueryScan.beforeFirst();
		if (subqueryScan.next())
		{
			subqueryScan.beforeFirst();
			return true;
		}
		return false;
	}

	@Override
	public boolean notNeed(Schema s)
	{
		return false;
	}
}
