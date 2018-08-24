package com.nav.multithreading.executorservice;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

import com.sun.javafx.runtime.async.AsyncOperationListener;

public class A {
	public static void main(String[] args) throws InterruptedException {
		ExecutorService executorService = Executors.newFixedThreadPool(3, new MyThreadFactory("Nav"));
		executorService.execute(A::printThreadNameTask);
		executorService.execute(A::printThreadNameTask);
		executorService.execute(A::printThreadNameTask);
		
		ExecutorService executorService2 = Executors.newCachedThreadPool( new MyThreadFactory("Nav"));
		CompletionService<Integer> completionService = new ExecutorCompletionService<>(executorService2);
		for (int i = 0; i < 5;i++) {
			completionService.submit(A::returnRandomNumber);
		}
		
//		Callable<?> callable = Executors.callable(A::printThreadNameTask); // Adapter pattern
//	    static final class RunnableAdapter<T> implements Callable<T> {
//	        final Runnable task;
//	        final T result;
//	        RunnableAdapter(Runnable task, T result) {
//	            this.task = task;
//	            this.result = result;
//	        }
//	        public T call() {
//	            task.run();
//	            return result;
//	        }
//	    }
		executorService2.shutdown();
		
		for (int i = 0; i < 5;i++) {
			try {
				System.out.println(completionService.take().get());
			} catch (InterruptedException | ExecutionException e) {
				e.printStackTrace();
			}
		}
		
		executorService2.awaitTermination(10, TimeUnit.SECONDS);
		
		List<String> jedis = new ArrayList<>(Arrays.asList("Luke", "Yoda"));
		System.out.println(jedis);
		for (Iterator<String> iterator = jedis.iterator(); iterator.hasNext();){
			String jedi = iterator.next();
			if (jedi.charAt(0) == 'L'){
				iterator.remove();
			}
		}
		System.out.println(jedis);
		
	}
	
	public static void printThreadNameTask() {
		System.out.println(Thread.currentThread().getName());
		sleep(2);
	}
	
	public static int returnRandomNumber() {
		int ran = (new Random()).nextInt(10);
		System.out.println(Thread.currentThread().getName() + " - " + ran);
		sleep(ran);
		return ran;
	}

	private static void sleep(int i) {
		try {
			TimeUnit.SECONDS.sleep(i);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}

class MyThreadFactory implements ThreadFactory {

	String prefix = "Nav";
	int count;
	public MyThreadFactory() {}
	public MyThreadFactory(String prefix) {this.prefix = prefix;}
	@Override
	public Thread newThread(Runnable r) {
		return new Thread(r, prefix+"-"+count++);
	}
	
}
