package com.rukawa.algorithm.trainingcamp.top100likedquestions;

/**
 * Created with Intellij IDEA
 *
 * @Author：SuperHai
 * @Date：2020-10-11 9:09
 * @Version：1.0
 */
public class Problem_0334_IncreasingTripletSubsequence {
    /**
     * 递增的三元子序列
     * 给定一个未排序的数组，判断这个数组中是否存在长度为 3 的递增子序列。
     *
     * 数学表达式如下:
     * 如果存在这样的 i, j, k,  且满足 0 ≤ i < j < k ≤ n-1，
     * 使得 arr[i] < arr[j] < arr[k] ，返回 true ; 否则返回 false 。
     * 说明: 要求算法的时间复杂度为 O(n)，空间复杂度为 O(1) 。
     *
     * 示例 1:
     * 输入: [1,2,3,4,5]
     * 输出: true
     *
     * 示例 2:
     * 输入: [5,4,3,2,1]
     * 输出: false
     */
    public static boolean increasingTriplet(int[] nums) {
        if (nums == null || nums.length < 2) {
            return false;
        }
        int[] ends = new int[3];
        ends[0] = nums[0];
        int right = 0;
        int l = 0;
        int r = 0;
        int m = 0;
        for (int i = 1; i < nums.length; i++) {
            l = 0;
            r = right;
            while (l <= r) {
                m = (l + r) / 2;
                if (nums[i] > ends[m]) {
                    l = m + 1;
                } else {
                    r = m - 1;
                }
            }
            right = Math.max(right, l);
            if (right > 1) {
                return true;
            }
            ends[l] = nums[i];
        }
        return false;
    }

    public static void main(String[] args) {
        int[] nums= {5,4,3,2,1};
        System.out.println(increasingTriplet(nums));
    }
}
