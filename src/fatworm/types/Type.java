package fatworm.types;

public interface Type
{
	public boolean isBOOLEAN();

	public boolean isCHAR();

	public boolean isDATETIME();

	public boolean isDECIMAL();

	public boolean isFLOAT();

	public boolean isINT();

	public boolean isTIMESTAMP();

	public boolean isVARCHAR();

	public boolean isNULL();

	public void setNULL();

	public void setNotNULL();

	public Object getValue();

	public boolean lessThan(Type value);

	public boolean greaterThan(Type value);

	public boolean equalTo(Type value);

	public boolean noGreaterThan(Type value);

	public boolean noLessThan(Type value);

	public boolean notEqual(Type value);

	public Type clone();

	public String toString();

	public Type add(Type val);

	public Type devideBy(int count);

	public int compareTo(Type tb);

	public int length();
}
