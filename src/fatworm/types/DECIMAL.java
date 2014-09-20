package fatworm.types;

import java.math.BigDecimal;

public class DECIMAL extends BasicType
{
	private int effective;

	private int decimal;

	private BigDecimal value;

	public DECIMAL()
	{
	}

	public DECIMAL(int v1)
	{
		effective = v1;
		decimal = 0;
	}

	public DECIMAL(int v1, int v2)
	{
		effective = v1;
		decimal = v2;
	}

	public DECIMAL(int v1, int v2, String s)
	{
		setNotNULL();
		if (s.equals("null"))
		{
			setNULL();
			return;
		}
		effective = v1;
		decimal = v2;
		value = new BigDecimal(s);
	}

	public DECIMAL(float f)
	{
		setNotNULL();
		value = new BigDecimal(f);
		effective = value.scale();
		decimal = value.precision();
	}

	public DECIMAL(BigDecimal b)
	{
		setNotNULL();
		value = b;
		effective = value.scale();
		decimal = value.precision();
	}

	public Type clone()
	{
		return new DECIMAL(value);
	}

	public int getEffective()
	{
		return effective;
	}

	public int getDecimalSetting()
	{
		return decimal;
	}

	public DECIMAL(String s, int v1) throws Exception
	{
		setNotNULL();
		if (s.equals("null"))
		{
			setNULL();
			return;
		}
		effective = v1;
		decimal = 0;
		value = new BigDecimal(s);
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
		return true;
	}

	public boolean isFLOAT()
	{
		return false;
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

	public BigDecimal getDECIMAL()
	{
		return value;
	}

	public void setDECIMAL(BigDecimal bd)
	{
		setNotNULL();
		value = bd;
	}

	public void setDECIMAL(double bd)
	{
		setNotNULL();
		value = new BigDecimal(bd);
	}

	public Object getValue()
	{
		if (isNULL()) return null;
		return value;
	}

	@Override
	public boolean lessThan(Type value)
	{
		if (isNULL()) return false;
		if (value.isNULL()) return false;
		int c = this.value.compareTo(((DECIMAL) value).getDECIMAL());
		return (c < 0);
	}

	@Override
	public boolean greaterThan(Type value)
	{
		if (isNULL()) return false;
		if (value.isNULL()) return false;
		int c = this.value.compareTo(((DECIMAL) value).getDECIMAL());
		return (c > 0);
	}

	@Override
	public boolean equalTo(Type value)
	{
		if (isNULL()) return false;
		if (value.isNULL()) return false;
		int c = this.value.compareTo(((DECIMAL) value).getDECIMAL());
		return (c == 0);
	}

	@Override
	public boolean noGreaterThan(Type value)
	{
		if (isNULL()) return false;
		if (value.isNULL()) return false;
		int c = this.value.compareTo(((DECIMAL) value).getDECIMAL());
		return (c <= 0);
	}

	@Override
	public boolean noLessThan(Type value)
	{
		if (isNULL()) return false;
		if (value.isNULL()) return false;
		int c = this.value.compareTo(((DECIMAL) value).getDECIMAL());
		return (c >= 0);
	}

	@Override
	public boolean notEqual(Type value)
	{
		if (isNULL()) return false;
		if (value.isNULL()) return false;
		int c = this.value.compareTo(((DECIMAL) value).getDECIMAL());
		return (c != 0);
	}

	@Override
	public Type add(Type t)
	{
		return new DECIMAL(getDECIMAL().add(((DECIMAL) t).getDECIMAL()));
	}

	@Override
	public Type devideBy(int count)
	{
		return new DECIMAL(value.divide(new BigDecimal(count), 6, value.ROUND_HALF_UP));
	}

	public String toString()
	{
		return value.toString();
	}

	public int length()
	{
		return effective + 6;
	}
}
