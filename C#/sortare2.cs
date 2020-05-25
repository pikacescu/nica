using System;
class nou
{
	public static int Main(string[] args)
	{
		Random rnd = new Random();
		int[] x = new int[30];
		for (int i = 0; i < x.Length; i++)
			x[i] = rnd.Next() % 101;
		for (int i = 0; i < x.Length; i++)
		    Console.Write ("" + x[i] + ", ");
		Console.WriteLine();
		
		for (int i = 0; i < x.Length; i++)
        {
			int z = x[i];
			int a = i;
			for (int j = i; j < x.Length; j++)
			{
				if (z < x[j])
				{
					a = j;
					z = x[j];
				}
			}
			x[a] = x[i];
			x[i] = z;
		}
        for (int i = 0; i < x.Length; i++)
            Console.Write ("" + x[i] + ", ");			
		
		return 0;
	}


}
   