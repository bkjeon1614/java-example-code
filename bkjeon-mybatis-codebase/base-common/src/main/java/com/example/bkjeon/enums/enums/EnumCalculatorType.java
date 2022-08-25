package com.example.bkjeon.enums.enums;

import java.util.function.Function;

/**
 * [상태와 행위를 한곳에서 관리]
 */
public enum EnumCalculatorType {

	CALC_A(value -> value),
	CALC_B(value -> value * 5),
	CALC_C(value -> value * 10),
	CALC_ETC(value -> 0);

	private Function<Integer, Integer> expression;

	EnumCalculatorType(Function<Integer, Integer> expression) {
		this.expression = expression;
	}

	public int calculate(int value) {
		return expression.apply(value);
	}

}
