package com.nav.multithreading.prodcons;

import java.util.Queue;
import java.util.Random;

public class Producer implements Runnable {
	
	Queue<Integer> q ;
	Object lock;
	volatile boolean isStopped = false;
	
	public Producer(Queue q, Object lock) {
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
				if (q.size() == 5) {
					try {
						lock.wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
						isStopped = true;
					}
				} else {
					int val = (new Random()).nextInt(50);
					System.out.println("Producing " + val);
					q.add(val);
					lock.notify();
				}
			}
		}
	}

	public void doStop() { isStopped = true; }
}
