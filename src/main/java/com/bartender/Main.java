package com.bartender;

import java.util.concurrent.TimeUnit;

//Driver
public class Main {
    public static void main(String[] args) {
        Bartender bartender = new Bartender();
        Thread bartenderThread = new Thread(bartender, "Bartender");
        bartenderThread.start();
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException ex){
            //ignore
        }

        int numCustomer = 5;
        Thread[] customerThreads = new Thread[numCustomer];
        for (int i = 1; i <= numCustomer; i++) {
            String custName = "Customer " + i;
            Customer cust = new Customer(bartenderThread, custName, (int) (Math.random() * 10 ));
            customerThreads[i-1]  = new Thread(cust, custName);
            customerThreads[i-1].start();
        }

        // Yuk! Active waiting - we can do better in join below instead
        // while (bartenderThread.isAlive()) {}

        try
        {
            bartenderThread.join();
        }
        catch (InterruptedException e)
        {
            // This can be ignored
        }

        System.out.println("A voice: Hey! Isn't that the bartender sneaking out the door?");

        for (int i=1; i<=numCustomer; i++) {
            customerThreads[i-1].interrupt();
        }

    }
}
