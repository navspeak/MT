package com.nav.multithreading.executorservice;

import java.util.concurrent.Executors;

public class B {
	public static void main(String[] args) {
		//https://github.com/douglascraigschmidt/POSA/tree/master/ex/M3/GCD
		//Count down latch await returns void
		//Cyclic Barrier await returns arrival action of thread at the barrier
		//Can be used in lieu of BarrierAction like if (barrier.await == 0)
		//If barrier.reset() is called the count will be rest to original and whichever thread was waiting 
		//at the barrier will receive BrokenBarrierException
		
		//Executors.newScheduledThreadPool()
	}
}
