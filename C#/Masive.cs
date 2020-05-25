using System;
class Masive
{
	public static int Main(string[] args)
	{
		Random rnd = new Random();
		Console.WriteLine (rnd.Next());
        
    	int[] mas1 = new int[10];
		for (int i = 0; i < mas1.Length; i++)
			mas1[i] = rnd.Next();

        foreach (int x in mas1)
		{
            Console.Write (x);
			Console.Write(",");
		}
		Console.WriteLine ();
		int y = mas1[0];
		for (int i = 1; i < mas1.Length; i++)
			if (y < mas1[i])  y = mas1[i];
		Console.WriteLine("maximul este " + y);
		
		
		int z = mas1 [0];
		for (int i = 1; i < mas1.Length; i++)
			if (z > mas1[i]) z = mas1[i];
		Console.WriteLine ("minimul este " + z);
		return 0;
	}
	
	
	
	
}
   