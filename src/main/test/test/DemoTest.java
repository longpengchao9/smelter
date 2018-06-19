package test;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.junit.Test;

/**
 * @author longpengchao
 * @email longpengchao@58ganji.com
 * @create 2017-07-31 11:32
 */
public class DemoTest {

	@Test
	public void test1() {
		float f1 = 0.2f;
		float f2 = 0.05f;
		f2 += 0.2;
		f2 += 0.05;
		System.out.println(String.format("%.2f", (f1 + f2) * 100));
		System.out.println((int) ((f1 + f2) * 100));
	}

	@Test
	public void test2() {
		String startTime = "2018-08-18 17:38";
		String endTime = "2018-08-18 18:38";
		long start = 0, end = Long.MAX_VALUE;
		try{
			if (StringUtils.isNotBlank(startTime)) {
				start = DateUtils.parseDate(startTime, new String[] { "yyyyMMdd" }).getTime();
			}
			if (StringUtils.isNotBlank(endTime)) {
				end = DateUtils.parseDate(endTime, new String[] { "yyyyMMdd" }).getTime();
			}
			long now = System.currentTimeMillis();
			if ((now >= start) && (now < end)) {

			}
		} catch (Exception e){
			e.printStackTrace();
		}
	}
	@Test
	public void test3(){
	}

}
