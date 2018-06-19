package com.lpc.smelter.source_analysis.guavacache;

import com.lpc.smelter.source_analysis.log.LoggerUtil;

import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * 异步更新测试
 *
 * @author longpengchao
 * @email longpengchao@58ganji.com
 * @create 2018-06-19 16:57
 */
public class AsynUpdate extends AsynUpdateGuavaCache<String, String> {

	public static final AsynUpdate singleton = new AsynUpdate();

	@Override
	protected void initCacheFields() {
		this.refreshTime = 2;
		this.expireTime = 1;
		this.timeUnit = TimeUnit.SECONDS;
		this.cacheMaximumSize = 10;
		this.refreshThreadSize = 2;
	}

	@Override
	protected String getValueWhenExpire(String key) throws Exception {
		Random random = new Random();
		int n = random.nextInt(100);
		String value = key + "-" + n;
		LoggerUtil.getLogger().info("key=" + key + ",value=" + value);
		Thread.sleep(10);
		return value;
	}

	public static void main(String[] args) throws Exception {
		ExecutorService service = Executors.newFixedThreadPool(10);
//		AsynUpdate.singleton.put("key1", "11");
//		AsynUpdate.singleton.put("key2", "22");
//		AsynUpdate.singleton.put("key3", "33");
//		AsynUpdate.singleton.put("key4", "44");
		for (int i = 1; i < 5; i++) {
			final int n = i;
			service.execute(new Runnable() {
				@Override
				public void run() {
					String val = null;
					try {
						val = AsynUpdate.singleton.getFromCache("key" + 1);
						LoggerUtil.getLogger().info("第1次执行，value=" + val);
					} catch (ExecutionException e) {
						e.printStackTrace();
					}
				}
			});
		}
		Thread.sleep(3000);

		for (int i = 1; i < 5; i++) {
			final int n = i;
			service.execute(new Runnable() {
				@Override
				public void run() {
					String val = null;
					try {
						val = AsynUpdate.singleton.getFromCache("key" + 1);
						LoggerUtil.getLogger().info("第2次执行，value=" + val);
					} catch (ExecutionException e) {
						e.printStackTrace();
					}
				}
			});
		}
		System.out.println("success");

	}
}
