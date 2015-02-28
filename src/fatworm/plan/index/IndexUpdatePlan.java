package fatworm.plan.index;

import java.util.Iterator;
import java.util.LinkedList;

import fatworm.database.Column;
import fatworm.database.DataBase;
import fatworm.database.Schema;
import fatworm.database.Table;
import fatworm.expr.Expr;
import fatworm.index.Index;
import fatworm.plan.Plan;
import fatworm.plan.SelectPlan;
import fatworm.plan.UpdatePlan;
import fatworm.pred.Predicate;
import fatworm.scan.UpdateScan;
import fatworm.types.Type;

public class IndexUpdatePlan extends UpdatePlan
{
	public IndexUpdatePlan(Table t, Predicate pred, LinkedList<Expr> exprs, Schema s) throws Exception
	{
		super(t, pred, exprs, s);
	}

	public int execute() throws Exception
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
				int scanPlace = scan.setVal(string, column.castValue(expr.getValue(scan)));
				for (Index index : table.indexes.values())
				{
					String string2 = index.getColumn();
					if (string.equals(string2))
					{
						Expr expr2 = exprs.get(table.schema.getIndex(string2));
						Column column2 = table.getColumns().get(string2);
						Type type = column2.castValue(expr2.getValue(scan));
						Type type1 = scan.getVal(string2);
						if (index != null)
						{
							index.delete(type1, scanPlace);
							index.insert(type, scanPlace);
						}
					}
				}
			}
//			scan.moveToBottom();
			count++;
		}
		scan.close();
		DataBase.getDataBase().getOpenedScans().pop();
		return count;
	}
}
