package fatworm.types;

@SuppressWarnings("serial")
public class INT extends BasicType
{
	private int value;

	public INT()
	{
	}

	public INT(String s)
	{
		setNotNULL();
		if (s.equals("null"))
		{
			setNULL();
			return;
		}
		value = new Integer(s).intValue();
	}

	public INT(int s)
	{
		setNotNULL();
		value = s;
	}

	public Type clone()
	{
		return new INT(value);
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
		return false;
	}

	public boolean isINT()
	{
		return true;
	}

	public boolean isTIMESTAMP()
	{
		return false;
	}

	public boolean isVARCHAR()
	{
		return false;
	}

	public int getINT()
	{
		return value;
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
		return this.value < (((INT) value).getINT());
	}

	@Override
	public boolean greaterThan(Type value)
	{
		if (isNULL()) return false;
		if (value.isNULL()) return false;
		return this.value > (((INT) value).getINT());
	}

	@Override
	public boolean equalTo(Type value)
	{
		if (isNULL()) return false;
		if (value.isNULL()) return false;
		if (value instanceof CHAR)
		{
			return this.value == new Integer((String) (value.getValue()));
		}
		return this.value == (((INT) value).getINT());
	}

	@Override
	public boolean noGreaterThan(Type value)
	{
		if (isNULL()) return false;
		if (value.isNULL()) return false;
		return this.value <= (((INT) value).getINT());
	}

	@Override
	public boolean noLessThan(Type value)
	{
		if (isNULL()) return false;
		if (value.isNULL()) return false;
		return this.value >= (((INT) value).getINT());
	}

	@Override
	public boolean notEqual(Type value)
	{
		if (isNULL()) return false;
		if (value.isNULL()) return false;
		return this.value != (((INT) value).getINT());
	}

	@Override
	public Type add(Type t)
	{
		return new INT(getINT() + ((INT) t).getINT());
	}

	@Override
	public Type devideBy(int count)
	{
		return new DECIMAL(new Float((float) value / (float) count));
	}

	public String toString()
	{
		if (isNULL()) return "null";
		return "" + value;
	}

	public int length()
	{
		return 6;
	}
}
