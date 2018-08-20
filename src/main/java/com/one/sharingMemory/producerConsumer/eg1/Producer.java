package com.one.sharingMemory.producerConsumer.eg1;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Producer implements Runnable {
	private volatile List<ProduceObserver> observers = new ArrayList<>();
	
	public void registerObserver(ProduceObserver observer)
	{
		observers.add(observer);
	}
	
	public void run()
	{
		System.out.println("Producer starting");
		
		for (int i=1; i<=10; i++) {
			sleep(2);
			System.out.println("Producer producing " + Produce.Color.values()[i % Produce.Color.values().length]);
			Produce produce = new Produce.ProduceBuilder().
					instance(i).
					color(Produce.Color.values()[i % Produce.Color.values().length]).
					build();

			observers.forEach(observer -> observer.onProduction(produce));
		}
		
		System.out.println("Producer terminating");
	}

	public static void sleep(int timeInSeconds) {
	    try {
	        TimeUnit.SECONDS.sleep(timeInSeconds);
	    } catch(InterruptedException ex) {
	        ex.printStackTrace();
	    }
	}
}
