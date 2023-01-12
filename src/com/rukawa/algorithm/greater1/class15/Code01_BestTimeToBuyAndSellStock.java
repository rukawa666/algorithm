package com.rukawa.algorithm.greater1.class15;

// leetcode 121
public class Code01_BestTimeToBuyAndSellStock {

	/**
	 * 买卖股票的最佳时机
	 */
	public static int maxProfit(int[] prices) {
		if (prices == null || prices.length == 0) {
			return 0;
		}
		// 必须在0时刻卖掉，[0] - [0]
		int ans = 0;
		// arr[0...0]
		int min = prices[0];
		for (int i = 1; i < prices.length; i++) {
			min = Math.min(min, prices[i]);
			ans = Math.max(ans, prices[i] - min);
		}
		return ans;
	}

}
