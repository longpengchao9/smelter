package com.lpc.smelter.source_analysis.jdk.lock;

import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;

/**
 * @author longpengchao
 * @email longpengchao@58ganji.com
 * @create 2017-09-30 10:50
 */
public class ArrayBlockQueueTest {
	private static ArrayBlockingQueue<Long> queue = new ArrayBlockingQueue<Long>(5);

	public static void main(String[] args) {
		Thread t1 = new Thread(new Add(),"生产");
		Thread t2 = new Thread(new Get(),"消费");
		t1.start();
		t2.start();
	}

	static class Add implements Runnable{

		@Override
		public void run() {
			Random random = new Random();
			while(true){
				try {
					long l = random.nextInt(1000);
					boolean flag = queue.offer(l);
					System.out.println("生产：" + l + "," + flag);
					Thread.currentThread().sleep(500);
				}catch (Exception e){
					e.printStackTrace();
				}
			}
		}
	}
	static class Get implements Runnable{

		@Override
		public void run() {
			while(true){
				try {
					long l = queue.take();
					System.out.println("消费：" + l);
					Thread.currentThread().sleep(100);
				}catch (Exception e){
					System.out.println("消费异常");
				}
			}
		}
	}
}
