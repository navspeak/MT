package com.nav.again;

public class AThreeThreads {

    public static Integer val = 0;
    public static final Object lock = new Object();

   static Runnable printTask = () -> {
       while (val < 21){
           final String threadName = Thread.currentThread().getName();
           final int threadNo = Integer.parseInt(threadName.split("-")[1]) -1;
           synchronized (lock) {
               if (val % 3 == threadNo) {
                   System.out.println(threadName + " is printing " + (++val));
               }
           }
       }
   };

    static Runnable printTask1 = () -> {
        while (val < 21){
            final String threadName = Thread.currentThread().getName();
            final int threadNo = Integer.parseInt(threadName.split("-")[1]) -1;
            synchronized (lock) {
                if (val % 3 == threadNo) {
                    System.out.println(threadName + " is printing " + (++val));
                    lock.notify();
                } else {
                    try{
                        lock.wait();
                    } catch (InterruptedException ex) {};
                }
            }
        }
    };

    public static void main(String[] args) throws InterruptedException {

        Thread thread1 = new Thread(printTask, "Thread-1");
        Thread thread2 = new Thread(printTask, "Thread-2");
        Thread thread3 = new Thread(printTask, "Thread-3");

        thread1.start();
        thread2.start();
        thread3.start();
        thread1.join();
    }

}
