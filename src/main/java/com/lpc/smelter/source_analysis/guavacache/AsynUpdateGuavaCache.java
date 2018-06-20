package com.lpc.smelter.source_analysis.guavacache;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;
import com.lpc.smelter.source_analysis.log.LoggerUtil;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * 异步更新本地缓存
 * 
 * @author longpengchao
 * @email longpengchao@58ganji.com
 * @create 2018-03-29 19:15
 */
public abstract class AsynUpdateGuavaCache<K, V> {
	/**
	 * 缓存自动刷新周期
	 */
	protected long refreshTime;
	/**
	 * 缓存刷新周期时间格式
	 */
	protected TimeUnit timeUnit;
	/**
	 * 缓存最大容量
	 */
	protected int cacheMaximumSize;
	/**
	 * 异步更新线程池大小
	 */
	protected int refreshThreadSize;

	private LoadingCache<K, V> cache;

	private ListeningExecutorService refreshThreadPools;

	/**
	 * @description: 初始化所有protected字段：
	 *               refreshDuration、refreshTimeunitType、cacheMaximumSize
	 */
	protected abstract void initCacheFields();

	/**
	 * @description: 定义缓存值的计算方法
	 * @description: 新值计算失败时抛出异常，get操作时将继续返回旧的缓存
	 * @param key
	 * @throws Exception
	 */
	protected abstract V getValueWhenExpire(K key) throws Exception;

	public void invalidate(K key) {
		getCache().invalidate(key);
	}

	public void invalidateAll() {
		getCache().invalidateAll();
	}

	public void put(K key, V value) {
		getCache().put(key, value);
	}

	/**
	 * @description: 获取cache实例
	 * @return
	 */
	private LoadingCache<K, V> getCache() {
		if (cache == null) {
			synchronized (this) {
				if (cache == null) {
					initCacheFields();
					refreshThreadPools = MoreExecutors.listeningDecorator(Executors.newFixedThreadPool(refreshThreadSize));
					/**
					 * expireAfterWrite和refreshAfterWrite同时设置时（前后无差别）
					 * expireAfterWrite起作用：1、过期时间设置相同  2、expireAfterWrite过期时间小
					 * refreshAfterWrite起作用：1、refreshAfterWrite过期时间小
					 */
					cache = CacheBuilder.newBuilder().maximumSize(cacheMaximumSize)
							.refreshAfterWrite(refreshTime, timeUnit).build(new CacheLoader<K, V>() {
								@Override
								public V load(K key) throws Exception {
									LoggerUtil.getLogger().info("---load---- key=" + key);
									return getValueWhenExpire(key);
								}

								@Override
								public ListenableFuture<V> reload(final K key, V oldValue) throws Exception {
									LoggerUtil.getLogger().info("---reload---- key=" + key);
									return refreshThreadPools.submit(new Callable<V>() {
										public V call() throws Exception {
											return getValueWhenExpire(key);
										}
									});
								}
							});
				}
			}
		}
		return cache;
	}

	/**
	 * @description: 从cache中拿出数据的操作
	 * @param key
	 * @return
	 * @throws ExecutionException
	 */
	public V getFromCache(K key) throws ExecutionException {
		return getCache().get(key);
	}

}
