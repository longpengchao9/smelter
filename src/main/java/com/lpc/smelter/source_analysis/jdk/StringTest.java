package com.lpc.smelter.source_analysis.jdk;

import org.junit.Test;

/**
 * @author longpengchao
 * @email longpengchao@58ganji.com
 * @create 2017-09-01 14:55
 */
public class StringTest {

	@Test
	public void test1(){
		System.out.println(5/24 +"");
	}

	@Test
	public void test2(){
		int i = 0;
		System.out.println(i++);
		System.out.println(++i);
	}
	@Test
	public void test3(){
		int i = 0;
		int n =3;
		int s = 4;
		if(i==0){
			if(n==3){
				System.out.println("n=2");
				return;
			}
		} else if(s == 4){
			System.out.println("s=4");
			return;
		}
		System.out.println("nnnn");
	}
}
