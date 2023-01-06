package com.rukawa.algorithm.leetcode.highfrequency;

/**
 * Created with Intellij IDEA
 *
 * @Author：SuperHai
 * @Date：2020-09-21 8:39
 * @Version：1.0
 */
public class Problem_0309_BestTimeToBuyAndSellStockV {
    /**
     * 最佳买卖股票时机含冷冻期
     * 给定一个整数数组prices，其中第  prices[i] 表示第 i 天的股票价格 。​
     * 设计一个算法计算出最大利润。在满足以下约束条件下，你可以尽可能地完成更多的交易（多次买卖一支股票）:
     * 卖出股票后，你无法在第二天买入股票 (即冷冻期为 1 天)。
     * 注意：你不能同时参与多笔交易（你必须在再次购买前出售掉之前的股票）。
     *
     * 示例 1:
     * 输入: prices = [1,2,3,0,2]
     * 输出: 3
     * 解释: 对应的交易状态为: [买入, 卖出, 冷冻期, 买入, 卖出]
     *
     * 示例 2:
     * 输入: prices = [1]
     * 输出: 0
     *
     * 提示：
     * 1 <= prices.length <= 5000
     * 0 <= prices[i] <= 1000
     */
    /**
     * 最优尝试如下：
     * buy[i]：在0～i范围上，最后一次操作是buy操作
     * 最后一次操作有可能发生在i位置，也可能发生在i之前
     * buy[i]的含义是：max{所有可能性[之前交易获得的最大收益-最后buy动作的收购价格]}
     * 比如：arr[0...i]假设是[1,3,4,6,2,7,1...i...]
     * 其中一种可能性：
     *   假设最后一次buy动作动作发生在2这个数的时候，那么之前的交易只能在[1,3,4]上面发生，因为6要冷冻
     *   此时的最大收益是多少？4-1==3。那么之前获得的最大收益 - 最后buy动作的收购价格=3-2==1
     * 另外一种可能性
     *   比如最后一次buy动作发生在最后的1这个数的时候，那么之前的交易只能在[1,3,4,6,2]上发生，因为7要冷冻
     *   此时的最大收益是多少？6-1==5。那么之前获得的最大收益 - 最后buy动作的收购价格=5-1==4
     *   除了上面两种可能性，还有很多可能性，可以假设每个数字都是最后buy动作的收购价格
     *   所有可能性中，(之前交易的最大收益-最后buy动作的收购价格)的最大值，就是buy[i]的含义
     *
     * sell[i]：0～i范围上，最后一次操作是sell动作，这最后一次操作可能发生在i位置，也可能发生在i之前
     * sell[i]值的含义：在0～i范围上，最后一次动作是sell的情况下，最好的收益
     *
     * 于是通过分析得到，能得到以下方程
     * buy[i] = Math.max(buy[i-1], sell[i-2]-price[i])
     * 如果i位置没有发生buy行为，说明有没有i位置都一样，那么buy[i]=buy[i-1]
     * 如果i位置发生了了buy行为，那么buy[i] = sell[i-2] - price[i]
     * 如果想在i位置买的话，必须保证之前的交易发生在0～i-2上
     * 如果i-1位置要参与交易，i位置就要被冷冻
     *
     * sell[i] = Math.max(sell[i - 1], buy[i - 1] + price[i])
     * 如果i位置没有发生sell，则直接依赖sell[i-1]
     * 如果i位置要参与sell，在之前找到一个{尽可能的最好收益 - 最后一次的收购价格尽可能最低}
     * 而这正好是buy[i-1]的含义
     */
    public int maxProfit(int[] prices) {
        if (prices == null || prices.length < 2) {
            return 0;
        }
        int n = prices.length;
        int[] buy = new int[n];
        int[] sell = new int[n];
        buy[1] = Math.max(-prices[0], -prices[1]);
        sell[1] = Math.max(0, prices[1] - prices[0]);
        for (int i = 2; i < n; i++) {
            buy[i] = Math.max(buy[i - 1], sell[i - 2] - prices[i]);
            sell[i] = Math.max(sell[i - 1], buy[i - 1] + prices[i]);
        }
        return sell[n - 1];
    }

    // 空间压缩
    public int maxProfit1(int[] prices) {
        if (prices == null || prices.length < 2) {
            return 0;
        }
        int n = prices.length;
        int buy1 =  Math.max(-prices[0], -prices[1]);
        int sell1 = Math.max(0, prices[1] - prices[0]);
        int sell2 = 0;
        for (int i = 2; i < n; i++) {
            int tmp = sell1;
            sell1 = Math.max(sell1, buy1 + prices[i]);
            buy1 = Math.max(buy1, sell2 - prices[i]);
            sell2 = tmp;
        }
        return sell1;
    }
}
