package com.lpc.smelter.source_analysis.jdk.util;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;


/**
 * 
 * @Description: 
 * @author seven
 * @date 2017年6月2日 下午8:32:43
 */
public class MyThreadPool {

	public static final int CORE_POOL_SIZE_1 = 2;
	public static final int MAX_IMUM_POOL_SIZE_1 = 4;
	public static final long KEEP_ALIVE_TIME_1 = 60;
	public static final int WORK_QUEUE_SIZE_1 = 4;
	
	/**一级业务线程池**/
	public static final ThreadPoolExecutor oneBusinessThreadPool = new ThreadPoolExecutor(CORE_POOL_SIZE_1,
																						  MAX_IMUM_POOL_SIZE_1, 
																						  KEEP_ALIVE_TIME_1,
																						  TimeUnit.SECONDS,
																						  new LinkedBlockingQueue<Runnable>(WORK_QUEUE_SIZE_1),
																						  new ThreadPoolExecutor.CallerRunsPolicy());

	static{
		monitorMyThreadPool();
	}
	
	public static void monitorMyThreadPool(){
		
		Thread monitorMyThreadPool = new Thread(new Runnable() {
			
			@Override
			public void run() {
				
				long taskCount = 0;
				long completedTaskCount = 0;
				long noCompletedTaskCount = 0;
				
				int thisNum = 0;
				
				while(true){
					
					taskCount = oneBusinessThreadPool.getTaskCount();
					completedTaskCount = oneBusinessThreadPool.getCompletedTaskCount();
					noCompletedTaskCount = taskCount - completedTaskCount;
					
					//若未完成数量大于10 或者 每5分钟打印一次
					if( noCompletedTaskCount > 10 || thisNum%300 == 299){

						System.out.println("总数=" + taskCount
								+ " 已完成=" + completedTaskCount
								+ " 未完成=" + noCompletedTaskCount
								+ " activeCount=" + oneBusinessThreadPool.getActiveCount()
								+ " KeepAliveTime=" + oneBusinessThreadPool.getKeepAliveTime(TimeUnit.MILLISECONDS) + " 毫秒"
								+ " LargestPoolSize=" + oneBusinessThreadPool.getLargestPoolSize()
								+ " PoolSize=" + oneBusinessThreadPool.getPoolSize());
						
						thisNum = 0;
					}
					
					thisNum ++;
					
					try {
						Thread.sleep(1000);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				
			}
		});
		
		monitorMyThreadPool.setName("监控");
		
		monitorMyThreadPool.start();
	}
	
	
	public static void main(String[] args) {
		
		
//		List<Future<String>> futureList = new ArrayList<Future<String>>(); 
//		
//		for(int i=0;i<10;i++){
//			futureList.add(oneBusinessThreadPool.submit(new SetCircleInfoOnLineTask(i+"")));
//		}
//		
//		for(Future<String> thisFuture:futureList){
//			try {
//				thisFuture.get(5000, TimeUnit.MILLISECONDS);
//			} catch (Exception e) {
//				logger.error("设置是否在线 失败", e);
//			} 
//			
//		}
		
		List<Callable<String>> thisList = new ArrayList<Callable<String>>();
		
		for(int i=0;i<10;i++){
			thisList.add(new SetCircleInfoOnLineTask(i+"") );
		}
		
		List<Future<String>> thisResult = null;
		
		try {
			thisResult = oneBusinessThreadPool.invokeAll(thisList, 5000, TimeUnit.MILLISECONDS);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
//		for(int i=0;i<thisResult.size();i++){
//			try {
//				System.out.println(i+" "+thisResult.get(i).get()+" "+thisResult.size());
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//		}
		
		System.out.println("the end");
		
	}
	
	static class SetCircleInfoOnLineTask implements Callable<String> {

		private String thisInfo;
		
		public SetCircleInfoOnLineTask(String thisInfo){
			this.thisInfo=thisInfo;
		}
		
		@Override
		public String call()  {
			try {
				Thread.sleep(4000);
				System.out.println(thisInfo+" ------");
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			return thisInfo;
		}
		
	}
	
	

}
