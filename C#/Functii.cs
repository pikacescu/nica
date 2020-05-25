using System;
class Functii
{
	public static int Main(string[] args)
	{
		int a = 1, b = 2, c = 0;
		sumaNumerelor(a, b, c);
		float d = diferenta(a, b, c);
		
		int s = sumaNumerelor(0, b,  (1+2*3)/4 + 5);
		Console.WriteLine ("**** rezultat = " + s);
        Console.WriteLine ("diferenta este " + d);
		return 0;
	}
	
	static int  sumaNumerelor(int x, int y, int z)
	{
	    Console.WriteLine("x este 0?");
		if (x == 0 && y != 0 && z != 0) return y + z;			
	    Console.WriteLine("y este 0?");
		if (x != 0 && y == 0 && z != 0) return x + z;			
	    Console.WriteLine("z este 0?");
		if (x != 0 && y != 0 && z == 0) return x + y;			
	    Console.WriteLine("Sunt toate 0?");
		if (x == 0 && y == 0 && z == 0) return 0;			
	    Console.WriteLine("Sunt x,y 0?");
		if (x == 0 && y == 0 && z != 0) return z;			
	    Console.WriteLine("Sunt x,z 0?");
		if (x == 0 && y != 0 && z == 0) return y;			
	    Console.WriteLine("Sunt y,z 0?");
		if (x != 0 && y == 0 && z == 0) return x;			
	    Console.WriteLine("toate sunt diferite de 0");
		return x+y+z;
	}
	
	static float    diferenta(float x, float y, float z)
	{
		if (x >= y && y >= z) return x - y - z;
	
		if (x >= y && x <= z) return z - x - y;
		
		if (x >= z && y >= x) return y - x - z;
		
		return 0f;
	}
	
	
}
   