package sorting;

import java.util.Arrays;
import java.util.Random;
// In-palce sorting
// unstable
//Space = O(logn). Time = O(n^2) worst, O(nlogn) average
public class QuickSort {
    public static void sort(int[] arr) {
        sort(arr, 0, arr.length - 1);
    }

    private static void sort(int[] arr, int leftIndex, int rightIndex) {
        if (leftIndex >= rightIndex) return;

        int partionIndex = partition(arr, leftIndex, rightIndex);
        // The element at partionIndex is at correct position
        // We need to sort the elements left of it and right of it
        sort(arr, leftIndex, partionIndex - 1);
        sort(arr, partionIndex + 1 , rightIndex);
    }

    public static int partition(int[] arr, int leftIndex, int rightIndex) {
        assert leftIndex < rightIndex;
        //int pivotIndex = (leftIndex + rightIndex) / 2
        int pivotIndex = leftIndex + (new Random().nextInt(rightIndex - leftIndex + 1));
        swap(arr, pivotIndex, rightIndex); // let's park the pivot at the end. Just makes it easy
        int pivot = arr[rightIndex];
        for (int i = leftIndex; i < rightIndex; i++){
            if (arr[i] < pivot) {
                swap (arr, leftIndex, i);
                leftIndex++;
            }
        }
        swap(arr, leftIndex, rightIndex);
        return leftIndex;

    }

    private static void swap(int[] arr, int i, int j) {
        int tmp = arr[i];
        arr[i] = arr[j];
        arr[j] = tmp;
    }

    public static void main(String[] args) {
        int [] arr = {9,2,2,8,5,3,6};
        System.out.println(Arrays.toString(arr));
        sort(arr);
        System.out.println(Arrays.toString(arr));
    }


}
