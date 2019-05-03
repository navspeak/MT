package com.nav.multithreading;
//Ping-pong threading problem. (Semaphore)

/**
 * 
 * @author abhdogra
 *
 */

public class ThreeThreadsSynchOrigional {

	// private AtomicInteger sharedOutput = new AtomicInteger(0);

	// With keeping an int volatile we will again get the same output, here we
	// have used AtomicInteger
	// we need initial value to be zero other wise modulo of no of thread with
	// sharedOutput number at some point will be 0 and in that case we will not
	// have any matching condition--->if(sharedOutput % 3 ==
	// this.threadPosition)
	// 0 mod 3 = 0
	// 1 mod 3 = 1
	// 2 mod 3 = 2
	// 3 mod 3 = 0 < --- at this position when we will find modulo of
	// sharedOutput with no of threads, it will be zero and if we are setting
	// thread positions from 1 then it will never match the criteria
	volatile int sharedOutput = 0;
	private Object object = new Object();

	public static void main(String[] args) {
		ThreeThreadsSynchOrigional obj = new ThreeThreadsSynchOrigional();

		PrintNumberTask t1 = obj.new PrintNumberTask(0); //0,3,6,9
		PrintNumberTask t2 = obj.new PrintNumberTask(1); //1,4,7,10
		PrintNumberTask t3 = obj.new PrintNumberTask(2); //2,5,8,11

		Thread thread1 = new Thread(t1, "One");
		Thread thread2 = new Thread(t2, "Two");
		Thread thread3 = new Thread(t3, "Three");

		thread1.start();
		thread2.start();
		thread3.start();
	}

	private class PrintNumberTask implements Runnable {

		private final int threadPosition;

		public PrintNumberTask(int i) {
			super();
			this.threadPosition = i;

		}

		@Override
		public void run() {

			// here all the threads are continuously working here but only those
			// threads are printing the output which match the condition a if
			// (sharedOutput % 3 == this.threadPosition)

			// While loop is making every thread go inside this till the loop
			// condition is matched
			while (sharedOutput < 10) {
				// this block will allow threads inside it one by one
				synchronized (object) {
					// Which ever passed this condition will go inside it update
					// the sharedOutput value, which will become the criteria
					// for next thread to go inside this loop
					if (sharedOutput % 3 == this.threadPosition) {
						System.out.println("Thread: " + Thread.currentThread().getName() + " " + ++sharedOutput);
					}
				}
			}
		}

	}

}
