package com.lpc.smelter.source_analysis.jdk.queue;

/**
 * @author longpengchao
 * @email longpengchao@58ganji.com
 * @create 2017-11-03 15:32
 */
public class MyBlockQueueTest {

	private static MyBlockQueue<Integer> blockQueue = new MyBlockQueue<Integer>(4);

	public static void main(String[] args) {
		Thread t1 = new Thread(new Runnable() {
			@Override
			public void run() {
				for (int i = 0; i < 10000; i++) {
					boolean flag = blockQueue.add(i);
					System.out.println("生产：" + i + "，结果：" + flag);

				}
			}
		});
		Thread t2 = new Thread(new Runnable() {
			@Override
			public void run() {
				for (int i = 0; i < 10000; i++) {
					Integer n = blockQueue.take();
					System.out.println("消费：" + n);
					try {
						Thread.sleep(4000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		});
		t1.start();
		t2.start();
	}

}
