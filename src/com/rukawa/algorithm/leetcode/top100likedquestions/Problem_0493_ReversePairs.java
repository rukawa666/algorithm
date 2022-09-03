package com.rukawa.algorithm.leetcode.top100likedquestions;

/**
 * create by hqh on 2022/8/26
 */
public class Problem_0493_ReversePairs {

    /**
     * 给定一个数组 nums ，如果 i < j 且 nums[i] > 2*nums[j] 我们就将 (i, j) 称作一个重要翻转对。
     * 你需要返回给定数组中的重要翻转对的数量。
     *
     * 示例 1:
     * 输入: [1,3,2,3,1]
     * 输出: 2
     *
     * 示例 2:
     * 输入: [2,4,3,5,1]
     * 输出: 3
     *
     * 注意:
     * 给定数组的长度不会超过50000。
     * 输入数组中的所有数字都在32位整数的表示范围内。
     */

    public static int reversePairs(int[] nums) {
        if (nums == null || nums.length < 2) {
            return 0;
        }
        return process(nums, 0, nums.length - 1);
    }

    public static int process(int[] nums, int l, int r) {
        if (l == r) {
            return 0;
        }
        int m = l + ((r - l) >> 1);
        return process(nums, l, m) + process(nums, m + 1, r) + merge(nums, l, m, r);
    }

    public static int merge(int[] nums, int l, int m, int r) {

        int res = 0;
        int start = m + 1;
        for (int i = l; i <= m; i++) {
            while (start <= r && (nums[i] > ((long) (nums[start]) << 1))) {
                start++;
            }
            res += start - m - 1;
        }

        int[] help = new int[r - l + 1];
        int p1 = l;
        int p2 = m + 1;
        int index = 0;
        while (p1 <= m && p2 <= r) {
            help[index++] = nums[p1] < nums[p2] ? nums[p1++] : nums[p2++];
        }

        while (p1 <= m) {
            help[index++] = nums[p1++];
        }

        while (p2 <= r) {
            help[index++] = nums[p2++];
        }
        for (index = 0; index <= r - l; index++) {
            nums[l + index] = help[index];
        }
        return res;
    }

    public static void main(String[] args) {
        int[] nums = {2147483647,2147483647,2147483647,2147483647,2147483647,2147483647};
        System.out.println(reversePairs(nums));
    }
}
