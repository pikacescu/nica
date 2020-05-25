using System;
class Primul
{
	public static int Main(string[] args)
	{
		Console.WriteLine("salut");
		Console.Write("salut");
		Console.WriteLine("salut");
		int i = 0;
		
		if (i > 0)
		{
		    Console.WriteLine ("i este mai mare decat 0");
		}
        else if (i < 0)
			Console.WriteLine ("i este mai mic decat 0");
		else
			Console.WriteLine ("i nu este nici mai mare si nici mai mic decat 0");
		
		{
			int x = 123;
			Console.WriteLine ("x = " + x);
			{
				int y = 321;
				x = 234;
				
				for (int ii = 0; ii < 10; ii++)
				{
					ii --;
					Console.WriteLine ("ciclu for1");

					break;
					Console.WriteLine ("ciclu for2");
				}
			    for (int ii = 0, zz = 123; zz >= 0; zz--);
			    Console.WriteLine ("x = " + x);
				Console.WriteLine ("y = " + y);

			}
			{
				{
					{
						
					}
					{
						
					}
				}
				{
					
				}
			}
			{
				
			}

		}
			
        int l = 1, p = 2;
        string xx = "x+y=z=" + (p + l);
		Console.WriteLine(xx);
		Console.WriteLine(xx,xx);
		Console.WriteLine(xx,xx);
		
		Console.WriteLine(xx+l+p*5);
		
		
		for (int m =23; m > 1; m--)
		{
			if (m % 4 == 0)
				continue;
			if (m == 5)
				break;
	        Console.WriteLine ("m inca este mai mare decat 1: m este egal cu " + m);
			
	    }
		
        		
		return 0;
	}
}