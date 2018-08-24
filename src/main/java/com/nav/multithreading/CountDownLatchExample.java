package com.nav.multithreading;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

public class CountDownLatchExample {

	public static class Task implements Runnable {
		String taskName;
		AtomicLong sum;
		CountDownLatch latch;
		
		public Task(String name, AtomicLong sum, CountDownLatch latch) {
			taskName = name;
			this.sum = sum;
			this.latch = latch;
		}
		@Override
		public void run() {
			System.out.println(Thread.currentThread().getName() + " is running " + taskName);
			for (int i =0; i< 10; i++){
				System.out.println("Thread " + Thread.currentThread().getName() + " is adding up " + i);
				sum.incrementAndGet();
				latch.countDown();
			}
			System.out.println(Thread.currentThread().getName() + " has run " + taskName);
		}

	}
	
	public static void main(String[] args) {
		ExecutorService executors = Executors.newFixedThreadPool(10);
		AtomicLong sum = new AtomicLong(0);
		CountDownLatch latch = new CountDownLatch(10);
		
	}
}
