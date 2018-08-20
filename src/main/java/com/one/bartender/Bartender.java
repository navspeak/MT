package com.one.bartender;

import java.util.concurrent.TimeUnit;

public class Bartender implements Runnable {
    //Goes in a loop
    // sleeps for 5 sec. Wakes up to check if bartender was there.
    @Override
    public void run() {
        int numOfTimesWoken = 0;
        System.out.println("[Bartender] My boss isn't in today. Time for some quick snooze!");
        while(true){
            if (Thread.interrupted()){
                System.out.println("[Bartender] Zzz. em, is someone still waiting?");
            }
            if (numOfTimesWoken == 2) {
                break;
            }
            try {
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException ex) {
                numOfTimesWoken++;
                Thread.currentThread().interrupt();
            }
        }

    }
}
