package com.lpc.smelter.source_analysis.jdk.nio;

import org.junit.Test;

import java.io.RandomAccessFile;

/**
 * @author longpengchao
 * @email longpengchao@58ganji.com
 * @create 2017-08-08 17:21
 */
public class IoTest {

	private final String PATH_PER = "E:\\GIT_PRO\\smelter2\\src\\main\\java\\com\\lpc\\smelter\\source_analysis\\jdk\\nio\\";

	@Test
	public void test1(){
		try {
			RandomAccessFile randomAccessFile = new RandomAccessFile(PATH_PER + "text.txt", "rw");
			String str = null;
			while ( (str= randomAccessFile.readLine()) != null){
				System.out.println(new String(str.getBytes("8859_1"), "UTF-8"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	@Test
	public void test2(){
		try {
			RandomAccessFile randomAccessFile = new RandomAccessFile(PATH_PER + "text.txt", "rw");
			String str = null;
			randomAccessFile.write("哈哈哈\n".getBytes());
			randomAccessFile.write("我是中文乱码\n".getBytes());
			randomAccessFile.write("我是中文乱码\n".getBytes());
			randomAccessFile.write("我是中文乱码\n".getBytes());
			randomAccessFile.writeByte(1);
			randomAccessFile.writeFloat(1.12f);
			randomAccessFile.writeDouble(1d);
			randomAccessFile.writeInt(100);
			randomAccessFile.writeBoolean(true);
			randomAccessFile.writeUTF("咕咕咕");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
