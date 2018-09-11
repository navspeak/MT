package com.three;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Mt2 {

    public static void main(String[] args) {

        CyclicBarrier cb = new CyclicBarrier(3);

        MyCounter counterT1 = new MyCounter("Thread T1", 3, 1, cb);
        MyCounter counterT2 = new MyCounter("Thread T2", 3, 2, cb);
        MyCounter counterT3 = new MyCounter("Thread T3", 3, 3, cb);

        ExecutorService es = Executors.newFixedThreadPool(3);

        es.execute(counterT1);
        es.execute(counterT2);
        es.execute(counterT3);
        es.shutdown();
    }

}

class MyCounter implements Runnable {
    private CyclicBarrier barrier;
    private int initialValue;
    private int increment;
    private String name;

    public MyCounter(String name, int increment, int initialValue, CyclicBarrier barrier) {
        this.initialValue = initialValue;
        this.increment = increment;
        this.name = name;
        this.barrier = barrier;
    }

    public void run() {
        System.out.println(name + " : " + initialValue);
        try {
            barrier.await();
            System.out.println("All have arrived " + barrier.getNumberWaiting());

            while (initialValue < 10) {
                initialValue = initialValue + increment;
                System.out.println(name + " : " + initialValue);
                barrier.await();
            }

        } catch (InterruptedException | BrokenBarrierException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}