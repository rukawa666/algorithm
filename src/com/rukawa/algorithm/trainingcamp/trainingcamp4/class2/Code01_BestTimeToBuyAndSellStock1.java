package com.rukawa.algorithm.trainingcamp.trainingcamp4.class2;

/**
 * Created with Intellij IDEA
 *
 * @Author：SuperHai
 * @Date：2020-10-10 7:36
 * @Version：1.0
 */
public class Code01_BestTimeToBuyAndSellStock1 {
    /**
     * 给定一个数组arr，从左到右表示昨天从早到晚股票的价格。作为一个事后诸葛亮，你想知道
     * 如果只做一次交易，且每次交易只买卖一股，返回能挣到的最大钱数
     */

    public int maxProfit(int[] prices) {
        if (prices == null || prices.length == 0) {
            return 0;
        }
        int min = prices[0];
        int max = 0;
        // 允许在i位置买入，在i位置卖出
        for (int i = 0; i < prices.length; i++) {
            min = Math.min(min, prices[i]);
            max = Math.max(max, prices[i] - min);
        }
        return max;
    }
}
