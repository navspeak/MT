package com.two.signalling.restaurant.nav;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.Queue;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class BarSimulator {

    static final LocalTime OPENING_TIME = LocalTime.of(9,00) ;
    static final int OPEN_DURATION_IN_MINS = 360;
    static final LocalTime CLOSING_TIME = OPENING_TIME.plus(OPEN_DURATION_IN_MINS, ChronoUnit.MINUTES);
    static final int TIMESCALER = 10; //while sleep assume 1 mins of actual sleep = 10 ms in this simulation

    static final BlockingQueue<Request> requests = new LinkedBlockingQueue<>();
    static final int NUM_WAITERS =5;
    static final int NUM_CUSTOMERS =50;

    static volatile boolean closed = true;
    static AtomicInteger numCustomersInBar = new AtomicInteger(0);

    static final CyclicBarrier waitersBarrier = new CyclicBarrier(NUM_WAITERS + 1,BarSimulator::openRestaurant);


    public static void main(String[] args) {
        Thread[] waiters = createWaiters();
        Thread[] customers = createCustomers();

        startWaiters(waiters);
        waitForWaiters();
        //Restaurant is now open.
        System.out.println(" Restaurant is opening...");
        startCustomers(customers);
        openForService();
        closed = true;
        // now closing. signal the customers
        System.out.println(" Restaurant is closed...");
        for (int i = 0; i < NUM_CUSTOMERS; i++) {
            customers[i].interrupt();
        }

        // still some customers eating. Let's wait for them
        int x;
        while ((x = numCustomersInBar.get()) > 0) {
            System.out.println(x + " customers are still eating. Will let them finish");
            sleep(20);
        }

        // Now waiters can go home. Let's signal them
        for (int i = 0; i < NUM_WAITERS; i++) {
            waiters[i].interrupt();
        }

    }

    private static Thread[] createWaiters() {
        Thread[] waiters = new Thread[NUM_WAITERS];
        for (int i = 1; i <= NUM_WAITERS ; i++) {
            waiters[i-1] = new Thread(new Waiter("Waiter-"+i) , "Waiter-"+i);
        }
        return waiters;
    }

    private static Thread[] createCustomers() {
        Thread[] customers = new Thread[NUM_CUSTOMERS];
        for (int i = 1; i <= NUM_CUSTOMERS ; i++) {
            customers[i-1] = new Thread(new Customer("Customer-"+i), "Customer-"+i);
        }
        return customers;
    }

    private static void startWaiters(Thread[] waiters) {
        for (int i = 1; i <= NUM_WAITERS ; i++)
            waiters[i-1].start();
    }

    private static void startCustomers(Thread[] customers) {
        for (int i = 1; i <= NUM_CUSTOMERS ; i++)
            customers[i-1].start();
    }

    private static void openRestaurant() {
        System.out.println("========================================");
        System.out.println("All Waiters have arrived. Opening the restaurant now at " + OPENING_TIME);
        System.out.println("Restaurants will close at " + CLOSING_TIME);
        System.out.println("========================================");
        closed = false;
    }

    private static void openForService() {
        sleep(OPEN_DURATION_IN_MINS);
    }

    private static void waitForWaiters() {
        try {
            BarSimulator.waitersBarrier.await();
        } catch (InterruptedException | BrokenBarrierException e) { }
    }

    public static void sleep(int timeInMinutes) {
        try {
            Thread.sleep(timeInMinutes * TIMESCALER);
        } catch (InterruptedException ex) {
        }
    }
}
