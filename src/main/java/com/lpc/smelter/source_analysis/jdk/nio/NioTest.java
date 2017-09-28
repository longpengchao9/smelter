package com.lpc.smelter.source_analysis.jdk.nio;

import org.junit.Test;

import java.io.FileInputStream;
import java.io.FileOutputStream;
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
	@Test
	public void copy(){
		String infile = "/text.txt";
		String outfile = "/copy.txt";
		try {
			// 获取源文件和目标文件的输入输出流
			FileInputStream fin = new FileInputStream(infile);
			FileOutputStream fout = new FileOutputStream(outfile);
			// 获取输入输出通道
			FileChannel fcin = fin.getChannel();
			FileChannel fcout = fout.getChannel();
			// 创建缓冲区
			ByteBuffer buffer = ByteBuffer.allocate(1024);
			while (true) {
				// clear方法重设缓冲区，使它可以接受读入的数据
				buffer.clear();
				// 从输入通道中将数据读到缓冲区
				int r = fcin.read(buffer);
				// read方法返回读取的字节数，可能为零，如果该通道已到达流的末尾，则返回-1
				if (r == -1) {
					break;
				}
				// flip方法让缓冲区可以将新读入的数据写入另一个通道
				buffer.flip();
				// 从输出通道中将数据写入缓冲区
				fcout.write(buffer);
			}
			fcin.close();
			fout.close();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Test
	public void getPath(){
		System.out.println(this.getClass().getResource("").getPath());
	}
}
