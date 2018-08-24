package com.nav.multithreading;

import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class FindMaxParallel {
	static int THRESHOLD;
	public static void main(String[] args) {
		int[] array = generateIntArray(1000000);
		THRESHOLD = array.length / Runtime.getRuntime().availableProcessors();
		//System.out.println(Arrays.toString(array));
		long start = System.currentTimeMillis();
		System.out.println(sequentialFindMax(array, 0, array.length));
		long end = System.currentTimeMillis();
		System.out.println("Time taken in sequential processing " + (end - start));

		ForkJoinPool pool = new ForkJoinPool(Runtime.getRuntime().availableProcessors());
		start = System.currentTimeMillis();
		System.out.println(pool.invoke(new FindMaxParallelTask(array, 0, array.length)));
		end = System.currentTimeMillis();
		System.out.println("Time taken in parellel processing " + (end - start));
		

	}

	static int sequentialFindMax(int [] num, int lo, int hi){
		int max = num[lo];
		for (int i = lo + 1; i < hi; i++){
			max = Math.max(max, num[i]);
		}
		return max;
	}


	static int[]  generateIntArray(int size){
		int [] array = new int[size];
		for (int i = 0; i< size; i++) {
			array[i] = (new Random()).nextInt(1000);
		}
		return array;
	}


	static class FindMaxParallelTask extends RecursiveTask<Integer>{
		int num[];
		int hi, lo;
		public FindMaxParallelTask(int[] a, int lo, int hi) {
			this.num = a;
			this.hi = hi;
			this.lo = lo;
		}

		int sequentialFindMax(){
			int max = num[lo];
			for (int i = lo + 1; i < hi; i++){
				max = Math.max(max, num[i]);
			}
			return max;
		}

		@Override
		protected Integer compute() {
			if (hi - lo < THRESHOLD)
				return sequentialFindMax();
			else {
				int mid = ( hi + lo )/2;
				FindMaxParallelTask t1 = new FindMaxParallelTask(num, lo, mid);
				FindMaxParallelTask t2 = new FindMaxParallelTask(num, mid + 1, hi);
				invokeAll(t1, t2);
				int solution;
				return Math.max(t1.join(), t2.join());
			}
		}

	}
}
