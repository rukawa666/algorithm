package com.rukawa.algorithm.leetcode.highfrequency;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * create by hqh on 2022/9/21
 */
public class Problem_0047_PermutationsII {
    /**
     * 全排列 II
     * 给定一个可包含重复数字的序列 nums ，按任意顺序 返回所有不重复的全排列。
     */
    public List<List<Integer>> permuteUnique(int[] nums) {
        List<List<Integer>> res = new ArrayList<>();
        List<Integer> list = new ArrayList<>();
        int[] visit = new int[10];
        func(nums, 0, list, res, visit);
        return res;
    }

    public void func(int[] nums, int index, List<Integer> list, List<List<Integer>> res, int[] visit) {
        if (index == nums.length) {
            list = Arrays.stream(nums).boxed().collect(Collectors.toList());
            res.add(list);
        } else {
            for (int i = 0; i < nums.length; i++) {
                if (visit[i] > 0) {
                    continue;
                }
                visit[i] = i;
                swap(nums, index, i);
                func(nums, index + 1, list, res, visit);
                visit[i] = 0;
                swap(nums, index, i);
            }
        }
    }

    public void swap(int[] nums, int i, int j) {
        int tmp = nums[i];
        nums[i] = nums[j];
        nums[j] = tmp;
    }
}
