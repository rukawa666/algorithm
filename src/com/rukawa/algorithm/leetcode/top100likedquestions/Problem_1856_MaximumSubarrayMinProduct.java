package com.rukawa.algorithm.leetcode.top100likedquestions;

import java.util.Stack;

/**
 * create by hqh on 2022/9/15
 */
public class Problem_1856_MaximumSubarrayMinProduct {
    /**
     * 子数组最小乘积的最大值
     * 一个数组的 最小乘积 定义为这个数组中 最小值 乘以 数组的 和 。
     * 比方说，数组 [3,2,5] （最小值是 2）的最小乘积为 2 * (3+2+5) = 2 * 10 = 20 。
     * 给你一个正整数数组 nums ，请你返回 nums 任意 非空子数组 的最小乘积 的 最大值 。由于答案可能很大，请你返回答案对  109 + 7 取余 的结果。
     * 请注意，最小乘积的最大值考虑的是取余操作 之前 的结果。题目保证最小乘积的最大值在 不取余 的情况下可以用 64 位有符号整数 保存。
     * 子数组 定义为一个数组的 连续 部分。
     *  
     *
     * 示例 1：
     * 输入：nums = [1,2,3,2]
     * 输出：14
     * 解释：最小乘积的最大值由子数组 [2,3,2] （最小值是 2）得到。
     * 2 * (2+3+2) = 2 * 7 = 14 。
     *
     * 示例 2：
     * 输入：nums = [2,3,3,1,2]
     * 输出：18
     * 解释：最小乘积的最大值由子数组 [3,3] （最小值是 3）得到。
     * 3 * (3+3) = 3 * 6 = 18 。
     *
     * 示例 3：
     * 输入：nums = [3,1,5,6,4,2]
     * 输出：60
     * 解释：最小乘积的最大值由子数组 [5,6,4] （最小值是 4）得到。
     * 4 * (5+6+4) = 4 * 15 = 60 。
     *  
     * 提示：
     * 1 <= nums.length <= 105
     * 1 <= nums[i] <= 107
     */
    public int maxSumMinProduct(int[] nums) {
        long mod = (long) 1e9 + 7;
        int n = nums.length;
        long[] sums = new long[n];
        sums[0] = nums[0];
        for (int i = 1; i < n; i++) {
            sums[i] = nums[i] + sums[i - 1];
        }
        Stack<Integer> stack = new Stack<>();
        long max = 0;
        for (int i = 0; i < nums.length; i++) {
            while (!stack.isEmpty() && nums[stack.peek()] >= nums[i]) {
                int pos = stack.pop();
                max = Math.max(max,
                        (stack.isEmpty() ? sums[i - 1] : (sums[i - 1] - sums[stack.peek()])) * nums[pos]);
            }
            stack.push(i);
        }

        while (!stack.isEmpty()) {
            int pos = stack.pop();
            max = Math.max(max,
                    (stack.isEmpty() ? sums[n - 1] : (sums[n - 1] - sums[stack.peek()])) * nums[pos]);
        }

        return (int) (max % mod);
    }
}
