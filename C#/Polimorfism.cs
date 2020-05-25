using System;
class Polimorfism
{
	interface Animal
	{
		string denumire();
		void faSunet();
		void deplaseaza();
		void mananca();
		int numarPicioare();
	}
    static void faAnimal(Animal a)
    {
		Console.WriteLine ("animal: " + a.denumire());
		a.faSunet();
		a.deplaseaza();
		a.mananca();
		Console.WriteLine ("am " + a.numarPicioare() + " picioare");
	}
	
	class Vrabie : Animal
	{
		string nume;
		public Vrabie (string nume){this.nume = nume;}
		public string denumire(){return "vrabie " + nume;}
		public void faSunet () {Console.WriteLine ("cip cirip!");}
		public void deplaseaza () {Console.WriteLine ("zboara si sare");}
		public void mananca () {Console.WriteLine ("prinde gaze din zbor in aer");}
		public int numarPicioare () {return 2;}
	}
	
	class Zizi : Animal
	{
		string nume;
		public Zizi (string nume) { this.nume = nume; }
		public string denumire() {return "musca " + nume;}
		public void faSunet() {Console.WriteLine ("Miau");}
		public void deplaseaza() {Console.WriteLine ("orice fel de deplasare care enerveaza lumea");}
		public void mananca() {Console.WriteLine ("se hraneste cu nervii acumulati pe parcursul realizarii padlelor sale (si orice alta mancare)");}
		public int numarPicioare() {return 6;}
	}	
	
	class RAF : Animal
	{
		string nume;
		public RAF (string nume) {this.nume = nume;}
		public string denumire() {return "oaia" + nume;}
		public void faSunet() {Console.WriteLine ("beeeee");}
		public void deplaseaza() {Console.WriteLine ("merge ca un dalbaiob");}
		public void mananca() {Console.WriteLine ("mananca mizerie");}
		public int numarPicioare() {return 4;}
	}	
		
	
	public static int Main(string[] args)
	{
		Vrabie v = new Vrabie ("Cip");
		Animal v1 = new Vrabie("Boris");
		
		faAnimal(v);
		Console.WriteLine();
		faAnimal(v1);
		Console.WriteLine();
		
		Animal m = new Zizi ("Teta");
		faAnimal (m);
		Console.WriteLine();
		
		Animal r = new RAF ("Rafik");
		faAnimal (r);
		
	
		return 0;
	}
}
   