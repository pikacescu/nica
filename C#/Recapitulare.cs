using System;
class Recapitulare
{
	public static int Main(string[] args)
	{
		double x = Math.Pow(3 , 4);
		Console.WriteLine("3 ^ 4 = " + x);
		
	    int d = dif2(10 , 11);
        Console.WriteLine("diferenta numerelor este " + d);
		
		int [] mas1 = new int[10]{1,2,3,2,3,4,1,2,3,4};
		int [] mas11 = {1,2,3,4,5};
		for (int i = 0; i < mas11.Length; i++)
			Console.WriteLine (mas11[i]);
		foreach (int m in mas1)
		    Console.WriteLine(m);
		double [,,] mas2;
		double [,][] mas3;
		
        return 0;		
	}
    
	static int dif2(int x , int y)
	{
		if (x > y) return x - y;
		if (x < y) return y - x;
		return 0;		
	}
	
}
   