package com.nav.multithreading.prodcons;

import java.util.Random;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ProdCons {

	public static class Drop {
		Lock lock = new ReentrantLock();
		Condition notFull = lock.newCondition();
		Condition notEmpty = lock.newCondition();
		String value;
		Boolean isEmpty = true;

		public void put(String val) throws InterruptedException {
			lock.lock();
			try{
				while(isEmpty==false){//QFull
					notFull.await();
				}
				this.value = val;
				isEmpty = false;
				notEmpty.signal();

			}finally{
				lock.unlock();
			}
		}


		public String take() throws InterruptedException {
			lock.lock();
			try{
				while(isEmpty==true){//QEmpty
					notEmpty.await();
				}
				isEmpty = true;
				notFull.signal();
				return value;
			}finally{
				lock.unlock();
			}
		}

		public static class Drop1 {
			Semaphore s1 = new Semaphore(1);
			Semaphore s2 = new Semaphore(0);
			String value;
			Boolean isEmpty = true;

			public void put(String val) throws InterruptedException {
					while(isEmpty==false){//QFull
						s1.acquire();
					}
					this.value = val;
					isEmpty = false;
					s2.release();
				}


			public String take() throws InterruptedException {
			
					while(isEmpty==true){//QEmpty
						s2.acquire();
					}
					isEmpty = true;
					s1.release();
					return value;
			}
		}

			public static void main(String[] args) throws InterruptedException {
				//Drop drop = new Drop();
				Drop1 drop = new Drop1();
				Thread producer = new Thread(new Runnable() {

					@Override
					public void run() {
						while(true) {
							try {
								int val = new Random().nextInt(10);
								System.out.println("Producing " + val);
								drop.put(Integer.toString(val));
								TimeUnit.SECONDS.sleep(5);
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}

					}
				});

				Thread consumer = new Thread(new Runnable() {

					@Override
					public void run() {
						while(true) {
							try {
								System.out.println("Consumed ..." + drop.take());
								TimeUnit.SECONDS.sleep(5);
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}

					}
				});

				producer.start();
				consumer.start();

				producer.join();

			}
		}
	}



