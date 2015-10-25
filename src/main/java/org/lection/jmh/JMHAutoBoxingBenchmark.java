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
public class JMHAutoBoxingBenchmark {
	
	private Random rand = new Random();
	
	private int intValue1 = rand.nextInt(99999);
	private int intValue2 = rand.nextInt(99999);
	
	private Integer integerValue1 = new Integer(intValue1); 
	private Integer integerValue2 = new Integer(intValue2);
    
	@Benchmark
	public int multiplyWithoutAutoBoxing() {
		return multiplyInts(intValue1, intValue2);
	}

	@Benchmark
	public Integer multiplyBoxing() {
		return multiplyIntegers(intValue1, intValue2);
	}
	
	@Benchmark
	public int multiplyUnboxing() {
		return multiplyInts(integerValue1, integerValue2);
	}

	private static int multiplyInts(int a, int b){
		return a * b;
	}
	
	private static Integer multiplyIntegers(Integer a, Integer b){
		return a * b;
	}
	

	public static void main(String[] args) throws RunnerException {
		Options options = new OptionsBuilder()
				.include(JMHAutoBoxingBenchmark.class.getSimpleName())
				.forks(1)
				.warmupIterations(5)
				.measurementIterations(10)
				.build();

		new Runner(options).run();
	}
}
