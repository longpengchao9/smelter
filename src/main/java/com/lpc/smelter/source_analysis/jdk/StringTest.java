package com.lpc.smelter.source_analysis.jdk;

import com.alibaba.fastjson.JSON;
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

	@Test
	public void test4(){
		System.out.println(get(16));
		System.out.println(get(3));
		System.out.println(Integer.MAX_VALUE);
		System.out.println(get(32));
		System.out.println(Long.MAX_VALUE);
	}
	private long get(int n){
		long result = 2;
		for(int i=1;i<n;i++){
			result = result * 2;
		}
		return result;
	}
private static int temp  = 0;
private static int LIMIT  = 5;
private static boolean flag  = false;

	@Test
	public void test6(){
		for(int n = 0;n<20;n++){
			if(n > 8 && n <14) {
				fail();
			}else {
				isOk();
			}
		}
		System.out.println(flag);
		System.out.println(temp);
	}
	private void isOk(){
		if(temp == 0){
			if(flag) {
				flag = false;
			}
			return;
		}
		temp--;
	}

	private void fail(){
		if(temp == LIMIT){
			if(!flag) {
				flag = true;
			}
			return;
		}
		temp++;
	}

	@Test
	public void test18(){
		int COUNT_BITS = Integer.SIZE - 3;
		int CAPACITY   = (1 << COUNT_BITS) - 1;

		int RUNNING    = -1 << COUNT_BITS;
		int SHUTDOWN   =  0 << COUNT_BITS;
		int STOP       =  1 << COUNT_BITS;
		int TIDYING    =  2 << COUNT_BITS;
		int TERMINATED =  3 << COUNT_BITS;
		System.out.println(RUNNING|0);

	}
	@Test
	public void test19() throws InterruptedException {
		long n = System.nanoTime();
		Thread.currentThread().sleep(10);
		long end = System.nanoTime() - n;
		System.out.println("end="+end);
		n = System.currentTimeMillis();
		Thread.currentThread().sleep(10);
		end = System.currentTimeMillis() - n;
		System.out.println("end="+end);
		System.out.println(JSON.toJSONString(System.getProperties()));
	}

	}
