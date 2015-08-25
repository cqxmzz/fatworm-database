package fatworm.pred;

import fatworm.database.Schema;
import fatworm.scan.Scan;

public interface Predicate
{
	public boolean isSatisfied(Scan scan);

	public boolean dontNeedSchema(Schema schema);
}
