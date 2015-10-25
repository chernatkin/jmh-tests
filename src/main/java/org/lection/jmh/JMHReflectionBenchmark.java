package org.lection.jmh;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.lection.TestPojo;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
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
@OutputTimeUnit(TimeUnit.NANOSECONDS)
public class JMHReflectionBenchmark {

	private Random rand = new Random();
	
    private TestPojo staticPojo = new TestPojo();
	
    private Method setterMethod;
    
    private Method getterMethod;
    
    private Field valueField;
    
	private Integer staticValue = rand.nextInt(99999);
	
	@Setup
	public void setUp() throws NoSuchMethodException, SecurityException, NoSuchFieldException{
		setterMethod = TestPojo.class.getDeclaredMethod("setValue", Integer.class);
		getterMethod = TestPojo.class.getDeclaredMethod("getValue");
		valueField = TestPojo.class.getDeclaredField("value");
		valueField.setAccessible(true);
	}
	
	@Benchmark
	public Integer useSetter() {
		return staticPojo.setValue(staticValue);
	}

	@Benchmark
	public Integer useReflectionSetter() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		return (Integer) setterMethod.invoke(staticPojo, staticValue);
	}
	
	@Benchmark
	public Integer useGetter() {
		return staticPojo.getValue();
	}
	
	@Benchmark
	public Integer useReflectionGetter() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		return (Integer) getterMethod.invoke(staticPojo);
	}
	
	@Benchmark
	public Integer useReflectionField() throws IllegalArgumentException, IllegalAccessException {
		return (Integer) valueField.get(staticPojo);
	}
	
	@Benchmark
	public Method findSetterMethod() throws NoSuchMethodException, SecurityException {
		return TestPojo.class.getDeclaredMethod("setValue", Integer.class);
	}
	
	@Benchmark
	public Method findGetterMethod() throws NoSuchMethodException, SecurityException  {
		return TestPojo.class.getDeclaredMethod("getValue");
	}
	
	@Benchmark
	public Field findField() throws NoSuchFieldException, SecurityException {
		return TestPojo.class.getDeclaredField("value");
	}
	
	public static void main(String[] args) throws RunnerException {
		Options options = new OptionsBuilder()
				.include(JMHReflectionBenchmark.class.getSimpleName())
				.forks(1)
				.warmupIterations(5)
				.measurementIterations(10)
				.build();

		new Runner(options).run();
	}
}