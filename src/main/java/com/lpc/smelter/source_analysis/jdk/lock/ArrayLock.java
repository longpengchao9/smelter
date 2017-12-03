package com.lpc.smelter.source_analysis.jdk.lock;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author longpengchao
 * @email longpengchao@58ganji.com
 * @create 2017-09-29 17:34
 */
public class ArrayLock {

	private Object[] items;
	private int addIndex;
	private int removeIndex;
	private int count;
	private ReentrantLock lock;
	private Condition notEmpty;
	private Condition notFull;

	public ArrayLock(int capacity) {
		items = new Object[capacity];
		lock = new ReentrantLock();
		notEmpty = lock.newCondition();
		notFull = lock.newCondition();
	}

	public boolean add(Object obj) {
		final ReentrantLock lock = this.lock;
		lock.lock();
		try {
			if (count == items.length) {//数组满了
				return false;
			} else {//数组没满
				insert(obj);//插入一个元素
				return true;
			}
		} finally {
			lock.unlock();
		}
	}

	final int inc(int i){
		return (++i==items.length) ? 0 : i;
	}

	private void insert(Object obj){
		items[addIndex] = obj;
		addIndex = inc(addIndex);
		count++;
		/**
		 * 唤醒一个线程
		 * 如果有任意一个线程正在等待这个条件，那么选中其中的一个区唤醒。
		 * 在从等待状态被唤醒之前，被选中的线程必须重新获得锁
		 */
		notEmpty.signal();
	}

	public Object take() {
		final ReentrantLock lock = this.lock;
		lock.lock();
		try {
			try{
				while (count == 0){//如果数组为空，一直阻塞在这里
					 /*
                     * 一直等待条件notEmpty，即被其他线程唤醒
                     * （唤醒其实就是，有线程将一个元素入队了，然后调用notEmpty.signal()唤醒其他等待这个条件的线程，同时队列也不空了）
                     */
					notEmpty.await();
				}
			} catch (Exception e){
				notEmpty.signal();
			}
			Object obj = extract();
			return obj;
		} finally {
			lock.unlock();
		}
	}

	/**
	 * 出队
	 */
	private Object extract() {
		final Object[] items = this.items;
		Object x = items[removeIndex];//获取出队元素
		items[removeIndex] = null;//将出队元素位置置空
        /*
         * 第一次出队的元素takeIndex==0,第二次出队的元素takeIndex==1
         * (注意：这里出队之后，并没有将后面的数组元素向前移)
         */
		removeIndex = inc(removeIndex);
		--count;//数组元素个数-1
		notFull.signal();//数组已经不满了，唤醒其他等待notFull条件的线程
		return x;//返回出队的元素
	}

}
