package com.rukawa.algorithm.base.class11;

/**
 * Created with Intellij IDEA
 *
 * @Author：SuperHai
 * @Date：2020-07-27 8:13
 * @Version：1.0
 */
public class Code08_CardsInLine {

    /**
     * 从范围上尝试的模型
     * 给定一个整型数组arr，代表数值不同的纸牌排成一条线，
     * 玩家A和玩家B依次拿走没有纸牌，规定玩家A先拿，玩家B后拿，
     * 但是每个玩家每次只能拿走最左或者最右的纸牌，
     * 玩家A和玩家B都绝顶聪明。请返回最后获胜者的分数
     * @param arr
     * @return
     */
    public static int win01(int[] arr) {
        if (arr == null || arr.length == 0) {
            return 0;
        }
        return Math.max(
                f(arr, 0, arr.length - 1),  // 先手
                s(arr, 0, arr.length - 1)   // 后手
        );
    }

    // 先手
    public static int f(int[] arr, int L, int R) {
        if (L == R) {   // 先手，只剩下一张牌，直接拿走
            return arr[L];
        }
        // 先手你能在两者之间选择最大
        return Math.max(
                arr[L] + s(arr, L + 1, R),  // 先手拿L，在[L+1,R]中后手选择
                arr[R] + s(arr, L, R - 1)   // 先手拿R，在[L,R-1]中后手选择
        );
    }

    // 后手
    public static int s(int[] arr, int L, int R) {
        if (L == R) {
            return 0;
        }
        // 后手你只能选择两者之间最小的
        return Math.min(
                    f(arr, L + 1, R),  // 对手先挑选i
                    f(arr, L, R - 1)    // 对手先挑选j
        );
    }

    public static int win02(int[] arr) {
        if (arr == null || arr.length == 0) {
            return 0;
        }
        int N = arr.length;
        int[][] fdp = new int[N][N];
        int[][] sdp = new int[N][N];

        for (int i = 0; i < N; i++) {
            fdp[i][i] = arr[i];
        }

        // sdp[i][i] = 0;
        for (int i = 1; i < N; i++) {
            int L = 0;
            int R = i;
            while (L < N && R < N) {
                fdp[L][R] = Math.max(
                        arr[L] + sdp[L + 1][R],
                        arr[R] + sdp[L][R - 1]);

                sdp[L][R] = Math.min(
                        fdp[L + 1][R],
                        fdp[L][R - 1]);
                L++;
                R++;
            }

        }
        return Math.max(fdp[0][N-1], sdp[0][N-1]);


    }

    public static void main(String[] args) {
        int[] arr = { 4,7,9,5,19,29,80,4 };
        // A 4 9 19 80  93+19=112
        System.out.println(f(arr, 0, 7));
        // B 7 5 29 4   16+29=45
        System.out.println(s(arr, 0, 7));

        System.out.println(win01(arr));
        System.out.println(win02(arr));

    }

}
