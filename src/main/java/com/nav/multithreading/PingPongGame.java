package com.nav.multithreading;

import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;

public class PingPongGame {
	final static Semaphore s1 = new Semaphore(0); // For PING
	final static Semaphore s2 = new Semaphore(0); // For PONG
	final static AtomicInteger countDown = new AtomicInteger( 10 );
	static Runnable printPing = () -> {
		try {
			while (countDown.get() > 0){
				s1.acquire();
				System.out.println(countDown.get());
				System.out.println("PING");
				s2.release(); // because we want PONG to begin
										
				countDown.decrementAndGet();
			}

		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {

		}
	};
	static Runnable printPong = () -> {
		try {
			while (countDown.get() > -1){
				s2.acquire();
				System.out.println("PONG");
				System.out.println("===============");
				s1.release(); // because we want PING to begin
				
				countDown.decrementAndGet();
			}

		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {

		}
	};

	public static void main(String[] args) throws InterruptedException {

		Thread t1 = new Thread(printPing);
		Thread t2 = new Thread(printPong);
		t1.start();
		t2.start();

		s1.release();

		t1.join();
		t2.join();


	}

}
