package org.lection.jmh;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;
import java.util.concurrent.TimeUnit;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import org.openjdk.jmh.runner.options.TimeValue;

@State(Scope.Thread)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
public class JMHAddToCollectionBenchmark {
	
	private Random rand = new Random();
	
	private int intValue = rand.nextInt(9999999);
	
	private Integer integerValue = new Integer(intValue);
	
	private static final int MAX_SIZE = 10000000;
    
	private List<Integer> arrayList = new ArrayList<>(MAX_SIZE);
	
	private ArrayDeque<Integer> deque = new ArrayDeque<>(MAX_SIZE);
	
	private List<Integer> linkedList = new LinkedList<>();
	
	private Map<Integer, Integer> hashMap = new HashMap<>(MAX_SIZE);
	
	private Map<Integer, Integer> treeMap = new TreeMap<>();
	
	private Integer[] arrayIntegers = new Integer[MAX_SIZE];
	
	private IntBuffer intBuffer = ByteBuffer.allocateDirect(4 * MAX_SIZE).asIntBuffer();
	
	@Benchmark
	public boolean putIntoArrayList() {
		return arrayList.add(integerValue);
	}

	@Benchmark
	public boolean putIntoLinkedList() {
		return linkedList.add(integerValue);
	}
	
	@Benchmark
	public Integer putIntoArray() {
		return arrayIntegers[10] = integerValue;
	}
	
	@Benchmark
	public IntBuffer putIntoIntBuffer() {
		return intBuffer.put(intValue);
	}
	
	@Benchmark
	public boolean putIntoArrayDeque() {
		return deque.add(integerValue);
	}

	@Benchmark
	public Integer putIntoHashMap() {
		return hashMap.put(integerValue, integerValue);
	}
	
	@Benchmark
	public Integer putIntoTreeMap() {
		return treeMap.put(integerValue, integerValue);
	}
	
	public static void main(String[] args) throws RunnerException {
		Options options = new OptionsBuilder()
				.include(JMHAddToCollectionBenchmark.class.getSimpleName())
				.forks(1)
				.warmupIterations(5)
				.warmupTime(TimeValue.milliseconds(10))
				.measurementIterations(10)
				.measurementTime(TimeValue.milliseconds(10))
				.shouldDoGC(true)
				//.jvmArgsPrepend("-Xmx2048M")
				.build();

		new Runner(options).run();
	}
}