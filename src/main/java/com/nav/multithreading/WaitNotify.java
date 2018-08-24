package com.nav.multithreading;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Random;

public class WaitNotify {

	static class Processor {
		Queue<Integer> queue = new LinkedList<>();
		private static final int LIMIT = 5;
		private static final int BOTTOM = 0;
		private Object lock = new Object();
		void produce() throws InterruptedException{
			synchronized (lock) {
				while(true) {
					if (queue.size() == LIMIT){
						System.out.println("Queue is full. Waiting...");
						lock.wait();
					} else {
						int val = (new Random()).nextInt(50);
						System.out.println("Queue has room. Adding" + val + "...");
						queue.add(val);
						lock.notify();
					}
					Thread.sleep(1000);
				}

			}

		}

		int consume() throws InterruptedException{
			synchronized (lock) {
				while (true) {
					if (queue.size() == BOTTOM){
						System.out.println("Queue is Empty. Waiting for producer to add something...");
						lock.wait();
					} else {
						int val = queue.remove();
						System.out.println("Consuming " + val);
						lock.notify();// will not hand over the lock till end of sync block
						return val;
					}
					Thread.sleep(1000);
				}
			}
		}
	}

	public static void main(String[] args) throws InterruptedException {
		Processor processor = new Processor();

		Thread t1 = new Thread(() -> {
			try {
				processor.produce();
			} catch(InterruptedException ex) {
				ex.printStackTrace();
			}
		});

		Thread t2 = new Thread(() -> {
			try {
				processor.consume();
			} catch(InterruptedException ex) {
				ex.printStackTrace();
			}
		});

		t1.start();
		t2.start();

		t1.join();
		t2.join();
	}

}
