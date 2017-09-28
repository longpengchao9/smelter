package com.lpc.smelter.source_analysis.jdk.nio;

/**
 * @author longpengchao
 * @email longpengchao@58ganji.com
 * @create 2017-08-25 14:51
 */
public class NioServer {

	// 要连接的服务器Ip地址
	private static String hostIp;
	// 要连接的远程服务器在监听的端口
	private static int DEFAULT_PORT = 8899;

	public static void main(String[] args) {
		try {
			MultiplexerTimeServer server = new MultiplexerTimeServer(DEFAULT_PORT);
			new Thread(server, "nio server").start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
