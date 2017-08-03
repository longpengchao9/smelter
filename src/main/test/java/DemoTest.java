package java;

import org.junit.Test;

/**
 * @author longpengchao
 * @email longpengchao@58ganji.com
 * @create 2017-07-31 11:32
 */
public class DemoTest {

	@Test
	public void test1(){
		float f1 = 0.2f;
		float f2 = 0.05f;
		f2 += 0.2;
		f2 += 0.05;
		System.out.println(String.format("%.2f", (f1+f2)*100));
		System.out.println((int)((f1+f2)*100));
	}


}
