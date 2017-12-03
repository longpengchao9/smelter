package com.lpc.smelter.source_analysis.jdk.thread;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author longpengchao
 * @email longpengchao@58ganji.com
 * @create 2017-11-02 18:38
 */
public class ThreadTest {

	private static int n = 0;
	private static Object obj1 = new Object();
	private static ReentrantLock lock = new ReentrantLock();
	private static Condition condition1 = lock.newCondition();
	private static Condition condition2 = lock.newCondition();

	public static void main(String[] args) {
		// test1();
		// test2();
//		test3();
		test4();
	}

	private static void test4(){
		RunTask task1 = new RunTask();
		task1.setName("one");
		RunTask task2 = new RunTask();
		task2.setName("two");
		Thread t1 = new Thread(task1);
		Thread t2 = new Thread(task2);
		t1.setPriority(Thread.MAX_PRIORITY);
		t2.setPriority(Thread.MIN_PRIORITY);
		t1.start();
		t2.start();

	}

	static class RunTask implements Runnable{

		private String name;

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		@Override
		public void run() {
			for(int i=0;i<10;i++){
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				System.out.println(getName() + "------" + i);
			}
		}
	}
	//指定顺序执行
	private static void test3() {
		Thread t1 = new Thread(new Runnable() {
			@Override
			public void run() {
				System.out.println(1);

			}
		});
		Thread t2 = new Thread(new Runnable() {
			@Override
			public void run() {
				System.out.println(2);
			}
		});

		Thread t3 = new Thread(new Runnable() {
			@Override
			public void run() {
				System.out.println(3);
			}
		});
		//保证顺序执行
		try {
			t3.start();
			t3.join();
			t1.start();
			t1.join();
			t2.start();
			t2.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}

	private static void test2() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				while (true) {
					lock.lock();
					try {
						condition2.signal();
						condition1.await();
						System.out.println(1);
						Thread.sleep(1000);
					} catch (Exception e) {
						e.printStackTrace();
					} finally {
						lock.unlock();
					}
				}
			}
		}).start();
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		new Thread(new Runnable() {
			@Override
			public void run() {
				while (true) {
					lock.lock();
					try {
						condition1.signal();
						System.out.println(2);
						condition2.await();
						Thread.sleep(1000);
					} catch (Exception e) {
						e.printStackTrace();
					} finally {
						lock.unlock();
					}
				}
			}
		}).start();
	}

	private static void test1() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				synchronized (obj1) {
					while (true) {
						try {
							Thread.currentThread().sleep(1000);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						System.out.println(Thread.currentThread().getName() + "count=" + 1);

						try {
							obj1.notify();
							obj1.wait();
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				}
			}
		}).start();

		new Thread(new Runnable() {
			@Override
			public void run() {
				synchronized (obj1) {
					while (true) {
						try {
							Thread.currentThread().sleep(1000);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						System.out.println(Thread.currentThread().getName() + "count=" + 2);
						try {
							obj1.notify();
							obj1.wait();
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				}
			}
		}).start();
	}
}
