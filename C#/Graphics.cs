using System;
using System.Windows.Forms;
using System.Drawing;
class Graphics
{
	public static int Main(string[] args)
	{
		Form f = new Form ();
		
		Button b =  new Button();   b.Size = new Size(500, 20);     b.Text = "Cele mai cultivate flori de gradina";
		Button b1 = new Button();   b1.Size = new Size(500, 20);   	b1.Text = "Cele mai raspandite flori de camera";
		Button b2 = new Button();   b2.Size = new Size(100, 20);    b2.Text = "Pretul";
		
		
		b.Location = new Point(10, 10);
		b.Click += new System.EventHandler((object sender, EventArgs e) =>
        {
			Console.WriteLine ("trandafiri, lilii, gladiole, regina noptii, lalele, bujori, crizanteme");
        });
		b.MouseMove += new System.Windows.Forms.MouseEventHandler ( (object sender, System.Windows.Forms.MouseEventArgs e) =>
        {
			f.Text = "Button b se misca mouse x=" + e.X + " y=" + e.Y;
        } );
		f.Controls.Add(b);
		
		
		b1.Location = new Point(10, 50);
		b1.Click += new System.EventHandler((object sender, EventArgs e) =>
        {
			Console.WriteLine ("cactusi, ficusi, trandafiri chinezesti, ferigi, toporasi, orhidei");
        });
			f.Controls.Add(b1);
			
			Console.WriteLine("");
			
		
		b2.Location = new Point(230, 90);
		b2.Click += new System.EventHandler((object sender, EventArgs e) =>
        {
			Console.WriteLine ("Pretul Florilor de gradina:");
			Console.WriteLine ("trandafiri = 50 lei");	
            Console.WriteLine ("lilii = 48 lei");
            Console.WriteLine ("gladiole = 45 lei");
            Console.WriteLine ("regina noptii = 70 lei");
            Console.WriteLine ("lalele = 40 lei");
            Console.WriteLine ("bujori = 40 lei");
            Console.WriteLine ("crizanteme = 40 lei");
            Console.WriteLine (" ");
            Console.WriteLine ("Pretul Florilor de camera");
            Console.WriteLine ("cactusi = 100 lei");
            Console.WriteLine ("ficusi = 120 lei");
            Console.WriteLine ("trandafiri chinezesti = 200 lei");
            Console.WriteLine ("ferigi = 60 lei");
            Console.WriteLine ("toporasi = 40-50 lei");
            Console.WriteLine ("orhidei = 120-300 lei");
			
        });
		f.Controls.Add(b2);
		f.Paint += new System.Windows.Forms.PaintEventHandler ((object sender, System.Windows.Forms.PaintEventArgs pe) =>
		{
			System.Drawing.Graphics g = pe.Graphics;

            Pen myPen = new Pen(Color.Blue, 1);
            g.DrawRectangle(myPen, 100, 100, 140, 120);
            myPen = new Pen(Color.Red, 1);
            g.DrawRectangle(myPen, 500, 100, 140, 120);
            myPen = new Pen(Color.Yellow, 1);
            g.DrawRectangle(myPen, 1000, 100, 55, 50);
			
		});
		
		f.StartPosition = FormStartPosition.WindowsDefaultBounds;
        

		Application.Run(f);
		return 0;
	}
}
   