package com.lpc.smelter.source_analysis.jdk.lock;

/**
 * 同步锁测试
 *
 * @author longpengchao
 * @email longpengchao@58ganji.com
 * @create 2017-09-28 17:19
 */
public class SynchTest {

	private static int num = 102;
	private static final Object lock = new Object();

	public static void main(String[] args) {
		Thread t1 = new Thread(new Pay());
		t1.start();
		Thread t2 = new Thread(new Create());
		t2.start();
	}

	/**
	 * 花钱
	 */
	static class Pay implements Runnable {
		@Override
		public void run() {
			synchronized (lock) {
				while (true) {
					if (num < 100) {
						System.out.println("没钱了。。。" + num);
						lock.notifyAll();
						try {
							lock.wait();
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					} else {
						System.out.println("花钱了。。。" + num);
						num--;
					}
					try {
						Thread.currentThread().sleep(500);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}

	/**
	 * 挣钱
	 */
	static class Create implements Runnable {
		@Override
		public void run() {
			synchronized (lock) {
				while (true) {
					if (num > 110) {
						System.out.println("休息下。。。" + num);
						lock.notifyAll();
						try {
							lock.wait();
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					} else {
						System.out.println("挣钱了。。。" + num);
						num++;
					}
					try {
						Thread.currentThread().sleep(500);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}

	static class Print implements Runnable{
		@Override
		public void run() {
			while (true) {
				System.out.println(lock.hashCode());
				try {
					Thread.currentThread().sleep(500);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
