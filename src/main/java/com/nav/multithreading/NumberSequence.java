package com.nav.multithreading;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

public class NumberSequence {


	private static class Worker implements  Runnable {
		BlockingQueue<Integer> queue = null;
		private Worker nextWorker;

		public void setNextWorker(Worker nextWorker) {
			this.nextWorker = nextWorker;
		}
		private void put(int i) {
			queue.add(i);
		}
		public Worker() {
			this.queue = new ArrayBlockingQueue<Integer>(10);
			setNextWorker(null);
		}

		@Override
		public void run() {
			while(true) {
				try {
					int i = queue.take();
					System.out.printf("Thread %s - %d \n", Thread.currentThread().getName(), i);
					TimeUnit.SECONDS.sleep(2);
					if (nextWorker !=null) 
						nextWorker.put(i+1);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}

		}
	}

	public static void main(String[] args) {
		Worker w1 = new Worker();
		Worker w2 = new Worker();
		Worker w3 = new Worker();

		Thread t1 = new Thread(w1, "T1");
		Thread t2 = new Thread(w2, "T2");
		Thread t3 = new Thread(w3, "T3");

		w1.setNextWorker(w2);
		w2.setNextWorker(w3);
		w3.setNextWorker(w1);
		
		t1.start();
		t2.start();
		t3.start();

		w1.put(1);


	}

}
