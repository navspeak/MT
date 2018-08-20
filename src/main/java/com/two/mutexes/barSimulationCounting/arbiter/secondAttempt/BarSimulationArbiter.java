package com.two.mutexes.barSimulationCounting.arbiter.secondAttempt;

import java.util.Hashtable;
import java.util.concurrent.atomic.AtomicInteger;

public class BarSimulationArbiter
{
	private final Hashtable<String, Integer> roundsBought = new Hashtable<>();
	private final AtomicInteger roundsSoFar = new AtomicInteger(0);

	public void register(String patron) { roundsBought.put(patron, 0);}
	public void roundBought(String patron) {
//		int val = roundsBought.get(patron);
//		roundsBought.put(patron, val+1);
		roundsBought.computeIfPresent(patron, (k,v) -> {return v+1;});
		roundsSoFar.getAndIncrement();
	}

	public int getNumBought(String name){ return roundsBought.get(name);}

	public int getNumRoundsSoFar(){	return roundsSoFar.get();}
}
