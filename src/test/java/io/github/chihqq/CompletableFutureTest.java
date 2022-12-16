package io.github.chihqq;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;
import org.junit.Test;

/**
 * Created by ChenHao on 2022/12/16 is 10:48.
 *
 * @author tsinglink
 */

public class CompletableFutureTest {


	public String test(int i) {

		try {
			TimeUnit.SECONDS.sleep(i);
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}

		System.out.println(Thread.currentThread().getName());

		return "停止了" + i;

	}

	// java 并行测试
	@Test
	public void test01() {

		// 声明线程池
		/*
		1.corePoolSize
		线程池创建后初始化的就绪线程数量，Thread=new Thread() * 5

		2.maximumPoolSize
		线程池的最大资源数

		3.keepAliveTime
		当线程数大于corePoolSize，会释放空闲时间大于keepAliveTime的线程

		4.workQueue
		阻塞队列，比如LinkedBlockingQueue,当任务数超过corePoolsize,就会放入阻塞队列，阻塞队列大小不能超过maximumPoolSize

		5.RejectedExceptionHandler
		当阻塞队列满了，会根据设定的策略拒绝执行任务
		 */
		ThreadPoolExecutor threadPool = new ThreadPoolExecutor( 2, 4, 3, TimeUnit.SECONDS, new ArrayBlockingQueue< Runnable >( 3 ), new ThreadPoolExecutor.AbortPolicy() );

		System.out.println(new Date());

		Map<String, Object> map = new HashMap<>();

		TwentyFourService twentyFourService = new TwentyFourService();
		//CompletableFuture<String> twentyFourQuery = CompletableFuture.supplyAsync(twentyFourService::getResult);
		CompletableFuture<String> twentyFourQuery = CompletableFuture.supplyAsync(() -> test(4), threadPool);
		twentyFourQuery.thenAccept((result) -> {
			map.put("twentyFourResult", result);
		}).exceptionally((e) -> {
			map.put("twentyFourResult", "");
			return null;
		});

		TwentyFourService2 twentyFourService2 = new TwentyFourService2();
		CompletableFuture<String> twentyFourQuery2 = CompletableFuture.supplyAsync(() -> test(5), threadPool);
		twentyFourQuery2.thenAccept((result) -> {
			map.put("twentyFourResult2", result);
		}).exceptionally((e) -> {
			map.put("twentyFourResult2", "");
			return null;
		});

		// 3、allOf-两个查询必须都完成
		CompletableFuture<Void> allQuery = CompletableFuture.allOf(twentyFourQuery, twentyFourQuery2);
		CompletableFuture<Map<String, Object>> future = allQuery.thenApply((result) -> {
			System.out.println(("------------------ 全部查询都完成 ------------------ "));
			return map;
		}).exceptionally((e) -> {
			return null;
		});

		// 获取异步方法返回值
		// get()-内部抛出了异常需手动处理; join()-内部处理了异常无需手动处理，点进去一看便知。
		System.out.println(future.join());

		System.out.println(new Date());
		System.out.println(map);

	}

	class TwentyFourService {

		public String getResult() {
			String str = "5";
			// 模拟耗时
			try {
				TimeUnit.SECONDS.sleep(5);
			} catch (Exception e) {
				e.printStackTrace();
			}

			return str;
		}

	}

	class TwentyFourService2 {

		public String getResult() {
			String str = "5";
			// 模拟耗时
			try {
				TimeUnit.SECONDS.sleep(5);
			} catch (Exception e) {
				e.printStackTrace();
			}

			return str;
		}

	}

}
