package com.nav.multithreading.fairlock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


public class FairLock {

	public static Object mutex = new Object();
	public static void main(String[] args) {
		
		Lock lock = new ReentrantLock();
		Test target = new Test();
		Thread myThread = new Thread(target);
		myThread.start();
		try {
			TimeUnit.SECONDS.sleep(20);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		myThread.interrupt();
	}


	public static class Test implements Runnable {
		@Override
		public void run() {
			synchronized (mutex) {
				while (true) {
					try {
						System.out.println("1....");
						if (Thread.currentThread().isInterrupted()) {
							System.out.println("Interrupted");
						}
						mutex.wait();
						System.out.println("2....");
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}


		}
	}

}
