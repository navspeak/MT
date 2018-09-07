package sorting;

import java.util.Arrays;

public class HeapSort {

    public static void sort(int[] arr) {
        int end = arr.length - 1;
        while (end >= 0) {
            heapify(arr, end);
            swap(arr, 0, end);
            end--;
        }
    }



    private static void heapify(int[] arr, int end) {
        int i = getParentIndex(end);
        if (!hasLeftChild(i, end)) return;
        while (i >= 0) {
            int greaterIndex = getLeftChildIndex(i);
            if (hasRightChild(i, end)) {
                final int rightChildIndex = getRightChildIndex(i);
                greaterIndex = arr[greaterIndex] > arr[rightChildIndex] ? greaterIndex : rightChildIndex;
            }
            if (arr[i] < arr[greaterIndex]) {
                swap(arr, i, greaterIndex);
            }
            i--;
        }

    }

/*Array {10,9,2,2,8,5,3,6} can be represented as heap as below [index=value]
* denotes where i is
1st Heapify stage. end = 7
End of this Heapify, arr[0] contains max.
Swap 0 with end and decrement end.
REPEAT heapify with end = 6
                    [0=10]
         [1=9]              [2=2]
    *[3=2]    [4=8]       [5=5]    [6=3] => doesn't satisfy heap property | Swap
[7=6]
================================
                    [0=10]
         [1=9]              *[2=2] => doesn't satisfy heap property | Swap
    [3=6]    [4=8]       [5=5]    [6=3]
[7=2]
 ================================
                        [0=10]
         *[1=9]              [2=5] => satisfy heap property | no Swap
    [3=2]    [4=8]       [5=2]    [6=3]
[7=2]
 ================================
                   *[0=10] => satisfy heap property | no Swap
         [1=9]              [2=2]
    [3=2]    [4=8]       [5=5]    [6=3]
[7=2]

 */

    //Utility methods
    static int getLeftChildIndex(int i) {
        return 2 * i + 1;
    }

    static int getRightChildIndex(int i) {
        return 2 * i + 2;
    }

    static int getParentIndex(int i) {
        return (i - 1) / 2;
    }

    static boolean hasLeftChild(int i, int end) {
        return getLeftChildIndex(i) <= end;
    }

    static boolean hasRightChild(int i, int end) {
        return getRightChildIndex(i) <= end;
    }

    static boolean hasParent(int i, int end) {
        return i >= 0 && getParentIndex(i) >= 0;
    }

    private static void swap(int[] arr, int i, int j) {
        int tmp = arr[i];
        arr[i] = arr[j];
        arr[j] = tmp;
    }

    public static void main(String[] args) {
        int [] arr = {10,9,2,2,8,5,3,6,8};
        System.out.println(Arrays.toString(arr));
        sort(arr);
        System.out.println(Arrays.toString(arr));
    }
}
