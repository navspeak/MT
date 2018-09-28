package Maze;
// variation on FindPath
// using backtracking
public class RatInAMaze {
    static final int N = 4;
    static int[][] maze;
    static int [][] mazeWithExit = {
            { 1, 0, 0, 0}, // 1 means there is a path
            { 1, 1, 0, 0},
            { 1, 1, 1, 0},
            { 1, 0, 1, 1}
    };

    static int [][] mazeWithoutExit = {
            { 1, 0, 0, 0}, // 1 means there is a path
            { 1, 1, 0, 0},
            { 1, 1, 1, 0},
            { 1, 0, 0, 1}
    };

    static int[][] solution = new int[N][N];

    public static void main(String[] args) {
        maze = mazeWithExit;
        printAllPossiblePaths(0,0);
        resetSolutionToZeros();
        System.out.println("==============");
        maze = mazeWithoutExit;
        printAllPossiblePaths(0,0);
        resetSolutionToZeros();

        System.out.println("===============");
        maze = mazeWithExit;
        System.out.println("Is exit possible " + isExitPossible(0,0));

        System.out.println("===============");
        maze = mazeWithoutExit;
        System.out.println("Is exit possible " + isExitPossible(0,0));
        if (isExitPossible(0,0) == true)
            printPath();
    }

    private static void resetSolutionToZeros() {
        for (int i = 0; i < N ; i++) {
            for (int j = 0; j <N ; j++) {
               solution[i][j] = 0;
            }
            System.out.println();
        }
    }

    // Prints
    private static void printAllPossiblePaths(int x, int y) {
        if (x==N-1 && y == N-1 ){
            solution[x][y] = 1;
            printPath();
            return;
        }

        if (x < 0 || x > N-1 || y < 0 || y > N - 1 || maze[x][y] == 0 || solution[x][y] == 1) return;
        solution[x][y] = 1;
        printAllPossiblePaths(x+1, y); //down
        printAllPossiblePaths(x, y+1); // right
        //findPath(x-1, y); //up
        // findPath(x, y-1); //left
        solution[x][y] = 0;

    }

    private static boolean isExitPossible(int x, int y) {
        if (x==N-1 && y == N-1 ){
            solution[x][y] = 1;
            return true;
        }

        if (x < 0 || x > N-1 || y < 0 || y > N - 1 || maze[x][y] == 0) return false;
        solution[x][y] = 1;
        if (isExitPossible(x+1, y)) return true; //going down
        if (isExitPossible(x, y+1)) return true; // going right
        //findPath(x-1, y); //up
        // findPath(x, y-1); //left
        solution[x][y] = 0;
        return false;
    }


    private static void printPath() {
        System.out.println();
        for (int i = 0; i < N ; i++) {
            for (int j = 0; j <N ; j++) {
                System.out.print(solution[i][j] + " ");
            }
            System.out.println();
        }
    }

}
