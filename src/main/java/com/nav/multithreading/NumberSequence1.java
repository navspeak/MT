package com.nav.multithreading;

import java.util.concurrent.TimeUnit;

public class NumberSequence1 {
	
	/**
	 * Drop
	 * @author knavneet
	 *
	 */
	private static class Drop {
		private Integer val = 0;
		private boolean empty = true;
		
		public synchronized void put(int i) throws InterruptedException {
			while (empty == false)
				wait();
			val = i;
			empty = false;
			notify();
		}
		public synchronized int take() throws InterruptedException {
			while (empty == true)
				wait();
			int ret = val;
			empty = true;
			notify();
			return ret;
		}

	}

    /**
     * Worker
     * @author knavneet
     *
     */
	private static class Worker implements  Runnable {
		Drop queue = null;
		private Worker nextWorker;

		public void setNextWorker(Worker nextWorker) {
			this.nextWorker = nextWorker;
		}
		private void put(int i) throws InterruptedException {
			queue.put(i);
		}
		public Worker() {
			this.queue = new Drop();
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

	/**
	 * main
	 * @param args
	 * @throws InterruptedException
	 */
	public static void main(String[] args) throws InterruptedException {
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
