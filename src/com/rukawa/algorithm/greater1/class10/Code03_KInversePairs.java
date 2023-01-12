package com.rukawa.algorithm.greater1.class10;

// 测试链接 : https://leetcode.com/problems/k-inverse-pairs-array/
public class Code03_KInversePairs {

	/**
	 * K个逆序对数组
	 * 给出两个整数 n 和 k，找出所有包含从 1 到 n 的数字，且恰好拥有 k 个逆序对的不同的数组的个数。
	 * 逆序对的定义如下：对于数组的第i个和第 j个元素，如果满i < j且 a[i] > a[j]，则其为一个逆序对；否则不是。
	 * 由于答案可能很大，只需要返回 答案 mod 109 + 7 的值。
	 */
	public static int kInversePairs(int n, int k) {
		if (n < 1 || k < 0) {
			return 0;
		}
		int[][] dp = new int[n + 1][k + 1];
		dp[0][0] = 1;
		int mod = 1000000007;
		for (int i = 1; i <= n; i++) {
			dp[i][0] = 1;
			for (int j = 1; j <= k; j++) {
				dp[i][j] = (dp[i][j - 1] + dp[i - 1][j]) % mod;
				if (j >= i) {
					dp[i][j] = (dp[i][j] - dp[i - 1][j - i] + mod) % mod;
				}
			}
		}
		return dp[n][k];
	}

	public static int kInversePairs2(int n, int k) {
		if (n < 1 || k < 0) {
			return 0;
		}
		int[][] dp = new int[n + 1][k + 1];
		dp[0][0] = 1;
		for (int i = 1; i <= n; i++) {
			dp[i][0] = 1;
			for (int j = 1; j <= k; j++) {
				dp[i][j] = dp[i - 1][j] + dp[i][j - 1];
				if (j >= i) {
					dp[i][j] -= dp[i - 1][j - i];
				}
			}
		}
		return dp[n][k];
	}

}
