package com.lpc.smelter.source_analysis.jdk.io;

import java.io.*;
import java.net.Socket;

public class BioHandle implements Runnable {
	private Socket socket;

	public BioHandle(Socket socket) {
		this.socket = socket;
	}

	@Override
	public void run() {
		BufferedReader in = null;
		PrintWriter out = null;
		try {
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
			StringBuilder sb = new StringBuilder();
			String str;
			while ((str = in.readLine()) != null) {
				System.out.println("客户端端口号：" + socket.getPort() + "请求数据：" + str);
				out.println("感谢来访！");
				out.flush();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (in != null)
					in.close();
				;
				if (out != null)
					out.close();
				if (socket != null) {
					socket.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}