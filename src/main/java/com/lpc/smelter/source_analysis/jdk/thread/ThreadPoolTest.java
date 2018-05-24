package com.lpc.smelter.source_analysis.jdk.thread;

import com.lpc.smelter.source_analysis.jdk.util.MyThreadPool;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

/**
 * @author longpengchao
 * @email longpengchao@58ganji.com
 * @create 2017-08-10 16:54
 */
public class ThreadPoolTest {

	public static void main(String[] args) {
		final Random random = new Random();
		task();
	}

	private static void task(){
		final Random random = new Random();
		long start = System.currentTimeMillis();
		try {
			List<Callable<Object>> task = new ArrayList<Callable<Object>>();
			// umc获取用户信息
			task.add(new Callable<Object>() {
				@Override
				public Object call() throws Exception {
					System.out.println("Thread="+Thread.currentThread().getName());
					return null;
				}
			});
			// tumc获取账号绑定信息
			task.add(new Callable<Object>() {
				@Override
				public Object call() throws Exception {
					System.out.println("Thread=" + Thread.currentThread().getName());
					return null;
				}
			});
			// wxlbs 获取用户状态
			task.add(new Callable<Object>() {
				@Override
				public Object call() throws Exception {
					System.out.println("Thread=" + Thread.currentThread().getName());
					return null;
				}
			});
			// 运营平台获取用户勋章个数
			task.add(new Callable<Object>() {
				@Override
				public Object call() throws Exception {
					System.out.println("Thread=" + Thread.currentThread().getName());
					return null;
				}
			});
			// 认证服务获取认证数据
			task.add(new Callable<Object>() {
				@Override
				public Object call() throws Exception {
					System.out.println("Thread=" + Thread.currentThread().getName());
					return null;
				}
			});
			// 放入线程池中处理
			MyThreadPool.oneBusinessThreadPool.invokeAll(task, 1500, TimeUnit.MILLISECONDS);
			long time = System.currentTimeMillis() - start;
			if(time > 1000){
				System.out.println("cost:"+time);
			}
		} catch (Exception e){
			e.printStackTrace();
			long time = System.currentTimeMillis() - start;
			if(time > 1000){
				System.out.println("error cost:"+time);
			}
		}
	}
}
