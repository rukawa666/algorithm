package com.rukawa.algorithm.trainingcamp.top100likedquestions;

/**
 * Created with Intellij IDEA
 *
 * @Author：SuperHai
 * @Date：2020-09-19 11:20
 * @Version：1.0
 */
public class Problem_0152_MaximumProductSubarray {
    /**
     * 乘积最大子数组
     * 给你一个整数数组 nums ，请你找出数组中乘积最大的连续子数组（该子数组中至少包含一个数字），并返回该子数组所对应的乘积。
     *
     * 示例 1:
     * 输入: [2,3,-2,4]
     * 输出: 6
     * 解释: 子数组 [2,3] 有最大乘积 6。
     *
     * 示例 2:
     * 输入: [-2,0,-1]
     * 输出: 0
     * 解释: 结果不能为 2, 因为 [-2,-1] 不是子数组。
     */

    public int maxProduct(int[] nums) {
        int ans = nums[0];
        int min = nums[0];
        int max = nums[0];

        /**
         * i结尾的情况下：
         * 可能1：i自己  0.3, 0.1, 100 -> 100(i)
         * 可能2：i自己是负数，i-1是负数(最小累乘积)，则nums[i] * nums[i-1] 为最大
         * 可能3：i自己是正数，i-1是正数(最大累乘积)，则nums[i] * nums[i-1] 为最大
         * 可能4，不包含自己，i-1位置的最大累乘积是最大
         */
        for (int i = 1; i < nums.length; i++) {
            int curMin = Math.min(nums[i], Math.min(min * nums[i], max * nums[i]));
            int curMax = Math.max(nums[i], Math.max(min * nums[i], max * nums[i]));

            min = curMin;
            max = curMax;
            ans = Math.max(ans, max);
        }
        return ans;
    }

    public int maxProductFromDouble(double[] nums) {
        return 0;
    }
}
