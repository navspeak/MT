package com.nav.multithreading;

import java.util.Random;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class ReceiveOrderAndExecute {

    BlockingQueue<Order> orders ;

    ReceiveOrderAndExecute(BlockingQueue<String> q) {
        orders = new LinkedBlockingQueue<>();
    }

    void receiveAndExecuteClientOrdersBest() {

        int expectedConcurrentOrders = 100;
        Executor orderExecutionThreads = Executors.newFixedThreadPool(expectedConcurrentOrders, new MyThreadFactory());
        ExecutorService orderReceiverThread = Executors.newSingleThreadExecutor();
        orderReceiverThread.submit(new OrderReceiver(orders));

        while (true) {
            Order order = null;
            try {
                order = orders.take();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            orderExecutionThreads.execute(order);

        }

    }

    public static void main(String[] args) {
        ReceiveOrderAndExecute r = new ReceiveOrderAndExecute(new LinkedBlockingQueue<>());
        r.receiveAndExecuteClientOrdersBest();
    }
}

class OrderReceiver implements Runnable {

    private String orderString;
    private BlockingQueue<Order> orderQueue;
    OrderReceiver(BlockingQueue q) { orderQueue = q;}


    @Override
    public void run() {
        while(true) {
            orderQueue.offer(new Order("Order " + (new Random()).nextInt(5000)));
            try {
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {

            }
        }
    }
}

class Order implements Runnable{
    final String text;
    ThreadLocal<Integer> counter = ThreadLocal.withInitial(()->0);
    Order(String text) { this.text = text;}

    @Override
    public void run() {
        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {

        }
        counter.set((counter.get() + 1));
        System.out.println("Executing order " + text + " in thread " +
                Thread.currentThread().getName());
    }
}

class MyThreadFactory implements ThreadFactory {
    private static AtomicInteger counter = new AtomicInteger(1);

    @Override
    public Thread newThread(Runnable r) {
        return new Thread(r, "Thread " + counter.getAndIncrement());
    }
}
