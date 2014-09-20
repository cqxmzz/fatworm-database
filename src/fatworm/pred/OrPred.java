package fatworm.pred;

import fatworm.database.Schema;
import fatworm.expr.FieldExpr;
import fatworm.scan.Scan;
import fatworm.types.Type;

public class OrPred implements Predicate
{
	public Predicate lftPredicate;

	public Predicate rgtPredicate;

	public OrPred(Predicate l, Predicate r)
	{
		lftPredicate = l;
		rgtPredicate = r;
	}

	@Override
	public boolean isSatisfied(Scan scan)
	{
		if (lftPredicate instanceof CopPred && rgtPredicate instanceof CopPred)
		{
			CopPred lftCop = (CopPred) lftPredicate;
			CopPred rgtCop = (CopPred) rgtPredicate;
			if (lftCop.lftExpr instanceof FieldExpr && rgtCop.lftExpr instanceof FieldExpr)
			{
				FieldExpr lftExpr = (FieldExpr) lftCop.lftExpr;
				FieldExpr rgtExpr = (FieldExpr) rgtCop.lftExpr;
				if (lftExpr.fieldName.equals(rgtExpr.fieldName))
				{
					Type value = lftExpr.getValue(scan);
					return lftCop.isSatisfied(scan, value, lftCop.rgtExpr.getValue(scan)) || rgtCop.isSatisfied(scan, value, rgtCop.rgtExpr.getValue(scan));
				}
			}
		}
		return lftPredicate.isSatisfied(scan) || rgtPredicate.isSatisfied(scan);
	}

	@Override
	public boolean notNeed(Schema s)
	{
		return lftPredicate.notNeed(s) && rgtPredicate.notNeed(s);
	}
}
