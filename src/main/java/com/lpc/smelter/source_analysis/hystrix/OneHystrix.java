package com.lpc.smelter.source_analysis.hystrix;

import com.netflix.hystrix.*;

import java.util.Random;

/**
 * @author longpengchao
 * @email longpengchao@58ganji.com
 * @create 2018-05-15 15:58
 */
public class OneHystrix extends HystrixCommand<String> {

	private final String name;

	public OneHystrix(String name) {
		super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("OneGroup"))
				.andCommandKey(HystrixCommandKey.Factory.asKey("sss"))
				.andThreadPoolPropertiesDefaults(HystrixThreadPoolProperties.Setter().withCoreSize(2))
				.andCommandPropertiesDefaults(HystrixCommandProperties.Setter().withExecutionTimeoutInMilliseconds(50))
				.andThreadPoolKey(HystrixThreadPoolKey.Factory.asKey("test")));
		this.name = name;
	}

	@Override
	protected String run() throws Exception {
		Random ran = new Random();
		int n = ran.nextInt(100);
		System.out.println("sleep " + n + " ms");
		Thread.sleep(n);
		return name + "--" + Thread.currentThread().getName();
	}

	@Override
	protected String getFallback() {
		return "熔断了,name=" + name + "--" + Thread.currentThread().getName();
	}

	public static void main(String[] args) {
		OneHystrix oneHystrix = new OneHystrix("hello");
		String res = oneHystrix.execute();
		System.out.println("是否超时："+oneHystrix.isResponseTimedOut());
		System.out.println("是否执行失败："+oneHystrix.isFailedExecution());
		System.out.println("");
	}
}
