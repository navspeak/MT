package com.sharingMemory.producerConsumer.eg1;

import java.util.concurrent.TimeUnit;

public class Consumer implements Runnable, ProduceObserver {
	private volatile Produce produce = null;
	private final String name;
	
	public void onProduction(Produce produce)
	{
		this.produce = produce;
	}
	Consumer(String name) { this.name = name;}
	
	public void run()
	{
		System.out.println("["+name+"] Consumer starting");
		
		while (true) {
			sleep(1);
			if (produce != null) {
				int produceInstance = produce.getInstance();
				Produce.Color color = produce.getColor();
				
				System.out.println("["+name+"] Last produce instance: " + produceInstance);
				System.out.println("["+name+"] Last produce color: " + color.name());
				
				if (produceInstance == 10) {
					break;
				}
			}
		}
		
		System.out.println("["+name+"] Consumer terminating");
	}

	public static void sleep(int timeInSeconds) {
	    try {
	        TimeUnit.SECONDS.sleep(timeInSeconds);
	    } catch(InterruptedException ex) {
	        ex.printStackTrace();
	    }
	}
}
