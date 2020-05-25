using System;
class nou
{
	public static int Main(string[] args)
	{
		Random rnd = new Random();
		Console.WriteLine (rnd.Next() % 101);
		int[] x = new int[10];
		int[] x1 = new int[20];
		for (int i = 0; i < x.Length; i++)
            x[i] = rnd.Next() % 101;
		for (int i = 0; i < x1.Length; i++)
            x1[i] = rnd.Next() % 101;

		for (int i = 0; i < x.Length; i++)
            Console.Write ("" + x[i] + ",");
		Console.WriteLine();
		for (int i = 0; i < x1.Length; i++)
            Console.Write ("" + x1[i] + ",");
		Console.WriteLine();


        for (int i = 0; i < x.Length; i++)
		{
			for (int j = 0; j < x.Length-1; j++)
            {
				if (x[j] > x[j+1]) 
				{
					int z = x[j];
					x[j] = x[j+1];
					x[j+1] = z;
				}
            }    				
		}			

		Console.WriteLine ();
		
		for (int i = 0; i < x.Length; i++)
			Console.Write ("" + x[i] + ",");
		
		return 0;
	}


}
   