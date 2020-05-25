using System;
class sortare
{
	public static int Main(string[] args)
	{
		Random rnd = new Random();
		int[] x = new int[10];
		for (int i = 0; i < x.Length; i++)
			x[i] = rnd.Next() % 101;
		for (int i = 0; i < x.Length; i++)
		    Console.Write ("" + x[i] + ", ");
		Console.WriteLine();
        Console.WriteLine ("min = " + min(x)); 
		
		int[] y = new int[20];
		for (int i = 0; i < y.Length; i++)
			y[i] = rnd.Next() % 101;
		for (int i = 0; i < y.Length; i++)
		    Console.Write ("" + y[i] + ", ");
		Console.WriteLine();
		Console.WriteLine ("min = " + min(y));
		
		int[] z = new int[30];
		for (int i = 0; i < z.Length; i++)
			z[i] = rnd.Next() % 101;
		for (int i = 0; i < z.Length; i++)
		    Console.Write ("" + z[i] + ", ");
		Console.WriteLine ();
		Console.WriteLine ( "min = " + min(z)); 
		return 0;
	}
	static int min(int[] x)
	{
		int y = x[0];
		for (int i = 1; i < x.Length; i++)
			if (y > x[i])
				y = x[i];
		return y;
	}


}
   