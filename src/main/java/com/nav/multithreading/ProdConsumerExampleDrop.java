package com.nav.multithreading;

import java.util.Random;

public class ProdConsumerExampleDrop {

	static class Drop {
		String message;
		volatile boolean empty = true;
		public synchronized void put(String msg) throws InterruptedException {
			//if  message already there wait
			while (empty == false){
				wait();
			}
			empty = false;
			message = msg;
			notify();
		}
		public synchronized String take() throws InterruptedException {
			while (empty == true){
				wait();
			}
			String msg = message;
			empty = true;
			notify();
			return msg;
		}
		
	}


	static class Producer implements Runnable {
		Drop drop;
		public Producer(Drop drop) {
			this.drop = drop;
		}

		@Override
		public void run() {
			 String importantInfo[] = {
			            "Mares eat oats",
			            "Does eat oats",
			            "Little lambs eat ivy",
			            "A kid will eat ivy too"
			        };
			        Random random = new Random();

			        for (String t : importantInfo) {
			            //drop.put(importantInfo[i]);
			            try {
			            	System.out.println("MESSAGE Produced: " + t);
			            	drop.put(t);
			                Thread.sleep(random.nextInt(5000));
			            } catch (InterruptedException e) {
			            	e.printStackTrace();
			            }
			        }
			        try {
			        	System.out.println("DONE producing " );
						drop.put("DONE");
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
		}
	}

	static class Consumer implements Runnable {
		Drop drop;
		public Consumer(Drop drop) {
			this.drop = drop;
		}

		@Override
		public void run() {
	        Random random = new Random();
			try {
				for(String str = drop.take(); !str.equals("DONE"); str = drop.take()) {
					System.out.println("Consumed  "+  str);
					Thread.sleep(random.nextInt(5000));
				}
			}catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public static void main(String[] args) throws InterruptedException {
		Drop drop = new Drop();
		Thread prod = new Thread(new Producer(drop ));
		Thread cons = new Thread (new Consumer(drop));

		prod.start();
		cons.start();

		prod.join();
		cons.join();



	}
}
