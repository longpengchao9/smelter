package com.lpc.smelter.source_analysis.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.io.UnsupportedEncodingException;
import java.util.Scanner;

/**
 * @author longpengchao
 * @email longpengchao@58ganji.com
 * @create 2017-10-12 16:31
 */
public class SimpleClient implements Runnable{

	static ClientHandler client = new ClientHandler();
	public static void main(String[] args) throws Exception {
		new Thread(new SimpleClient()).start();
		@SuppressWarnings("resource")
		Scanner scanner = new Scanner(System.in);
		while(client.sendMsg(scanner.nextLine()));
	}
	@Override
	public void run() {
		String host = "127.0.0.1";
		int port = 9999;
		EventLoopGroup workerGroup = new NioEventLoopGroup();
		try {
			Bootstrap b = new Bootstrap();
			b.group(workerGroup);
			b.channel(NioSocketChannel.class);
			b.option(ChannelOption.SO_KEEPALIVE, true);
			b.handler(new ChannelInitializer<SocketChannel>() {
				@Override
				public void initChannel(SocketChannel ch) throws Exception {
					ch.pipeline().addLast(client);
				}
			});
			ChannelFuture f = b.connect(host, port).sync();
			f.channel().closeFuture().sync();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			workerGroup.shutdownGracefully();
		}
	}

	private static class ClientHandler extends ChannelInboundHandlerAdapter {
		ChannelHandlerContext ctx;
		/**
		 * tcp链路建立成功后调用
		 */
		@Override
		public void channelActive(ChannelHandlerContext ctx) throws Exception {
			this.ctx = ctx;
		}
		public boolean sendMsg(String msg){
			System.out.println("客户端发送消息："+msg);
			byte[] req = msg.getBytes();
			ByteBuf m = Unpooled.buffer(req.length);
			m.writeBytes(req);
			ctx.writeAndFlush(m);
			return msg.equals("q")?false:true;
		}
		/**
		 * 收到服务器消息后调用
		 * @throws UnsupportedEncodingException
		 */
		@Override
		public void channelRead(ChannelHandlerContext ctx, Object msg) throws UnsupportedEncodingException {
			ByteBuf buf = (ByteBuf) msg;
			byte[] req = new byte[buf.readableBytes()];
			buf.readBytes(req);
			String body = new String(req,"utf-8");
			System.out.println("服务器消息："+body);
		}
		/**
		 * 发生异常时调用
		 */
		@Override
		public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
			cause.printStackTrace();
			ctx.close();
		}
	}
}

