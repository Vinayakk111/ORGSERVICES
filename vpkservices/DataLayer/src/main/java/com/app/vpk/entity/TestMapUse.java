package com.app.vpk.entity;

public class TestMapUse {
	public static void main(String[] args) {
		 String input = "hello";
		 Double input1 = 2.365974d;

	        String result = TestInterface.stringCommands
	                .getOrDefault("upper", s -> "Unknown command")
	                .apply(input);

	        System.out.println(result);  // Outputs: HELLO
	        
	        String result1 = TestInterface.numericCommands
	                .getOrDefault("2digit", s -> Double.toString(input1))
	                .apply(input1);

	        System.out.println(result1);
	        
	        String result2 = TestInterface.BiFunCommands
	                .getOrDefault("concat", (str1, str2)
	                		-> {return (str1 + str2);})
	                .apply("Vinayak","Kamble");
	        System.out.println(result2); 
	}
}