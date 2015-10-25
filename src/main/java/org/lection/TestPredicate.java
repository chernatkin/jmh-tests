package org.lection;

@FunctionalInterface
public interface TestPredicate<T> {

	public void test(T t);
}
