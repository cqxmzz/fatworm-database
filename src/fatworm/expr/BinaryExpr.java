package fatworm.expr;

import java.math.BigDecimal;

import fatworm.database.Schema;
import fatworm.scan.Scan;
import fatworm.types.DECIMAL;
import fatworm.types.FLOAT;
import fatworm.types.INT;
import fatworm.types.Type;

public class BinaryExpr implements Expr
{
	Expr expression1;

	Expr expression2;

	String operator;

	public BinaryExpr(Expr expr1, Expr expr2, String op)
	{
		expression1 = expr1;
		expression2 = expr2;
		operator = op;
	}

	@Override
	public Type getValue(Scan scan)
	{
		try
		{
			Type ret = null;
			Type value1 = expression1.getValue(scan);
			Type value2 = expression2.getValue(scan);
			if (value1.isNULL() || value2.isNULL())
			{
				if (value1 instanceof DECIMAL || value2 instanceof DECIMAL) return new DECIMAL();
				if (value1 instanceof FLOAT || value2 instanceof FLOAT) return new FLOAT();
				if (value1 instanceof INT || value2 instanceof INT) return new INT();
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
			// start calculating
			if (operator.equals("+"))
			{
				if (value1 instanceof INT)
				{
					ret = new INT(((INT) value1).getINT() + ((INT) value2).getINT());
				}
				if (value1 instanceof FLOAT)
				{
					ret = new FLOAT(((FLOAT) value1).getBigDecimal().add(((FLOAT) value2).getBigDecimal()));
				}
				if (value1 instanceof DECIMAL)
				{
					ret = new DECIMAL(((DECIMAL) value2).getEffective(), ((DECIMAL) value2).getDecimalSetting());
					((DECIMAL) ret).setDECIMAL(((DECIMAL) value1).getDECIMAL().add(((DECIMAL) value2).getDECIMAL()));
				}
			}
			if (operator.equals("-"))
			{
				if (value1 instanceof INT)
				{
					ret = new INT(((INT) value1).getINT() - ((INT) value2).getINT());
				}
				if (value1 instanceof FLOAT)
				{
					ret = new FLOAT(((FLOAT) value1).getBigDecimal().subtract(((FLOAT) value2).getBigDecimal()));
				}
				if (value1 instanceof DECIMAL)
				{
					ret = new DECIMAL(((DECIMAL) value1).getEffective(), ((DECIMAL) value2).getDecimalSetting());
					((DECIMAL) ret).setDECIMAL(((DECIMAL) value1).getDECIMAL().subtract(((DECIMAL) value2).getDECIMAL()));
				}
			}
			if (operator.equals("*"))
			{
				if (value1 instanceof INT)
				{
					ret = new INT(((INT) value1).getINT() * ((INT) value2).getINT());
				}
				if (value1 instanceof FLOAT)
				{
					ret = new FLOAT(((FLOAT) value1).getBigDecimal().multiply(((FLOAT) value2).getBigDecimal()));
				}
				if (value1 instanceof DECIMAL)
				{
					ret = new DECIMAL(((DECIMAL) value1).getEffective(), ((DECIMAL) value2).getDecimalSetting());
					((DECIMAL) ret).setDECIMAL(((DECIMAL) value1).getDECIMAL().multiply(((DECIMAL) value2).getDECIMAL()));
				}
			}
			if (operator.equals("/"))
			{
				if (value1 instanceof INT)
				{
					ret = new DECIMAL((float) ((INT) value1).getINT() / (float) ((INT) value2).getINT());
				}
				if (value1 instanceof FLOAT)
				{
					ret = new FLOAT(((FLOAT) value1).getBigDecimal().divide(((FLOAT) value2).getBigDecimal()));
				}
				if (value1 instanceof DECIMAL)
				{
					ret = new DECIMAL(((DECIMAL) value1).getEffective(), ((DECIMAL) value2).getDecimalSetting());
					((DECIMAL) ret).setDECIMAL(((DECIMAL) value1).getDECIMAL().divide(((DECIMAL) value2).getDECIMAL()));
				}
			}
			if (operator.equals("%"))
			{
				if (value1 instanceof INT)
				{
					ret = new INT(((INT) value1).getINT() % ((INT) value2).getINT());
				}
				if (value1 instanceof FLOAT)
				{
					ret = new FLOAT(((FLOAT) value1).getBigDecimal().remainder(((FLOAT) value2).getBigDecimal()));
				}
				if (value1 instanceof DECIMAL)
				{
					ret = new DECIMAL(((DECIMAL) value1).getEffective(), ((DECIMAL) value2).getDecimalSetting());
					((DECIMAL) ret).setDECIMAL(((DECIMAL) value1).getDECIMAL().remainder(((DECIMAL) value2).getDECIMAL()));
				}
			}
			return ret;
		} catch (Exception e)
		{
			e.printStackTrace();
			System.out.println("some thing wrong when calculating expression while casting types");
			return null;
		}
	}

	@Override
	public Type getType(Schema s)
	{
		Type t1 = expression1.getType(s);
		Type t2 = expression2.getType(s);
		if (operator.equals("/")) return new DECIMAL();
		if (t1 instanceof DECIMAL || t2 instanceof DECIMAL) return new DECIMAL();
		if (t1 instanceof FLOAT || t2 instanceof FLOAT) return new FLOAT();
		if (t1 instanceof INT || t2 instanceof INT) return new INT();
		return null;
	}

	@Override
	public boolean hasFunc()
	{
		return expression1.hasFunc() || expression2.hasFunc();
	}

	public String toString()
	{
		return expression1.toString() + operator + expression2.toString();
	}

	@Override
	public boolean notNeed(Schema s)
	{
		return expression1.notNeed(s) && expression2.notNeed(s);
	}

	@Override
	public boolean isConst()
	{
		return false;
	}
}
