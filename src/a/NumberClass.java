package a;
import java.util.Arrays;

public class NumberClass {

	//STORES QUESTIONS
	final int NUM_OF_NUMBERS = 5;
	public double[] list;
	public String[] operators;
	public double answer;

	public NumberClass(){
		list = new double[NUM_OF_NUMBERS];
		operators = new String[NUM_OF_NUMBERS-1];
	}
	@Override
	public String toString(){
		return "NUMBERS:"+ Arrays.toString(list)+",OPERATORS:"+Arrays.toString(operators)+",ANSWER:"+answer;
	}
	
}


