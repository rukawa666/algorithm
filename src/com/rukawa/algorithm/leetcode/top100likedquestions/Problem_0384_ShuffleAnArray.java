package com.rukawa.algorithm.leetcode.top100likedquestions;

/**
 * Created with Intellij IDEA
 *
 * @Author：SuperHai
 * @Date：2020-10-11 0:53
 * @Version：1.0
 */
public class Problem_0384_ShuffleAnArray {
    /**
     * 打乱数组
     * 打乱一个没有重复元素的数组。
     *
     * 示例:
     * // 以数字集合 1, 2 和 3 初始化数组。
     * int[] nums = {1,2,3};
     * Solution solution = new Solution(nums);
     *
     * // 打乱数组 [1,2,3] 并返回结果。任何 [1,2,3]的排列返回的概率应该相同。
     * solution.shuffle();
     *
     * // 重设数组到它的初始状态[1,2,3]。
     * solution.reset();
     *
     * // 随机返回数组[1,2,3]打乱后的结果。
     * solution.shuffle();
     */

    public static class Solution {

        private int[] origin;
        private int[] shuffle;
        int N;

        public Solution(int[] nums) {
            origin = nums;
            N = origin.length;
            shuffle = new int[N];
            for (int i = 0; i < N; i++) {
                shuffle[i] = origin[i];
            }
        }

        /** Resets the array to its original configuration and return it. */
        public int[] reset() {
            return origin;
        }

        /** Returns a random shuffling of the array. */
        public int[] shuffle() {
            for (int i = N - 1; i >= 0; i--) {
                int r = (int) (Math.random() * (i + 1));
                int tmp = shuffle[r];
                shuffle[r] = shuffle[i];
                shuffle[i] = tmp;
            }
            return shuffle;
        }
    }
}
