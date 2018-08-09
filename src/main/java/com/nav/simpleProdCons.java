package com.nav;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class simpleProdCons {
    private static final int LIMIT = 10;

    static class Producer implements Runnable {
        Queue<Integer> q;
        Object lock;
        private volatile boolean isStopped = false;

        public Producer(Queue<Integer> q, Object lock) {
            this.q = q;
            this.lock = lock;
        }

        @Override
        public void run() {
            synchronized (lock) {
                while (!isStopped) {
                    if (Thread.interrupted() == true)
                        isStopped = true;
                    if (q.size() < LIMIT) {
                        Integer num = (new Random()).nextInt(10);
                        q.add(num);
                        System.out.println("[Prod]" + q.size() + " Produced " + num);
                        sleep(3);
                        lock.notifyAll();
                    } else {
                        try {
                            lock.wait();
                        } catch (InterruptedException e) {
                            isStopped = true;
                            e.printStackTrace();
                        }
                    }
                }
            }
        }

        void doStop() {
            this.isStopped = true;
        }

        private void sleep(int timeout) {
            try {
                TimeUnit.SECONDS.sleep(timeout);
            } catch (InterruptedException e) {
                isStopped = true;
                e.printStackTrace();
            }
        }
    }

    static class Consumer implements Runnable {
        Queue<Integer> q;
        Object lock = null;
        volatile boolean isStopped = false;

        public Consumer(Queue<Integer> q, Object lock) {
            this.q = q;
            this.lock = lock;
        }


        @Override
        public void run() {
            synchronized (lock) {
                while (!isStopped) {
                    if (!q.isEmpty()) {
                        int num = q.remove();
                        System.out.println("[cons] " + q.size() + " Consumed " + num);
                        sleep(3);
                        lock.notifyAll();
                    } else {
                        try {
                            lock.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                            isStopped = true;
                        }
                    }
                }
            }

        }

        private void sleep(int timeout) {
            try {
                TimeUnit.SECONDS.sleep(timeout);
            } catch (InterruptedException e) {
                isStopped = true;
                e.printStackTrace();
            }
        }

    }

    public static void main(String[] args) throws InterruptedException {
        Queue<Integer> q = new LinkedList<>();
        Object lock = new Object();
        Producer prod = new Producer(q, lock);
        Consumer cons = new Consumer(q, lock);

        Thread prodThread = new Thread(prod);
        Thread consThread = new Thread(cons);

        prodThread.start();
        consThread.start();
        //===============

        TimeUnit.SECONDS.sleep(10);
        System.out.println("Press any key to cancel");
        Scanner in = new Scanner(System.in);
        if (in.next().equalsIgnoreCase("Y")); {
            System.out.println("Cancelling....");
            prodThread.interrupt();
            consThread.interrupt();
        }
        prodThread.join();
    }

}
