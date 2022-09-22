package com.rukawa.algorithm.leetcode.top100likedquestions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created with Intellij IDEA
 *
 * @Author：SuperHai
 * @Date：2020-09-20 8:31
 * @Version：1.0
 */
public class Problem_0015_3Sum {
    /**
     * 三数之和
     * 给你一个整数数组 nums ，判断是否存在三元组 [nums[i], nums[j], nums[k]] 满足 i != j、i != k 且 j != k ，
     * 同时还满足 nums[i] + nums[j] + nums[k] == 0 。请
     * 你返回所有和为 0 且不重复的三元组。
     * 注意：答案中不可以包含重复的三元组。
     *
     * 示例 1：
     * 输入：nums = [-1,0,1,2,-1,-4]
     * 输出：[[-1,-1,2],[-1,0,1]]
     * 解释：
     * nums[0] + nums[1] + nums[2] = (-1) + 0 + 1 = 0 。
     * nums[1] + nums[2] + nums[4] = 0 + 1 + (-1) = 0 。
     * nums[0] + nums[3] + nums[4] = (-1) + 2 + (-1) = 0 。
     * 不同的三元组是 [-1,0,1] 和 [-1,-1,2] 。
     * 注意，输出的顺序和三元组的顺序并不重要。
     *
     * 示例 2：
     * 输入：nums = [0,1,1]
     * 输出：[]
     * 解释：唯一可能的三元组和不为 0 。
     *
     * 示例 3：
     * 输入：nums = [0,0,0]
     * 输出：[[0,0,0]]
     * 解释：唯一可能的三元组和为 0 。
     *  
     * 提示：
     * 3 <= nums.length <= 3000
     * -105 <= nums[i] <= 105
     */
    public List<List<Integer>> threeSum(int[] nums) {
        /**
         * 两数之和：数组先排序，然后从l位置和最r开始求和，如果l和r的和小于k，则l++，移动到比l的值大的位置
         * 如果有重复数字，下一个数是已经被收集的数，则直接跳过
         */
        Arrays.sort(nums);
        int n = nums.length;
        List<List<Integer>> res = new ArrayList<>();
        // 为什么要从后往前遍历
        // 假设从左往右遍历，-5去找二元组累加和等于5的结果，找到之后假设是[2,3],要把-5插入到开头位置，代价高
        // 但是从右往左遍历，5去找二元组累加和等于-5的结果，找到之后假设是[-3,-2],可以直接把5插入到最后，代价底
        for (int i = n - 1; i > 1; i--) {
            // 重复的数字，只在第一次的时候收集，如果之前收集过了，下一个直接跳过
            // 如果i是最后位置，则之前没有收集过
            if (i == n - 1 || nums[i] != nums[i + 1]) {
                List<List<Integer>> next = twoSum(nums, i - 1, -nums[i]);
                for (List<Integer> cur : next) {
                    cur.add(nums[i]);
                    res.add(cur);
                }
            }
        }
        return res;
    }


    // nums[0...end]这个范围上，有多少个不同的二元组，相加=target，全部返回
    public static List<List<Integer>> twoSum(int[] nums, int end, int target) {
        int l = 0;
        int r = end;
        List<List<Integer>> res = new ArrayList<>();
        while (l < r) {
            if (nums[l] + nums[r] < target) {
                l++;
            } else if (nums[l] + nums[r] > target) {
                r--;
            } else {
                // 有重复数字的时候，只收集第一次出现的
                // 左边没数，可以收集
                // 和左边的数不想等可以收集
                if (l == 0 || nums[l] != nums[l - 1]) {
                    List<Integer> cur = new ArrayList<>();
                    cur.add(nums[l]);
                    cur.add(nums[r]);
                    res.add(cur);
                }
                // 约定，相等去比较下一个
                l++;
            }
        }
        return res;
    }
}
