package Matrix;

import java.util.ArrayList;
import java.util.List;

public class FindPath {
    public static final int N = 3;
    static String[][] arr = new String[N][N];
    static int destx = 1;
    static int desty = 2;
    public static void init() {
        for (int i = 0; i < N ; i++) {
            for (int j = 0; j <N ; j++) {
                arr[i][j] = "("+i+","+j+")";
            }

        }
    }

    public static void print() {
        for (int i = 0; i < N ; i++) {
            for (int j = 0; j <N ; j++) {
                System.out.print(arr[i][j] + " ");
            }
            System.out.println();
        }
    }
/*  (0,0) (0,1) (0,2)
    (1,0) (1,1) (1,2)
    (2,0) (2,1) (2,2)*/
//
//                                           F(0,0,"")
//                         F(1,0,"00-")                     F(0,1,"00-")
//              F(2,0,"00-10")       F(1,1,"00-10")
//            "00-10-20 -remcols at this row"


    private static void findPaths(int x, int y, String paths) {
        if (x == destx){
            for (int i = y; i < N ; i++) {
                paths = paths + "-" + arr[x][i];
            }
            System.out.println(paths);
            return;
        }
        if (y == desty){
            for (int i = x; i < N ; i++) {
                paths = paths + "-" + arr[i][y];
            }
            System.out.println(paths);
            return;
        }
        paths = paths + "-" + arr[x][y];
        findPaths(x+1, y, paths);
        findPaths(x, y+1, paths);
    }

    public static void main(String[] args) {
        init();
        print();
        findPaths(0,0,"");

    }



}
