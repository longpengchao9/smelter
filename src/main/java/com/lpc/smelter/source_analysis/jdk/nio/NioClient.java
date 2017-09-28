package com.lpc.smelter.source_analysis.jdk.nio;

/**
 * @author longpengchao
 * @email longpengchao@58ganji.com
 * @create 2017-08-25 14:52
 */
public class NioClient {
	public static void main(String[] args) {
		new Thread(new TimeClientHandle(null, 8899)).start();
	}
}
