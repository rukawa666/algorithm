package com.rukawa.algorithm.leetcode.weeklyContest;

import java.util.HashMap;
import java.util.Map;

/**
 * create by hqh on 2022/2/14
 */
public class Problem_2170_MinimumOperationsToMakeTheArrayAlternating {
    /**
     * 使数组变成交替数组的最少操作数
     * 给你一个下标从 0 开始的数组 nums ，该数组由 n 个正整数组成。
     * 如果满足下述条件，则数组 nums 是一个 交替数组 ：
     * nums[i - 2] == nums[i] ，其中 2 <= i <= n - 1 。
     * nums[i - 1] != nums[i] ，其中 1 <= i <= n - 1 。
     * 在一步 操作 中，你可以选择下标 i 并将 nums[i] 更改 为 任一 正整数。
     * 返回使数组变成交替数组的 最少操作数 。
     *
     * 示例 1：
     * 输入：nums = [3,1,3,2,4,3]
     * 输出：3
     * 解释：
     * 使数组变成交替数组的方法之一是将该数组转换为 [3,1,3,1,3,1] 。
     * 在这种情况下，操作数为 3 。
     * 可以证明，操作数少于 3 的情况下，无法使数组变成交替数组。
     *
     * 示例 2：
     * 输入：nums = [1,2,2,2,2]
     * 输出：2
     * 解释：
     * 使数组变成交替数组的方法之一是将该数组转换为 [1,2,1,2,1].
     * 在这种情况下，操作数为 2 。
     * 注意，数组不能转换成 [2,2,2,2,2] 。因为在这种情况下，nums[0] == nums[1]，不满足交替数组的条件。
     *
     * 提示：
     * 1 <= nums.length <= 105
     * 1 <= nums[i] <= 105
     */

    public int minimumOperations(int[] nums) {
        Map<Integer, Integer> oddMap = new HashMap<>();
        Map<Integer, Integer> evenMap = new HashMap<>();
        int n = nums.length;
        for (int i = 0; i < n; i++) {
            if ((1 & i) == 0) {
                evenMap.put(nums[i], evenMap.getOrDefault(nums[i], 0) + 1);
            } else {
                oddMap.put(nums[i], oddMap.getOrDefault(nums[i], 0) + 1);
            }
        }

        int[][] oddCount = statistics(oddMap);
        int[][] evenCount = statistics(evenMap);

        int res = 0;
        if (oddCount[0][0] != evenCount[0][0]) {
            res = n - oddCount[0][1] - evenCount[0][1];
        } else {
            res = Math.min(n - oddCount[0][1] - evenCount[1][1], n - evenCount[0][1] - oddCount[1][1]);
        }
        return res;
    }

    public int[][] statistics(Map<Integer, Integer> map) {
        int[][] res = {
                        {0, 0},
                        {0, 0}
                                };
        for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
            int num = entry.getKey();
            int count = entry.getValue();
            if (count > res[0][1]) {
                res[1][0] = res[0][0];
                res[1][1] = res[0][1];
                res[0][0] = num;
                res[0][1] = count;
            } else {
                res[1][0] = num;
                res[1][1] = count;
            }
        }
        return res;
    }
}
