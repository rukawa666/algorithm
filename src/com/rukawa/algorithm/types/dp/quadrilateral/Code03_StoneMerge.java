package com.rukawa.algorithm.types.dp.quadrilateral;

/**
 * @className: Code03_StoneMerge
 * @description: TODO 类描述
 * @author: 鎏川疯
 * @date: 2021/7/19 0019 14:19
 **/
public class Code03_StoneMerge {
    /**
     * 摆放着n堆石子。现要将石子有次序的合并成一堆
     * 规定每次只能选择相邻的2堆石子合并成新的一堆，并将新的一堆石子数即为该次合并的得分
     * 求出将n堆石子合并成一堆的最小得分(或最大得分)合并方案
     *
     * 思路：
     *   定义dp[L][R]：范围上的尝试模型，arr[L...R]最优划分的情况下最小代价
     *   L>R位置不用填写，0~0位置已经是一块了，不需要合并，代价是0，对角线全是0
     *   次对角线位置，两个位置的累加和
     *   dp[1][3]：代表1~3范围上怎么合并最省，先把[1,1]合并，再把[2,3]合并+1~3的累加和
     *                                       [1,2]合并，再把[3,3]合并+1~3的累加和
     *                                       得出最小值
     */

    // O(N^3)
    public static int min2(int[] arr) {
        if (arr == null || arr.length < 2) {
            return 0;
        }
        int N = arr.length;
        int[] s = sum(arr);
        int[][] dp = new int[N][N];
        for (int L = N - 2; L >= 0; L--) {
            // 对象线的位置不需要填，从下一个位置开始
            for (int R = L + 1; R < N; R++) {
                int next = Integer.MAX_VALUE;
                // 尝试dp[L...leftEnd]作为左部分，dp[leftEnd...R]作为右部分，最终加sum[L...R]的累加和
                // 尝试所有的枚举可能性
                for (int leftEnd = L; leftEnd < R; leftEnd++) {
                    next = Math.min(next, dp[L][leftEnd] + dp[leftEnd + 1][R]);
                }
                dp[L][R] = next + w(s, L, R);
            }
        }
        return dp[0][N - 1];
    }

    // 0~i位置的累加和函数
    public static int[] sum(int[] arr) {
        int N = arr.length;
        int[] sum = new int[N + 1];
        sum[0] = 0;
        for (int i = 0; i < N; i++) {
            sum[i + 1] = sum[i] + arr[i];
        }
        return sum;
    }

    // 求原来的数组arr中，arr[L...R]的累加和
    // arr = {5, 3, 1, 3}
    //        0  1  2  3
    // sum = {0, 5, 8, 9, 12}
    //        0  1  2  3  4
    // 0~2 -> sum[3] - sum[0]
    // 1~3 -> sum[4] - sum[1]
    public static int w(int[] sum, int L, int R) {
        return sum[R + 1] - sum[L];
    }
}
