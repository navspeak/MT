package sorting;

import java.util.Arrays;
import java.util.Random;
// Worst case can be O(n^2) but average is O(nlogn)
public class QuickSelect {
    // find nth max
    public static void main(String[] args) {
        int arr[] = {9, 7, 5, 11, 12, 2, 14, 3, 10, 6};
        System.out.println(Arrays.toString(arr));
        System.out.println("1st Max " + findkthMax(arr,1));
        System.out.println("2nd Max " + findkthMax(arr,2));
        System.out.println("3rd Max " + findkthMax(arr,3));
        System.out.println("4th Max " + findkthMax(arr,4));
    }

    private static int findkthMax(int[] arr, int k) {
        return select(arr, 0, arr.length -1, k -1);
    }

    private static int select(int[] arr, int leftIndex, int rightIndex, int kMinus1) {
        int pivotIndex = partition(arr, leftIndex, rightIndex);
        if (kMinus1 < pivotIndex)
            return select(arr, leftIndex, pivotIndex - 1, kMinus1);
        else if (kMinus1 > pivotIndex)
            return select(arr,  pivotIndex + 1, rightIndex, kMinus1);
        else
            return arr[kMinus1];
    }


    /**
     * @param arr
     * @param leftIndex
     * @param rightIndex
     * @return index such that LHS > PivotIndex > RHS
     */
    private static int partition(int[] arr, int leftIndex, int rightIndex) {
        int pivotIndex = leftIndex + (new Random()).nextInt(rightIndex - leftIndex + 1);
        swap(arr, pivotIndex, rightIndex);
        int pivot = arr[rightIndex];
        for(int i = leftIndex; i < rightIndex; i++) {
            if (arr[i] > pivot) {
                swap(arr, leftIndex, i);
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
}
