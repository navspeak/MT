package dp;

public class SubsetSum {
    public boolean solve(int[] arr, int sum){
        Boolean [][] memo = new Boolean[arr.length][sum+1];
        //return hasSubSetSumRec(arr, sum, 0, memo);
        return hasSubSetSumDP(arr, sum);
    }

    // Space O(N) Recursion tree + O(N*Sum) for memo
    // Time: O(N*sum)
    private boolean hasSubSetSumRec(int[] arr, int sum, int currentIndex, Boolean[][] memo) {
        if (sum == 0) return true;
        if (arr.length == 0) return false;
        if (currentIndex == arr.length) return false;
        if (memo[currentIndex][sum]) return memo[currentIndex][sum];
        boolean a = false;
        if (sum >= arr[currentIndex]) {
            if (hasSubSetSumRec(arr, sum - arr[currentIndex], currentIndex + 1, memo)
                    == true) {
                memo[currentIndex][sum] = true;
            }
        }
        memo[currentIndex][sum] = hasSubSetSumRec(arr, sum, currentIndex + 1, memo);
        return memo[currentIndex][sum];
    }

    private boolean hasSubSetSumDP(int[] arr, int sum) {
        boolean[][] dp = new boolean[arr.length + 1][sum + 1];

        for (int i = 0; i <= sum; i++) {
            dp[0][i] = false;
        }


        for (int i = 0; i <= arr.length; i++) {
            dp[i][0] = true;
        }


        /*
             0  1  2  3  4  5  6  7
        0 [] T  F  F  F  F  F  F  F
        1 3  T  F  F  T  F  F  F  F
        2 34 T  F  F  T  F  F  F  F
        3 4  T
        4 12 T  F  F  T  F  F  F  F
        5 5  T  F  F  T  F  T  F  F
        6 2  T  F  T  T  F  T  F  T
         */

        for (int i = 1; i <= arr.length; i++) {
            for (int j = 0; j <= sum; j++) {
                if (dp[i-1][j])
                    dp[i][j] = dp[i-1][j];
                else if (j>=arr[i-1])
                    dp[i][j] = dp[i-1][j-arr[i-1]];
            }
        }
        boolean ret = dp[arr.length][sum];
        if (ret == true) {
            printParticipants(arr, dp);
        }
        return ret;
    }

    private void printParticipants(int[] arr, boolean[][] dp) {

        int col = dp[0].length - 1;
        int row = dp.length - 1 ;
        for (int i = 0; i <= row; i++) {
            for (int j = 0; j <= col; j++) {
                System.out.print(dp[i][j] ? "T " : "F " );
            }
            System.out.println();
        }
        while(col >= 0 && row >= 0) {
            if(dp[row][col] && dp[row - 1][col] == false){
                System.out.print(arr[row - 1]);
                row--;
            } else {
                col = col - arr[row-1];
                row--;
            }
        }
        System.out.println();
    }

    public static void main(String[] args) {
        SubsetSum subsetSum = new SubsetSum();
        System.out.println(subsetSum.solve(new int[] {3, 34, 4, 12, 5, 2}, 7));
        System.out.println(subsetSum.solve(new int[] {3, 34, 4, 12, 5, 2}, 12));
    }
}
