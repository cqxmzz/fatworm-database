package fatworm.expr;

import fatworm.database.Schema;
import fatworm.scan.Scan;
import fatworm.types.Type;

public class ConstantExpr implements Expr
{
	public Type constantType;

	public ConstantExpr(Type type)
	{
		constantType = type;
	}

	@Override
	public Type getValue(Scan scan)
	{
		return constantType;
	}

	@Override
	public Type getType(Schema s)
	{
		return constantType;
	}

	@Override
	public boolean hasFunc()
	{
		return false;
	}

	public String toString()
	{
		return constantType.toString();
	}

	@Override
	public boolean notNeed(Schema s)
	{
		return true;
	}

	@Override
	public boolean isConst()
	{
		return true;
	}
}
