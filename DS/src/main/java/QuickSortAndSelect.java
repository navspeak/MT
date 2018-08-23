import java.util.Arrays;
import java.util.Random;

public class QuickSortAndSelect {

    public static void quickSort(int[] arr) {
        quickSort(arr, 0, arr.length - 1);
    }

    public static int select(int[] arr, int k) {
        return select(arr, 0, arr.length - 1, k - 1);
    }
    private static void quickSort(int[] arr, int leftIndex, int rightIndex) {
        if (leftIndex >= rightIndex) return;
        int pivot = partition(arr, leftIndex, rightIndex);
        quickSort(arr, leftIndex, pivot - 1);
        quickSort(arr, pivot + 1, rightIndex);
    }

    private static int partition(int[] arr, int firstIndex, int lastIndex) {
        int pivotIndex = firstIndex + new Random().nextInt(lastIndex - firstIndex + 1);
        int pivot = arr[pivotIndex];
        swap(arr, pivotIndex, lastIndex);
        for (int i = firstIndex; i < lastIndex ; i++) {
            if (arr[i] < pivot) {
                swap(arr, i, firstIndex);
                firstIndex++;
            }
        }
        swap(arr, firstIndex, lastIndex);
        return firstIndex;
    }

    private  static int select(int[] arr, int firstIndex, int lastIndex, int k) {
        int pivotIndex = partition(arr, firstIndex, lastIndex);
        if (pivotIndex > k)
            return select(arr, firstIndex, pivotIndex - 1, k );
        else if (pivotIndex < k)
            return select(arr,  pivotIndex +1, lastIndex, k );
        else
            return arr[k];
    }

    private static void swap(int[] arr, int i, int j) {
        int tmp = arr[i];
        arr[i] = arr[j];
        arr[j] = tmp;
    }

    public static void main(String[] args) {
        int arr[] = {9, 7, 5, 11, 12, 2, 14, 3, 10, 6};
        System.out.println(Arrays.toString(arr));
        quickSort(arr);
        System.out.println(Arrays.toString(arr));
        System.out.println(select(arr,4));
    }


}
