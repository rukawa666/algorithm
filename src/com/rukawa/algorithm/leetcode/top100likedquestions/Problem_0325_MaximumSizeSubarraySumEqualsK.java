package com.rukawa.algorithm.leetcode.top100likedquestions;

import java.util.HashMap;

/**
 * create by hqh on 2022/9/19
 */
public class Problem_0325_MaximumSizeSubarraySumEqualsK {
    /**
     * 和等于 k 的最长子数组长度
     * 给定一个数组 nums 和一个目标值 k，找到和等于 k 的最长连续子数组长度。如果不存在任意一个符合要求的子数组，则返回 0。
     *
     * 示例 1:
     * 输入: nums = [1,-1,5,-2,3], k = 3
     * 输出: 4
     * 解释: 子数组 [1, -1, 5, -2] 和等于 3，且长度最长。
     *
     * 示例 2:
     * 输入: nums = [-2,-1,2,1], k = 1
     * 输出: 2
     * 解释: 子数组 [-1, 2] 和等于 1，且长度最长。
     *  
     *
     * 提示：
     * 1 <= nums.length <= 2 * 105
     * -104 <= nums[i] <= 104
     * -109 <= k <= 109
     */
    public int maxSubArrayLen(int[] nums, int k) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        int sum = 0;
        int maxLen = 0;
        // 前缀和最早出现的问题
        HashMap<Integer, Integer> preSumMap = new HashMap<>();
        // 必须初始化一个0位置的信息，不然错过0开头的记录，比如：k=10 [5,5,...]
        preSumMap.put(0, -1);
        for (int i = 0; i < nums.length; i++) {
            sum += nums[i];

            if (preSumMap.containsKey(sum - k)) {
                maxLen = Math.max(maxLen, i - preSumMap.get(sum - k));
            }

            if (!preSumMap.containsKey(sum)) {
                preSumMap.put(sum, i);
            }
        }
        return maxLen;
    }
}
