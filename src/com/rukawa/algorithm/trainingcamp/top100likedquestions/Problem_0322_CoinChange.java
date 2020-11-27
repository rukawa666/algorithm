package com.rukawa.algorithm.trainingcamp.top100likedquestions;

/**
 * Created with Intellij IDEA
 *
 * @Author：SuperHai
 * @Date：2020-10-11 9:02
 * @Version：1.0
 */
public class Problem_0322_CoinChange {
    /**
     * 零钱兑换
     * 给定不同面额的硬币 coins 和一个总金额 amount。编写一个函数来计算可以凑成总金额所需的最少的硬币个数。
     * 如果没有任何一种硬币组合能组成总金额，返回 -1。
     * 你可以认为每种硬币的数量是无限的。
     *
     * 示例 1：
     * 输入：coins = [1, 2, 5], amount = 11
     * 输出：3
     * 解释：11 = 5 + 5 + 1
     *
     * 示例 2：
     * 输入：coins = [2], amount = 3
     * 输出：-1
     *
     * 示例 3：
     * 输入：coins = [1], amount = 0
     * 输出：0
     *
     * 示例 4：
     * 输入：coins = [1], amount = 1
     * 输出：1
     *
     * 示例 5：
     * 输入：coins = [1], amount = 2
     * 输出：2
     *
     * 提示：
     * 1 <= coins.length <= 12
     * 1 <= coins[i] <= 231 - 1
     * 0 <= amount <= 104
     */
    public int coinChange2(int[] coins, int amount) {
        if (coins == null || coins.length == 0 || amount < 0) {
            return -1;
        }
        int n = coins.length;
        // index 做行，目标做列
        int[][] dp = new int[n][amount + 1];
        // dp[i][0] = 0 -> 目标为0，不需要选硬币
        // dp[0][1...] = arr[0]的整数倍，有张数，倍数，其他格子为-1(表示无方案)
        for (int j = 1; j <= amount; j++) {
            if (j % coins[0] != 0) {
                dp[0][j] = -1;
            } else {
                dp[0][j] = j / coins[0];
            }
        }

        for (int i = 1; i < n; i++) {
            for (int j = 1; j <= amount; j++) {
                dp[i][j] = Integer.MAX_VALUE;
                if (dp[i - 1][j] != - 1) {
                    dp[i][j] = dp[i - 1][j];
                }
                if (j - coins[i] >= 0 && dp[i][j - coins[i]] != -1) {
                    dp[i][j] = Math.min(dp[i][j], dp[i][j - coins[i]] + 1);
                }
                if (dp[i][j] == Integer.MAX_VALUE) {
                    dp[i][j] = -1;
                }
            }
        }
        return dp[n - 1][amount];
    }
}
