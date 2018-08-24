package com.nav.multithreading.prodcons;

public class Drop {
	
	private int value;
	private boolean empty = true;
	
	public synchronized void put(int val) throws InterruptedException {
		if (empty == false) {
			wait();
		}
		value = val;
		empty = false;
		notify();
	}

	public synchronized int take() throws InterruptedException{
		if (empty == true) {
			wait();
		}
		
		empty = true;
		notify();
		return value;
	}
}
