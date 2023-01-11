package com.rukawa.algorithm.base.class31_quardrilateral;

/**
 * create by hqh on 2023/1/11
 */
public class Code03_StoneMerge {
    /**
     * 摆放着n堆石子。现要将石子有序地合并成一堆
     * 规定每次只能选相邻的2堆石子合并成新的一堆
     * 并将新的一堆石子数记为该次合并的得分
     * 求出将n堆石子合并成一堆的最小得分(或最大得分)合并方案
     */

    // 暴力递归
    public static int min1(int[] arr) {
        if (arr == null || arr.length == 0) {
            return 0;
        }
        int n = arr.length;
        int[] sum = sum(arr);
        return process1(sum, 0, n - 1);
    }

    public static int process1(int[] sum, int L, int R) {
        if (L == R) {
            return 0;
        }
        int next = Integer.MAX_VALUE;
        for (int leftEnd = L; leftEnd < R; leftEnd++) {
            // sum[L, leftEnd] sum[leftEnd, R] 左部分的累加和 + 右部分的累加和
            next = Math.min(next, process1(sum, L , leftEnd) + process1(sum, leftEnd + 1, R));
        }
        // 左部分的累加和 + 右部分的累加和 + 左右两部分合并后的累加和
        return next + w(sum, L, R);
    }

    private static int w(int[] sum, int l, int r) {
        return sum[r + 1] - sum[l];
    }

    // 累加和
    private static int[] sum(int[] arr) {
        int n = arr.length;
        int[] sum = new int[n + 1];
        sum[0] = arr[0];
        for (int i = 0; i < n; i++) {
            sum[i + 1] = sum[i] + arr[i];
        }
        return sum;
    }

    // 范围上的尝试模型
    // O(N^3)
    public static int min2(int[] arr) {
        if (arr == null || arr.length == 0) {
            return 0;
        }
        int n = arr.length;
        int[] sum = sum(arr);
        int[][] dp = new int[n][n];
        // dp[i][i] = 0;
        for (int L = n - 2; L >= 0; L--) {
            for (int R = L + 1; R < n; R++) {
                int next = Integer.MAX_VALUE;
                // dp[L, leftEnd] + dp[leftEnd, R] + sum[L, R]
                for (int leftEnd = L; leftEnd < R; leftEnd++) {
                    next = Math.min(next, dp[L][leftEnd] + dp[leftEnd + 1][R]);
                }
                dp[L][R] = next + w(sum, L, R);
            }
        }
        return dp[0][n - 1];
    }

    // dp优化
    // O(N^2)
    public static int min3(int[] arr) {
        if (arr == null || arr.length == 0) {
            return 0;
        }
        int n = arr.length;
        int[] sum = sum(arr);
        int[][] dp = new int[n][n];
        // 最优划分点
        int[][] best = new int[n][n];
        // dp[i][i] = 0
        // best[i][i] 只有一个点，不存在最优划分点
        // 所以有最优划分点，必须最少要有两堆石子
        for (int i = 0; i < n - 1; i++) {
            best[i][i + 1] = i;
            dp[i][i + 1] = w(sum, i, i + 1);
        }
        // 对角线不用填写  倒数第二条对角线上面已经填写过了
        // 只需要从第第三条对角线位置开始填写
        for (int L = n - 3; L >= 0; L--) {
            // R == L 倒数第一条对角线不用管理
            // R == L + 1 倒数第二条对角线已经填写
            for (int R = L + 2; R < n; R++) {
                int next = Integer.MAX_VALUE;
                int choose = -1;
                // 下限：best[L][R - 1]  上限：best[L + 1][R]
                for (int leftEnd = best[L][R - 1]; leftEnd <= best[L + 1][R]; leftEnd++) {
                    int cur = dp[L][leftEnd] + dp[leftEnd + 1][R];
                    if (cur < next) {
                        next = cur;
                        choose = leftEnd;
                    }
                }
                best[L][R] = choose;
                dp[L][R] = next + w(sum, L, R);
            }
        }
        return dp[0][n - 1];
    }

    public static int[] randomArray(int len, int maxValue) {
        int[] arr = new int[len];
        for (int i = 0; i < len; i++) {
            arr[i] = (int) (Math.random() * maxValue);
        }
        return arr;
    }

    public static void main(String[] args) {
        int N = 15;
        int maxValue = 100;
        int testTime = 1000;
        System.out.println("测试开始");
        for (int i = 0; i < testTime; i++) {
            int len = (int) (Math.random() * N);
            int[] arr = randomArray(len, maxValue);
            int ans1 = min1(arr);
            int ans2 = min2(arr);
            int ans3 = min3(arr);
            if (ans1 != ans2 || ans1 != ans3) {
                System.out.println("Oops!");
            }
        }
        System.out.println("测试结束");
    }
}
