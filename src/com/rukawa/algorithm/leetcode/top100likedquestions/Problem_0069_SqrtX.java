package com.rukawa.algorithm.leetcode.top100likedquestions;

/**
 * Created with Intellij IDEA
 *
 * @Author：SuperHai
 * @Date：2020-09-06 9:30
 * @Version：1.0
 */
public class Problem_0069_SqrtX {
    /**
     * x 的平方根
     * 实现 int sqrt(int x) 函数。
     * 计算并返回 x 的平方根，其中 x 是非负整数。
     * 由于返回类型是整数，结果只保留整数的部分，小数部分将被舍去。
     * 示例 1:
     * 输入: 4
     * 输出: 2
     *
     * 示例 2:
     * 输入: 8
     * 输出: 2
     * 说明: 8 的平方根是 2.82842...,
     *      由于返回类型是整数，小数部分将被舍去。
     */

    // x开根号向下取整返回
    public int mySqrt(int x) {
       if (x == 0) {
           return 0;
       }
       if (x < 3) {
           return 1;
       }
       long res = 1;
       long l = 1;
       long r = x;
       long m = 0;
       while (l <= r) {
           m = l + ((r - l) >> 1);
           if (m * m <= x) {
               res = m;
               l = m + 1;
           } else {
               r = m - 1;
           }
       }
       return (int) res;
    }
}
