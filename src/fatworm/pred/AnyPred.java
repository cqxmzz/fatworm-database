package fatworm.pred;

import fatworm.database.Schema;
import fatworm.expr.Expr;
import fatworm.plan.Plan;
import fatworm.scan.Scan;
import fatworm.types.Type;

public class AnyPred implements Predicate
{
	Plan subqueryPlan;

	Scan subqueryScan = null;

	Expr valueExpr;

	String operator;

	public AnyPred(Expr expr, String s, Plan plan)
	{
		subqueryPlan = plan;
		valueExpr = expr;
		operator = s;
	}

	@Override
	public boolean isSatisfied(Scan scan)
	{
		if (subqueryScan == null)
		{
			subqueryScan = subqueryPlan.open();
		}
		Type type = valueExpr.getValue(scan);
		subqueryScan.beforeFirst();
		while (subqueryScan.next())
		{
			if (operator.equals("<"))
			{
				if (type.lessThan(subqueryScan.getFirstVal()))
				{
					subqueryScan.beforeFirst();
					return true;
				}
			}
			if (operator.equals(">"))
			{
				if (type.greaterThan(subqueryScan.getFirstVal()))
				{
					subqueryScan.beforeFirst();
					return true;
				}
			}
			if (operator.equals("="))
			{
				if (type.equalTo(subqueryScan.getFirstVal()))
				{
					subqueryScan.beforeFirst();
					return true;
				}
			}
			if (operator.equals("<="))
			{
				if (type.noGreaterThan(subqueryScan.getFirstVal()))
				{
					subqueryScan.beforeFirst();
					return true;
				}
			}
			if (operator.equals(">="))
			{
				if (type.noLessThan(subqueryScan.getFirstVal()))
				{
					subqueryScan.beforeFirst();
					return true;
				}
			}
			if (operator.equals("<>"))
			{
				if (type.notEqual(subqueryScan.getFirstVal()))
				{
					subqueryScan.beforeFirst();
					return true;
				}
			}
		}
		subqueryScan.beforeFirst();
		return false;
	}

	@Override
	public boolean dontNeedSchema(Schema s)
	{
		return false;
	}
}
