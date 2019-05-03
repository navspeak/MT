package com.nav.multithreading.prodcons;

public class ThreeNumbersSemaphores {
    public static void main(String[] args) {
        Object lock = new Object();

        Thread t1 = new Thread(new PrintNumber(lock, 0));
        Thread t2 = new Thread(new PrintNumber(lock, 1));
        Thread t3 = new Thread(new PrintNumber(lock, 2));

        t1.start();
        t2.start();
        t3.start();


    }


}

class PrintNumber implements Runnable {
    private Object lock;
    private int position;
    private static int val = 0;


    PrintNumber(Object lock, int position) {
        this.lock = lock;
        this.position = position;
    }


    @Override
    public void run() {
        while(val < 30) { //upto 32
            synchronized (lock){
                while (val%3 != position) {
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.println(Thread.currentThread().getName() + " " + ++val);
                lock.notifyAll();
            }
        }
    }
}
