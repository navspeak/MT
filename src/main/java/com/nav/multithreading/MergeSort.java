package com.nav.multithreading;

import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;


public class MergeSort {
	
	public static void main(String[] args) {
//		int[] array = generateIntArray(300000);
//		long start = System.currentTimeMillis();
//		System.out.println("Original Array : " + Arrays.toString(array));
//		mergeSort(array);
//		System.out.println("Sorted Array : " + Arrays.toString(array));
//		long end = System.currentTimeMillis();
//		System.out.println("Time taken in seq processing " + (end - start));
		
		int[] array = generateIntArray(300000); // seq took 12508, parallel took 150
		System.out.println("Original Array : " + Arrays.toString(array));
		ForkJoinPool pool = new ForkJoinPool(Runtime.getRuntime().availableProcessors());

		long start = System.currentTimeMillis();
		System.out.println(pool.invoke(new MergeSortAction(array)));
		long end = System.currentTimeMillis();
		System.out.println("Sorted Array : " + Arrays.toString(array));
		System.out.println("Time taken in parellel processing " + (end - start));
	}
	
	static int[]  generateIntArray(int size){
		int [] array = new int[size];
		for (int i = 0; i< size; i++) {
			array[i] = (new Random()).nextInt(1000) + 1;
		}
		return array;
	}

	public static void mergeSort(int[] nums){
		if (nums.length <= 1) return;
		int mid = nums.length / 2;
		int [] left = Arrays.copyOfRange(nums, 0, mid);
		int [] right = Arrays.copyOfRange(nums, mid, nums.length);
		//System.arraycopy(src, srcPos, dest, destPos, length);
		mergeSort(left);
		mergeSort(right);
		merge(left, right, nums);
	}

	private static void merge(int[] left, int[] right, int[] nums) {
		int i = 0, j = 0 , k = 0;

		while (i < left.length && j < right.length) {
			if (left[i] < right[j])
				nums[k++] = left[i++];
			else
				nums[k++] = right[j++];
		}
		
		while(i < left.length)
			nums[k++] = left[i++];
		while(j < right.length)
			nums[k++] = right[j++];

	}
	
	static class MergeSortAction extends RecursiveAction {
		int[] numArray; 
		public MergeSortAction(int[] num) {
			this.numArray = num;
		}

		@Override
		protected void compute() {
			if (numArray.length < 10){
				mergeSort(numArray);
			} else {
				int mid = numArray.length / 2;
				int[] left = Arrays.copyOfRange(numArray, 0, mid);
				int [] right = Arrays.copyOfRange(numArray,  mid , numArray.length);
				MergeSortAction a1 = new MergeSortAction(left);
				MergeSortAction a2 = new MergeSortAction(right);	
				invokeAll(a1, a2);
				merge(left, right, numArray);
			}
			
		}
		
		private  void mergeSort(int[] nums){
			if (nums.length <= 1) return;
			int mid = nums.length / 2;
			int [] left = Arrays.copyOfRange(nums, 0, mid);
			int [] right = Arrays.copyOfRange(nums, mid, nums.length);
			mergeSort(left);
			mergeSort(right);
			merge(left, right, nums);
		}

		private void merge(int[] left, int[] right, int[] nums) {
			int i = 0, j = 0 , k = 0;

			while (i < left.length && j < right.length) {
				if (left[i] < right[j])
					nums[k++] = left[i++];
				else
					nums[k++] = right[j++];
			}
			
			while(i < left.length)
				nums[k++] = left[i++];
			while(j < right.length)
				nums[k++] = right[j++];

		}
		
	}

}
