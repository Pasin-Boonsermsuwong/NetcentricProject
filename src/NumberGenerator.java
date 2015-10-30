import java.util.Random;

import bsh.EvalError;
import bsh.Interpreter;

public class NumberGenerator {

	//GENERATE QUESTIONS
	
	
	final int maxInteger = 9;
	final int minInteger = 1;
	
	public static void main(String[] args) {

		System.out.println(calculateAnswer("1/2"));
		
	}

	/**
	 * Generate the question (five numbers and answer)
	 * @param seed
	 * @return
	 */
	public static NumberClass generate(long seed){
		
		NumberClass question = new NumberClass();
		Random rand = new Random(seed);
		//Fill number array
		for(int i =0;i<question.list.length;i++){
			question.list[i] = rand.nextInt((maxInteger - minInteger) + 1) + minInteger;
		}
		//Generate random + - * / operation for number, calculate for answer
		for(int i =0;i<question.list.length;i++){
			question.answer = rand.nextInt((maxInteger - minInteger) + 1) + minInteger;
		}
		
		
		return question;
		
	}
	public static double calculateAnswer(String question){
		Interpreter interpreter = new Interpreter();

		try {
			interpreter.eval("result = "+question);
		} catch (EvalError e) {
			System.err.println("EvalError");
			e.printStackTrace();
		}
		
		Object result = null;
		try {
			result = interpreter.get("result");
		} catch (EvalError e) {
			System.err.println("EvalError");
			e.printStackTrace();
		}
		System.out.println(result+" "+result.getClass());
		return Double.parseDouble((String) result);

	}

}
