package fatworm;

public class Fatworm
{
	public static void main(String[] args)
	{
		try
		{
			System.out.println("FATWORM GO TO HELL!!!");
		} catch (Exception e)
		{
			e.printStackTrace();
			throw new RuntimeException("fatworm Connection cannot set up!");
		}
	}
}
