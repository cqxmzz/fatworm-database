package fatworm.expr;

import fatworm.database.Schema;
import fatworm.scan.Scan;
import fatworm.types.Type;

public interface Expr
{
	public Type getValue(Scan scan);

	public Type getType(Schema schema);

	public boolean hasFunc();

	public String toString();

	public boolean notNeed(Schema s);

	public boolean isConst();
}
