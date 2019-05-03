package com.nav.multithreading.prodcons;

import java.util.ArrayDeque;
import java.util.Queue;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class ProducerConsumerForInterview {
    final static int CAPACITY = 5;

    public static void main(String[] args) throws InterruptedException {
        Queue<Integer> q = new ArrayDeque<>(CAPACITY);
        Object lock = new Object();
        Thread prodTh = new Thread(new MyProducer(q,lock));
        Thread consTh = new Thread(new MyConsumer(q,lock));

        prodTh.start();
        TimeUnit.SECONDS.sleep(1);
        consTh.start();

    }
}

class MyProducer implements Runnable {
    private Queue<Integer> q;
    private Object lock;
    private AtomicInteger counter = new AtomicInteger(0);
    MyProducer(Queue<Integer> myQ, Object myLock) {
        q = myQ;
        lock = myLock;
    }


    @Override
    public void run() {
        while (counter.getAndIncrement() < 15) {
            synchronized (lock) {
                while (q.size() == ProducerConsumerForInterview.CAPACITY) {
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                final int produce = ThreadLocalRandom.current().nextInt(10);
                System.out.println(Thread.currentThread().getName() + " produced " + produce);
                q.add(produce);
                lock.notify();
            }
        }
    }
}

class MyConsumer implements Runnable {
    private Queue<Integer> q;
    private Object lock;
    private AtomicInteger counter = new AtomicInteger(0);
    MyConsumer(Queue<Integer> myQ, Object myLock) {
        q = myQ;
        lock = myLock;
    }

    @Override
    public void run() {
        while (counter.getAndIncrement() < 15) {
            synchronized (lock) {
                while (q.size() == 0) {
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.println(Thread.currentThread().getName() + " consumed " + q.remove());
                lock.notify();
            }
        }
    }
}

