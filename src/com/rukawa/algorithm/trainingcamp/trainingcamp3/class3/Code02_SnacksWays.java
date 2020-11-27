package com.rukawa.algorithm.trainingcamp.trainingcamp3.class3;

/**
 * Created with Intellij IDEA
 *
 * @Author：SuperHai
 * @Date：2020-08-24 22:45
 * @Version：1.0
 */
public class Code02_SnacksWays {
    /**
     * 背包容量为W
     * 一共有n袋零食，第i袋零食的体积为V[i]
     * 总体积不超过背包容量的情况下，一共有多少种零食放法？总体积为0也算一种放法
     */

    // 从左往右的经典模型
    // 还剩的容量是rest，arr[index...]自由选择，
    // 返回选择方案
    // index ： 0～N
    // rest : 0~w
    public static int ways1(int[] arr, int w) {
        return process(arr, 0, w);
    }

    public static int process(int[] arr, int index, int rest) {
        if (rest < 0) { // 没有容量了
            return -1;  // -1 无方案的意思
        }
        if (index == arr.length) {   // 无零食可选
            return 1;
        }
        // rest >=0
        // 有零食index
        // index号零食，要 or 不要
        // index, rest
        // (index+1, rest)
        // (index+1, rest-arr[i])
        int next1 = process(arr, index + 1, rest);
        int next2 = process(arr, index + 1, rest - arr[index]);
        return next1 + (next2 == -1 ? 0 : next2);
    }

    public static int ways2(int[] arr, int w) {
        int N = arr.length;
        // 0~i号零食自由选择，要求体积必须严格要求累加成j
        int[][] dp = new int[N + 1][w + 1];
        for (int j = 0; j <= w; j++) {
            dp[N][j] = 1;
        }

        for (int i = N - 1; i >= 0; i--) {
            for (int j = 0; j <= w; j++) {
                dp[i][j] = dp[i + 1][j]
                        + ((j - arr[i] >= 0)
                        ? dp[i + 1][j - arr[i]]
                        : 0);
            }
        }
        return dp[0][w];
    }

    public static int ways3(int[] arr, int w) {
        int N = arr.length;
        int[][] dp = new int[N][w + 1];
        for (int i = 0; i < N; i++) {
            dp[i][0] = 1;
        }

        if (arr[0] <= w) {
            dp[0][arr[0]] = 1;
        }

        for (int i = 1; i < N; i++) {
            for (int j = 1; j <= w; j++) {
                dp[i][j] = dp[i - 1][j]
                        + ((j - arr[i] >= 0)
                        ? dp[i - 1][j - arr[i]]
                        : 0);
            }
        }
        int ans = 0;
        for (int j = 0; j <= w; j++) {
            ans += dp[N - 1][j];
        }
        return ans;
    }

    public static void main(String[] args) {
        int[] arr = { 4, 3, 2, 9 };
        int w = 8;
        System.out.println(ways1(arr, w));
        System.out.println(ways2(arr, w));
        System.out.println(ways3(arr, w));

    }
}
