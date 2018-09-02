package com.two.signalling.restaurant.nav;

import java.util.concurrent.BrokenBarrierException;

public class Waiter implements Runnable{
    private final String name;
    private Request currentRequest = null;
    Waiter(String name) {this.name = name;}

    public String getName() { return name; }

    @Override
    public void run() {
        System.out.println(name + " has shown up for work");
        waitTillAllWaitersArrive();
        while (!BarSimulator.closed || BarSimulator.numCustomersInBar.get() > 0) {
            currentRequest = null;
            waitForRequest();
            if (currentRequest != null) {
                synchronized (currentRequest) {
                    currentRequest.setHandled(true);
                    currentRequest.notify();
                }
            }
        }
        System.out.println(name + " is going home");
    }

    private void waitTillAllWaitersArrive() {
        try {
            BarSimulator.waitersBarrier.await();
        } catch (InterruptedException | BrokenBarrierException e) { }
    }


    private void waitForRequest() {
        System.out.println(name + " is waiting for customer request");
        while(true) {
            try {
                currentRequest = BarSimulator.requests.take();
            } catch (InterruptedException e) { /* we will go back to loop and try to take again */}

            if (currentRequest != null) break;
            if ((BarSimulator.numCustomersInBar.get() == 0 )&&
                    BarSimulator.closed == true)
                break;
        }
        if (currentRequest != null) {
            System.out.println(name + " has a request from "
                    + currentRequest.getCustomer().getName() + ": "
                    + currentRequest.getRequestType());
            System.out.println(currentRequest.getRequestType().getWaiterMessage());
        }

    }
}
