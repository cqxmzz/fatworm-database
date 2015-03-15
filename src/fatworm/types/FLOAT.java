package fatworm.types;

import java.math.BigDecimal;

@SuppressWarnings("serial")
public class FLOAT extends BasicType
{
	private BigDecimal value;

	public FLOAT()
	{
	}

	public FLOAT(String s) throws Exception
	{
		setNotNULL();
		if (s.equals("null"))
		{
			setNULL();
			return;
		}
		value = new BigDecimal(s);
	}

	public FLOAT(float f)
	{
		setNotNULL();
		value = new BigDecimal(f);
	}

	public FLOAT(BigDecimal b)
	{
		setNotNULL();
		value = b;
	}

	public Type clone()
	{
		return new FLOAT(value);
	}

	public boolean isBOOLEAN()
	{
		return false;
	}

	public boolean isCHAR()
	{
		return false;
	}

	public boolean isDATETIME()
	{
		return false;
	}

	public boolean isDECIMAL()
	{
		return false;
	}

	public boolean isFLOAT()
	{
		return true;
	}

	public boolean isINT()
	{
		return false;
	}

	public boolean isTIMESTAMP()
	{
		return false;
	}

	public boolean isVARCHAR()
	{
		return false;
	}

	public float getFLOAT()
	{
		return value.floatValue();
	}

	public BigDecimal getBigDecimal()
	{
		return value;
	}

	public void setFLOAT(float f)
	{
		setNotNULL();
		value = new BigDecimal(f);
	}

	public Object getValue()
	{
		if (isNULL()) return null;
		return value.floatValue();
	}

	@Override
	public boolean lessThan(Type value)
	{
		if (isNULL()) return false;
		if (value.isNULL()) return false;
		int i = this.value.compareTo(((FLOAT) value).getBigDecimal());
		return (i < 0);
	}

	@Override
	public boolean greaterThan(Type value)
	{
		if (isNULL()) return false;
		if (value.isNULL()) return false;
		int i = this.value.compareTo(((FLOAT) value).getBigDecimal());
		return (i > 0);
	}

	@Override
	public boolean equalTo(Type value)
	{
		if (isNULL()) return false;
		if (value.isNULL()) return false;
		int i = this.value.compareTo(((FLOAT) value).getBigDecimal());
		return (i == 0);
	}

	@Override
	public boolean noGreaterThan(Type value)
	{
		if (isNULL()) return false;
		if (value.isNULL()) return false;
		int i = this.value.compareTo(((FLOAT) value).getBigDecimal());
		return (i <= 0);
	}

	@Override
	public boolean noLessThan(Type value)
	{
		if (isNULL()) return false;
		if (value.isNULL()) return false;
		int i = this.value.compareTo(((FLOAT) value).getBigDecimal());
		return (i >= 0);
	}

	@Override
	public boolean notEqual(Type value)
	{
		if (isNULL()) return false;
		if (value.isNULL()) return false;
		int i = this.value.compareTo(((FLOAT) value).getBigDecimal());
		return (i != 0);
	}

	@Override
	public Type add(Type t)
	{
		return new FLOAT(value.add(((FLOAT) t).getBigDecimal()));
	}

	@SuppressWarnings("static-access")
	@Override
	public Type devideBy(int count)
	{
		return new FLOAT(value.divide(new BigDecimal(count), 6, value.ROUND_HALF_UP));
	}

	public String toString()
	{
		return "" + value.floatValue();
	}

	public int length()
	{
		return 6;
	}
}
