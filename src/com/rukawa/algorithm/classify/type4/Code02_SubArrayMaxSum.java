package com.rukawa.algorithm.classify.type4;

/**
 * create by hqh on 2022/9/27
 */
public class Code02_SubArrayMaxSum {
    /**
     * 返回一个数组中，子数组最大的累加和
     */
    public int maxSumSubArray(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        /**
         * 以每个位置结尾的时候，往左扩，什么时候累加和最大
         * 可能性1：不向左边扩，当前值就是最大累加和
         * 可能性2：往左扩，需要i-1位置的扩出来的最好
         */
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
