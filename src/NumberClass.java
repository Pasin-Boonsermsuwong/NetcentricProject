import java.util.Arrays;


public class NumberClass {

	//STORES QUESTIONS
	
	public int[] list;
	public int answer;
	
	public NumberClass(){
		list = new int[5];
	}
	
	public String toString(){
		return "NUMBERS:"+ Arrays.toString(list)+",ANSWER:"+answer;
	}
}


