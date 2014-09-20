package fatworm.types;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class DATETIME extends BasicType
{
	private Timestamp value;

	public DATETIME()
	{
	}

	public DATETIME(Timestamp d)
	{
		value = d;
	}

	public DATETIME(String s)
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
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Timestamp date;
			date = new Timestamp(sdf.parse(sl).getTime());
			value = date;
		} catch (ParseException e)
		{
			e.printStackTrace();
		}
	}

	public Type clone()
	{
		return new DATETIME(value);
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
		return true;
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

	Timestamp getDATETIME()
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
		return this.value.before(((DATETIME) value).getDATETIME());
	}

	@Override
	public boolean greaterThan(Type value)
	{
		if (isNULL()) return false;
		if (value.isNULL()) return false;
		return this.value.after(((DATETIME) value).getDATETIME());
	}

	@Override
	public boolean equalTo(Type value)
	{
		if (isNULL()) return false;
		if (value.isNULL()) return false;
		return this.value.equals(((DATETIME) value).getDATETIME());
	}

	@Override
	public boolean noGreaterThan(Type value)
	{
		if (isNULL()) return false;
		if (value.isNULL()) return false;
		return !this.value.after(((DATETIME) value).getDATETIME());
	}

	@Override
	public boolean noLessThan(Type value)
	{
		if (isNULL()) return false;
		if (value.isNULL()) return false;
		return !this.value.before(((DATETIME) value).getDATETIME());
	}

	@Override
	public boolean notEqual(Type value)
	{
		if (isNULL()) return false;
		if (value.isNULL()) return false;
		return !this.value.equals(((DATETIME) value).getDATETIME());
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
