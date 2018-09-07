package sorting;

import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.*;

public class ParallelMergeSort extends RecursiveAction {

    private final int[] arr;

    ParallelMergeSort (int [] arr) {
        this.arr = arr;
    }

    @Override
    protected void compute() {
        if (arr.length <= 10) {
            MergeSort.sort(arr);
            return;
        }
        int mid = arr.length / 2;
        int left[] = Arrays.copyOfRange(arr,0, mid);
        int right[] = Arrays.copyOfRange(arr,mid, arr.length);
        ParallelMergeSort leftSort = new ParallelMergeSort(left);
        ParallelMergeSort rightSort = new ParallelMergeSort(right);
        invokeAll(leftSort, rightSort);
        MergeSort.merge(left, right, arr);
    }

    public static void main(String[] args) throws InterruptedException {
        int [] arr = initNumbers();
        long now = System.currentTimeMillis();
        System.out.println(Arrays.toString(arr));
        ForkJoinPool pool = new ForkJoinPool(Runtime.getRuntime().availableProcessors());
        pool.invoke(new ParallelMergeSort(arr));
        System.out.println(Arrays.toString(arr));
    }

    private static int[] initNumbers() {
        final int NUM_ELEMS = 10;
        int [] arr = new int[NUM_ELEMS];
        for (int i = 0; i < NUM_ELEMS ; i++) {
            arr[i] = (new Random()).nextInt(12);
        }
        return arr;
    }


}
