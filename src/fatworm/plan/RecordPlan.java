package fatworm.plan;

import java.util.LinkedList;

import fatworm.database.Record;
import fatworm.database.Schema;
import fatworm.scan.RecordScan;
import fatworm.scan.Scan;

public class RecordPlan implements Plan
{
	Record insertRecord;

	Plan fatherPlan;

	public RecordPlan(Record r)
	{
		insertRecord = r;
	}

	@Override
	public Scan open()
	{
		return new RecordScan(insertRecord);
	}

	@Override
	public Schema schema()
	{
		return insertRecord.schema();
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
		return "Record ";
	}
}
