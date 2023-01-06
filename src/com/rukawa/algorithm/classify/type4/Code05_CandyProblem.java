package com.rukawa.algorithm.classify.type4;

/**
 * create by hqh on 2022/9/27
 */
public class Code05_CandyProblem {
    /**
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
        left[0] = 1;
        for (int i = 1; i < n; i++) {
            if (ratings[i] > ratings[i - 1]) {
                left[i] = left[i - 1] + 1;
            } else {
                left[i] = 1;
            }
        }

        int[] right = new int[n];
        right[n - 1] = 1;
        for (int i = n - 2; i >= 0; i--) {
            if (ratings[i] > ratings[i + 1]) {
                right[i] = right[i + 1] + 1;
            } else {
                right[1] = 1;
            }
        }

        int min = 0;
        for (int i = 0; i < n; i++) {
            min += Math.max(left[i], right[i]);
        }
        return min;
    }

    // 优化解：去除辅助空间
    // T(N)=O(N) S(N)=O(1)
    public int candy1(int[] ratings) {
        /**
         * 思路：无重复值
         * [1,2,3,5,4,2,1,3,4,6,5,2]
         * 先找到一个上坡和下坡的区间 1,2,3,5,4,2,1  此时上坡起始点给1，依次++，坡顶位置由两边的最大值+1
         * 接着再找到一个上坡和下坡区间 1,3,4,6,5,2  此时上坡起始点给1，依次++，坡顶位置有两边的最大值+1
         * 上坡重复的位置(1)要减掉一个1
         */
        if (ratings == null || ratings.length == 0) {
            return 0;
        }
        int index = nextMinIndex2(ratings, 0);
        int res = rightCandies(ratings, 0, index++);
        int leftBase = 1;
        int next = 0;
        int rightCandies = 0;
        int rightBase = 0;
        while (index != ratings.length) {
            if (ratings[index] > ratings[index - 1]) {
                res += ++leftBase;
                index++;
            } else if (ratings[index] < ratings[index - 1]) {
                next = nextMinIndex2(ratings, index - 1);
                rightCandies = rightCandies(ratings, index - 1, next++);
                rightBase = next - index + 1;
                res += rightCandies + (rightBase > leftBase ? -leftBase : -rightBase);
                leftBase = 1;
                index = next;
            } else {
                res += 1;
                leftBase = 1;
                index++;
            }
        }
        return res;
    }

    public int nextMinIndex2(int[] nums, int start) {
        for (int i = start; i < nums.length - 1; i++) {
            if (nums[i] <= nums[i + 1]) {
                return i;
            }
        }
        return nums.length - 1;
    }

    public int rightCandies(int[] nums, int left, int right) {
        int n = right - left + 1;
        // 连续整数的求和公式
        return n + n * (n - 1) / 2;
    }


    // 进阶问题：在原问题的基础上，增加一个原则，相邻的孩子间评分一样，分的糖果必须一样，返回至少需要分多少糖
    public int candy2(int[] ratings) {
        /**
         * 贪心：
         *  left：数组从0开始
         *      如果当前数前一个数大，在前一个数的基础上+1
         *      如果相等则继承
         *      如果比前一个数小，则为1
         *  right：数组从n-1开始，
         *      如果当前数比后一个大，在后一个数的基础上+1
         *      如果相等则继承
         *      如果比后一个数小，则为1
         *  糖果数量=sum{Math.max(left[i],right[i])}
         */
        int n = ratings.length;

        int[] left = new int[n];
        for (int i = 1; i < n; i++) {
            if (ratings[i] > ratings[i - 1]) {
                left[i] = left[i - 1] + 1;
            } else if (ratings[i] == ratings[i - 1]) {
                left[i] = left[i - 1];
            } else {
                continue;
            }
        }

        int[] right = new int[n];
        for (int i = n - 2; i > 0; i--) {
            if (ratings[i] > ratings[i + 1]) {
                right[i] = right[i + 1] + 1;
            } else if (ratings[i] == ratings[i + 1]) {
                right[i] = right[i + 1];
            } else {
                continue;
            }
        }

        int res = 0;
        for (int i = 0; i < n; i++) {
            res += Math.max(left[i], right[i]);
        }
        return res + n;
    }
}
