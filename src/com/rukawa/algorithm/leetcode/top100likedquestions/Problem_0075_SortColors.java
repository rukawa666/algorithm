package com.rukawa.algorithm.leetcode.top100likedquestions;

import java.util.Arrays;

/**
 * Created with Intellij IDEA
 *
 * @Author：SuperHai
 * @Date：2020-09-06 10:25
 * @Version：1.0
 */
public class Problem_0075_SortColors {
    /**
     * 颜色分类
     * 给定一个包含红色、白色和蓝色，一共 n 个元素的数组，原地对它们进行排序，使得相同颜色的元素相邻，并按照红色、白色、蓝色顺序排列。
     * 此题中，我们使用整数 0、 1 和 2 分别表示红色、白色和蓝色。
     * 注意:
     * 不能使用代码库中的排序函数来解决这道题。
     * 示例:
     * 输入: [2,0,2,1,1,0]
     * 输出: [0,0,1,1,2,2]
     * 进阶：
     * 一个直观的解决方案是使用计数排序的两趟扫描算法。
     * 首先，迭代计算出0、1 和 2 元素的个数，然后按照0、1、2的排序，重写当前数组。
     * 你能想出一个仅使用常数空间的一趟扫描算法吗？
     */
    public static void sortColors(int[] nums) {
        partition(nums, 0, nums.length - 1);
    }

    public static void partition(int[] nums, int l, int r) {
        int zero = l - 1;
        int two = r + 1;
        int index = l;
        while (index < two) {
            if (nums[index] == 0) {
                swap(nums, index++, ++zero);
            } else if (nums[index] == 2) {
                swap(nums, index, --two);
            } else {
                index++;
            }
        }
    }

    public static void swap(int[] nums, int i, int j) {
        int tmp = nums[i];
        nums[i] = nums[j];
        nums[j] = tmp;
    }

    public static void main(String[] args) {
        int[] nums = {2, 0, 2, 1, 1, 0};
        sortColors(nums);
        System.out.println(Arrays.toString(nums));
    }


}
