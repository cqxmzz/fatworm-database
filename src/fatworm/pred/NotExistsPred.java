package fatworm.pred;

import fatworm.database.Schema;
import fatworm.plan.Plan;
import fatworm.scan.Scan;

public class NotExistsPred implements Predicate
{
	Plan subqueryPlan;

	Scan subqueryScan = null;

	public NotExistsPred(Plan sub)
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
			return false;
		}
		return true;
	}

	@Override
	public boolean notNeed(Schema s)
	{
		return false;
	}
}
