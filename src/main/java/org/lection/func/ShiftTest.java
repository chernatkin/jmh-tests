package org.lection.func;

public class ShiftTest {

	public static void main(String[] args) {
		
		printBinaryString(0);
		printBinaryString(Integer.MIN_VALUE);
		printBinaryString(Integer.MIN_VALUE + 1);
		printBinaryString(Integer.MAX_VALUE);
		System.out.println();
		
		printBinaryString(-10);
		printBinaryString(10);
		System.out.println();
		
		printBinaryString(10 >> 1);
		printBinaryString(-10 >> 1);
		System.out.println();
		
		printBinaryString(10 >>> 1);
		printBinaryString(-10 >>> 1);
		System.out.println();
	}

	private static void printBinaryString(final int value){
		System.out.println(value + "=" + Integer.toBinaryString(value));
	}
}
