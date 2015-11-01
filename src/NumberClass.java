import java.util.Arrays;

public class NumberClass {

	//STORES QUESTIONS
	final int NUM_OF_NUMBERS = 5;
	public int[] list;
	public String[] operators;
	public int answer;

	public NumberClass(){
		list = new int[NUM_OF_NUMBERS];
		operators = new String[NUM_OF_NUMBERS-1];
	}
	@Override
	public String toString(){
		return "NUMBERS:"+ Arrays.toString(list)+",OPERATORS:"+Arrays.toString(operators)+",ANSWER:"+answer;
	}
	
}


