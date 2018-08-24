package com.nav.multithreading;

public class MyThreadTest {

	public static void main(String[] args) throws Exception {
		MyThreadPool pool = new MyThreadPool(2, 5);
		pool.execute(()->System.out.println(Thread.currentThread().getName() + " TEST1"));
		pool.execute(()->System.out.println(Thread.currentThread().getName() + " TEST2"));
		pool.execute(()->System.out.println(Thread.currentThread().getName() + " TEST3"));
		pool.execute(()->System.out.println(Thread.currentThread().getName() + " TEST4"));
		pool.execute(()->System.out.println(Thread.currentThread().getName() + " TEST5"));
		pool.execute(()->System.out.println(Thread.currentThread().getName() + " TEST6"));
		
		pool.stop();
		System.out.println("===========");
	}

}
