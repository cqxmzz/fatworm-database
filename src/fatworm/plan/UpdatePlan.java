package fatworm.plan;

import java.util.Iterator;
import java.util.LinkedList;

import fatworm.FatwormException;
import fatworm.database.Column;
import fatworm.database.DataBase;
import fatworm.database.Schema;
import fatworm.database.Table;
import fatworm.expr.Expr;
import fatworm.pred.Predicate;
import fatworm.scan.Scan;
import fatworm.scan.UpdateScan;

public class UpdatePlan implements Plan
{
	protected Table table;// to be updated

	protected Plan plan;// to be updated

	protected LinkedList<Expr> exprs;// to update

	protected Predicate predicate;

	protected Schema schema;

	public UpdatePlan(Table t, Predicate pred, LinkedList<Expr> exps, Schema s) throws FatwormException
	{
		table = t;
		plan = new TablePlan(table);
		predicate = pred;
		exprs = exps;
		schema = s;
	}

	public int execute() throws FatwormException
	{
		Plan p = plan;
		if (predicate != null) p = new SelectPlan(plan, predicate);
		UpdateScan scan = (UpdateScan) p.open();
		DataBase.getDataBase().getOpenedScans().push(scan);
		int count = 0;
		while (scan.next())
		{
			Iterator<Expr> iterator = exprs.iterator();
			for (int i = 0; i < schema.getColumns().size(); ++i)
			{
				String string = schema.getName(i);
				Column column = schema.getColumns().get(i);
				Expr expr = iterator.next();
				scan.setVal(string, column.castValue(expr.getValue(scan)));
			}
//			scan.moveToBottom();
			count++;
		}
		scan.close();
		DataBase.getDataBase().getOpenedScans().pop();
		return count;
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
		return ret;
	}

	@Override
	public void setSon(LinkedList<Plan> plans)
	{
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
		return "Update " + plan.tostring();
	}
}
