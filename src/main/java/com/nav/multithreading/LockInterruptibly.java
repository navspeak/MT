package com.nav.multithreading;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

public class LockInterruptibly {

	public static void main(String[] args) {
		final int threadCount = 5;
		final ExecutorService service = Executors.newFixedThreadPool(threadCount);
		final LockDemo task = new LockDemo();
		for (int i=0; i< threadCount; i++) {
			service.execute(new Worker(task));
		}
		service.shutdown();
	}

	public static class Worker implements Runnable {
		private LockDemo task;
		public Worker(LockDemo task) {
			this.task = task;
		}
		@Override
		public void run() {
			task.performTask();
		}
	} 

	public static class LockDemo {
		final ReentrantLock reentrantLock = new ReentrantLock();

		public void performTask() {
			try {
				reentrantLock.lockInterruptibly();
				//if it is not able to acquire lock because of other threads interrupts,
				//it will throw InterruptedException and control will go to catch block.
				try {
					System.out.println(Thread.currentThread().getName() +": Lock acquired.");
					System.out.println("Work on progress...");
					Thread.sleep(2000);	
				} finally {
					System.out.println(Thread.currentThread().getName() +": Lock released.");
					reentrantLock.unlock();
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	} 
}
