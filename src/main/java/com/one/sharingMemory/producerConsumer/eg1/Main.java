package com.one.sharingMemory.producerConsumer.eg1;

public class Main {
	public static void main(String[] args)
	{
		Producer producer = new Producer();
		Consumer consumerA = new Consumer("ConsumerA");
		Consumer consumerB = new Consumer("ConsumerB");
		
		producer.registerObserver(consumerA);
		producer.registerObserver(consumerB);
		
		Thread producerThread = new Thread(producer, "producerThread");
		Thread consumerAThread = new Thread(consumerA, "consumerAThread");
		Thread consumerBThread = new Thread(consumerB, "consumerBThread");
		
		producerThread.start();
		consumerAThread.start();
		consumerBThread.start();
	}
}
