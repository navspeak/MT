package sorting;

import java.lang.reflect.Array;
import java.util.Arrays;

public class MergeSort {

    // Merge Sort is a - Stable sort - https://www.geeksforgeeks.org/stability-in-sorting-algorithms/
    // if we sort a list of object that has two fields - a numeric id & a character id
    // let's represent the object as (10,a) for e.g
    // thus sorting by numeric id
    // (10,a) (10,b), (9,a), (1,b) => (1,b), (9,a), (10,a), (10, b)
    // NOTE: since in original array (10,a) came before (10,b) the sorted array also maintains the
    // same relative position
    // With MergeSort it WILL NEVER happen that a sorted array is (1,b), (9,a), (10,b), (10, a)
    public static void sort(int[] arr){
        if (arr.length <= 1) return;
        int mid = arr.length / 2;
        // 1,3,4,6 len = 4, mid = 2 => left = [1,3], right = [4, 5]
        // 1,3,4,5,7 len = 5, mid = 2 => left = [1,3], right = [4, 5, 7]

        /* You could write a while loop and copy. But using Java provided utility for brewity */
        int[] left = Arrays.copyOfRange(arr, 0, mid ); // 0 to mid -1
        int [] right = Arrays.copyOfRange(arr, mid, arr.length); // mid to len - 1
        sort(left);
        sort(right);
        merge(left, right, arr);
    }

    public static void merge(int[] left, int[] right, int[] arr) {
        int i = 0, j = 0, k = 0;
        // [1,3] & [ 0,2,3] & [0,0,0,0,0]
        while (i < left.length && j< right.length ) {
            if (left[i] < right[j]){
                arr[k++] = left[i++];
            } else
                arr[k++] = right[j++];
        }
        int numOfElementsToCopy = left.length - i;
        System.arraycopy(left,i,arr, k, numOfElementsToCopy );
        numOfElementsToCopy = right.length - j ;
        System.arraycopy(right,j,arr, k,numOfElementsToCopy );

// You could write a while loop and copy. But using Java provided utility for brewity */
//        while (i < left.length) {
//            arr[k++] = left[i++];
//        }
//        while (j < right.length) {
//            arr[k++] = right[j++];
//        }
    }

    public static void main(String[] args) {
        int [] arr = {9,2,2,8,5,3,6};
        System.out.println(Arrays.toString(arr));
        sort(arr);
        System.out.println(Arrays.toString(arr));
    }

}
