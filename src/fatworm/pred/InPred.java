package fatworm.pred;

import java.math.BigDecimal;

import fatworm.database.Schema;
import fatworm.expr.Expr;
import fatworm.plan.Plan;
import fatworm.scan.Scan;
import fatworm.types.CHAR;
import fatworm.types.DATETIME;
import fatworm.types.DECIMAL;
import fatworm.types.FLOAT;
import fatworm.types.INT;
import fatworm.types.TIMESTAMP;
import fatworm.types.Type;
import fatworm.types.VARCHAR;

public class InPred implements fatworm.pred.Predicate
{
	public Plan subqueryPlan;

	public Scan subqueryScan = null;

	public Expr valueExpr;

	public InPred(Expr e, Plan p)
	{
		valueExpr = e;
		subqueryPlan = p;
	}

	@Override
	public boolean isSatisfied(Scan scan)
	{
		if (subqueryScan == null)
		{
			subqueryScan = subqueryPlan.open();
		}
		subqueryScan.beforeFirst();
		while (subqueryScan.next())
		{
			Type value1 = valueExpr.getValue(scan);
			Type value2 = subqueryScan.getFirstVal();
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
				} catch (Exception e)
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
				} catch (Exception e)
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
			if (value1.equalTo(value2))
			{
				subqueryScan.beforeFirst();
				return true;
			}
		}
		subqueryScan.beforeFirst();
		return false;
	}

	@Override
	public boolean notNeed(Schema s)
	{
		return false;
	}

	public String toString()
	{
		return "IN" + subqueryPlan.tostring();
	}
}
