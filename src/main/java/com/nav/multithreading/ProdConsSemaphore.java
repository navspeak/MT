package com.nav.multithreading;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public class ProdConsSemaphore {

	private static Semaphore s1 = new Semaphore(1);
	private static Semaphore s2 = new Semaphore(0);

	private static class producer implements Runnable {
		Queue<String> queue ;
		public producer(Queue queue) {
			this.queue = queue;
		}
		String[] info = {"Info 1", "Info 2", "Info 3", "Info 4"};
		@Override
		public void run() {
			while(true){
				try {

					if (Thread.interrupted() == true) {
						System.out.println("Producer thread interuppted. Breaking...");
					}
					s1.acquire();
					for (String e: info){
						System.out.println("Producing " + e);
						queue.add(e);
					}
					queue.add("Done!");
					s2.release();
				} catch (InterruptedException e) {
					Thread.currentThread().interrupt(); // very important
					System.out.println("Producer thread interuppted (Exception). Breaking...");
					break;
				} finally{

				}
			}
		}
	}

	private static class consumer implements Runnable {
		Queue<String> queue = new LinkedList<>();
		public consumer(Queue queue) {
			this.queue = queue;
		}
		@Override
		public void run() {
			while(true){
				try {
					if (Thread.interrupted() == true) {
						System.out.println("Consumer thread interuppted. Breaking...");
					}
					
					TimeUnit.SECONDS.sleep(10);
					s2.acquire();
					for (String e = queue.remove(); !e.equals("Done!"); e = queue.remove()){
						System.out.println("consuming " + e);
					}
					s1.release();
				} catch (InterruptedException e) {
					Thread.currentThread().interrupt(); // very important
					System.out.println("Consumer thread interuppted (Exception). Breaking...");
					break;
				} finally{

				}
			}
		}
	}


	public static void main(String[] args) {
		Queue<String> q = new LinkedList<>();
		Thread prod = new Thread(new producer(q));
		Thread cons = new Thread(new consumer(q));
		prod.start();
		cons.start();
		System.out.println("Press any key to cancel");
		Scanner in = new Scanner(System.in);
		if (in.next().equalsIgnoreCase("Y")); {
			prod.interrupt();
			cons.interrupt();
		}
	}
}
