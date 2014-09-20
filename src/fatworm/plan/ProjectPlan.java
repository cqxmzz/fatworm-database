package fatworm.plan;

import java.util.LinkedList;
import java.util.List;

import fatworm.database.Schema;
import fatworm.expr.Expr;
import fatworm.scan.ProjectScan;
import fatworm.scan.Scan;

public class ProjectPlan implements Plan
{
	Plan fatherPlan = null;

	Plan plan;

	List<Expr> exprs;

	List<Integer> places;

	Schema schema;

	public ProjectPlan(Plan p, List<Expr> e, List<String> name)
	{
		plan = p;
		plan.setFather(this);
		exprs = e;
		places = plan.schema().project(e, name);
		schema = new Schema();
		for (Integer i : places)
		{
			schema.add(plan.schema().getIndex(i), plan.schema().getTableName(i), plan.schema().getName(i));
		}
	}

	@Override
	public Scan open()
	{
		return new ProjectScan(plan.open(), places, schema);
	}

	@Override
	public Schema schema()
	{
		return schema;
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
		return "Project " + plan.tostring();
	}
}
