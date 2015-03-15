package fatworm.expr;

import java.math.BigDecimal;

import fatworm.FatwormException;
import fatworm.database.Schema;
import fatworm.scan.Scan;
import fatworm.types.DECIMAL;
import fatworm.types.FLOAT;
import fatworm.types.INT;
import fatworm.types.Type;

public class NegativeExpr implements Expr
{
	Expr expression;

	public NegativeExpr(Expr expr)
	{
		expression = expr;
	}

	@Override
	public Type getValue(Scan scan)
	{
		try
		{
			Type value = expression.getValue(scan);
			if (value.isNULL())
			{
				return value;
			}
			if (value instanceof INT)
			{
				INT ret = (INT) value;
				return new INT(-ret.getINT());
			}
			if (value instanceof DECIMAL)
			{
				DECIMAL ret = new DECIMAL(((DECIMAL) value).getEffective(), ((DECIMAL) value).getDecimalSetting());
				ret.setDECIMAL(new BigDecimal(0).subtract(((DECIMAL) value).getDECIMAL()));
				return ret;
			}
			if (value instanceof FLOAT)
			{
				FLOAT ret = new FLOAT();
				ret.setFLOAT(-((FLOAT) value).getFLOAT());
				return ret;
			}
			throw new FatwormException("Type not accepted");
		} catch (Exception e)
		{
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public Type getType(Schema s)
	{
		return expression.getType(s);
	}

	@Override
	public boolean hasFunc()
	{
		return expression.hasFunc();
	}

	public String toString()
	{
		return "-" + expression.toString();
	}

	@Override
	public boolean notNeed(Schema s)
	{
		return expression.notNeed(s);
	}

	@Override
	public boolean isConst()
	{
		return expression.isConst();
	}
}
