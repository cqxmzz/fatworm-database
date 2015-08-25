package fatworm.expr;

import fatworm.database.Schema;
import fatworm.plan.Plan;
import fatworm.scan.Scan;
import fatworm.types.BasicType;
import fatworm.types.Type;

public class SubqueryExpr implements Expr
{
	Plan subqueryPlan;

	public SubqueryExpr(Plan plan)
	{
		subqueryPlan = plan;
	}

	@Override
	public Type getValue(Scan scan)
	{
		Type ret;
		Scan scan2 = subqueryPlan.open();
		if (scan2.next())
			ret = scan2.getFirstVal();
		else
			ret = new BasicType();
		scan2.close();
		return ret;
	}

	@Override
	public Type getType(Schema s)
	{
		return subqueryPlan.schema().getColumn(0).getType();
	}

	@Override
	public boolean hasFunc()
	{
		return false;
	}

	public String toString()
	{
		return "SUBQUERY";
	}

	@Override
	public boolean dontNeedSchema(Schema s)
	{
		return false;
	}

	@Override
	public boolean isConst()
	{
		return false;
	}
}
