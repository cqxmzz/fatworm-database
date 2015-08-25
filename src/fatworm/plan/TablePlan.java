package fatworm.plan;

import java.util.LinkedList;

import fatworm.FatwormException;
import fatworm.database.Schema;
import fatworm.database.Table;
import fatworm.scan.TableScan;
import fatworm.scan.UpdateScan;

public class TablePlan implements Plan
{
	Table table;

	Plan fatherPlan = null;

	public TablePlan(Table t) throws FatwormException
	{
		if (t == null) throw new FatwormException("don't have that table");
		table = t;
	}

	public UpdateScan open()
	{
		return new TableScan(table);
	}

	@Override
	public Schema schema()
	{
		return table.getSchema();
	}

	@Override
	public LinkedList<Plan> son()
	{
		LinkedList<Plan> ret = new LinkedList<Plan>();
		return ret;
	}

	@Override
	public void setSon(LinkedList<Plan> plans)
	{
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
		return "Table " + table.name;
	}
}
