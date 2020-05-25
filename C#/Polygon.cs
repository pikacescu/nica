using System;
using System.Windows.Forms;

using System.Drawing;

class Polygon
{

	public class F : Form
	{
		public F()
		{
			Paint += this.OnPaint;
		}
		private void OnPaint(object sender, PaintEventArgs a)
		{
			System.Drawing.Graphics g = a.Graphics;
			Pen p = new Pen(Color.Blue, 10);
			Point[] m = {new Point(50, 50), new Point(140, 140),   new Point(240, 140)};
			//g.DrawPolygon (p, m);
			Brush b = Brushes.Red;
			g.FillPolygon (b, m);
		}
		
	}	
	public static int Main(string[] args)
	{
		
		Application.Run(new F());
		return 0;
	}

}
   