package com.nav.multithreading;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;



public class ProdConsUsingLockCondition {

	private static class Processor{

		private Lock lock = new ReentrantLock();
		private Condition notFull = lock.newCondition(); 
		private Condition notEmpty = lock.newCondition();
		private Queue<Integer> queue = new LinkedList<Integer>();
		//Producer wait for notFull, signal notEmpty
		//Consumer wait for notEmpty, signal notFull
		private void put(int i) throws InterruptedException {

			lock.lock();
			try {
				while (queue.size() == 1) { //QFull
					//System.out.println("Queue is full. Waiting...");
					notFull.await(); // wait for it to be full
				}
				//System.out.println("Queue has room. Putting " + str);
				if (queue.offer(i)) {
					//TimeUnit.SECONDS.sleep(5);
					notEmpty.signal();
				}
			} finally {
				lock.unlock();
			}

		}

		private int take() throws InterruptedException {
			lock.lock();
			Integer i = 0;
			try {
				while (queue.size() == 0) {
					//System.out.println("Queue is empty. Waiting...");
					notEmpty.await();
				}
				i = queue.poll();
				if (i != null) {
					//System.out.println("Queue has some items. Consuming " + str);
					//TimeUnit.SECONDS.sleep(5);
					notFull.signal();
				}
			} finally {
				lock.unlock();
			}
			return i;

		}
	}

	public static void main(String[] args) {
		
		Processor processor = new Processor();
		Thread producer = new Thread(() -> {
			while(true) {
				if (Thread.interrupted() == true) {
					System.out.println("Producer thread interuppted. Breaking...");
					break;
				}
				try {
					int i = (new Random()).nextInt(5);
					System.out.println("Producing " + i);
					processor.put(i);
				} catch (InterruptedException e) {
					Thread.currentThread().interrupt(); // very important
					System.out.println("Producer thread interuppted (Exception). Breaking...");
				}
			}

		});

		Thread consumer = new Thread(() -> {
			while(true) {
				if (Thread.interrupted() == true) {
					System.out.println("Consumer thread interuppted. Breaking...");
					break;
				}
				try {
					int i = processor.take();
					System.out.println("Consumed " + i);
				} catch (InterruptedException e) {
					Thread.currentThread().interrupt(); // very important
					System.out.println("Consumer thread interuppted (Exception). Breaking...");
				}
			}
		});

		producer.start();
		consumer.start();
		System.out.println("Press any key to cancel");
		Scanner in = new Scanner(System.in);
		if (in.next().equalsIgnoreCase("Y")); {
			System.out.println("Cancelling....");
			producer.interrupt();
			consumer.interrupt();
		}
	}
}


