package com.nav.multithreading;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class AExecutorServiceExample {

	public static void main(String[] args) throws InterruptedException, ExecutionException {
		ExecutorService executorService = Executors.newCachedThreadPool();
		Task<String> task1 = new Task<>("task1");
		Task<String> task2 = new Task<>("task2");
		Task<String> task3 = new Task<>("task3");
		Task<String> task4 = new Task<>("task4");
		Task<String> task5 = new Task<>("task5");
		
		Future<String> f1 = executorService.submit(task1);
		Future<String> f2 = executorService.submit(task2);
		Future<String> f3 = executorService.submit(task3);
		Future<String> f4 = executorService.submit(task4);
		Future<String> f5 = executorService.submit(task5);
		
		executorService.shutdown();
		System.out.println(f4.get());
		System.out.println(f5.get());
		System.out.println(f1.get());
		System.out.println(f2.get());
		System.out.println(f3.get());

		
	}
	
	public static class Task<T> implements Callable<String> {
		String taskName;
		
		public Task(String name) {
			taskName = name;
		}

		@Override
		public String call() throws Exception {
			System.out.println(Thread.currentThread().getName() + " is running " + taskName);
			TimeUnit.SECONDS.sleep(1);
			System.out.println(Thread.currentThread().getName() + " has run " + taskName);
			return taskName+" Finished";
		}
		
	}
}
