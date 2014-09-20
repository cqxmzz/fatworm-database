package fatworm.plan;

import java.util.LinkedList;

import fatworm.database.Schema;
import fatworm.scan.Scan;

public interface Plan
{
	public Scan open();

	public Schema schema();

	public LinkedList<Plan> son();

	public void setSon(LinkedList<Plan> plans);

	public Plan father();

	public void setFather(Plan plan);

	public String tostring();
}
