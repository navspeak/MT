package com.nav.multithreading;

import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.ThreadPoolExecutor;

public class DownloaderApp {
	
	enum Downloader {
		INSTANCE; // You can name it anything
		private Semaphore semaphore = new Semaphore(5, true);
		
		// By default ENUM has private no arg constructor
		private Downloader() {
			
		}
		
		public void download()  {
			try {
				System.out.println(Thread.currentThread().getName() + " - Permits available " + semaphore.availablePermits());
				semaphore.acquire();
				System.out.println(Thread.currentThread().getName() + " - Downloading..." + semaphore.availablePermits());

				Thread.sleep(new Random().nextInt(1000));//millisecond
				System.out.println(Thread.currentThread().getName() + " -Downloaded...");
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				semaphore.release();
				System.out.println(Thread.currentThread().getName() + "Download done Permits available " + semaphore.availablePermits());
			}
			
		}
		
	}
	
	public static Callable<String> downloadTask = () -> {
		Downloader.INSTANCE.download();
		return "Done";
	};
	
	public static void main(String[] args)  {

		ExecutorService executorService = Executors.newCachedThreadPool();
		System.out.println(((ThreadPoolExecutor)executorService).getPoolSize()); // 0
		CompletionService<String> downloadtasks = new ExecutorCompletionService<String>(executorService);
		System.out.println(((ThreadPoolExecutor)executorService).getPoolSize()); // 0
		for(int i=0;i<12;i++) {
			//executorService.execute(()->Downloader.INSTANCE.download());
			System.out.println(((ThreadPoolExecutor)executorService).getPoolSize()); // 0
			downloadtasks.submit(downloadTask);
		}
		System.out.println(((ThreadPoolExecutor)executorService).getLargestPoolSize());
		System.out.println(((ThreadPoolExecutor)executorService).getPoolSize());
		executorService.shutdown();
		for(int i=0;i<12;i++) {
			//executorService.execute(()->Downloader.INSTANCE.download());
			try {
				System.out.println(downloadtasks.take().get());
			} catch (InterruptedException | ExecutionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}


