package fatworm.pred;

import java.math.BigDecimal;

import fatworm.FatwormException;
import fatworm.database.Schema;
import fatworm.expr.Expr;
import fatworm.scan.Scan;
import fatworm.types.CHAR;
import fatworm.types.DATETIME;
import fatworm.types.DECIMAL;
import fatworm.types.FLOAT;
import fatworm.types.INT;
import fatworm.types.TIMESTAMP;
import fatworm.types.Type;
import fatworm.types.VARCHAR;

public class CopPred implements Predicate
{
	public Expr lftExpr;

	public Expr rgtExpr;

	public String operator;

	public CopPred(Expr l, Expr r, String opt)
	{
		lftExpr = l;
		rgtExpr = r;
		operator = opt;
	}

	public boolean isSatisfied(Scan scan)
	{
		return isSatisfied(scan, null, null);
	}

	public boolean isSatisfied(Scan scan, Type t1, Type t2)
	{
		Type value1;
		Type value2;
		if (t1 == null)
		{
			value1 = lftExpr.getValue(scan);
		}
		else
		{
			value1 = t1;
		}
		if (t2 == null)
		{
			value2 = rgtExpr.getValue(scan);
		}
		else
		{
			value2 = t2;
		}
		if (value1 instanceof INT && value2 instanceof DECIMAL)
		{
			DECIMAL tmp = new DECIMAL(((DECIMAL) value2).getEffective(), ((DECIMAL) value2).getDecimalSetting());
			int i = ((INT) value1).getINT();
			tmp.setDECIMAL(new BigDecimal(i));
			value1 = tmp;
		}
		if (value2 instanceof INT && value1 instanceof DECIMAL)
		{
			DECIMAL tmp = new DECIMAL(((DECIMAL) value1).getEffective(), ((DECIMAL) value1).getDecimalSetting());
			int i = ((INT) value2).getINT();
			tmp.setDECIMAL(new BigDecimal(i));
			value2 = tmp;
		}
		if (value1 instanceof FLOAT && value2 instanceof DECIMAL)
		{
			DECIMAL tmp = new DECIMAL(((DECIMAL) value2).getEffective(), ((DECIMAL) value2).getDecimalSetting());
			float i = ((FLOAT) value1).getFLOAT();
			tmp.setDECIMAL(new BigDecimal(i));
			value1 = tmp;
		}
		if (value2 instanceof FLOAT && value1 instanceof DECIMAL)
		{
			DECIMAL tmp = new DECIMAL(((DECIMAL) value1).getEffective(), ((DECIMAL) value1).getDecimalSetting());
			float i = ((FLOAT) value2).getFLOAT();
			tmp.setDECIMAL(new BigDecimal(i));
			value2 = tmp;
		}
		if (value1 instanceof INT && value2 instanceof FLOAT)
		{
			FLOAT tmp = new FLOAT();
			int i = ((INT) value1).getINT();
			tmp.setFLOAT(i);
			value1 = tmp;
		}
		if (value2 instanceof INT && value1 instanceof FLOAT)
		{
			FLOAT tmp = new FLOAT();
			int i = ((INT) value2).getINT();
			tmp.setFLOAT(i);
			value2 = tmp;
		}
		if (value2 instanceof CHAR && value1 instanceof VARCHAR)
		{
			VARCHAR tmp;
			String s = ((CHAR) value2).getCHAR();
			try
			{
				tmp = new VARCHAR(s.length(), "'" + s + "'");
			} catch (FatwormException e)
			{
				tmp = null;
				e.printStackTrace();
			}
			value2 = tmp;
		}
		if (value1 instanceof CHAR && value2 instanceof VARCHAR)
		{
			VARCHAR tmp;
			String s = ((CHAR) value1).getCHAR();
			try
			{
				tmp = new VARCHAR(s.length(), "'" + s + "'");
			} catch (FatwormException e)
			{
				tmp = null;
				e.printStackTrace();
			}
			value1 = tmp;
		}
		if (value1 instanceof CHAR && value2 instanceof DATETIME)
		{
			DATETIME tmp;
			String s = ((CHAR) value1).getCHAR();
			tmp = new DATETIME("'" + s + "'");
			value1 = tmp;
		}
		if (value2 instanceof CHAR && value1 instanceof DATETIME)
		{
			DATETIME tmp;
			String s = ((CHAR) value2).getCHAR();
			tmp = new DATETIME("'" + s + "'");
			value2 = tmp;
		}
		if (value1 instanceof CHAR && value2 instanceof TIMESTAMP)
		{
			TIMESTAMP tmp;
			String s = ((CHAR) value1).getCHAR();
			tmp = new TIMESTAMP("'" + s + "'");
			value1 = tmp;
		}
		if (value2 instanceof CHAR && value1 instanceof TIMESTAMP)
		{
			TIMESTAMP tmp;
			String s = ((CHAR) value2).getCHAR();
			tmp = new TIMESTAMP("'" + s + "'");
			value2 = tmp;
		}
		// char varchar?
		if (operator.equals("<")) return value1.lessThan(value2);
		if (operator.equals(">")) return value1.greaterThan(value2);
		if (operator.equals("=")) return value1.equalTo(value2);
		if (operator.equals("<=")) return value1.noGreaterThan(value2);
		if (operator.equals(">=")) return value1.noLessThan(value2);
		if (operator.equals("<>")) return value1.notEqual(value2);
		return false;
	}

	public String toString()
	{
		return lftExpr.toString() + operator + rgtExpr.toString();
	}

	@Override
	public boolean dontNeedSchema(Schema s)
	{
		return lftExpr.dontNeedSchema(s) && rgtExpr.dontNeedSchema(s);
	}
}
