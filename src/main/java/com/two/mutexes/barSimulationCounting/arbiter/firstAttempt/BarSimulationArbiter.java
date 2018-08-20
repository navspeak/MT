package com.two.mutexes.barSimulationCounting.arbiter.firstAttempt;

import java.util.HashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class BarSimulationArbiter
{
	private final HashMap<String, Integer> roundsBought = new HashMap<>();
	private final AtomicInteger roundsSoFar = new AtomicInteger();

	public void register(String name)
	{
		roundsBought.put(name, 0);
	}

	public void roundBought(String name)
	{
//		int numBought = roundsBought.get(name);
//		roundsBought.put(name, numBought + 1);
		roundsBought.compute(name, (k, v) -> v + 1 );
		roundsSoFar.getAndIncrement();
	}

	public int getNumBought(String name)
	{
		return roundsBought.get(name);
	}

	public int getNumRoundsSoFar()
	{
		return roundsSoFar.get();
	}

}
