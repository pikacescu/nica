using System;
using System.Windows.Forms;
using System.Drawing;
class Visual
{
	public static int Main(string[] args)
	{
		Form f = new Form ();
		
		Button b =  new Button();
		Button b1 = new Button();
		Button b2 = new Button();
		Button b3 = new Button();
		
		b.Location = new Point(10, 10);
		b.Click += new System.EventHandler((object sender, EventArgs e) =>
        {
			Console.WriteLine ("Noroc");

        });
		b1.Location = new Point(10, 50);
		b1.Click += new System.EventHandler((object sender, EventArgs e) =>
        {
			Console.WriteLine ("Salut");

        });
		b2.Location = new Point(10, 90);
		b2.Click += new System.EventHandler((object sender, EventArgs e) =>
        {
			Console.WriteLine ("Buna ziua");

        });
		b3.Location = new Point(10, 130);
		b3.Click += new System.EventHandler((object sender, EventArgs e) =>
        {
			Console.WriteLine ("O zi buna");

        });
		
		b.Size = new Size(148, 23);
		b1.Size = new Size(148, 23);
		b2.Size = new Size(148, 23);
		b3.Size = new Size(148, 23);
		
		b.Text = "noroc";
		b1.Text = "salut";
		b2.Text = "buna ziua";
		b3.Text = "o zi buna";
		
		f.Controls.Add(b);
		f.Controls.Add(b1);
		f.Controls.Add(b2);
		f.Controls.Add(b3);
		
		f.StartPosition = FormStartPosition.WindowsDefaultBounds;

		//f.ShowDialog();
		Application.Run(f);
		return 0;
	}
}
   