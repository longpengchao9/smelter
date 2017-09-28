package com.lpc.smelter.source_analysis.jdk.nio;

import org.apache.commons.lang3.StringUtils;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * 多路复用器、绑定监听端口
 *
 * @author longpengchao
 * @email longpengchao@58ganji.com
 * @create 2017-09-18 13:24
 */
public class MultiplexerTimeServer implements Runnable {
	// 信道选择器
	private Selector selector;
	// 与服务器通信的信道
	private ServerSocketChannel serverSocketChannel;
	private volatile boolean stop;

	public MultiplexerTimeServer(int port) {
		try {
			selector = Selector.open();
			serverSocketChannel = ServerSocketChannel.open();
			// 设置IO是否使用阻塞模式
			serverSocketChannel.configureBlocking(false);
			serverSocketChannel.socket().bind(new InetSocketAddress(port), 1024);
			// 注册信道到选择器里，设置为接受
			serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
			System.out.println("server is start in port:" + port);
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
	}

	@Override
	public void run() {
		while (!stop) {
			try {
				// 睡眠1s
				selector.select(20000);
				Set<SelectionKey> selectionKeys = selector.selectedKeys();
				Iterator<SelectionKey> it = selectionKeys.iterator();
				while (it.hasNext()) {
					SelectionKey key = it.next();
					it.remove();
					try {
						handleInput(key);
					} catch (Exception e) {
						e.printStackTrace();
						if(key != null){
							key.cancel();
							if(key.channel() != null) key.channel().close();
						}
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		// 多路复用器关闭后，所有注册在上面的channel和pipe等资源都会被关闭，所以不需要重复释放资源
		if (selector != null) {
			try {
				selector.close();
			} catch (Exception e) {

			}
		}
	}

	private void handleInput(SelectionKey key) throws Exception {
		if (key.isValid()) {
			// 处理新接入的请求消息
			if (key.isAcceptable()) {
				ServerSocketChannel ssc = (ServerSocketChannel) key.channel();
				SocketChannel sc = ssc.accept();
				sc.configureBlocking(false);
				sc.register(selector, SelectionKey.OP_READ);
			}
			if (key.isReadable()) {
				SocketChannel sc = (SocketChannel) key.channel();
				ByteBuffer readBuffer = ByteBuffer.allocate(1024);
				int readBytes = sc.read(readBuffer);
				if (readBytes > 0) {
					readBuffer.flip();
					byte[] bytes = new byte[readBuffer.remaining()];
					readBuffer.get(bytes);
					String body = new String(bytes, "UTF-8");
					System.out.println("server receive msg:" + body);
					doWrite(sc, "这是返回数据");
				} else if (readBytes < 0) {
					key.cancel();
					sc.close();
				} else {
					System.out.println("read 0");
				}
			}
		}
	}

	private void doWrite(SocketChannel channel, String response) throws Exception {
		if (StringUtils.isNotBlank(response)) {
			byte[] bytes = response.getBytes();
			ByteBuffer buffer = ByteBuffer.allocate(bytes.length);
			buffer.put(bytes);
			buffer.flip();
			channel.write(buffer);
		}
	}
}
