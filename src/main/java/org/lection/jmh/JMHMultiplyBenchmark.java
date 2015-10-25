package org.lection.jmh;

import java.util.Random;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;


@State(Scope.Thread)
public class JMHMultiplyBenchmark {
	
	private int intValue = new Random().nextInt(99999);
	
	@Benchmark
	public int multiply() {
		return multiply(intValue);
	}

	@Benchmark
	public Integer leftShift() {
		return lefShift(intValue);
	}
		
	@Benchmark
	public int division() {
		return division(intValue);
	}
	
	@Benchmark
	public int rightShift() {
		return rightShift(intValue);
	}

	private static int multiply(int a){
		return a * 2;
	}
		
	private static int division(int a){
		return a / 2;
	}
	
	private static Integer lefShift(int a){
		return a << 1;
	}
		
	private static Integer rightShift(int a){
		return a >> 1;
	}

	public static void main(String[] args) throws RunnerException {
		assert multiply(42) == lefShift(42) : "multiply and left shift is not equals";
		assert division(42) == rightShift(42) : "division and right shift is not equals";
		
		Options options = new OptionsBuilder()
				.include(JMHMultiplyBenchmark.class.getSimpleName())
				.forks(1)
				.warmupIterations(5)
				.measurementIterations(10)
				.build();

		new Runner(options).run();
	}
}