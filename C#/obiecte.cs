using System;
class obiecte
{
	class figura
	{
		int x = 0, y = 0;
		int getX() {return x;}
		int getY() {return y;}
	}
	class triunghi : figura
	{
		public static int triunghiuri = 0;
		public double a;
		public double b  = 123;
		public double c = Math.Sqrt (Math.PI + Math.E);
		public triunghi ()
		{
			triunghiuri ++;
		}
		public triunghi (int i, int j, int k)
		{
			a = i; b = j; c = k;
			triunghiuri ++;
		}
	}
	class dreptunghi : figura
	{
		public double a, b;
		public double diagonala()
		{
			return Math.Sqrt(a * a + b * b);
		}
	}
	
	public static int Main(string[] args)
	{
		triunghi t1 = new triunghi { a = 1, b = 2, c = 3};
		Console.WriteLine ("Avem " + triunghi.triunghiuri + " triunghiuri");
		triunghi t2 = new triunghi()
		{
			a = 4,
			b = 5,
			c = 6
		};
		Console.WriteLine ("Avem " + triunghi.triunghiuri + " triunghiuri");
		triunghi t3 = new triunghi (10, 22, 11);
		Console.WriteLine ("Avem " + triunghi.triunghiuri + " triunghiuri");
		
		dreptunghi d1 = new dreptunghi() {a = 3, b = 4};

		Console.WriteLine ("diagonala dreptunghiului d1 are lungimea de " + d1.diagonala() + " (unitati de masura)");
		
		return 0;
	}
}
   