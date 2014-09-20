package fatworm.plan;

import java.util.LinkedList;
import java.util.List;

import fatworm.database.Column;
import fatworm.database.Schema;
import fatworm.expr.Expr;
import fatworm.record.Record;
import fatworm.scan.ExtendScan;
import fatworm.scan.RecordScan;
import fatworm.scan.Scan;
import fatworm.types.Type;

public class ExtendPlan implements Plan
{
	Plan plan;

	Plan fatherPlan = null;

	// Schema schema;
	List<String> asNames;

	List<Expr> exprs;

	public ExtendPlan(Plan wherePlan, List<Expr> exprs2, List<String> asNames2)
	{
		asNames = new LinkedList<String>();
		exprs = new LinkedList<Expr>();
		for (int i = 0; i < asNames2.size(); ++i)
		{
			if (asNames2.get(i) == null)
			{
				exprs.add(exprs2.get(i));
				asNames.add(asNames2.get(i));
			}
			else
			{
				exprs.add(exprs2.get(i));
				asNames.add(asNames2.get(i));
				exprs.add(exprs2.get(i));
				asNames.add(null);
			}
		}
		plan = wherePlan;
		if (plan != null)
		{
			plan.setFather(this);
		}
	}

	@Override
	public Scan open()
	{
		if (plan == null)
		{
			LinkedList<Type> list = new LinkedList<Type>();
			LinkedList<Column> cols = new LinkedList<Column>();
			LinkedList<String> st1 = new LinkedList<String>();
			LinkedList<String> st2 = new LinkedList<String>();
			for (Expr expr : exprs)
			{
				list.add(expr.getValue(null));
				cols.add(new Column(expr.getType(schema()), false, false, false, null));
				st1.add(null);
				st2.add(expr.toString());
			}
			return new RecordScan(new Record(list, new Schema(cols, st1, st2)));
		}
		return new ExtendScan(plan.open(), exprs, schema());
	}

	@Override
	public Schema schema()
	{
		if (plan != null) // has from
			return plan.schema().extend(exprs, asNames);
		else
			return (new Schema()).extend(exprs, asNames);
	}

	@Override
	public LinkedList<Plan> son()
	{
		LinkedList<Plan> ret = new LinkedList<Plan>();
		if (plan != null)
		{
			ret.add(plan);
		}
		return ret;
	}

	@Override
	public void setSon(LinkedList<Plan> plans)
	{
		if (!plans.isEmpty())
		{
			plan = plans.get(0);
		}
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
		if (plan != null)
			return "Extent " + plan.tostring();
		else
			return "Extent ";
	}
}
