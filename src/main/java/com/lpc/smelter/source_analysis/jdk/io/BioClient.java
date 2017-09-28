package com.lpc.smelter.source_analysis.jdk.io;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * @author longpengchao
 * @email longpengchao@58ganji.com
 * @create 2017-09-05 19:38
 */
public class BioClient {

	public static void sendMsg(String msg) {
		Socket socket = null;
		BufferedReader in = null;
		PrintWriter out = null;
		try {
			socket = new Socket("127.0.0.1", 9999);
			out = new PrintWriter(socket.getOutputStream(), true);
			out.println(msg);
			out.flush();
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			System.out.println("响应：" + in.readLine());
			System.out.println(1);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (in != null)
					in.close();
				if (out != null)
					out.close();
				if (socket != null)
					socket.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public static void sendMsg2(String msg) {
		Socket socket = null;
		BufferedReader in = null;
		PrintWriter out = null;
		try {
			socket = new Socket("127.0.0.1", 9999);
			out = new PrintWriter(socket.getOutputStream(), true);
			out.println(msg);
			out.flush();
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			System.out.println("响应：" + in.readLine());
			System.out.println(1);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (in != null)
					in.close();
				if (out != null)
					out.close();
				if (socket != null)
					socket.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
