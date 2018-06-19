package test;

/**
 * @author longpengchao
 * @email longpengchao@58ganji.com
 * @create 2017-09-12 15:21
 */
public class TTest {

	public static void main(String[] args) {
		long start = System.nanoTime();
		Thread st = new Thread(new Runnable() {
			@Override
			public void run() {
				System.out.println("hehe");
			}
		});
		st.start();
		long end = System.nanoTime()- start;
		System.out.println(end);
	}
}
