package com.nav.multithreading;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;


/**
 *
 * Latch --> multiple threads can wait for each other
 *
 * A CyclicBarrier is used in situations where you want to create a group of
 * tasks to perform work in parallel + wait until they are all finished before
 * moving on to the next step -> something like join() -> something like
 * CountDownLatch
 * 
 * CountDownLatch: one-shot event CyclicBarrier: it can be reused over and over
 * again
 * 
 * + cyclicBarrier has a barrier action: a runnable, that will run automatically
 * when the count reaches 0 !!
 * 
 * new CyclicBarrier(N) -> N threads will wait for each other
 *
 * WE CAN NOT REUSE LATCHES BUT WE CAN REUSE CyclicBarriers --> reset() !!!
 * 
 */

public class CyclicBarrierApp {

	static class Task implements Runnable {
		int id = 0;
		CyclicBarrier barrier;;
		public Task(int id, CyclicBarrier barrier) {
			this.id = id;
			this.barrier = barrier;
		}
		@Override
		public void run() {
			System.out.println("Task with id " + id + " started...");
			try {
				TimeUnit.SECONDS.sleep(10);
				System.out.println("Task with id " + id + " Stopped ...");
				barrier.await();
				System.out.println("After await in id  " + id);
			} catch (InterruptedException | BrokenBarrierException e) {
				// BrokenBarrierEx will occur when someone has reset the barrier while parties are waiting
				e.printStackTrace();
			}

		}

	}

	public static void main(String[] args) {
		CyclicBarrier barrier = new CyclicBarrier(3, ()-> System.out.println("All parties have are now done. "
				+ "The next line you see will be what you put after await"));
		ExecutorService svc = Executors.newFixedThreadPool(3);
		for (int i = 0; i < 3; i++){
			svc.execute(new Task(i+1, barrier));
		}
		System.out.println("Main thread. All tasks submitted...");
		svc.shutdown(); //doesn't block
		System.out.println(barrier.getNumberWaiting()); //Not there was cyclic barrier
		System.out.println("ShutDown called");

	}

}
