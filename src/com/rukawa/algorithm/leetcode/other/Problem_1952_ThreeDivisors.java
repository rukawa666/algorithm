package com.rukawa.algorithm.leetcode.other;

/**
 * @name: Problem_1952_ThreeDivsors
 * @description: 描述类的用途
 * @date: 2021/8/4 16:51
 * @auther: hanqinghai
 */
public class Problem_1952_ThreeDivisors {
    /**
     * 给你一个整数 n 。如果 n 恰好有三个正除数 ，返回 true ；否则，返回 false 。
     * 如果存在整数 k ，满足 n = k * m ，那么整数 m 就是 n 的一个 除数 。
     *
     * 示例 1：
     * 输入：n = 2
     * 输出：false
     * 解释：2 只有两个除数：1 和 2 。
     *
     * 示例 2：
     * 输入：n = 4
     * 输出：true
     * 解释：4 有三个除数：1、2 和 4 。
     *
     * 提示：
     * 1 <= n <= 104
     */

    public static boolean isThree(int n) {
        if (n < 4) {
            return false;
        }
        int mid = (int) Math.pow(n, 0.5);
        if (mid * mid != n) {
            return false;
        }

        for (int i = 2; i < mid; i++) {
            if (n % i == 0) {
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        System.out.println(isThree(4));
    }
}
