package dp;

import java.util.HashMap;
import java.util.Map;

public class Knapsack {

    public int solve(final int[] values, final int[] wts, final int capacity) {
        Map<String, Integer> memo = new HashMap<>();
        return getMaxProfitDP(values, wts, capacity);
        //return getMaxProfitRec(values, wts, capacity, 0, memo);
    }

    // With memo the Time complexity can be reduced from O(2^n)
    // to O(N*Capacity). Space complexity : O(N*C) for memo
    // plus O(N) for recusion stack
    private int getMaxProfitRec(int[] values, int[] wts, int capacity, int currentIndex,
                                Map<String, Integer> memo) {
        if (capacity <= 0) return 0;
        if (currentIndex >= values.length) return 0;
        String key = currentIndex + "___" + capacity;
        if (memo.containsKey(key)) return memo.get(key);
        int profitConsideringCurrentIntem = 0;
        if (capacity >= wts[currentIndex]) /* Important to note the condition */
            profitConsideringCurrentIntem = values[currentIndex] +
                    getMaxProfitRec(values, wts, capacity - wts[currentIndex],
                            currentIndex + 1, memo);
        int profitSkippingCurrentItem = getMaxProfitRec(values, wts,
                capacity, currentIndex + 1, memo);

        int ret = Math.max(profitConsideringCurrentIntem, profitSkippingCurrentItem);
        memo.put(key, ret);
        return ret;
    }

    //O(N*C) space and time complexity
    private int getMaxProfitDP(int[] values, int[] wts, int capacity) {
        int t[][] = new int[values.length+1][capacity+1];
        //for all u t[i][0] = 0 & t[0][i] = 0
        for (int i = 1; i < values.length + 1; i++) {
            for (int j = 1; j < capacity + 1; j++) {
                int profitConsideringCurrentIntem = 0;
                if (j >= wts[i-1]) /* Important to note the condition */
                    profitConsideringCurrentIntem = values[i-1] + t[i-1][j-wts[i-1]];
                int profitSkippingCurrentItem = t[i-1][j];
                t[i][j] = Math.max(profitConsideringCurrentIntem, profitSkippingCurrentItem);
            }
        }
        return t[values.length][capacity];
    }


    public static void main(String[] args) {
        final Knapsack knapsack = new Knapsack();
        int a = knapsack.solve(new int[] {1,6,10,16}, new int[]{1,2,3,5}, 7);
        System.out.println(a == 22 ? "1. Passed" : "1. Failed. Expected 22. Got " + a);
        a = knapsack.solve(new int[] {1,6,10,16}, new int[]{1,2,3,5}, 6);
        System.out.println(a == 17 ? "2. Passed" : "2. Failed. Expected 17. Got " + a);
    }

}
