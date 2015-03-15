package fatworm.types;

import java.io.Serializable;

@SuppressWarnings("serial")
public class BasicType implements Type, Serializable
{
	boolean isNULL = true;

	public BasicType()
	{
		isNULL = true;
	}

	public boolean isNULL()
	{
		return isNULL;
	}

	public void setNULL()
	{
		isNULL = true;
	}

	public void setNotNULL()
	{
		isNULL = false;
	}

	@Override
	public boolean isBOOLEAN()
	{
		return false;
	}

	@Override
	public boolean isCHAR()
	{
		return false;
	}

	@Override
	public boolean isDATETIME()
	{
		return false;
	}

	@Override
	public boolean isDECIMAL()
	{
		return false;
	}

	@Override
	public boolean isFLOAT()
	{
		return false;
	}

	@Override
	public boolean isINT()
	{
		return false;
	}

	@Override
	public boolean isTIMESTAMP()
	{
		return false;
	}

	@Override
	public boolean isVARCHAR()
	{
		return false;
	}

	@Override
	public Object getValue()
	{
		return null;
	}

	@Override
	public boolean lessThan(Type value)
	{
		return false;
	}

	@Override
	public boolean greaterThan(Type value)
	{
		return false;
	}

	@Override
	public boolean equalTo(Type value)
	{
		return false;
	}

	@Override
	public boolean noGreaterThan(Type value)
	{
		return false;
	}

	@Override
	public boolean noLessThan(Type value)
	{
		return false;
	}

	@Override
	public boolean notEqual(Type value)
	{
		return false;
	}

	public Type clone()
	{
		return new BasicType();
	}

	@Override
	public Type add(Type val)
	{
		return null;
	}

	@Override
	public Type devideBy(int count)
	{
		return null;
	}

	@Override
	public int compareTo(Type tb)
	{
		if (this.greaterThan(tb)) return 1;
		if (this.equalTo(tb)) return 0;
		return -1;
	}

	public String toString()
	{
		return "n";
	}

	@Override
	public int length()
	{
		return 5;
	}
}
