package com.rukawa.algorithm.leetcode.top100likedquestions;

/**
 * Created with Intellij IDEA
 *
 * @Author：SuperHai
 * @Date：2020-09-19 11:20
 * @Version：1.0
 */
public class Problem_0162_FindPeakElement {
    /**
     * 寻找峰值
     * 峰值元素是指其值大于左右相邻值的元素。
     * 给定一个输入数组 nums，其中 nums[i] ≠ nums[i+1]，找到峰值元素并返回其索引。
     * 数组可能包含多个峰值，在这种情况下，返回任何一个峰值所在位置即可。
     * 你可以假设 nums[-1] = nums[n] = -∞。
     *
     * 示例 1:
     * 输入: nums = [1,2,3,1]
     * 输出: 2
     * 解释: 3 是峰值元素，你的函数应该返回其索引 2。
     *
     * 示例 2:
     * 输入: nums = [1,2,1,3,5,6,4]
     * 输出: 1 或 5
     * 解释: 你的函数可以返回索引 1，其峰值元素为 2；
     *      或者返回索引 5， 其峰值元素为 6。
     *
     * 说明:
     * 你的解法应该是 O(logN) 时间复杂度的。
     */

    public int findPeakElement(int[] nums) {
        int N = nums.length;
        if (N < 2) {
            return 0;
        }
        if (nums[0] > nums[1]) {
            return 0;
        }
        if (nums[N - 1] > nums[N - 2]) {
            return N - 1;
        }

        int L = 1;
        int R = N - 2;
        int M = 0;
        while (L < R) {
            M = (L + R) / 2;
            if (nums[M - 1] < nums[M] && nums[M] > nums[M + 1]) {
                return M;
            } else if (nums[M - 1] > nums[M]) {
                R = M - 1;
            } else {
                L = M + 1;
            }
        }
        return L;
    }
}
