package com.rukawa.algorithm.leetcode.top100likedquestions;

/**
 * Created with Intellij IDEA
 *
 * @Author：SuperHai
 * @Date：2020-09-20 8:32
 * @Version：1.0
 */
public class Problem_0029_DivideTwoIntegers {
    /**
     * 两数相除
     * 给定两个整数，被除数 dividend 和除数 divisor。将两数相除，要求不使用乘法、除法和 mod 运算符。
     * 返回被除数 dividend 除以除数 divisor 得到的商。
     * 整数除法的结果应当截去（truncate）其小数部分，例如：truncate(8.345) = 8 以及 truncate(-2.7335) = -2
     *
     * 示例 1:
     * 输入: dividend = 10, divisor = 3
     * 输出: 3
     * 解释: 10/3 = truncate(3.33333..) = truncate(3) = 3
     *
     * 示例 2:
     * 输入: dividend = 7, divisor = -3
     * 输出: -2
     * 解释: 7/-3 = truncate(-2.33333..) = -2
     *  
     *
     * 提示：
     * 被除数和除数均为 32 位有符号整数。
     * 除数不为 0。
     * 假设我们的环境只能存储 32 位有符号整数，其数值范围是 [−231,  231 − 1]。本题中，如果除法结果溢出，则返回 231 − 1
     */
    public int divide(int dividend, int divisor) {
        if (dividend == Integer.MIN_VALUE && divisor == Integer.MIN_VALUE) {
            return 1;
        } else if (divisor == Integer.MIN_VALUE) {
            return 0;
        } else if (dividend == Integer.MIN_VALUE) {
            if (divisor == negNum(1)) {
                return Integer.MAX_VALUE;
            } else {
                int res = div(add(dividend, 1), divisor);
                // 系统最小值无法转相反数
                // 此时x是系统最小，可以把x+1，除以y 得到结果m
                // 然后得到结果m*y 看看和x相差多少，补偿
                return add(res, div(minus(dividend, multi(res, divisor)), divisor));
            }
        } else {
            return div(dividend, divisor);
        }
    }

    public int div(int dividend, int divisor) {
        /**
         * a/b=c  => b=01110 c=00110
         * a=b*2^1 + b*2^2
         *
         * 2的几次方*b 离a最近，找到最近的2的某次方假设是2^7  a'= a - 2^7 * b
         * 此时离a'最近的2的某次方是5  a'' = a' - 2^5 * b
         * ...
         * 找到的这些2的某次方就是c
         *
         * 2的几次方*b最接近a，只需要把b往左移动7位，a >= 2^7*b，此时有风险，b往左移动，可能来到符号位
         * 所以让a右移动，看移动几个位置能够到b
         */

        int x = isNeg(dividend) ? negNum(dividend) : dividend;
        int y = isNeg(divisor) ? negNum(divisor) : divisor;
        int res = 0;
        // x/y
        for (int i = 30; i >= 0; i = minus(i, 1)) {
            // 右移30位看看能不能找到接近b
            // 右移29位去尝试
            if ((x >> i) >= y) {
                // 如果右移30位，此时2^30是答案，异或进去
                res |= (1 << i);
                // x要减去2^30*b
                // 相当于y<<30 得到x'
                x = minus(x, y << i);
            }
        }
        return isNeg(dividend) ^ isNeg(divisor) ? negNum(res) : res;

    }

    // 判断是否是负数
    public boolean isNeg(int n) {
        return n < 0;
    }

    // 两个数相加
    public int add(int x, int y) {
        // 46的二进制：32+14(8+4+2)  0101110
        // 20的二进制：16+4          0010100
        // 无进位相加'^'  进位'&'
        // ^: 0111010   &:0000100
        // 所以两个数相加=> 无进位相加 + 进位信息
        int sum = x;
        // 有进位信息
        while (y != 0) {
            sum = x ^ y;
            // 进位信息
            y = (x & y) << 1;
            x = sum;
        }
        return sum;
    }

    // x的相反数
    public int negNum(int x) {
        return add(~x, 1);
    }

    // 两数的减法
    public int minus(int x, int y) {
        return add(x, negNum(y));
    }

    // 两数的乘法
    public int multi(int x, int y) {
        /**
         * x： 0 1 1 0
         * y： 0 1 1 1
         * 从右往左依次判断y的二进制是否是1
         * res = x*2^0 + x*2^1 + x*2^2
         * res = 0110 + 01100 + 011000
         */
        int res = 0;
        while (y != 0) {
            if ((y & 1) != 0) {
                res = add(res, x);
            }
            x <<= 1;
            y >>>= 1;
        }
        return res;
    }
}
