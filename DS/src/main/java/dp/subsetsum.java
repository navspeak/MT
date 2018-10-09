package dp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

public class subsetsum {
    // Given an array {A[i]} find no. of subsets that produce a sum of S
    // NOTE: all nos. are positive. Needn't be sorted
    // e.g. [2,4,6,10] S=16
    // [10,6]  [10,2,4]
    static final List<List<Integer>> results = new ArrayList<>();

    public static void main(String[] args) {
        int[] arr = {2,6,4,10}; int sum = 16;
        System.out.println(countSubsetsDP(arr, sum));//should be 2

        int [] arr1 = {5,2,3,6,3,5,8}; int sum1 = 12;
        System.out.println(countSubsetsDP(arr1, sum1));//should be 2

        int [] arr2 = {2, 3, 5, 6, 8, 10}; int sum2 = 10;
        System.out.println(countSubsetsDP(arr2, sum2));//should be 3

        int [] arr3 = {1, 2, 3, 4, 5}; int sum3 = 10;
        System.out.println(countSubsetsDP(arr3, sum3));//should be 3

//        Input : arr[] = {2, 3, 5, 6, 8, 10}
//        sum = 10
//        Output : 5 2 3
//        2 8
//        10
//
//        Input : arr[] = {1, 2, 3, 4, 5}
//        sum = 10
//        Output : 4 3 2 1
//        5 3 2
//        5 4 1
    }

    // It's easy to wrap the head around with recursive solution
    // F(A, sum, i) => gives number of ways by which sum can be obtained if we consider elements from A[0] to A[i]
    //   if sum < A[i] then we can't include A[i] for example if Array is [2,6,10] and sum is 6 then we can't include 10
    //      thus the number of possbile ways = F(A, sum, i-1)
    //   else if sum >= A[i] for example [2,6,10] and sum is 16,
    //            we can include 10 in solution and see if [2,6] produce 16- 10 =6 and also see if we don't include 10
    //            can we get 16 from [2,6]
    //       thus the number of possible ways = F(A, sum - arr[i], i -1]) + F(A, sum, i-1)
    //  Base conditions: if i < 0 => no solution possible (out of range)
    //                 : if sum = 0 => 1 solution possible and i.e. empty set
    //                 : if sum < 0 => 0 no solution possible

    static int countSubsets(int[] arr, int sum){
        Map<String, Integer> memo = new HashMap<>();
        return countSubsets(arr, sum, arr.length - 1, memo);
    }
    // no. of subsets that produce sum by considering elements upto index i
    static int countSubsets(int[] arr, int sum, int i, Map<String, Integer> memo){
        String key = sum + ":" + i;
        if (memo.containsKey(key)) return memo.get(key);
        int ret;
        if (sum == 0) ret = 1;  //empty set
        else if (sum < 0) ret = 0; // no subset possible
        else if (i < 0) ret = 0; //out of array range
        else if (sum < arr[i]){ // means arr[i] can't be included
            ret = countSubsets(arr, sum, i-1, memo );
        } else {
            ret = countSubsets(arr, sum - arr[i], i-1, memo) // included a[i]
                    + countSubsets(arr, sum, i-1, memo); //didn't include
        }
        return ret;
    }

    static int countSubsetsDP(int[] arr, int sum){
        int T[][] = new int[arr.length+1][sum+1];
        T[0][0] = 1; // taking no elements how many ways can we get 0 => 1
        for (int j = 1; j <= sum ; j++) {
            T[0][j] = 0;
        }
        for (int i = 1; i <= arr.length ; i++) {
            T[i][0] = 1;
        }
        for (int i = 1; i <= arr.length; i++) {
            for (int j = 1; j <= sum ; j++) {
                if (j<arr[i-1])
                    T[i][j] = T[i-1][j];
                else
                    T[i][j] = T[i-1][j-arr[i-1]] + T[i-1][j];
            }
        }
        return T[arr.length][sum];

    }
}
