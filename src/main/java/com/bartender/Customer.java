package com.bartender;

import java.util.concurrent.TimeUnit;

public class Customer implements Runnable{
    private final Thread bartenderThread ;
    private final String name;
    private final int waitTime;

    public Customer(Thread bartenderThread, String customerName, int waitTime) {
        this.bartenderThread = bartenderThread;
        this.name = customerName;
        this.waitTime = waitTime;
    }

    @Override
    public void run() {
        // will sleep for a random time and wake the bartender thread
        System.out.println(name + ": Doesn't seem to be anyone around. I'll wait a moment");
        try {
            TimeUnit.SECONDS.sleep(waitTime);
        } catch (InterruptedException ex) {
            System.out.println(name + ":Hey! Where is the bartender going? I'm telling my lawyer!");
            return;
        }
        System.out.println(name + ": Oh there's a bell. Can I get some service?");
        bartenderThread.interrupt();
    }
}
