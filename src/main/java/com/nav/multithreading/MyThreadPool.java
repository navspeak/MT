package com.nav.multithreading;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;


public class MyThreadPool {
	
	private BlockingQueue<Runnable> taskQueue = null;
    private List<MyThread> threadsList = null;
    private boolean isStopped = false;

    public MyThreadPool(int noOfThreads, int maxNoOfTasks){
        taskQueue = new ArrayBlockingQueue<>(maxNoOfTasks);
        threadsList = new ArrayList<>(noOfThreads);
        System.out.printf(Thread.currentThread().getName() + " - Thread Pool with %d threads and %d maxNoOfTasks \n", noOfThreads, maxNoOfTasks );
        for(int i=0; i<noOfThreads; i++){
        	threadsList.add(new MyThread(taskQueue, "Thread - " + i));
        }
        for(MyThread thread : threadsList){
            thread.start();
        }
    }

    public synchronized void  execute(Runnable task) throws Exception{
        if(this.isStopped) throw
            new IllegalStateException("ThreadPool is stopped");

        this.taskQueue.offer(task);// offer returns true or false
    }

    public synchronized void stop(){
        this.isStopped = true;
        for(MyThread thread : threadsList){
           thread.doStop();
        }
    }
}


