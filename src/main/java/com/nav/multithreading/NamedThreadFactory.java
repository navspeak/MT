package com.nav.multithreading;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

public class NamedThreadFactory  implements ThreadFactory{
	static AtomicInteger i=new AtomicInteger(1);

	@Override
	public Thread newThread(Runnable r) {
		return new Thread(r, "MyThread"+i.getAndIncrement());
	}

	
}
