package fatworm.types;

import fatworm.FatwormException;

@SuppressWarnings("serial")
public class CHAR extends BasicType
{
	private int capacity;

	private String value;

	public CHAR(int n)
	{
		capacity = n;
	}

	public CHAR(int n, String s) throws FatwormException
	{
		setNotNULL();
		if (s.equals("null"))
		{
			setNULL();
			return;
		}
		value = s.substring(1, s.length() - 1);
		capacity = n;
		if (value.length() > n)
		{
			throw new FatwormException("string longer than capacity!");
		}
	}

	public Type clone()
	{
		try
		{
			return new CHAR(capacity, "'" + value + "'");
		} catch (FatwormException e)
		{
			e.printStackTrace();
			return null;
		}
	}

	public int getCapacity()
	{
		return capacity;
	}

	public void setCapacity(int n)
	{
		capacity = n;
	}

	public boolean isBOOLEAN()
	{
		return false;
	}

	public boolean isCHAR()
	{
		return true;
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

	public String getCHAR()
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
		if (this.value.toLowerCase().compareTo(((CHAR) value).getCHAR().toLowerCase()) < 0) return true;
		return false;
	}

	@Override
	public boolean greaterThan(Type value)
	{
		if (isNULL()) return false;
		if (value.isNULL()) return false;
		if (this.value.toLowerCase().compareTo(((CHAR) value).getCHAR().toLowerCase()) > 0) return true;
		return false;
	}

	@Override
	public boolean equalTo(Type value)
	{
		if (isNULL()) return false;
		if (value.isNULL()) return false;
		return this.value.toLowerCase().equals(((CHAR) value).getCHAR().toLowerCase());
	}

	@Override
	public boolean noGreaterThan(Type value)
	{
		if (isNULL()) return false;
		if (value.isNULL()) return false;
		if (this.value.toLowerCase().compareTo(((CHAR) value).getCHAR().toLowerCase()) <= 0) return true;
		return false;
	}

	@Override
	public boolean noLessThan(Type value)
	{
		if (isNULL()) return false;
		if (value.isNULL()) return false;
		if (this.value.toLowerCase().compareTo(((CHAR) value).getCHAR().toLowerCase()) >= 0) return true;
		return false;
	}

	@Override
	public boolean notEqual(Type value)
	{
		if (isNULL()) return false;
		if (value.isNULL()) return false;
		return !this.value.toLowerCase().equals(((CHAR) value).getCHAR().toLowerCase());
	}

	public String toString()
	{
		return "'" + value + "'";
	}

	public int length()
	{
		return capacity + 8;
	}
}
