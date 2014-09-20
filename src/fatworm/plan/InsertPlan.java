package fatworm.plan;

import java.util.LinkedList;

import fatworm.database.Schema;
import fatworm.database.Table;
import fatworm.record.Record;
import fatworm.scan.Scan;
import fatworm.scan.TableScan;
import fatworm.types.CHAR;
import fatworm.types.Type;
import fatworm.types.VARCHAR;

public class InsertPlan implements Plan
{
	protected Table table;// to be inserted

	protected Plan plan;// to be inserted

	protected Plan plan2;// to insert

	public InsertPlan(Table t, Plan p)
	{
		table = t;
		plan2 = p;
		plan2.setFather(this);
	}

	public int execute() throws Exception
	{
		plan = new TablePlan(table);
		TableScan tableScan = (TableScan) plan.open();
		Scan scan = plan2.open();
		while (scan.next())
		{
			Record record = scan.getRecord();
			for (int j = 0; j < record.getValues().size(); ++j)
			{
				if (record.getValue(j) instanceof CHAR && plan.schema().getIndex(j).getType() instanceof VARCHAR)
				{
					Type type = plan.schema().getIndex(j).getType();
					record.setValue(j, new VARCHAR(((VARCHAR) type).getCapacity(), ((CHAR) record.getValue(j)).getCHAR()));
					record.schema().getIndex(j).setType(type);
				}
			}
			tableScan.insert(record);
		}
		return 1;
	}

	@Override
	public Scan open()
	{
		return plan.open();
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
		ret.add(plan2);
		return ret;
	}

	@Override
	public void setSon(LinkedList<Plan> plans)
	{
		plan2 = plans.get(0);
	}

	@Override
	public Plan father()
	{
		return null;
	}

	@Override
	public void setFather(Plan plan)
	{
	}

	@Override
	public String tostring()
	{
		return "Insert" + plan.tostring();
	}
}
