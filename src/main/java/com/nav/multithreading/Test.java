package com.nav.multithreading;

import java.util.concurrent.TimeUnit;

public class Test {

	public static synchronized void methodA() {

		try {
			System.out.println(Thread.currentThread().getName() + " Method A");
			TimeUnit.SECONDS.sleep(5);
			System.out.println(Thread.currentThread().getName() + " Method A");			
			
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public static synchronized void methodB() {

		try {
			System.out.println(Thread.currentThread().getName() + " Method B");
			TimeUnit.SECONDS.sleep(5);
			System.out.println(Thread.currentThread().getName() + " Method B");			
			
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}


}
