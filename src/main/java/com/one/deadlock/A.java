package com.one.deadlock;

public class A {
    public Object mutex1 = new Object();
    public Object mutex2 = new Object();

    public void a() {
        synchronized (mutex1){
            System.out.println("["+ Thread.currentThread().getName() + "] - a()");
            b();
        }
    }
    public void b() {
        synchronized (mutex2){
            System.out.println("["+ Thread.currentThread().getName() + "] - b()");
            c();
        }
    }

    public void c() {
        synchronized (mutex1){
            System.out.println("["+ Thread.currentThread().getName() + "] - c()");
        }
    }

    public static void main(String[] args) throws InterruptedException {
        A a = new A();
        Runnable r1 = () -> a.a();
        Runnable r2 = () -> a.b();

        Thread t1 = new Thread(r1);
        Thread t2 = new Thread(r2);

        t1.start();
        t2.start();

        t1.join();
        t2.join();
    }

}
