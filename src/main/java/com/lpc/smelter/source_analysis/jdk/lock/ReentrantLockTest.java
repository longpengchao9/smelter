package com.lpc.smelter.source_analysis.jdk.lock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 可重入锁测试
 *
 * @author longpengchao
 * @email longpengchao@58ganji.com
 * @create 2017-09-29 11:06
 */
public class ReentrantLockTest {

	public static void main(String[] args) {
		SellTicket sellTicket = new SellTicket();
		Thread t1 = new Thread(sellTicket, "窗口1");
		Thread t2 = new Thread(sellTicket, "窗口2");
		Thread t3 = new Thread(sellTicket, "窗口3");
		t1.start();
		t2.start();
		t3.start();
	}

	static class SellTicket implements Runnable{

		private int tickets = 100;
		private Lock lock = new ReentrantLock();

		@Override
		public void run() {
			while(true){
				try {
					lock.lock();
					if(tickets > 0){
						try {
							Thread.sleep(500);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						System.out.println(Thread.currentThread().getName() + "正在出售第" + (tickets--) + "张票");
					}
				}finally {
					lock.unlock();
				}
			}
		}
	}
}
