import java.util.Random;

import bsh.EvalError;
import bsh.Interpreter;

public class NumberGenerator {

	//GENERATE QUESTIONS
	
	
	final static int maxInteger = 9;
	final static int minInteger = 1;
	
	public static void main(String[] args) {

		//System.out.println(calculateAnswer("1+2/2"));
		Random rand = new Random();
		for(int i=0;i<100;i++){
			System.out.println(generate(rand.nextLong()).toString());
		}
	//	System.out.println(calculateAnswer("5-3*4-3/9"));
	}

	/**
	 * Generate the question (five numbers and answer)
	 * @param seed
	 * @return
	 */
	public static NumberClass generate(long seed){
		
		NumberClass n = new NumberClass();
		Random rand = new Random(seed);
		
		//Fill number array
		for(int i =0;i<n.list.length;i++){
			n.list[i] = rand.nextInt((maxInteger - minInteger) + 1) + minInteger;
		}
		
		//Generate random + - * / operation for number
		n.operators = new String[n.list.length-1];	
		for(int i = 0;i<n.operators.length;i++){
			int operator = rand.nextInt((4 - 1) + 1) + 1;		
			switch(operator){
			case 1:		// +
				n.operators[i] ="+";
				break;
			case 2:		// -
				n.operators[i] ="-";
				break;
			case 3:		// X
				n.operators[i] ="*";
				break;
			case 4:		// /
				n.operators[i] ="/";
				//special case, numerator / denominator must be INT
				
				//Find numerator = all numbers connected by * or / ---> how long backward do we need to calculate
				int leng = 0;
				for(int j = i-1;j>=0;j--){
					if(n.operators[j].equals("*")||n.operators[j].equals("/")){
						leng++;
					}
					else break;
				}
				//calculate numerator
				StringBuilder sb = new StringBuilder();
				for(int k = i-leng;k<=i;k++){
					sb.append(n.list[k]);
					if(k<i)sb.append(n.operators[k]);
				}
		//		System.out.println("NUMERATOR:"+sb.toString());
				int numerator = calculateAnswer(sb.toString());		
				
				//randomize possible denominator
				int maxDenom = Math.min(maxInteger, numerator);
				while(true){
					n.list[i+1] = rand.nextInt((maxDenom - minInteger) + 1) + minInteger;			
					if(numerator%n.list[i+1] == 0)break;			
				}
				break;
			}
		}	
		
		//Generator the string of equation; eg: 4+5*15/5
		StringBuilder sb = new StringBuilder();
		for(int i= 0;i<n.list.length;i++){
			sb.append(n.list[i] +".0");
			if(i<n.list.length-1)sb.append(n.operators[i]);
		}
		//Calculate answer
		n.answer = calculateAnswer(sb.toString());
		
		return n;
		
	}
	public static int calculateAnswer(String question){
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
	//	System.out.println(result+" "+result.getClass());
		return (int) result;
	}
	public static double calculateAnswerDouble(String question){
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
	//	System.out.println(result+" "+result.getClass());
		return (double) result;
	}

}
