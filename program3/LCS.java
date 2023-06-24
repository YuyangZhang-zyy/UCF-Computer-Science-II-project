

/* Yuyang Zhang
 * Dr. Andrew Steinberg
 * COP3503 Summer 2022
 * Programming Assignment 3
 */

class LCS {
	
	private static int[][] x;//arrow table shows dp's relationships: upleft, up and left
	private static char[] a;//string s1
	private static char[] b;//string s2
	
	public LCS(String s1, String s2)//constructor for 2 parameters
	{
		LCS.a = s1.toCharArray();//change string to character array
		LCS.b = s2.toCharArray();//change string to character array
	}
	
	public static void LCSlength(char a[], char b[])//create dp table
	{
		int i, j;//index
		int length = a.length;//length of string
		
		int dp[][] = new int[length + 1][length + 1];//create empty dp table
		x = new int[length + 1][length + 1];//create empty arrow table
		
		for(i = 0; i <= length; i++)//set dp table's first row to 0
			dp[i][0] = 0;
		for(j = 0; j <= length; j++)//set dp table's first col to 0
			dp[0][j] = 0;
		
		//set data in both tables
		for(i = 1; i <= length; i++)
		{
			for(j = 1; j <= length; j++)
			{
				if(a[i - 1] == b[j - 1])//same character
				{
					dp[i][j] = dp[i - 1][j - 1] + 1;//this position's value equals upleft values plus 1
					x[i][j] = 1;//1 represent upleft
				}
				else if(dp[i - 1][j] >= dp[i][j - 1])//different character
				{
					dp[i][j] = dp[i - 1][j];//this position's value equals up values
					x[i][j] = 2;//2 represent up
				}
				else//different character
				{
					dp[i][j] = dp[i][j - 1];//this position's value equals left values
					x[i][j] = 3;//3 represent left
				}
			}
		}
	}
	
	public static void lcsDynamicSol()//called function by main that set both tables
	{
		LCSlength(a, b);
	}
	
	public static String getLCS()//called function by main that return LCS string
	{
		int length = x.length;//length of string
		String ans = LcsString(a, length - 1, length - 1);//get final string from LcsString function
		return ans;//return to main
	}
	
	public static String LcsString(char[] a, int i, int j)//generate LCS strings by checking arrow table 
	{
		String ans = "";//initial string
		if(i == 0 || j == 0)//base case
			return ans;
		
		if(x[i][j] == 1)//upleft arrow shows on arrow table
		{
			String charToString = String.valueOf(a[i - 1]);//character change to string
			ans = ans + LcsString(a, i - 1, j - 1) + charToString;//connect corresponding character to answer string
		}
		else if(x[i][j] == 2)//up arrow shows on arrow table
			ans = ans + LcsString(a, i - 1, j);//don't connect character to answer string and perform recursion
		else//left arrow shows on arrow table
			ans = ans + LcsString(a, i, j - 1);//don't connect character to answer string and perform recursion
		
		return ans;//return final string
	}
}

