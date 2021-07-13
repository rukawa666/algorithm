package com.rukawa.algorithm.types.guessTheSolution;

/**
 * @className: Code02_SubSquenceMaxModM
 * @description: TODO 类描述
 * @author: 鎏川疯
 * @date: 2021/7/12 0012 23:25
 **/
public class Code02_SubSequenceMaxModM {
    /**
     * 给定一个非负数组arr，和一个正数m。返回arr的所有子序列中累加和%m之后的最大值
     */
    /**
     * 根据数据量猜解法：
     *  1、假设arr中的数字的值都不大(100以内)，长度也不大(1000~2000)，但是m很大(10^12)，可以使用最经典的背包解法
     *  dp含义：自由使用0~i的这些数字，能不能把某一个累加和j搞出来
     *  情况1：不要i的数字，去判断0~i-1之间的数字随便选择，能不能搞出来累加和j，如果它能，自己也能
     *  情况2：要i的数字，去判断0~i-1之间的数字随便选择，把i位置的数字去掉，看不能搞出j-arr[i]的累加和，如果能，自己也能
     */

    // 适用于arr数组的数规模比较小，且长度比较小的情况，但是m特别大的情况
    // 复杂度O(10^3 * 10^5) 能在10^8内完成
    public static int max1(int[] arr, int m) {
        int sum = 0;
        for (int num : arr) {
            sum += num;
        }
        int N = arr.length;
        // 自由使用0~i的这些数字，能不能把某一个累加和j搞出来
        boolean[][] dp = new boolean[N][sum + 1];
        for (int i = 0; i < N; i++) {
            dp[i][0] = true;
        }
        dp[0][arr[0]] = true;
        for (int i = 1; i < N; i++) {
            for (int j = 0; j <= sum; j++) {
                dp[i][j] = dp[i - 1][j];
                if (j - arr[i] >= 0) {
                    dp[i][j] |= dp[i - 1][j - arr[i]];
                }
            }
        }
        int max = 0;
        for (int j = 0; j <= sum; j++) {
            if (dp[N - 1][j]) {
                max = Math.max(max, j % m);
            }
        }
        return max;
    }
}
