using System;
class Tablouri
{
	public static int Main(string[] args)
	{
		Random rnd = new Random();
		Console.WriteLine (rnd.Next() % 326);
		int[] x = {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15};
		for (int i = 0; i < x.Length; i++)
            Console.Write ("" + x[i] + ",");
		Console.WriteLine ();
		int z = x[3];
		for (int i = 4; i <= 10; i++)
			x[i - 1] = x[i];
		x[10] = z;
		for (int i = 0; i < x.Length; i++)
			Console.Write ("" + x[i] + ",");
		int a = x[10];
		for (int i = 10; i >= 4; i--)
			x[i] = x[i - 1];
		x[3] = a;
		Console.WriteLine ();
		for ( int i = 0; i < x.Length; i++)
			Console.Write ("" + x[i] + ",");
		return 0;
	}


}
   