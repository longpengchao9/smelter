package com.lpc.smelter.source_analysis.jdk.queue;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 自定义阻塞队列
 * 
 * @author longpengchao
 * @email longpengchao@58ganji.com
 * @create 2017-11-03 14:57
 */
public class MyBlockQueue<T> {
	// 队列
	private Object[] array;
	// 队头
	private int headIndex;
	// 队尾
	private int tailIndex;
	// 实际元素个数
	private int count;
	private ReentrantLock lock = new ReentrantLock();
	private Condition empty = lock.newCondition();
	private Condition full = lock.newCondition();

	public MyBlockQueue(int size) {
		array = new Object[size];
	}

	public MyBlockQueue() {
		this(16);
	}

	public boolean add(T t) {
		try {
			lock.lock();
			while (count == array.length) {
				full.await();
			}
			if (headIndex == array.length) {
				headIndex = 0;
			}
			array[headIndex++] = t;
			count++;
			empty.signal();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			lock.unlock();
		}
		return false;
	}

	public T take() {
		try {
			lock.lock();
			while (count == 0) {
				empty.await();
			}
			if (tailIndex == array.length) {
				tailIndex = 0;
			}
			Object obj = array[tailIndex++];
			count--;
			full.signal();
			return (T) obj;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			lock.unlock();
		}
		return null;
	}
}
