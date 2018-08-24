package com.two.signalling.waitnotify;

import java.util.LinkedList;
import java.util.Queue;

// For discussion only: this is not intended to be run
public class ProducerConsumerWhileLoop
{
	final Queue<Object> queue = new LinkedList<>(); // Mock work as Object
	
	void producer(Object work) {
		synchronized(queue) {
			queue.offer(work);
			queue.notify();
		}
	}
	
	Object consumer() {
		Object work;
		
		synchronized(queue) {
		    // queue is empty. So consumer waits on queue's monitor
            // Producer will put some work on queue and call notify (or notifyAll
            // if there are multiple producers and consumers
            // However it could happen that a spurious wakeup caused the wait to be interuppted.
            // In that case queue.poll will lead to an NPE. Thus, after wait, before actually
            // continuing, check in a while loop if produced indeed put some work on queue
            // This solves spurious wakeup issue and also missed signal
            // In case of time wait check how much time is elapsed to see if it was real notify
            // or spurious wakeups
			while (queue.isEmpty()) {
				try {
					queue.wait();
				} catch (InterruptedException e) {
					// What to do here?
				}
			}
			
			work = queue.poll();
			// In case on multiple, producers and consumer might want to call notify here too
		}
		
		return work;
	}
}
