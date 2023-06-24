/* Yuyang Zhang
 * Dr. Andrew Steinberg
 * COP3503 Summer 2022
 * Programming Assignment 1
 */
import java.util.Random;

class Lottery {
	
	private String ticket;//private string for lottery
	
	//constructor 1(default)
	public Lottery(){
		ticket = "";
	}
		
	//constructor 2
	public Lottery(Random rn){
		
		ticket = "";
		String s = "0123456789";//random number list
		for(int i = 0; i < 6; i++) {
			int rand = rn.nextInt(10);//random pick one
			ticket = ticket.concat(String.valueOf(s.charAt(rand)));//connect to ticket
		}
	}
	
	public static String GenerateRandomWinner(Random rn){
		
		String ans = "";
		String s = "0123456789";//random number list
		for(int i = 0; i < 6; i++) {
			int rand = rn.nextInt(10);//random pick one
			ans = ans.concat(String.valueOf(s.charAt(rand)));//connect to random ticket 
		}
		return ans;
	}
	
	public static int GenerateSelectWinner(int num, Random rn) {
		
		return rn.nextInt(num);//return random value for index
	}
	
	
	public static void Sort(Lottery [] tc) {
		//set quicksort
		int p = 0, r = tc.length;
		quicksort(tc, p, r-1);
	}
	
	public static void quicksort(Lottery [] arr, int p, int r) {
		//call partition for quicksort
		if(p < r)
	    {
	        int q = partition(arr, p, r);
	        quicksort(arr, p, q - 1);
	        quicksort(arr, q + 1, r);
	    }
	}
	
	public static int partition(Lottery [] arr, int p, int r) {
		
		String x = arr[r].GetTicket();//pivot
	    int i = p - 1;
	    String temp = "";//set temp for exchange

	    for(int j = p; j <= r - 1; j++)//if less than pivot then exchange
	    {
	        if((arr[j].GetTicket().compareTo(x)) < 0)
	        {
	            ++i;
	            temp = arr[i].GetTicket();
	            arr[i]= arr[j];
	            arr[j].setTicket(temp);
	        }
	    }
	    //exchange pivot
	    temp = arr[i + 1].GetTicket();
	    arr[i + 1] = arr[r];
	    arr[r].setTicket(temp);

	    return (i + 1);
	}
	
	public static boolean Solution1(Lottery [] tc, String test, int num) {
		//check all value in array
		for(int i = 0; i < num; i++) {
			if(tc[i].GetTicket() == test) {
				return true;
			}
		}
		return false;
	}
	
	public static boolean Solution2(Lottery [] tc, int low, int high, String goal) {

		//binary search
		while(low <= high) {
			
			int mid = (low + high)/2;//set mid
			//change low or high in different conditions
			if((tc[mid].GetTicket().compareTo(goal)) < 0)
				low = mid + 1;
			else if((tc[mid].GetTicket().compareTo(goal)) > 0)
				high = mid - 1;
			else 
				return true;
		}

		return false;//not in array 
		
	}

	//setter and getter
	public String GetTicket() {
		return ticket;
	}

	public void setTicket(String ticket) {
		this.ticket = ticket;
	}
	
}
