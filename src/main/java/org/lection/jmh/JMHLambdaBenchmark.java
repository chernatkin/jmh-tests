package org.lection.jmh;

import java.util.Random;
import java.util.function.Predicate;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Level;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

@State(Scope.Thread)
public class JMHLambdaBenchmark {

	private Random rand;
	
	private Predicate<Integer> staticPredicate;
	
	private Integer staticValue;
	
	@Setup(Level.Trial)
	public void init(){
		rand = new Random();
		
		staticValue = rand.nextInt(99999);
		
		staticPredicate = new Predicate<Integer>() {

			@Override
			public boolean test(Integer t) {
				return t.intValue() % 2 == 0;
			}
		};
	}
	
	@Benchmark
	public boolean staticPredicate() {
		return test(staticPredicate);
	}

	@Benchmark
	public boolean anonymousObjectPredicate() {
		return test(new Predicate<Integer>() {

			@Override
			public boolean test(Integer t) {
				return t.intValue() % 2 == 0;
			}
		});
	}
	
	@Benchmark
	public boolean lambdaPredicate() {
		return test(t-> t.intValue() % 2 == 0);
	}
	
	public boolean test(Predicate<Integer> predicate){
		return predicate.test(staticValue);
	}
	
	public static void main(String[] args) throws RunnerException {
		Options options = new OptionsBuilder()
				.include(JMHLambdaBenchmark.class.getSimpleName())
				.forks(1)
				.warmupIterations(5)
				.measurementIterations(10)
				.build();

		new Runner(options).run();
	}
}
