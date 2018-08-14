package com.bartenderV2;

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
        for (int i = 0; i < numCustomer; i++) {
            String custName = "Customer " + i;
            Customer cust = new Customer(bartenderThread, custName, (int) (Math.random() * 10 ));
            new Thread(cust, custName).start();
        }

    }
}
