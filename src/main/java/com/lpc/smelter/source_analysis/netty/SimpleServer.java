package com.lpc.smelter.source_analysis.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.io.UnsupportedEncodingException;

/**
 * @author longpengchao
 * @email longpengchao@58ganji.com
 * @create 2017-10-12 14:43
 */
public class SimpleServer {

	private static int port = 9999;

	public static void main(String[] args) throws Exception {
		EventLoopGroup bossGroup = new NioEventLoopGroup();
		EventLoopGroup workerGroup = new NioEventLoopGroup();
		try {
			ServerBootstrap b = new ServerBootstrap();
			b.group(bossGroup, workerGroup)
					.channel(NioServerSocketChannel.class)
					.option(ChannelOption.SO_BACKLOG, 1024)
					.childOption(ChannelOption.SO_KEEPALIVE, true)
					.childHandler(new ChannelInitializer<SocketChannel>() {
						@Override
						public void initChannel(SocketChannel ch) throws Exception {
							ch.pipeline().addLast(new ServerHandler());
						}
					});
			ChannelFuture f = b.bind(port).sync();
			System.out.println("服务器开启：" + port);
			f.channel().closeFuture().sync();
		} finally {
			workerGroup.shutdownGracefully();
			bossGroup.shutdownGracefully();
		}
	}

	private static class ServerHandler extends ChannelInboundHandlerAdapter {
		@Override
		public void channelRead(ChannelHandlerContext ctx, Object msg) throws UnsupportedEncodingException {
			ByteBuf in = (ByteBuf) msg;
			byte[] req = new byte[in.readableBytes()];
			in.readBytes(req);
			String body = new String(req, "utf-8");
			System.out.println("收到客户端消息:" + body);
			String calrResult = null;
			if ("鱼丸".equals(body)) {
				calrResult = "给你鱼丸";
			} else {
				calrResult = "不提供其他服务";
			}
			ctx.write(Unpooled.copiedBuffer(calrResult.getBytes()));
		}

		@Override
		public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
			ctx.flush();
		}

		/**
		 * 异常处理
		 */
		@Override
		public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
			cause.printStackTrace();
			ctx.close();
		}
	}

}
