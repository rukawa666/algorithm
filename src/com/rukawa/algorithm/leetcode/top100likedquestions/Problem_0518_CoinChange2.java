package com.rukawa.algorithm.leetcode.top100likedquestions;

/**
 * create by hqh on 2022/9/8
 */
public class Problem_0518_CoinChange2 {
    /**
     * 零钱兑换 II
     * 给你一个整数数组 coins 表示不同面额的硬币，另给一个整数 amount 表示总金额。
     * 请你计算并返回可以凑成总金额的硬币组合数。如果任何硬币组合都无法凑出总金额，返回 0 。
     * 假设每一种面额的硬币有无限个。 
     * 题目数据保证结果符合 32 位带符号整数。
     *
     * 示例 1：
     * 输入：amount = 5, coins = [1, 2, 5]
     * 输出：4
     * 解释：有四种方式可以凑成总金额：
     * 5=5
     * 5=2+2+1
     * 5=2+1+1+1
     * 5=1+1+1+1+1
     *
     * 示例 2：
     * 输入：amount = 3, coins = [2]
     * 输出：0
     * 解释：只用面额 2 的硬币不能凑成总金额 3 。
     *
     * 示例 3：
     * 输入：amount = 10, coins = [10]
     * 输出：1
     *  
     *
     * 提示：
     * 1 <= coins.length <= 300
     * 1 <= coins[i] <= 5000
     * coins 中的所有值 互不相同
     * 0 <= amount <= 5000
     */

    public int change1(int amount, int[] coins) {
        if (coins == null || coins.length == 0 || amount < 0) {
            return 0;
        }
        int n = coins.length;
        int[][] dp = new int[n + 1][amount + 1];
        dp[n][0] = 1;
        // dp[n][...] = 0
        for (int i = n - 1; i >= 0; i--) {
            for (int j = 0; j <= amount; j++) {
                int ways = 0;
                for (int zhang = 0; zhang * coins[i] <= j; zhang++) {
                    ways += dp[i + 1][j - (zhang * coins[i])];
                }
                dp[i][j] = ways;
            }
        }
        return dp[0][amount];
    }

    public int change2(int amount, int[] coins) {
        int n = coins.length;
        int[][] dp = new int[n + 1][amount + 1];
        dp[n][0] = 1;
        // dp[n][...] = 0
        for (int i = n - 1; i >= 0; i--) {
            for (int j = 0; j <= amount; j++) {

                /**
                 * 分析情况：当前面值是arr[i]=3
                 * 如果i行14列的位置，得到的答案需要依赖以下位置
                 * [i+1,14] [i+1,11] [i+1,8] [i+1,5] [i+1,2]
                 *
                 * 如果i行11列的位置，得到的答案需要依赖以下位置
                 * [i+1,11] [i+1,8] [i+1,5] [i+1,2]
                 *
                 * 所以i行14列的位置依赖位置如下
                 * [i+1,14] [i,11]
                 */

                dp[i][j] = dp[i + 1][j];
                if (j - coins[i] >= 0) {
                    dp[i][j] += dp[i][j - coins[i]];
                }
            }
        }
        return dp[0][amount];
    }
}
