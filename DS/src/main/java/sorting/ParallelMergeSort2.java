package sorting;

import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

public class ParallelMergeSort2 extends RecursiveAction {

    public static final int THRESHOLD = 10;
    private final int[] arr;
    private final int lo, hi, mid;

    //hi = arr.len - 1 or the last Index
    ParallelMergeSort2(int[] arr, int lo, int hi) {
        this.arr = arr;
        this.lo = lo;
        this.hi = hi;
        this.mid = (hi+1-lo)/2;
    }

    @Override
    protected void compute() {
        if (hi - lo <= THRESHOLD) {
            MergeSort2.sort(arr);
            return;
        }
        ParallelMergeSort2 T1 = new ParallelMergeSort2(arr, 0,mid);
        ParallelMergeSort2 T2 = new ParallelMergeSort2(arr, mid+1, hi);
        invokeAll(T1, T2);
        MergeSort2.merge(arr, new int[arr.length], lo, mid, mid+1, hi);
    }

    public static void main(String[] args) throws InterruptedException {
        int [] arr = initNumbers();
        System.out.println(Arrays.toString(arr));
        ForkJoinPool pool = new ForkJoinPool(Runtime.getRuntime().availableProcessors());
        pool.invoke(new ParallelMergeSort2(arr, 0, arr.length - 1));
        System.out.println(Arrays.toString(arr));
    }

    private static int[] initNumbers() {
        final int NUM_ELEMS = 15;
        int [] arr = new int[NUM_ELEMS];
        for (int i = 0; i < NUM_ELEMS ; i++) {
            arr[i] = (new Random()).nextInt(20);
        }
        return arr;
    }


}
