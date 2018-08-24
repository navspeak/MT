package com.nav.multithreading;

public class MyLock {

	private volatile boolean isLocked = false; 
	private Thread lockedBy = null;
	private int lockedCount = 0;

	public synchronized void lock() {
		Thread callingThread = Thread.currentThread();
		while (isLocked == true && callingThread != lockedBy )
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		isLocked = true;
		lockedCount++;
		lockedBy = callingThread;
	}

	public synchronized void unlock() {

		if (lockedBy == Thread.currentThread())
			lockedCount--;
		if (lockedCount == 0) {
			isLocked = false;
			notify();
		}

	}
}


