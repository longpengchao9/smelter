package com.lpc.smelter.source_analysis.jdk.io;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 阻塞IO服务端
 *
 * @author longpengchao
 * @email longpengchao@58ganji.com
 * @create 2017-09-07 10:52
 */
public class BioServer {

	private static final int PORT = 9999;

	private static ServerSocket server = null;
	public static void start() {
		try {
			// 创建服务socket，并监听端口号：9999
			server = new ServerSocket(PORT);
			System.out.println("服务器已启动，端口号：" + 9999);
			while (true) {
				System.out.println("等待接入请求。。。。。");
				Socket socket = server.accept();
				System.out.println("接受到请求，启动线程处理请求");
				new Thread(new BioHandle(socket)).start();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			//一些必要的清理工作
			if(server != null){
				System.out.println("服务器已关闭。");
				try {
					server.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
				server = null;
			}
		}
	}

}
