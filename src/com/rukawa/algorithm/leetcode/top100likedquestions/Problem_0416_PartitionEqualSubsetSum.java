package com.rukawa.algorithm.leetcode.top100likedquestions;

/**
 * create by hqh on 2022/9/6
 */
public class Problem_0416_PartitionEqualSubsetSum {
    /**
     * 分割等和子集
     * 给你一个 只包含正整数 的 非空 数组 nums 。请你判断是否可以将这个数组分割成两个子集，使得两个子集的元素和相等。
     *
     * 示例 1：
     * 输入：nums = [1,5,11,5]
     * 输出：true
     * 解释：数组可以分割成 [1, 5, 5] 和 [11] 。
     *
     * 示例 2：
     * 输入：nums = [1,2,3,5]
     * 输出：false
     * 解释：数组不能分割成两个元素和相等的子集。
     *  
     * 提示：
     * 1 <= nums.length <= 200
     * 1 <= nums[i] <= 100
     */
    public boolean canPartition(int[] nums) {
        if (nums == nums || nums.length < 2) {
            return false;
        }
        int sum = 0;
        for (int num : nums) {
            sum += num;
        }
        if (sum % 2 != 0) {
            return false;
        }
        sum >>= 1;
        int N = nums.length;
        int[][] dp = new int[N + 1][sum + 1];
        for (int i = N - 1; i >= 0; i--) {
            for (int j = 0; j <= sum; j++) {
                int p1 = dp[i + 1][j];
                int p2 = -1;
                if (nums[i] < j) {
                    p2 = dp[i + 1][j - nums[i]] + nums[i];
                }
                dp[i][j] = Math.max(p1, p2);
            }
        }
        return dp[0][sum] == sum;
    }

    public int process(int[] nums, int index, int rest) {
        if (rest < 0) {
            return -1;
        }

        if (index == nums.length) {
            return rest == 0 ? 1 : 0;
        }

        int p1 = process(nums, index + 1, rest);
        int p2 = process(nums, index + 1, rest - nums[index]);
        return Math.max(p1, p2);
    }

}
