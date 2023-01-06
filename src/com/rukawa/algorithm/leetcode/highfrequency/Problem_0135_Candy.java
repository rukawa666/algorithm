package com.rukawa.algorithm.leetcode.highfrequency;

/**
 * create by hqh on 2022/9/27
 */
public class Problem_0135_Candy {
    /**
     * 分发糖果
     * n 个孩子站成一排。给你一个整数数组 ratings 表示每个孩子的评分。
     * 你需要按照以下要求，给这些孩子分发糖果：
     * 每个孩子至少分配到 1 个糖果。
     * 相邻两个孩子评分更高的孩子会获得更多的糖果。
     * 请你给每个孩子分发糖果，计算并返回需要准备的 最少糖果数目 。
     *
     * 示例 1：
     * 输入：ratings = [1,0,2]
     * 输出：5
     * 解释：你可以分别给第一个、第二个、第三个孩子分发 2、1、2 颗糖果。
     *
     * 示例 2：
     * 输入：ratings = [1,2,2]
     * 输出：4
     * 解释：你可以分别给第一个、第二个、第三个孩子分发 1、2、1 颗糖果。
     *      第三个孩子只得到 1 颗糖果，这满足题面中的两个条件。
     *
     * 提示：
     * n == ratings.length
     * 1 <= n <= 2 * 104
     * 0 <= ratings[i] <= 2 * 104
     */
    public int candy(int[] ratings) {
        /**
         * 贪心：
         *  left：数组从0开始，如果当前数前一个数大，在前一个数的基础上+1，如果比前一个数小，则为1
         *  right：数组从n-1开始，如果当前数比后一个大，在后一个数的基础上+1，如果比后一个数小，则为1
         *  糖果数量=sum{Math.max(left[i],right[i])}
         */
        int n = ratings.length;
        if (n == 1) {
            return 1;
        }
        int[] left = new int[n];
        for (int i = 1; i < n; i++) {
            if (ratings[i] > ratings[i - 1]) {
                left[i] = left[i - 1] + 1;
            }
        }

        int[] right = new int[n];
        for (int i = n - 2; i >= 0; i--) {
            if (ratings[i] > ratings[i + 1]) {
                right[i] = right[i + 1] + 1;
            }
        }

        int min = 0;
        for (int i = 0; i < n; i++) {
            min += Math.max(left[i], right[i]);
        }
        return min + n;
    }
}
