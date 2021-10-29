package com.rukawa.algorithm.leetcode;

import java.time.LocalDateTime;
import java.util.HashMap;

/**
 * @name: Problem_0001_TwoSum
 * @description: 描述类的用途
 * @date: 2021/10/12 15:28
 * @auther: hanqinghai
 */
public class Problem_0001_TwoSum {
    public int[] twoSum(int[] nums, int target) {
        HashMap<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            if (map.containsKey(target - nums[i])) {
                return new int[]{i, map.get(target - nums[i])};
            }
            map.put(nums[i], i);
        }
        return new int[]{-1, -1};
    }
}
