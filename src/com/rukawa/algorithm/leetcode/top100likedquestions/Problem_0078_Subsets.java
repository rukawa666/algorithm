package com.rukawa.algorithm.leetcode.top100likedquestions;

import javax.security.auth.Subject;
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
     * 解集 不能 包含重复的子集。你可以按 任意顺序 返回解集。
     * <p>
     * 示例 1：
     * 输入：nums = [1,2,3]
     * 输出：[[],[1],[2],[1,2],[3],[1,3],[2,3],[1,2,3]]
     * <p>
     * 示例 2：
     * 输入：nums = [0]
     * 输出：[[],[0]]
     * <p>
     * 提示：
     * 1 <= nums.length <= 10
     * -10 <= nums[i] <= 10
     * nums 中的所有元素 互不相同
     */
    public static List<List<Integer>> subsets(int[] nums) {
        List<List<Integer>> res = new ArrayList<>();
        List<Integer> path = new ArrayList<>();
        process(nums, 0, res, path);
        return res;
    }

    public static void process(int[] nums, int index, List<List<Integer>> res, List<Integer> path) {
        if (index == nums.length) {
            res.add(new ArrayList<>(path));
        } else {
            // 不要当前的数
            process(nums, index + 1, res, path);

            path.add(nums[index]);
            process(nums, index + 1, res, path);
            path.remove(path.size() - 1);
        }
    }

    public static void main(String[] args) {
        int[] nums = {1, 2, 3};
        List<List<Integer>> subsets = subsets(nums);
        for (List<Integer> subset : subsets) {
            for (Integer num : subset) {
                System.out.print(num + " ");
            }
            System.out.println();
        }
    }
}
