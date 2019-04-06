package dp;

import java.util.HashMap;
import java.util.Map;

public class EggBreaking {

    private int solve(int numOfFloors, int numOfEggs) {
        final HashMap<String, Integer> memo = new HashMap<>();
        return solveRec(numOfFloors, numOfEggs, memo);
        //return solveDP(numOfFloors, numOfEggs);
    }
    /* without memoization it would be O(2^n) *
       With memoziation it can be O(n^2 * e)
     */
    public int solveRec(int numOfFloors, int numOfEggs, Map<String, Integer> memo){
        if (numOfEggs == 1) return numOfFloors;
        if (numOfFloors == 1) return 1;
        String key = numOfFloors + "____" + numOfEggs;
        if (memo.containsKey(key)) return memo.get(key);
        int minTries = Integer.MAX_VALUE;
        for (int i = 1; i < numOfFloors; i++) {
            int caseWhenEggBreak = solveRec(i - 1, numOfEggs - 1, memo);
            int caseWhenEggDoesntBreak = solveRec(numOfFloors - i, numOfEggs, memo);
            minTries = Math.min(
                    Math.max(caseWhenEggBreak, caseWhenEggDoesntBreak),
                    minTries);
        }
        int val = minTries + 1; // 1 for first egg try to determine if egg broke or not
        memo.put(key, val);
        return val;

    }

    public int solveDP(int numOfFloors, int numOfEggs){
        int t[][] = new int[numOfFloors + 1][numOfEggs + 1];
        for (int i = 0; i <= numOfFloors; i++) {
            t[i][1] = i;
        }

        for (int i = 0; i <= numOfEggs; i++) {
            t[1][i] = 1;
        }

        for (int i = 2; i <= numOfFloors ; i++) {
            for (int j = 2; j <= numOfEggs; j++) {
                t[i][j] = Integer.MAX_VALUE;
                for (int k = 1; k < i; k++) {
                    int caseWhenEggBreak = t[k-1][j-1];
                    int caseWhenEggDoesntBreak = t[i-k][j];
                    t[i][j] = Math.min(
                            Math.max(caseWhenEggBreak, caseWhenEggDoesntBreak) + 1,
                            t[i][j]);

                }
            }
        }
        return t[numOfFloors][numOfEggs];

    }

    public static void main(String[] args) {
        final EggBreaking eggBreaking = new EggBreaking();
        int minTries = eggBreaking.solve(100, 2);
        System.out.println(minTries == 14 ? "Test case 1 passed" : "Test case 1 failed. " +
                "Expected 14. Got = " + minTries);
        minTries = eggBreaking.solve(6, 2);
        System.out.println(minTries == 3 ? "Test case 2 passed" : "Test case 2 failed. " +
                "Expected 3. Got = " + minTries);

        minTries = eggBreaking.solve(6, 2);
        System.out.println(minTries == 3 ? "Test case 3 passed" : "Test case 3 failed. " +
                "Expected 3. Got = " + minTries);

        minTries = eggBreaking.solve(14, 3);
        System.out.println(minTries == 4 ? "Test case 4 passed" : "Test case 4 failed. " +
                "Expected 4. Got = " + minTries);

    }


}
