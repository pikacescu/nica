using System;
using System.Windows.Forms;
using System.Drawing;

class Polygon2
{

	public class F : Form
	{
		public F()
		{
			Paint += this.OnPaint;
			StartPosition = FormStartPosition.WindowsDefaultBounds;
		}
		private void OnPaint(object sender, PaintEventArgs a)
		{
			System.Drawing.Graphics g = a.Graphics;
			Pen p = new Pen(Color.Blue, 1);
			Point[] m = {new Point(80, 20), new Point(30, 180),   new Point(160, 80), new Point(20, 80), new Point(140, 180) };
			Point[] n = {new Point(80, 80), new Point(70, 110),   new Point(100, 90), new Point(60, 90), new Point(90, 110)};
			Point[] o = 
			{
				new Point(350, 30), new Point(450, 30), new Point(470, 50), new Point(330, 50), new Point(350, 30), 
			    new Point(360, 50), new Point(370, 30), new Point(380, 50), new Point(390, 30), new Point(400, 50), 
				new Point(410, 30), new Point(420, 50), new Point(430, 30), new Point(440, 50), new Point(450, 30),
				new Point(470, 50), new Point(400, 130), new Point(440, 50), new Point(400, 130), new Point(420, 50),
				new Point(400, 50), new Point(400, 130), new Point(380, 50), new Point(400, 130), new Point(360, 50),
				new Point(400, 130), new Point(330, 50)
			};
			//g.DrawPolygon (p, m);
			Brush b = Brushes.BlueViolet;
			g.FillPolygon (b, m);
			g.FillPolygon (b, n);
			g.DrawPolygon (p, o);
		}
		
	}	
	public static int Main(string[] args)
	{
		
		Application.Run(new F());
		return 0;
	}

}
   