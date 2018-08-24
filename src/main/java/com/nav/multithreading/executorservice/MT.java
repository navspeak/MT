package com.nav.multithreading.executorservice;

import java.util.concurrent.Callable;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class MT {

}


class CallableTask implements Callable<String>{
	
	private long taskid;
	private long executiontime;
	
	public CallableTask(long taskid,long executiontime){
		this.taskid = taskid;
		this.executiontime = executiontime;
	}
	
	@Override
	public String call() throws Exception {
		System.out.println("Started executing task # "+taskid);
		Thread.currentThread().sleep(executiontime);
		System.out.println("Completed execution of task # "+taskid);
		return "Successfully complete task # "+taskid;
	}

}


class Poll_timeout_non_blocking {

	public static void main(String[] args) throws InterruptedException, ExecutionException {
		
		
		Producer p = new Producer();/* Task producer thread */
		CompletionService service = p.createCompletionService();
		
		Thread t = new Thread( p);
		t.start();
		
		Future f = null;
		int counter = 1;
		while((f = service.poll(20000, TimeUnit.MILLISECONDS)) != null){
			System.out.println(f.get()+"  "+counter);
			counter++;
		}

	}

}


class Producer implements Runnable{

	private CompletionService completionService = null;
	private ExecutorService executorService = null;
	
	private static long counter = 1000;
	
	@Override
	public void run() {
		int count = 1;
		while(count < 100){
			//adding task to ExecutorCompletionService to execute
			completionService.submit(new CallableTask(counter,counter+500));
			counter = counter+300;
			count++;
		}
		stopCompletionService();
	}
	
	void stopCompletionService(){
		executorService.shutdown();
	}
	CompletionService createCompletionService(){
		
		executorService = Executors.newCachedThreadPool();
		completionService = new ExecutorCompletionService(executorService);
		return completionService;
	}
}