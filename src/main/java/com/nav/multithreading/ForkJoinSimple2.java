package com.nav.multithreading;


import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

public class ForkJoinSimple2 {
	
	public static void main(String[] args) {
		ForkJoinPool pool = new ForkJoinPool(Runtime.getRuntime().availableProcessors());
		SimpleRecursiveAction task1 = new SimpleRecursiveAction(20);
		SimpleRecursiveAction task2 = new SimpleRecursiveAction(120);
		pool.invoke(task1);
	}
	//good to sort something not return anything
	// what if we are trying to find a file
	static class SimpleRecursiveAction extends RecursiveAction {
		int simulatedWork;
		public SimpleRecursiveAction(int simulatedWork) {
			this.simulatedWork = simulatedWork;
		}

		@Override
		protected void compute() {
			if (simulatedWork > 100) {
				System.out.println("Parellel execution and split tasks..." + simulatedWork);
				SimpleRecursiveAction action1 = new SimpleRecursiveAction(simulatedWork/2);
				SimpleRecursiveAction action2 = new SimpleRecursiveAction(simulatedWork/2);
				action1.fork();
				action2.fork();
			} else {
				System.out.println("Parellel execution not needed. Seq is ok" );
			}
			
		}
		
	}

}
