package com.rukawa.algorithm.base.class12;

/**
 * Created with Intellij IDEA
 *
 * @Author：SuperHai
 * @Date：2020-07-29 7:48
 * @Version：1.0
 */
public class Code04_CardsInLine {

    /**
     * 从范围上尝试的模型
     * 给定一个整型数组arr，代表数值不同的纸牌排成一条线，
     * 玩家A和玩家B依次拿走没有纸牌，规定玩家A先拿，玩家B后拿，
     * 但是每个玩家每次只能拿走最左或者最右的纸牌，
     * 玩家A和玩家B都绝顶聪明。请返回最后获胜者的分数
     * @param arr
     * @return
     */
    public static int win1(int[] arr) {
        if (arr == null || arr.length == 0) {
            return 0;
        }
        return Math.max(f(arr, 0, arr.length - 1), s(arr, 0, arr.length - 1));
    }

    public static int f(int[] arr, int L, int R) {
        if (L == R) {
            return arr[L];
        }
        return Math.max(arr[L] + s(arr, L + 1, R), arr[R] + s(arr, L, R - 1));
    }

    public static int s(int[] arr, int L, int R) {
        if (L == R) {
            return 0;
        }
        return Math.min(f(arr, L + 1, R), f(arr, L, R - 1));
    }

    public static int windp(int[] arr) {
        if (arr == null || arr.length == 0) {
            return 0;
        }
        int N = arr.length;
        int[][] f = new int[N][N];
        int[][] s = new int[N][N];
        for (int i = 0; i < N; i++) {
            f[i][i] = arr[i];
        }
        // 0,0 右下方移动
        // 0,1
        // 0,2
        // 0,N-1
        for (int col = 1; col < N; col++) {
            // 对角线出发位置(0,col)
            int L = 0;
            int R = col;
            while (L < N && R < N) {
                f[L][R] = Math.max(arr[L] + s[L + 1][R], arr[R] + s[L][R - 1]);
                s[L][R] = Math.min(f[L + 1][R], f[L][R - 1]);
                L++;
                R++;
            }
        }
        return Math.max(f[0][N - 1], s[0][N - 1]);
    }

    public static int winDp2(int[] arr) {
        if (arr == null || arr.length == 0) {
            return 0;
        }
        int N = arr.length;
        int[][] f = new int[N][N];
        int[][] s = new int[N][N];

        for (int R = 0; R < N; R++) {
            f[R][R] = arr[R];
            for (int L = R - 1; L >= 0; L--) {
                f[L][R] = Math.max(arr[L] + s[L + 1][R], arr[R] + s[L][R - 1]);
                s[L][R] = Math.min(f[L + 1][R], f[L][R - 1]);
            }
        }
        return Math.max(f[0][N - 1], s[0][N - 1]);
    }

    public static void main(String[] args) {
        int[] arr = { 5, 7, 4, 5, 8, 1, 6, 0, 3, 4, 6, 1, 7 };
        System.out.println(win1(arr));
        System.out.println(windp(arr));
        System.out.println(winDp2(arr));

    }

}
