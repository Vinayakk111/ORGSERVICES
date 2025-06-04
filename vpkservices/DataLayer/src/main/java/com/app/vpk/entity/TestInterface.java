package com.app.vpk.entity;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;

public interface TestInterface {
	Function<String, String> upperFunction = s -> s.toUpperCase();
	Function<String, String> lowerFunction = s -> s.toLowerCase();
	Function<String, String> repeatFunction = s -> s + s;

	Function<Double, String> getTwoDigitAfterDecimal = number -> {
		return new DecimalFormat("#.00").format(number);
	};
	Function<Double, String> getThreeDigitAfterDecimal = number -> {
		return new DecimalFormat("#.000").format(number);
	};

	BiFunction<String, String, String> concatenate = (str1, str2) -> {
		return (str1 + str2);
	};

	Function<String, String> bigFunction = s -> {
		return "vinayak";
	};
	Map<String, Function<String, String>> stringCommands = new HashMap<String, Function<String, String>>() {
		{
			put("upper", s -> upperFunction.apply(s));
			put("lower", s -> lowerFunction.apply(s));
			put("repeat", s -> repeatFunction.apply(s));
		}
	};
	Map<String, Function<Double, String>> numericCommands = new HashMap<String, Function<Double, String>>() {
		{
			put("2digit", s -> getTwoDigitAfterDecimal.apply(s));
			put("3digit", s -> getThreeDigitAfterDecimal.apply(s));
		}
	};

	Map<String, BiFunction<String, String, String>> BiFunCommands = new HashMap<String, BiFunction<String, String, String>>() {
		{
			put("concat", (s1,s2) -> concatenate.apply(s1,s2));
		}
	};

}
