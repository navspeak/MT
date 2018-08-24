package com.nav.multithreading;

import java.util.concurrent.ConcurrentHashMap;

public class TestApp {
	
	public static class TaskA implements Runnable {
		
		private Test test;
		
		public TaskA(Test test) {
			this.test = test;
			ConcurrentHashMap<String, String> map = new ConcurrentHashMap<String, String>();
		}

		@Override
		public void run() {
			Test.methodA();			
		}
		
	}
	
	public static class TaskB implements Runnable {
		
		private Test test;
		
		public TaskB(Test test) {
			this.test = test;
		}

		@Override
		public void run() {
			test.methodB();			
		}
		
	}

	public static void main(String[] args) {
		Test test = new Test();
		
		Thread t1 = new Thread(new TaskA(test));
		Thread t2 = new Thread(new TaskB(test));
		
		t1.start();
		t2.start();
		

		try {
			t1.join();
			t2.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
