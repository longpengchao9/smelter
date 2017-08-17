package com.lpc.smelter.source_analysis.jdk.nio;

import org.junit.Test;

import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @author longpengchao
 * @email longpengchao@58ganji.com
 * @create 2017-08-08 16:37
 */
public class NioTest {

	@Test
	public void test1(){
		try {
			RandomAccessFile aFile = new RandomAccessFile("data/nio-data.txt", "rw");
			FileChannel inChannel = aFile.getChannel();
			ByteBuffer buf = ByteBuffer.allocate(48);
			int bytesRead = inChannel.read(buf);
			while (bytesRead != -1) {
				System.out.println("Read " + bytesRead);
				buf.flip();
				while(buf.hasRemaining()){
					System.out.print((char) buf.get());
				}
				buf.clear();
				bytesRead = inChannel.read(buf);
			}
			aFile.close();
		}catch (Exception e){
			e.printStackTrace();
		}
	}
}
