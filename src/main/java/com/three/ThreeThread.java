package com.three;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class ThreeThread {

    static final int NUM_THREADS = 3;
    static final Object lock = new Object();
    static int val = 0;

    private static class PrintNumbers  implements Runnable{
        @Override
        public void run() {
            while (val < 10) {
                synchronized (lock){
                    final String threadName = Thread.currentThread().getName();
                    // Thread name is of format "Thread-<number>"
                    int threadNo = Integer.parseInt(threadName.split("-")[1]) - 1;
                    if (val % NUM_THREADS != threadNo)  {
                        try {
                            lock.wait();
                        } catch (InterruptedException e) {/*ignore*/ }
                    }
                    System.out.println(threadName + " is printing " + ++val);
                    lock.notify();
                }
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Thread thread1 = new Thread(new PrintNumbers(), "Thread-1");
        Thread thread2 = new Thread(new PrintNumbers(), "Thread-2");
        Thread thread3 = new Thread(new PrintNumbers(), "Thread-3");

        thread1.start();
        thread2.start();
        thread3.start();

        thread1.join();
    }

}
