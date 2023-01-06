package com.rukawa.algorithm.classify.type4;

/**
 * create by hqh on 2022/9/27
 */
public class Code04_SubArrayMaxSumFollowUp {
    /**
     * 返回一个数组中，选择的数字不能相邻的情况下
     * 最大子序列的累加和
     */
    public int maxSum(int[] nums) {
        /**
         * 子序列问题：从左往右的尝试模型
         * 可能性1：完全不用i位置的数 dp[i-1]
         * 可能性2：
         *      用i位置的数，只包含nums[i]
         *      用i位置的数 dp[i-2]+nums[i]
         * 两种可能性求最大值
         */
        if (nums == null || nums.length == 0) {
            return 0;
        }
        int n = nums.length;
        if (n == 1) {
            return nums[0];
        }
        if (n == 2) {
            return Math.max(nums[0], nums[1]);
        }
        int[] dp = new int[n];
        dp[0] = nums[0];
        dp[1] = Math.max(nums[0], nums[1]);
        for (int i = 1; i < n; i++) {
            dp[i] = Math.max(Math.max(nums[i], dp[i - 1]), dp[i - 2] + nums[i]);
        }
        return dp[n - 1];
    }
}
