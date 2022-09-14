package com.rukawa.algorithm.leetcode.top100likedquestions;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * Created with Intellij IDEA
 *
 * @Author：SuperHai
 * @Date：2020-09-21 8:42
 * @Version：1.0
 */
public class Problem_0078_Subsets {
    /**
     * 子集
     * 给你一个整数数组 nums ，数组中的元素 互不相同 。返回该数组所有可能的子集（幂集）。
     *
     * 解集 不能 包含重复的子集。你可以按 任意顺序 返回解集。
     *
     *  
     *
     * 示例 1：
     *
     * 输入：nums = [1,2,3]
     * 输出：[[],[1],[2],[1,2],[3],[1,3],[2,3],[1,2,3]]
     * 示例 2：
     *
     * 输入：nums = [0]
     * 输出：[[],[0]]
     *  
     *
     * 提示：
     *
     * 1 <= nums.length <= 10
     * -10 <= nums[i] <= 10
     * nums 中的所有元素 互不相同
     */
    public static HashSet<HashSet<Integer>> subsets(int[] nums) {
        HashSet<Integer> path = new HashSet<>();
        HashSet<HashSet<Integer>> set = new HashSet<>();
        process(nums, 0, set, path);
        return set;
    }

    public static void process(int[] nums, int index, HashSet<HashSet<Integer>> res, HashSet<Integer> path) {
        if (index == nums.length) {
            res.add(path);
            return;
        }
        process(nums, index + 1, res, path);
        path.add(nums[index]);
        process(nums, index + 1, res, path);
    }

    public static void main(String[] args) {
        int[] nums = {1, 2, 3};
        HashSet<HashSet<Integer>> subsets = subsets(nums);
        for (HashSet<Integer> hashSet : subsets) {
            for (Integer integer : hashSet) {
                System.out.print(integer + " ");
            }
            System.out.println();
        }
    }
}
