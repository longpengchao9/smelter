package com.lpc.smelter.source_analysis.jdk.lock;

import java.util.concurrent.locks.LockSupport;

/**
 * @author longpengchao
 * @email longpengchao@58ganji.com
 * @create 2018-05-18 15:30
 */
public class LockSupportTest {

	public static void main(String[] args) {
		Thread t1 = new Thread(new Runnable() {
			@Override
			public void run() {
				LockSupport.park(Thread.currentThread());
				System.out.println("t1");
			}
		});
		Thread t2 = new Thread(new Runnable() {
			@Override
			public void run() {
				while (true) {
					try {
						Thread.currentThread().sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					LockSupport.unpark(Thread.currentThread());
					System.out.println("t2");
				}
			}
		});
		t1.start();
		t2.start();
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		LockSupport.unpark(t1);
		System.out.println("unpark t1");
		LockSupport.park(t2);
		LockSupport.park(t2);
		System.out.println("park t2");

	}

}
