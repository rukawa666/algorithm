package com.rukawa.algorithm.leetcode.top100likedquestions;

/**
 * Created with Intellij IDEA
 *
 * @Author：SuperHai
 * @Date：2020-09-20 8:34
 * @Version：1.0
 */
public class Problem_0050_PowXN {
    public double myPow(double x, int n) {
        /**
         * 一个数的N次方怎么最快
         *
         * 求10的75次方。75的二进制是64+8+2+1     1001011
         * 10^75 = 10^1 + 10^2 + 10^8 + 10^64
         */

        /**
         * 如果n是负数情况：先转成正数，然后 1/正数
         * 但是系统最小值不能转正数
         * 系统最小值的解决办法：先求x的系统最大次方，然后结果乘x
         */
        if (n == 0) {
            return 1D;
        }
        double t = x;
        double res = 1D;
        // 系统最小值：-2147483648 系统最大值：2147483647
        // 如果是系统最小值，先转成系统最大值
        int pow = Math.abs(n == Integer.MIN_VALUE ? n + 1 : n);
        for (; pow != 0; pow >>= 1) {
            // 判断二进制末尾是否是1，有1需要把x乘到结果中
            if ((pow & 1) != 0) {
                res *= t;
            }
            // 此时t变成原来t的平方
            t *= t;
        }

        if (n == Integer.MIN_VALUE) {
            res *= x;
        }
        return n < 0 ? (1D / res) : res;
    }
}
