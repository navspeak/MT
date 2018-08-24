package com.nav.multithreading.prodcons;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class App {
	
	public static void main(String[] args) throws InterruptedException {
		Queue q = new LinkedList<>();
		Object lock = new Object();
		Producer p = new Producer(q, lock);
		Consumer c = new Consumer(q, lock);
		
		Thread tp = new Thread(p);
		Thread tc = new Thread(c);
		tp.start();
		tc.start();
		
		TimeUnit.SECONDS.sleep(10);
		System.out.println("Press any key to cancel");
		Scanner in = new Scanner(System.in);
		if (in.next().equalsIgnoreCase("Y")); {
			System.out.println("Cancelling....");
			tp.interrupt();
			tc.interrupt();
		}
		tp.join();
	}

}
