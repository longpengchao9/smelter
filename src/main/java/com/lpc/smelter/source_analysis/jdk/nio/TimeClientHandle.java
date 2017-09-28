package com.lpc.smelter.source_analysis.jdk.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * @author longpengchao
 * @email longpengchao@58ganji.com
 * @create 2017-09-19 19:46
 */
public class TimeClientHandle implements Runnable {
	private String host;
	private int port;
	private Selector selector;
	private SocketChannel socketChannel;
	private volatile boolean stop;


	public TimeClientHandle(String host, int port){
		this.host = host == null ? "127.0.0.1" : host;
		this.port = port;
		try {
			selector = Selector.open();
			socketChannel = SocketChannel.open();
			socketChannel.configureBlocking(false);
		}catch (Exception e){
			e.printStackTrace();
			System.exit(1);
		}
	}

	@Override
	public void run() {
		try {
			doConnect();
		}catch (Exception e){
			e.printStackTrace();
			System.exit(1);
		}
		while(!stop){
			try {
				selector.select(1000);
				Set<SelectionKey> selectionKeys = selector.selectedKeys();
				Iterator<SelectionKey> it = selectionKeys.iterator();
				while(it.hasNext()){
					SelectionKey key = it.next();
					it.remove();
					try {
						handleInput(key);
					}catch (Exception e){
						e.printStackTrace();
						if(key != null){
							key.cancel();
							if(key.channel() != null) key.channel().close();
						}
					}
				}
			}catch (Exception e){
				e.printStackTrace();
				System.exit(1);
			}
		}
		if(selector != null){
			try {
				selector.close();
			} catch (IOException e) {
			}
		}
	}

	private void handleInput(SelectionKey key) throws Exception{
		if(key.isValid()){
			//判断是否连接成功
			SocketChannel sc = (SocketChannel) key.channel();
			if(key.isConnectable()){
				if(sc.finishConnect()){
					sc.register(selector, SelectionKey.OP_READ);
					doWrite(sc);
				}else {
					System.exit(1);
				}
			}
			if(key.isReadable()){
				ByteBuffer buffer = ByteBuffer.allocate(1024);
				int read = sc.read(buffer);
				if(read > 0){
					buffer.flip();
					byte[] bytes = new byte[buffer.remaining()];
					buffer.get(bytes);
					String body = new String(bytes, "UTF-8");
					System.out.println("result = " + body);
					this.stop = true;
				} else if(read < 0){
					//对端链路关闭
					key.cancel();;
					sc.close();
				} else {
					System.out.println("read 0");
				}
			}
		}
	}

	private void doConnect() throws Exception{
		if(socketChannel.connect(new InetSocketAddress(host, port))){
			socketChannel.register(selector, SelectionKey.OP_READ);
			doWrite(socketChannel);
		} else{
			socketChannel.register(selector, SelectionKey.OP_CONNECT);
		}
	}
	private void doWrite(SocketChannel socketChannel) throws Exception {
		byte[] req = "你好".getBytes();
		ByteBuffer buffer = ByteBuffer.allocate(req.length);
		buffer.flip();
		socketChannel.write(buffer);
		if(!buffer.hasRemaining()){
			System.out.println("send server success");
		}
	}
}
