using System;
class sortare3
{
	public static int Main(string[] args)
	{
		Random rnd = new Random();
		int[] x = new int[10];
		for (int i = 0; i < x.Length; i++)
			x[i] = rnd.Next() % 101;
		for (int i = 0; i < x.Length; i++)
		    Console.Write ("" + x[i] + ", ");
		Console.WriteLine();
		
		for (int ii = 0, ia = x.Length - 1; ii < ia; ii++, ia--)
        {
			int min = x[ii];
			int max = x[ia];
			int ai = ii, aa = ia;
			for (int ji = ii; ji <= ia; ji++)
			{
				if (min > x[ji])
				{
					ai = ji;
					min = x[ji];
				}
			}
			x[ai] = x[ii];
			x[ii] = min;
			for (int ja = ia; ja > ii; ja--)
			{
				if (max < x[ja])
    			{
					aa = ja;
					max = x[ja];
				}					
			}
			x[aa] = x[ia];
			x[ia] = max;
			
		}
        for (int i = 0; i < x.Length; i++)
            Console.Write ("" + x[i] + ", ");			
		
		return 0;
	}


}
   