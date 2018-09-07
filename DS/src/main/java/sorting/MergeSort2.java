package sorting;

import java.util.Arrays;

public class MergeSort2 {

    /**
     * @param arr - the array to be sorted
     * @param tmp - temp array
     * @param leftIndex - start Index inclusive
     * @param rightIndex - end Index inclusive
     */
    public static void sort(int[] arr, int[] tmp, int leftIndex, int rightIndex){
        assert rightIndex < arr.length;
        if (leftIndex >= rightIndex) return;
        int mid = (leftIndex + rightIndex)/2;
        sort(arr, tmp, leftIndex, mid);
        sort(arr, tmp, mid+1, rightIndex);
        merge(arr, tmp, leftIndex, mid, mid+1, rightIndex);
    }

    /**
     * @param arr - the array to be sorted
     * @param tmp - temp array
     * @param leftStart - start index of left sub array inclusive
     * @param leftEnd - end index of left sub array inclusive
     * @param rightStart - start index of right sub array inclusive
     * @param rightEnd - end index of right sub array inclusive
     */
    public static void merge(int[] arr, int[] tmp, int leftStart, int leftEnd, int rightStart, int rightEnd) {
        int i=leftStart, j = rightStart, k = leftStart;
        while (i <= leftEnd && j <= rightEnd) {
            if (arr[i] < arr[j]) {
                tmp[k++] = arr[i++];
            } else {
                tmp[k++] = arr[j++];
            }
        }

        System.arraycopy(arr,leftStart,tmp, k,leftEnd - i + 1);
        System.arraycopy(arr,rightStart,tmp, k,rightEnd - j + 1);
        System.arraycopy(tmp,leftStart,arr, leftStart,rightEnd - leftStart + 1);
    }

    public static void sort(int[] arr){
        sort(arr, new int[arr.length], 0, arr.length -1 );
    }


    public static void main(String[] args) {
        int [] arr = {9,2,2,8,5,3,6};
        System.out.println(Arrays.toString(arr));
        sort(arr);
        System.out.println(Arrays.toString(arr));
    }

}
