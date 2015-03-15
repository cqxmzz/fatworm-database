package fatworm.types;

@SuppressWarnings("serial")
public class BOOLEAN extends BasicType
{
	private boolean value;

	public BOOLEAN()
	{
	}

	public BOOLEAN(boolean b)
	{
		setNotNULL();
		value = b;
	}

	public BOOLEAN(char c)
	{
		setNotNULL();
		if (c == 'f') value = false;
		if (c == 't') value = true;
	}

	public BOOLEAN(String s)
	{
		setNotNULL();
		if (s.equals("null"))
		{
			setNULL();
			return;
		}
		if (s.length() != 0)
		{
			char c = s.toCharArray()[0];
			if (c == 'F' || c == 'f')
			{
				value = false;
				return;
			}
			if (c == 'T' || c == 't')
			{
				value = true;
				return;
			}
		}
	}

	public Type clone()
	{
		return new BOOLEAN(value);
	}

	public boolean isBOOLEAN()
	{
		return true;
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

	boolean getBOOLEAN()
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
		if (this.value == false && ((BOOLEAN) value).getBOOLEAN() == true) return true;
		return false;
	}

	@Override
	public boolean greaterThan(Type value)
	{
		if (isNULL()) return false;
		if (value.isNULL()) return false;
		if (this.value == true && ((BOOLEAN) value).getBOOLEAN() == false) return true;
		return false;
	}

	@Override
	public boolean equalTo(Type value)
	{
		if (isNULL()) return false;
		if (value.isNULL()) return false;
		if (this.value == false && ((BOOLEAN) value).getBOOLEAN() == false || this.value == true && ((BOOLEAN) value).getBOOLEAN() == true) return true;
		return false;
	}

	@Override
	public boolean noGreaterThan(Type value)
	{
		if (isNULL()) return false;
		if (value.isNULL()) return false;
		if (this.value == false) return true;
		return false;
	}

	@Override
	public boolean noLessThan(Type value)
	{
		if (isNULL()) return false;
		if (value.isNULL()) return false;
		if (this.value == true) return true;
		return false;
	}

	@Override
	public boolean notEqual(Type value)
	{
		if (isNULL()) return false;
		if (value.isNULL()) return false;
		if (this.value == false && ((BOOLEAN) value).getBOOLEAN() == true || this.value == true && ((BOOLEAN) value).getBOOLEAN() == false) return true;
		return false;
	}

	public String toString()
	{
		if (value == true)
			return "t";
		else
			return "f";
	}

	public int length()
	{
		return 7;
	}
}
