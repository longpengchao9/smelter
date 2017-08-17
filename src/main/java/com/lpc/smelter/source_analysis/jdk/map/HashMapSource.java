package com.lpc.smelter.source_analysis.jdk.map;

import org.junit.Test;

/**
 * @author longpengchao
 * @email longpengchao@58ganji.com
 * @create 2017-07-31 17:00
 */
public class HashMapSource {

	@Test
	public void test1(){
		int n =-2;
		int n2 = n>>10;
		int n3 = n>>>10;
		System.out.println(Integer.MAX_VALUE);
		System.out.println(Integer.toBinaryString(n));
		System.out.println(n2);
		System.out.println(Integer.toBinaryString(n2));
		System.out.println(n3);
		System.out.println(Integer.toBinaryString(n3));
	}

	@Test
	public void binaryChange(){
		int n =16;
		System.out.println(Integer.toBinaryString(n));
	}

	@Test
	public void test2(){
		float f = 0.1f/0f;
		System.out.println(Float.isInfinite(f));
		System.out.println(Float.isNaN(f));
	}

	@Test
	public void test3(){
		int n = 1;
		int t = n<<=2;
		System.out.println(t);
		System.out.println(n);
	}

	@Test
	public void test4(){
		int n = 3;
		int t = 3;
		System.out.println(Integer.toBinaryString(n));
		System.out.println(Integer.toBinaryString(t));
		System.out.println(n & t);
		System.out.println(n%t);
	}

	@Test
	public void leftMoveTest(){
		int n = 2;
		System.out.println(Integer.toBinaryString(-1));
		System.out.println(n<<32);
		int m = -1;
		System.out.println(m<<31);
	}

	@Test
	public void rightMoveTest(){
		int n = -1;
		System.out.println(Integer.toBinaryString(n));
		System.out.println(Integer.toBinaryString(Integer.MAX_VALUE));
		System.out.println(n>>>31);
		System.out.println(n>>>32);
		System.out.println(1>>>31);
		System.out.println(1>>>1);
		System.out.println(1>>>34);
	}

	@Test
	public void xorTest(){
		int n1 = 1;
		int n2 = 10;
		System.out.println(Integer.toBinaryString(n1));
		System.out.println(Integer.toBinaryString(n2));
		System.out.println(Integer.toBinaryString(n1 ^ n2));
		System.out.println(n1^n2);
		System.out.println(Integer.toBinaryString(31));
	}

	@Test
	public void hashTest(){
		System.out.println(hash(1));
		System.out.println(hash(2));
		System.out.println(hash(3));
		System.out.println(hash(4));
		System.out.println(hash(5));
		System.out.println(hash(6));
		System.out.println(hash(7));
		System.out.println(hash(8));
		System.out.println(hash(9));
		System.out.println(hash(10));
		System.out.println(hash(11));
		System.out.println(hash(12));
		System.out.println(hash(13));
		System.out.println(hash(14));
		System.out.println(hash(15));
		System.out.println(hash(16));
		System.out.println(hash(17));
	}

	private int hash(int h){
		h ^= (h >>> 20) ^ (h >>> 12);
		return h ^ (h >>> 7) ^ (h >>> 4);
	}

	@Test
	public void test6(){
		int n1 = 5;
		int n2 = 7;
		System.out.println(n1^1);
		System.out.println(n1^2);
		System.out.println(n1^3);
		System.out.println(n1^4);
		System.out.println(n1^5);
		System.out.println(n1^6);
		System.out.println(n1^7);
		System.out.println(n1^8);
		System.out.println(n1^9);
		System.out.println(n2&1);
		System.out.println(n2&2);
		System.out.println(n2&3);
		System.out.println(n2&4);
		System.out.println(n2&5);
		System.out.println(n2&6);
		System.out.println(n2&7);
		System.out.println(n2&8);
		System.out.println(n2&9);
	}

}
