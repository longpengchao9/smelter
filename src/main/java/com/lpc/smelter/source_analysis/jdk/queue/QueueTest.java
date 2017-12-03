package com.lpc.smelter.source_analysis.jdk.queue;

import java.util.concurrent.ArrayBlockingQueue;

/**
 * @author longpengchao
 * @email longpengchao@58ganji.com
 * @create 2017-09-28 15:18
 */
public class QueueTest {
	private static ArrayBlockingQueue<Long> queue = new ArrayBlockingQueue<Long>(100);

	public static boolean offer(Long vo){
		return queue.offer(vo);
	}
	public static boolean add(Long vo){
		return queue.add(vo);
	}
	/**
	 * 获取但不移除
	 * @param vo
	 * @return
	 */
	public static Long peek(Long vo){
		return queue.peek();
	}

	/**
	 * 获取并移除此队列的头，队列为空返回null
	 */
	public static Long poll(){
		return queue.poll();
	}
	/**
	 * 获取并移除此队列的头，队列为空抛出异常
	 */
	public static Long remove(){
		return queue.remove();
	}
	/**
	 *
	 * @return
	 * @throws Exception
	 */
	public static Long take() throws Exception{
		return queue.take();
	}

}
