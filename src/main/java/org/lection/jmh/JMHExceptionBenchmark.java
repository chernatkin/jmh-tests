package org.lection.jmh;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Level;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

@State(Scope.Thread)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MICROSECONDS)
public class JMHExceptionBenchmark {

	private static final Random rand = new Random();
	
	private int intValue = rand.nextInt(9999);

	private static final Exception staticException = new Exception();
	
	@Setup(Level.Iteration)
	public void generateValues(){
		intValue = rand.nextInt(9999);
	}
	
	@Benchmark
	public int throwStaticException() {
		try{
		    throw staticException;
		} catch(Exception e){
			return intValue;
		}
	}
    
	@Benchmark
	public int throwException() {
		try{
		    throw new Exception();
		} catch(Exception e){
			return intValue;
		}
	}

	@Benchmark
	public int throwExceptionGetStacktrace() throws Exception {
		try{
		    throw new Exception();
		} catch(Exception e){
			return e.getStackTrace().length;
		}
	}

	public static void main(String[] args) throws RunnerException {
		Options options = new OptionsBuilder()
				.include(JMHExceptionBenchmark.class.getSimpleName())
				.forks(1)
				.warmupIterations(5)
				.measurementIterations(10)
				.build();

		new Runner(options).run();
	}
}
