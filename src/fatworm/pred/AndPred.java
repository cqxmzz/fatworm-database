package fatworm.pred;

import fatworm.database.Schema;
import fatworm.scan.Scan;

public class AndPred implements Predicate
{
	public Predicate lftPredicate;

	public Predicate rgtPredicate;

	public AndPred(Predicate l, Predicate r)
	{
		lftPredicate = l;
		rgtPredicate = r;
	}

	@Override
	public boolean isSatisfied(Scan scan)
	{
		return lftPredicate.isSatisfied(scan) && rgtPredicate.isSatisfied(scan);
	}

	@Override
	public boolean dontNeedSchema(Schema s)
	{
		return lftPredicate.dontNeedSchema(s) && rgtPredicate.dontNeedSchema(s);
	}
}
