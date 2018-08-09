package com.nav;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class usingLockProdCons {
    private static final int LIMIT = 10;

    static class Producer implements Runnable {
        Queue<Integer> q;
        Lock lock;
        Condition qFull;
        Condition qEmpty;
        private volatile boolean isStopped = false;

        public Producer(Queue<Integer> q, Lock lock, Condition qFull, Condition qEmpty) {
            this.q = q;
            this.lock = lock;
            this.qFull = qFull;
            this.qEmpty = qEmpty;
        }

        @Override
        public void run() {
          //  synchronized (lock) {
            try {
                lock.lock();
                while (!isStopped) {
                    if (Thread.interrupted() == true)
                        isStopped = true;
                    if (q.size() < LIMIT) {
                        Integer num = 10 + (new Random()).nextInt(100);
                        q.add(num);
                        System.out.println("[Prod]" + q.size() + " Produced " + num);
                        qEmpty.signal();
                        sleep(3);
                    } else {
                        try {
                            qFull.await();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                            isStopped = true;
                        }
                    }
                }
            } finally {
                lock.unlock();
            }
            // }
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
        Lock lock = null;
        Condition qFull;
        Condition qEmpty;
        volatile boolean isStopped = false;

        public Consumer(Queue<Integer> q, Lock lock, Condition qFull, Condition qEmpty) {
            this.q = q;
            this.lock = lock;
            this.qFull = qFull;
            this.qEmpty = qEmpty;
        }


        @Override
        public void run() {
            //synchronized (lock) {
            try {
                lock.lock();
                while (!isStopped) {
                    if (!q.isEmpty()) {
                        int num = q.remove();
                        System.out.println("[cons] " + q.size() + " Consumed " + num);
                        sleep(1);
                        qFull.signal();
                    } else {
                        try {
                            qEmpty.await();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                            isStopped = true;
                        }
                    }
                }
            } finally {
                lock.unlock();
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
        Lock lock = new ReentrantLock();
        Condition qFull = lock.newCondition();
        Condition qEmpty = lock.newCondition();
        Producer prod = new Producer(q, lock, qFull, qEmpty);
        Consumer cons = new Consumer(q, lock, qFull, qEmpty);

        Thread prodThread = new Thread(prod);
        Thread consThread = new Thread(cons);

        prodThread.setName("ProdThread");
        consThread.setName("ConsThread");

        Thread.currentThread().setName("Main");

        prodThread.start();
        consThread.start();

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
