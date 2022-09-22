package com.rukawa.algorithm.leetcode.top100likedquestions;

/**
 * create by hqh on 2022/9/21
 */
public class Problem_0062_UniquePaths {
    /**
     * 不同路径
     * 一个机器人位于一个 m x n 网格的左上角 （起始点在下图中标记为 “Start” ）。
     * 机器人每次只能向下或者向右移动一步。机器人试图达到网格的右下角（在下图中标记为 “Finish” ）。
     * 问总共有多少条不同的路径？
     * 示例 1：
     * 输入：m = 3, n = 7
     * 输出：28
     *
     * 示例 2：
     * 输入：m = 3, n = 2
     * 输出：3
     * 解释：
     * 从左上角开始，总共有 3 条路径可以到达右下角。
     * 1. 向右 -> 向下 -> 向下
     * 2. 向下 -> 向下 -> 向右
     * 3. 向下 -> 向右 -> 向下
     *
     * 示例 3：
     * 输入：m = 7, n = 3
     * 输出：28
     *
     * 示例 4：
     * 输入：m = 3, n = 3
     * 输出：6
     *
     * 提示：
     * 1 <= m, n <= 100
     * 题目数据保证答案小于等于 2 * 109
     */
    public int uniquePaths(int m, int n) {
        /**
         * 假设：从左上到右下一共要走m+n步，所以就是排列组合问题
         * 排列P：从n个元素中找到m个元素进行排列 P(n,m) = n! / (n - m)!
         * 组合C：从n个元素中找到m个元素进行组合，不进行排列 C(n,m) = n! / m! * (n - m)!
         */
        // m 往下走，只能走m-1步
        // n 往右走，只能走n-1步
        int right = n - 1;
        int all = m + n - 2;
        // 10! / 6!4!
        // 代表分子阶乘的初始值
        long o1 = 1;
        // 代表分母阶乘的初始值
        long o2 = 1;
        // all = 10  right = 4
        // o1 = 10*9*8*7*6*5   o2=4*3*2*1
        for (int i = right + 1, j = 1; i <= all || j <= all - right; i++, j++) {
            o1 *= i;
            o2 *= j;
            long gcb = gcb(o1, o2);
            o1 /= gcb;
            o2 /= gcb;
        }
        return (int) o1;
    }

    public long gcb(long x, long y) {
        return y == 0 ? x : gcb(y, x % y);
    }
}
