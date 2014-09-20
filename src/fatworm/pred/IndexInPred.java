package fatworm.pred;

import fatworm.database.Schema;
import fatworm.expr.Expr;
import fatworm.expr.FieldExpr;
import fatworm.index.Index;
import fatworm.scan.Scan;
import fatworm.types.Type;

public class IndexInPred implements Predicate
{
	Index index;

	Expr valueExpr;

	public IndexInPred(FieldExpr e, Index index)
	{
		valueExpr = e;
		this.index = index;
	}

	@Override
	public boolean isSatisfied(Scan scan)
	{
		Type value = valueExpr.getValue(scan);
		index.between(value, value);
		if (index.next()) return true;
		return false;
	}

	@Override
	public boolean notNeed(Schema s)
	{
		return false;
	}
}
