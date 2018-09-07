package sorting;

import org.junit.Test;

import java.util.Random;
import java.util.concurrent.ForkJoinPool;

public class ParallelMergeSort2Test {

    private static final Random rnd = new Random(1234567);
    private static final ForkJoinPool pool = new ForkJoinPool(Runtime.getRuntime().availableProcessors());

    private static int[] createArray(int n){
        int[] arr = new int[n];
        for (int i = 0; i < n; i++){
            arr[i] = rnd.nextInt();
        }
        return arr;
    }

    private static void sortArrayParallel(int n) {
        pool.invoke(new ParallelMergeSort(createArray(n)));
    }

    private static void sortArraySeq(int n) {
        MergeSort2.sort(createArray(n));
    }

    //==============
    @Test
    public void T1_parallel_sort_10() {
        sortArrayParallel(10);
    }

    @Test
    public void T1_Seq_sort_10() {
        sortArrayParallel(10);
    }
    //=======================
    @Test
    public void T2_parallel_sort_100() {
        sortArrayParallel(100);
    }

    @Test
    public void T2_Seq_sort_100() {
        sortArrayParallel(100);
    }
    //=======================
    @Test
    public void T3_parallel_sort_1000() {
        sortArrayParallel(1000);
    }

    @Test
    public void T3_Seq_sort_1000() {
        sortArrayParallel(1000);
    }
    //=======================
    @Test
    public void T4_parallel_sort_10000() {
        sortArrayParallel(10000);
    }

    @Test
    public void T4_Seq_sort_10000() {
        sortArrayParallel(10000);
    }
    //=======================
    @Test
    public void T5_parallel_sort_100000() {
        sortArrayParallel(100000);
    }

    @Test
    public void T5_Seq_sort_100000() {
        sortArrayParallel(100000);
    }
    //=========================
    @Test
    public void T6_parallel_sort_1000000() {
        sortArrayParallel(100000);
    }

    @Test
    public void T6_Seq_sort_1000000() {
        sortArrayParallel(100000);
    }
    //==========================
    @Test
    public void T7_parallel_sort_10000000() {
        sortArrayParallel(1000000);
    }

    @Test
    public void T7_Seq_sort_10000000() {
        sortArrayParallel(1000000);
    }
    //==========================
    @Test
    public void T8_parallel_sort_100000000() {
        sortArrayParallel(10000000);
    }

    @Test
    public void T8_Seq_sort_100000000() {
        sortArrayParallel(10000000);
    }
    //==========================
    @Test
    public void T8_parallel_sort_1000000000() {
        sortArrayParallel(100000000);
    }

    @Test
    public void T8_Seq_sort_1000000000() {
        sortArrayParallel(100000000);
    }
}