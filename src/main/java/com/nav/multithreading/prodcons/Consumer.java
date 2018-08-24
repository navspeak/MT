package com.nav.multithreading.prodcons;

import java.util.Queue;
import java.util.Random;

public class Consumer implements Runnable {
	
	Queue<Integer> q ;
	Object lock;
	volatile boolean isStopped = false;
	
	public Consumer(Queue q, Object lock) {
		this.q = q;
		this.lock = lock;
	}

	@Override
	public void run() {
		synchronized (lock) {
			while(!isStopped) {
				if (Thread.currentThread().isInterrupted() == true) {
					isStopped = true;
				}
				if (q.size() == 0) {
					try {
						lock.wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
						isStopped = true;
						
					}
				} else {
					int val = q.remove();
					System.out.println("Consuming " + val);
					lock.notify();
				}
			}
		}
	}
	
	public void doStop() { isStopped = true; }

}
