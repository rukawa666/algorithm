package com.rukawa.algorithm.leetcode.top100likedquestions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created with Intellij IDEA
 *
 * @Author：SuperHai
 * @Date：2020-09-20 8:34
 * @Version：1.0
 */
public class Problem_0046_Permutations {
    /**
     * 全排列
     * 给定一个不含重复数字的数组 nums ，返回其 所有可能的全排列 。你可以 按任意顺序 返回答案。
     *
     * 示例 1：
     * 输入：nums = [1,2,3]
     * 输出：[[1,2,3],[1,3,2],[2,1,3],[2,3,1],[3,1,2],[3,2,1]]
     *
     * 示例 2：
     * 输入：nums = [0,1]
     * 输出：[[0,1],[1,0]]
     *
     * 示例 3：
     * 输入：nums = [1]
     * 输出：[[1]]
     *  
     *
     * 提示：
     * 1 <= nums.length <= 6
     * -10 <= nums[i] <= 10
     * nums 中的所有整数 互不相同
     */

    public List<List<Integer>> permute(int[] nums) {
        List<List<Integer>> res = new ArrayList<>();
        List<Integer> list = new ArrayList<>();
        func(nums, 0, list, res);
        return res;
    }

    public void func(int[] nums, int index, List<Integer> list, List<List<Integer>> res) {
        if (index == nums.length) {
            list = Arrays.stream(nums).boxed().collect(Collectors.toList());
            res.add(list);
        } else {
            for (int i = index; i < nums.length; i++) {
                swap(nums, i, index);
                func(nums, index + 1, list, res);
                swap(nums, i, index);
            }
        }
    }

    public void swap(int[] nums, int i, int j) {
        int tmp = nums[i];
        nums[i] = nums[j];
        nums[j] = tmp;
    }
}
