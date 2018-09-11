package com.three;

import java.util.Arrays;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ThreeThread {

    static final int NUM_THREADS = 3;
    static final Object lock = new Object();
    static volatile int val = 0;
    static final Lock relock = new ReentrantLock();
    static final Condition printCondition = relock.newCondition();
    static Runnable PrintNumbersTask = () -> {
        while (val < 10) {
            synchronized (lock) {
                final String threadName = Thread.currentThread().getName();
                // Thread name is of format "pool-1-Thread-1 for Executors"
                int threadNo = Integer.parseInt(threadName.substring(threadName.length()-1)) - 1;
                // Thread-1 is ThreadNo 0 => It will print 1,4,7...
                // Thread-2 is ThreadNo 1 => It will print 2,5,9...
                // Thread-3 is ThreadNo 2 => It will print 3,6,10...
                if (val % NUM_THREADS == threadNo) {
                    System.out.println(threadName + " is printing " + ++val);
                }
            }
        }
    };

    static Runnable PrintNumbersTask_waitnotify = () -> {
        while (val < 5) {
            synchronized (lock) {
                final String threadName = Thread.currentThread().getName();
                // Thread name is of format "pool-1-Thread-1 for Executors"
                int threadNo = Integer.parseInt(threadName.substring(threadName.length()-1)) - 1;
                // Thread-1 is ThreadNo 0 => It will print 1,4,7...
                // Thread-2 is ThreadNo 1 => It will print 2,5,9...
                // Thread-3 is ThreadNo 2 => It will print 3,6,10...
                while (val % NUM_THREADS != threadNo) {
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {/*ignore*/}

                }
                System.out.println(threadName + " is printing " + ++val);
                lock.notifyAll();// If I have only Notify, I get a deadlock
                // because notify may chose to wake wrong thread all the time
                // correct thread may keep on waiting
            }
        }
    };


    static Runnable PrintNumbersTask_reentrantLock = () -> {
        while (val < 5) {
            try {
                relock.lock();
                final String threadName = Thread.currentThread().getName();
                // Thread name is of format "pool-1-Thread-1 for Executors"
                int threadNo = Integer.parseInt(threadName.substring(threadName.length()-1)) - 1;
                // Thread-1 is ThreadNo 0 => It will print 1,4,7...
                // Thread-2 is ThreadNo 1 => It will print 2,5,9...
                // Thread-3 is ThreadNo 2 => It will print 3,6,10...
                while (val % NUM_THREADS != threadNo) {
                    try {
                        printCondition.await();
                    } catch (InterruptedException e) {/*ignore*/}

                }
                System.out.println(threadName + " is printing " + ++val);
                printCondition.signalAll();
            } finally {
                relock.unlock();
            }
        }

    };



    public static void main(String[] args) throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(NUM_THREADS);
        for (int i = 0; i < NUM_THREADS; i++) {
            executorService.submit(PrintNumbersTask_reentrantLock);
        }
        executorService.shutdown();
        executorService.awaitTermination(1, TimeUnit.HOURS);
    }

}
