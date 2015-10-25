package org.lection.func;

import java.util.function.Predicate;

import org.lection.TestPredicate;

public class LampdaParamsTest {

	public static void main(String[] args) {
		System.out.println(new LampdaParamsTest().executePredicate(obj -> obj == null));
	}

	public String executePredicate(TestPredicate<Object> predicate){
		return "TestPredicate";
	}
	
	public String executePredicate(Predicate<Object> predicate){
		return "Predicate";
	}
}
