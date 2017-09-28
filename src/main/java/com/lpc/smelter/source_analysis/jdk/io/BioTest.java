package com.lpc.smelter.source_analysis.jdk.io;

/**
 * @author longpengchao
 * @email longpengchao@58ganji.com
 * @create 2017-09-07 16:57
 */
public class BioTest {
	public static void main(String[] args) throws Exception {
		//运行服务器
		new Thread(new Runnable() {
			@Override
			public void run() {
				BioServer.start();
			}
		}).start();
		//避免客户端先于服务器启动前执行代码
		Thread.sleep(100);

		//运行客户端
		new Thread(new Runnable() {
			@SuppressWarnings("static-access")
			@Override
			public void run() {
				while(true){
					String expression = "我是客户端：" + Thread.currentThread().getName();
					BioClient.sendMsg(expression);
					try {
						Thread.currentThread().sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}).start();
	}
}
