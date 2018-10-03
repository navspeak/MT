package Maze;


public class NQueen {
    final int[][] board;
    final int N;

    NQueen(int N){
        this.board = new int[N][N];
        this.N = N;

    }

    void placeQueen(int col){
        if (col == N){
            printBoard();
            return;
        }
        for (int i=0; i<N; i++){
            board[i][col] = 1;
            if (isSafe(i, col)){
                placeQueen(col + 1);
            }
            board[i][col] = 0;
        }
    }

    private boolean isSafe(int row, int col) {
        if (isRowSafe(row, col) == false)
            return false;
        if (isLeftDiagonalSafe(row, col) == false)
            return false;
        if (isRightDiagonalSafe(row, col) == false)
            return false;
        //no need to check column because the way we have written
        //the program, we place one queen in a column and try next columns
        return true;
    }

    private boolean isLeftDiagonalSafe(int row, int col) {
        //Running from Top Left to Bottom righ
        // \
        //  \
        //   \
//		0 0 0 0
//		0 0 1 0
//		0 0 0 0
//		0 0 0 0
        int i = 0;
        int j = 0;

        if (row > col) i = row - col;
        if (row < col) j = col - row;
        // (1,2) i = 0; j =1
        int sum = 0;
        while (i < N && j < N) {
            sum += board[i++][j++];
            if (sum > 1) return false;
        }
        return sum == 1;
    }

    private boolean isRightDiagonalSafe(int row, int col) {
        //Running from Top Right to Bottom left
        //        /
        //       /
        //      /
//		0 1 0 0 (0,1) (0,1)
//		0 0 1 0 (1,2) < 4
//		0 0 1 0 (3,2) > 4
//		0 0 0 0
        int i = 0;
        int j = N - 1;
        if (row + col < N){
            j = Math.min(row + col, N -1);
        } else {
            i = (row + col) % (N - 1);
        }
        int sum = 0;
        while (i < N && j >= 0) {
            sum += board[i++][j--];
            if (sum > 1) return false;
        }
        return sum == 1;
    }
    private boolean isRowSafe(int row, int col) {
        int sum = 0;
        for (int i = 0; i < N; i++) {
            sum = sum + board[row][i];
        }
        return sum == 1;
    }

    private void printBoard() {
        System.out.println("===");
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N ; j++) {
                System.out.print(board[i][j] + " ");
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        NQueen nQueen = new NQueen(2);
        nQueen.placeQueen(0);
    }
}
