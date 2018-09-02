package com.two.signalling.restaurant.nav;

import java.util.concurrent.ThreadLocalRandom;

public class Customer implements Runnable{
    private final String name;
    Customer(String name) {this.name = name;}
    public String getName() { return name; }

    @Override
    public void run() {
        //System.out.println(name + " is starting his day");
        arrive();
        BarSimulator.numCustomersInBar.getAndIncrement();
        // However, the bar could have closed
        if (Thread.interrupted() == true || BarSimulator.closed == true){
            BarSimulator.numCustomersInBar.getAndDecrement();
            System.out.println(name + " is late. Come early tomorrow. Bye!");
            return;
        }

        System.out.println(name + " has arrived.");

        CustomerHandler.handle(this, REQUEST_TYPE.SEATING);
        CustomerHandler.handle(this, REQUEST_TYPE.ORDER);
        CustomerHandler.handle(this, REQUEST_TYPE.SERVE);
        eatMeal();
        CustomerHandler.handle(this, REQUEST_TYPE.BILL);
        System.out.println(name + " has left.");

        BarSimulator.numCustomersInBar.decrementAndGet();

    }

    private void arrive() {
        ThreadLocalRandom tlr = ThreadLocalRandom.current();
        // arrival time is a random no. between 0 to Bar opening duration + 1 hr
        // Simulating arrival of a customer till 1 hour post bar closure
        int arrivalTime = Math.abs(tlr.nextInt()) % (BarSimulator.OPEN_DURATION_IN_MINS + 60);
        // before arrival, we let the customer thread sleep
        try {
            Thread.sleep(arrivalTime * BarSimulator.TIMESCALER);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        System.out.println(name + " has arrived " + arrivalTime + " mins after bar opening time");
    }

    private void eatMeal() {
        System.out.println(name + " has started eating.");
        ThreadLocalRandom tlr = ThreadLocalRandom.current();
        int eatingTime = Math.abs(tlr.nextInt()) % 30 + 30;
        long eatingTimeEnd = System.currentTimeMillis() +
                eatingTime * BarSimulator.TIMESCALER ;
        long remTime;
        while ((remTime = eatingTimeEnd - System.currentTimeMillis()) > 0) {
            try {Thread.sleep(remTime);} catch (InterruptedException ex) {};
        }
        System.out.println(name + " has eaten his meal.");
    }

}
