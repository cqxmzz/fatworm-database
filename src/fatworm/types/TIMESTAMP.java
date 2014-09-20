package fatworm.types;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class TIMESTAMP extends BasicType
{
	private Timestamp value;

	public TIMESTAMP()
	{
	}

	public TIMESTAMP(String s)
	{
		try
		{
			setNotNULL();
			String sl = s.substring(1, s.length() - 1);
			if (s.equals("null"))
			{
				setNULL();
				return;
			}
			DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			value = new Timestamp(sdf.parse(sl).getTime());
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public TIMESTAMP(Timestamp s)
	{
		setNotNULL();
		value = s;
	}

	public Type clone()
	{
		return new TIMESTAMP(value);
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
		return false;
	}

	public boolean isTIMESTAMP()
	{
		return true;
	}

	public boolean isVARCHAR()
	{
		return false;
	}

	Timestamp getTIMESTAMP()
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
		return this.value.before(((TIMESTAMP) value).getTIMESTAMP());
	}

	@Override
	public boolean greaterThan(Type value)
	{
		if (isNULL()) return false;
		if (value.isNULL()) return false;
		return this.value.after(((TIMESTAMP) value).getTIMESTAMP());
	}

	@Override
	public boolean equalTo(Type value)
	{
		if (isNULL()) return false;
		if (value.isNULL()) return false;
		return this.value.equals(((TIMESTAMP) value).getTIMESTAMP());
	}

	@Override
	public boolean noGreaterThan(Type value)
	{
		if (isNULL()) return false;
		if (value.isNULL()) return false;
		return this.value.equals(((TIMESTAMP) value).getTIMESTAMP()) || this.value.before(((TIMESTAMP) value).getTIMESTAMP());
	}

	@Override
	public boolean noLessThan(Type value)
	{
		if (isNULL()) return false;
		if (value.isNULL()) return false;
		return this.value.equals(((TIMESTAMP) value).getTIMESTAMP()) || this.value.after(((TIMESTAMP) value).getTIMESTAMP());
	}

	@Override
	public boolean notEqual(Type value)
	{
		if (isNULL()) return false;
		if (value.isNULL()) return false;
		return !this.value.equals(((TIMESTAMP) value).getTIMESTAMP());
	}

	public String toString()
	{
		return "'" + value.toString().substring(0, 19) + "'";
	}

	public int length()
	{
		return 27;
	}
}
