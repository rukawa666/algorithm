package com.rukawa.algorithm.leetcode.highfrequency;

/**
 * Created with Intellij IDEA
 *
 * @Author：SuperHai
 * @Date：2020-09-21 8:39
 * @Version：1.0
 */
public class Problem_0188_BestTimeToBuyAndSellStockIV {
    /**
     * 买卖股票的最佳时机 IV
     * 给定一个整数数组 prices ，它的第 i 个元素 prices[i] 是一支给定的股票在第 i 天的价格。
     * 设计一个算法来计算你所能获取的最大利润。你最多可以完成 k 笔交易。
     * 注意：你不能同时参与多笔交易（你必须在再次购买前出售掉之前的股票）。
     *
     * 示例 1：
     * 输入：k = 2, prices = [2,4,1]
     * 输出：2
     * 解释：在第 1 天 (股票价格 = 2) 的时候买入，在第 2 天 (股票价格 = 4) 的时候卖出，这笔交易所能获得利润 = 4-2 = 2 。
     *
     * 示例 2：
     * 输入：k = 2, prices = [3,2,6,5,0,3]
     * 输出：7
     * 解释：在第 2 天 (股票价格 = 2) 的时候买入，在第 3 天 (股票价格 = 6) 的时候卖出, 这笔交易所能获得利润 = 6-2 = 4 。
     *      随后，在第 5 天 (股票价格 = 0) 的时候买入，在第 6 天 (股票价格 = 3) 的时候卖出, 这笔交易所能获得利润 = 3-0 = 3 。
     *
     * 提示：
     * 0 <= k <= 100
     * 0 <= prices.length <= 1000
     * 0 <= prices[i] <= 1000
     */
    public int maxProfit(int k, int[] prices) {
        /**
         * 思路：
         *  1. 如果交易次数>(n>>1) ，则等同于交易次数不限, {1,3,1,3,1,3}
         *  2. 从左到后的尝试模型，0～n-1的正好不超过k的交易次数，达到的最大利润是多少
         *  可以建立一张dp标
         *  表
         */
        if (prices == null || prices.length == 0 || k < 1) {
            return 0;
        }
        int n = prices.length;
        if (k >= (n >> 1)) {
            return maxProfit(prices);
        }

        int[][] dp = new int[n][k + 1];
        // 第一列，做0次交易，每个位置都是0
        // dp[...][0] = 0;
        // 第一行，在0位置，分别做0次交易，做一次交易。。。都是当天买当天卖  都是0
        // dp[0][...] = 0;

        /**
         * 普遍位置分析，dp[8][3]
         * 1. 8位置不参与交易，0～7范围上怎么做不超过3次交易最好，等于dp[7][3]
         * 2. 8位置参与交易，只能是最后一次的卖出时机,之前占了一次交易
         *    a. 8位置买入8位置卖出  dp[8][2] + price[8]-price[8]
         *    b. 7位置买入8位置卖出  dp[7][2] + price[8]-price[7]
         *    c. 6位置买入8位置卖出  dp[6][2] + price[8]-price[6]
         *    ...
         *    d. 0位置买入8位置卖出  dp[0][2] + price[8]-price[0]
         * 3. 有枚举行为，需要做优化
         *    a. 8位置买入8位置卖出  dp[8][2] + price[8]-price[8]
         *    b. 7位置买入8位置卖出  dp[7][2] + price[8]-price[7]
         *    c. 6位置买入8位置卖出  dp[6][2] + price[8]-price[6]
         *    ...
         *    d. 0位置买入8位置卖出  dp[0][2] + price[8]-price[0]
         *    可以先把price[8]摘出来，求出dp[i][2]-price[i]的最大值，然后在加price[8]
         * 4.dp[8][3] = dp[7][3] - price[7] + price[8]
         */
        for (int j = 1; j <= k; j++) {
            int p1 = dp[0][j];
            int best = Math.max(dp[1][j - 1] - prices[1], dp[0][j - 1] - prices[0]);
            dp[1][j] = Math.max(p1, best + prices[1]);
            for (int i = 2; i < n; i++) {
                int cur = dp[i - 1][j];
                int a = dp[i][j - 1] - prices[i];
                best = Math.max(best, a);
                dp[i][j] = Math.max(cur, best + prices[i]);
            }
        }
        return dp[n - 1][k];
    }

    public int maxProfit(int[] prices) {
        if (prices == null || prices.length == 0) {
            return 0;
        }

        int res = 0;
        for (int i = 1; i < prices.length; i++) {
            res += Math.max(prices[i] - prices[i - 1], 0);
        }
        return res;
    }
}
