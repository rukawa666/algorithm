package com.rukawa.algorithm.leetcode.top100likedquestions;

import java.sql.SQLOutput;

/**
 * Created with Intellij IDEA
 *
 * @Author：SuperHai
 * @Date：2020-09-20 8:30
 * @Version：1.0
 */
public class Problem_0007_ReverseInteger {
    /**
     * 整数反转
     * 给你一个 32 位的有符号整数 x ，返回将 x 中的数字部分反转后的结果。
     * 如果反转后整数超过 32 位的有符号整数的范围 [−231,  231 − 1] ，就返回 0。
     * 假设环境不允许存储 64 位整数（有符号或无符号）。
     *
     * 示例 1：
     * 输入：x = 123
     * 输出：321
     *
     * 示例 2：
     * 输入：x = -123
     * 输出：-321
     *
     * 示例 3：
     * 输入：x = 120
     * 输出：21
     *
     * 示例 4：
     * 输入：x = 0
     * 输出：0
     *
     * 提示：
     * -231 <= x <= 231 - 1
     */
    public static int reverse(int x) {
        int res = 0;
        while (x != 0) {
            // 防止溢出
            if (res < Integer.MIN_VALUE / 10 || res > Integer.MAX_VALUE / 10) {
                return 0;
            }
            int digit = x % 10;
            x /= 10;
            res = res * 10 + digit;
        }
        return res;
    }

    public static int reverse1(int x) {
        boolean neg = ((x >>> 31) & 1) == 1;
        // 如果x是负数不处理，正数则转化为负数
        x = neg ? x : -x;
        int m = Integer.MIN_VALUE / 10;
        int o = Integer.MIN_VALUE % 10;
        int res = 0;
        while (x != 0) {
            // res是负数，比m小，如果*10，必然小于MIN_VALUE
            // res是负数和m相等，*10刚好是MIN_VALUE,但是res加一个负数，肯定小于MIN_VALUE
            // MIN_VALUE=-2147483648  MIN_VALUE/10*10=-2147483640
            if (res < m || (res == m && x % 10 < o)) {
                return 0;
            }
            res = res * 10 + x % 10;
            x /= 10;
        }

        return neg ? res : Math.abs(res);
    }

    public static void main(String[] args) {
        System.out.println(Integer.MIN_VALUE);
        System.out.println(Integer.MIN_VALUE / 10 * 10);
    }
}
