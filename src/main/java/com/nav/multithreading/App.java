package com.nav.multithreading;


import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.Semaphore;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class App {
	
	
	static Callable<Integer> task1 = () -> {
		try {
			System.out.println("Thread " + Thread.currentThread().getName());
	        TimeUnit.SECONDS.sleep(4);
	        return 123;
	    }
	    catch (InterruptedException e) {
	        throw new IllegalStateException("task interrupted", e);
	    }
	};
	
	static Callable<String> task4 = () -> {
		try {
	        TimeUnit.SECONDS.sleep(4);
	        return "123";
	    }
	    catch (InterruptedException e) {
	        throw new IllegalStateException("task interrupted", e);
	    }
	};
	static class NamedThreadsFactory implements ThreadFactory {
		
		private static int count = 0;

		@Override
		public Thread newThread(Runnable r) {
			Thread t = new Thread(r, "MyThread" + ++count);
			return t;
		}
	}

	public static void main(String[] args) throws InterruptedException, ExecutionException, TimeoutException {
//		Semaphore sem = new Semaphore(2);
//		AtomicInteger i = new AtomicInteger(8);
//		System.out.println(i);
//		i.compareAndSet(8, 5);
//		System.out.println(i);
//		
//		System.out.println(Runtime.getRuntime().availableProcessors());

// Runnable Task
		Runnable task = () -> {
		    String threadName = Thread.currentThread().getName();
		    System.out.println("Hello " + threadName);
		    try {
				TimeUnit.SECONDS.sleep(1);
				System.out.println("Bye " + threadName);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		};

		//task.run();
//		
//		Hello main
//		Bye main
//		Hello Thread-0
//		Bye Thread-0
//		Done!

		Thread thread = new Thread(task);
		thread.start();
		thread.join();

		System.out.println("Done!");

// CALLABLE
		
		
		ExecutorService executor = Executors.newFixedThreadPool(1, new NamedThreadsFactory());
		//((ThreadPoolExecutor)executor).getPoolSize()
		Future<Integer> future = executor.submit(task1);
		
		executor.shutdown();// If we don't call shutdown, the program will be running

		System.out.println("future done? " + future.isDone());

		Integer result = future.get();//blocks
		//Exception in thread "main" java.util.concurrent.TimeoutException

		System.out.println("future done? " + future.isDone());
		System.out.println("result: " + result);
		
// LIST OF CALLABLES
		ExecutorService executor1 = Executors.newWorkStealingPool();
		List<Callable<String>> callables = Arrays.asList(
		        () -> "task1",
		        () -> "task2",
		        () -> "task3",
		        task4);
		
		executor1.invokeAll(callables).stream().map(f-> {
			try {
				return f.get(); //blocks
			} catch(Exception e){
	            throw new IllegalStateException(e);				
			}
	})
	.forEach(System.out::println);
// http://winterbe.com/posts/2015/04/07/java8-concurrency-tutorial-thread-executor-examples/
		
//SCHEDULED EXECUTORS
		ScheduledExecutorService executor3 = Executors.newScheduledThreadPool(1);

		Runnable task3 = () -> System.out.println("Scheduling: " + System.nanoTime());
		ScheduledFuture<?> future3 = executor3.schedule(task3, 3, TimeUnit.SECONDS);
		executor3.shutdown();
		TimeUnit.MILLISECONDS.sleep(1337);

		long remainingDelay = future3.getDelay(TimeUnit.MILLISECONDS);
		System.out.printf("Remaining Delay: %sms", remainingDelay);

// REENTRANT LOCK
		Lock lock = new ReentrantLock();
		if(lock.tryLock()) {
			
		}
// COMPLETION SERVICE
		System.out.println("---------------------");
		ExecutorService e = Executors.newCachedThreadPool();
		CompletionService<String> csv = new ExecutorCompletionService<String>(e);
		Future<?> submit = null;
		for(Callable<String> call : callables){
			 submit = csv.submit(call);
		}
		e.shutdown();
		System.out.println(e.isTerminated());
		
		for(int i = 0; i< 4; i++){
			System.out.println(csv.take().get());
		}

		System.out.println(e.isTerminated());
	}

}
