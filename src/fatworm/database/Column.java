package fatworm.database;

import java.io.Serializable;

import fatworm.FatwormException;
import fatworm.types.*;

@SuppressWarnings("serial")
public class Column implements Serializable
{
	private Type type;

	private boolean notNull;

	private boolean autoIncrement;

	private int autoIncrementCount;

	private boolean hasDefault;

	private Type constValue;

	public Column(Type t, boolean notN, boolean autoI, boolean hasD, String ConstV)
	{
		type = t;
		notNull = notN;
		autoIncrement = autoI;
		hasDefault = hasD;
		if (hasDefault)
		{
			try
			{
				constValue = getConstValue(t, ConstV);
			} catch (FatwormException e)
			{
				e.printStackTrace();
			}
		}
		else
		{
			constValue = null;
		}
		if (autoI)
		{
			autoIncrementCount = 1;
		}
	}

	public boolean hasDefault()
	{
		return hasDefault;
	}
	
	public boolean notNull()
	{
		return notNull;
	}

	public Type getConstValue()
	{
		if (autoIncrement)
		{
			int i = getAutoIncrementCount();
			return new INT(i);
		}
		return constValue;
	}

	public Type getConstValue(Type t, String c) throws FatwormException
	{
		if (t.isNULL()) return t;
		if (t.isBOOLEAN()) return new BOOLEAN(c);
		if (t.isCHAR()) return new CHAR(((CHAR) type).getCapacity(), c);
		if (t.isDATETIME()) return new DATETIME(c);
		if (t.isDECIMAL()) return new DECIMAL(((DECIMAL) t).getEffective(), ((DECIMAL) t).getEffective(), c);
		if (t.isFLOAT()) return new FLOAT(c);
		if (t.isINT()) return new INT(c);
		if (t.isTIMESTAMP()) return new TIMESTAMP(c);
		if (t.isVARCHAR()) return new VARCHAR(((VARCHAR) t).getCapacity(), c);
		throw new FatwormException("can't cast const value " + c);
	}

	public Type getType()
	{
		return type;
	}

	public void setType(Type t)
	{
		type = t;
	}

	public boolean autoIncrement()
	{
		return autoIncrement;
	}

	public int getAutoIncrementCount()
	{
		return autoIncrementCount++;
	}

	public int getAutoIncrementValue()
	{
		return autoIncrementCount;
	}

	public void setAutoIncrementValue(int i)
	{
		autoIncrementCount = i;
	}

	public Type castValue(Type t)
	{
		if (t.isNULL()) return type;
		if (type.isVARCHAR() && t.isCHAR())
		{
			try
			{
				return new VARCHAR(((VARCHAR) type).getCapacity(), t.toString());
			} catch (FatwormException e)
			{
				e.printStackTrace();
			}
		}
		if (type.isDATETIME() && t.isCHAR())
		{
			return new DATETIME("'" + t.toString() + "'");
		}
		if (type.isTIMESTAMP() && t.isCHAR())
		{
			return new TIMESTAMP("'" + t.toString() + "'");
		}
		if (type.isFLOAT() && t.isINT())
		{
			return new FLOAT(((INT) t).getINT());
		}
		if (type.isDECIMAL() && t.isINT())
		{
			return new DECIMAL(((DECIMAL) type).getEffective(), ((DECIMAL) type).getDecimalSetting(), ((INT) t).toString());
		}
		if (type.isINT() && t.isDECIMAL())
		{
			return new INT(((DECIMAL) t).getDECIMAL().intValue());
		}
		if (type.isDECIMAL() && t.isFLOAT())
		{
			return new DECIMAL(((DECIMAL) type).getEffective(), ((DECIMAL) type).getDecimalSetting(), ((FLOAT) t).toString());
		}
		return t;
	}
}
