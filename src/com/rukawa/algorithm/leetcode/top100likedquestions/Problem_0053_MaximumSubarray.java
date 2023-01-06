package com.rukawa.algorithm.leetcode.top100likedquestions;

/**
 * Created with Intellij IDEA
 *
 * @Author：SuperHai
 * @Date：2020-09-20 8:34
 * @Version：1.0
 */
public class Problem_0053_MaximumSubarray {
    /**
     * 最大子数组和
     * 给你一个整数数组 nums ，请你找出一个具有最大和的连续子数组（子数组最少包含一个元素），返回其最大和。
     * 子数组 是数组中的一个连续部分。
     *
     * 示例 1：
     * 输入：nums = [-2,1,-3,4,-1,2,1,-5,4]
     * 输出：6
     * 解释：连续子数组 [4,-1,2,1] 的和最大，为 6 。
     *
     * 示例 2：
     * 输入：nums = [1]
     * 输出：1
     *
     * 示例 3：
     * 输入：nums = [5,4,-1,7,8]
     * 输出：23
     *
     * 提示：
     * 1 <= nums.length <= 105
     * -104 <= nums[i] <= 104
     * 进阶：如果你已经实现复杂度为 O(n) 的解法，尝试使用更为精妙的 分治法 求解。
     */
    public int maxSubArray(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        int pre = nums[0];
        int max = nums[0];
        for (int i = 1; i < nums.length; i++) {
            int curMax = Math.max(pre + nums[i], nums[i]);
            pre = curMax;
            max = Math.max(max, curMax);
        }
        return max;
    }

    public int maxSubArray1(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        int max = Integer.MIN_VALUE;
        int curMaxSum = 0;
        for (int i = 0; i < nums.length; i++) {
            curMaxSum += nums[i];
            max = Math.max(max, curMaxSum);
            curMaxSum = curMaxSum < 0 ? 0 : curMaxSum;
        }
        return max;
    }
}
