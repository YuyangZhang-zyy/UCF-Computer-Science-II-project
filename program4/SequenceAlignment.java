/* Yuyang Zhang
 * Dr. Andrew Steinberg
 * COP3503 Summer 2022
 * Programming Assignment 4
 */

public class SequenceAlignment {
	
	private static int[][] x;//arrow table shows dp's relationships: upright, up and right
	private static int[][]dp;//dp table
	private static char[] a;//string s1
	private static char[] b;//string s2
	
	public SequenceAlignment(String s1, String s2)//constructor change string to char array
	{
		a = s1.toCharArray();
		b = s2.toCharArray();
	}
	
	public static void computeAlignment(int det)//execute dynamic programming sequence alignment
	{
		int i, j;//index
		//obtain word length
		int m = a.length;
		int n = b.length;

		//initialize two tables, make sure each bound has max length for the sum of two words length
		dp = new int[m + n + 1][m  + n + 1];
		x = new int[m + n + 1][m + n + 1];
		
		//set first row and first column
		for(i = 0; i <= m + n; i++)
		{
			dp[i][0] = i * det;
			dp[0][i] = i * det;
		}
		
		//set values in tables
		for(i = 1; i <= m; i++)
		{
			for(j = 1; j <= n; j++)
			{
				//choose the minimum cost at the current position from left, down or leftdown
				dp[i][j] = Math.min(Math.min(alpha(a[i - 1], b[j - 1]) + dp[i - 1][j - 1], det + dp[i - 1][j]), det + dp[i][j - 1]);
				
				//set arrow in arrow table
				if(dp[i][j] == alpha(a[i - 1], b[j - 1]) + dp[i - 1][j - 1])//upright arrow
					x[i][j] = 1;
				else if(dp[i][j] == det + dp[i - 1][j])//up arrow
					x[i][j] = 2;
				else//right arrow
					x[i][j] = 3;
			}
		}
	}
	
	public static String getAlignment()//get strings of two completed words which may contain '-'
	{
		//set index
		int i = a.length;
		int j = b.length;
		int length = i + j;
		int c = length;
		int d = length;
		
		//char array for words that may have the sum of two words length
		char word1[] = new char[length + 1];
		char word2[] = new char[length + 1];
		
		//final answers
		String ans1 = "";
		String ans2 = "";
		
		while(!(i == 0 || j == 0))//loop if not reach bound
		{
			if(x[i][j] == 1)//arrow is upright
			{
				word1[c--] = a[i - 1];//current char can insert to answer1
	            word2[d--] = b[j - 1];//current char can insert to answer2
	            //move to leftdown
	            i--; 
	            j--;
			}
			else if(x[i][j] == 2)//arrow is up
			{
				word1[c--] = a[i - 1];//current char is ok to insert in answer
				word2[d--] = '-';//need a gap since we move up
				i--;//move to left
			}
			else if(x[i][j] == 3)//arrow is right
			{
				word1[c--] = '-';//need a gap since we move right
				word2[d--] = b[j - 1];//current char is ok to insert in answer
				j--;//move to down
			}
		}
		
		//set '-' from word beginning to its first valid char
		while(c > 0)
		{
			if(i > 0)//char array1 not reach bound
				word1[c--] = a[--i];//assigned rest char into word1
			else
				word1[c--] = '-';//fill gaps until reach bound for word1
		}
		while(d > 0)
		{
			if(j > 0)//char array2 not reach bound
				word2[d--] = b[--j];//assigned rest char into word2
			else
				word2[d--] = '-';//fill gaps until reach bound for word2
		}
		
		int start = 1;//initial start index
		for(i = length; i >= 1; i--)
		{
			if(word1[i] == '-' && word2[i] == '-')//check if '-'
			{
				start = i + 1;//find the start position of word1 and word2(skip the beginning '-')
				break;
			}
		}
		
		for(i = start; i <= length; i++)//change char array type to string type
		{
			ans1 = ans1 + String.valueOf((char)word1[i]);
			ans2 = ans2 + String.valueOf((char)word2[i]);
		}

		return ans1 + " " + ans2;//return final answer separated by space
	}
	
	public static int alpha(char x, char y)//determine conditions for alpha value
	{
		if(x == y)//same
			return 0;
		else if(isVowel(x) == isVowel(y))//both vowel or both consonant
			return 1;
		else//vowel and consonant
			return 3;
	}
	
	public static int isVowel(char ch)//determine if vowel
	{
		if(ch == 'a' || ch == 'e' || ch == 'i' || ch == 'o' || ch == 'u')
			return 1;
		else
			return 0;
	}
}
