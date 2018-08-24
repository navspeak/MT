package com.nav.multithreading;


import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.RecursiveTask;

public class ForkJoinSimple1 {
	
	public static void main(String[] args) {
		ForkJoinPool pool = new ForkJoinPool(Runtime.getRuntime().availableProcessors());
		SimpleRecursiveTask task1 = new SimpleRecursiveTask(20);
		SimpleRecursiveTask task2 = new SimpleRecursiveTask(120);
		pool.invoke(task1);
	}
	//good to sort something not return anything
	// what if we are trying to find a file
	static class SimpleRecursiveTask extends RecursiveTask<Integer> {
		int simulatedWork;
		public SimpleRecursiveTask(int simulatedWork) {
			this.simulatedWork = simulatedWork;
		}

		@Override
		protected Integer compute() {
			if (simulatedWork > 100) {
				System.out.println("Parellel execution needed as task is huge..." + simulatedWork);
				SimpleRecursiveTask task1 = new SimpleRecursiveTask(simulatedWork/2);
				SimpleRecursiveTask task2 = new SimpleRecursiveTask(simulatedWork/2);
				task1.fork();
				task2.fork();
				//invokeAll(task1, task2); //instead of fork we could use invokeAll
				int solution = 0;
				solution += task1.join();
				solution += task2.join();
				return solution;
			} else {
				System.out.println("Parellel execution not needed. Seq is ok" );
				return simulatedWork*2;
			}
			
		}
		
	}

}
