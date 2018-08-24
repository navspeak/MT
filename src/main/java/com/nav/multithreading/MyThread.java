package com.nav.multithreading;

import java.util.concurrent.BlockingQueue;

public class MyThread extends Thread {
	
	private BlockingQueue<Runnable> taskQueue = null;
    private volatile boolean       isStopped = false;

    public MyThread(BlockingQueue<Runnable> queue, String name){
        taskQueue = queue;
        setName(name);
    }

    public void run(){
        while(!isStopped()){
            try{
                Runnable runnable = taskQueue.take();
                runnable.run();
            } catch(Exception e){
                //log or otherwise report exception,
                //but keep pool thread alive.
            }
        }
    }

    public synchronized void doStop(){
        isStopped = true;
        this.interrupt(); //break pool thread out of dequeue() call.
    }

    public synchronized boolean isStopped(){
        return isStopped;
    }


}
