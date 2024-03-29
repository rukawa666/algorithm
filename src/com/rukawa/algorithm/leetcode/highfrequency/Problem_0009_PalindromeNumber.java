package com.rukawa.algorithm.leetcode.highfrequency;

/**
 * create by hqh on 2022/3/10
 */
public class Problem_0009_PalindromeNumber {
    /**
     * 回文数
     * 给你一个整数 x ，如果 x 是一个回文整数，返回 true ；否则，返回 false 。
     * 回文数是指正序（从左向右）和倒序（从右向左）读都是一样的整数。
     * 例如，121 是回文，而 123 不是。
     *
     * 示例 1：
     * 输入：x = 121
     * 输出：true
     *
     * 示例 2：
     * 输入：x = -121
     * 输出：false
     * 解释：从左向右读, 为 -121 。 从右向左读, 为 121- 。因此它不是一个回文数。
     *
     * 示例 3：
     * 输入：x = 10
     * 输出：false
     * 解释：从右向左读, 为 01 。因此它不是一个回文数。
     *  
     *
     * 提示：
     * -231 <= x <= 231 - 1
     */
    public static boolean isPalindrome(int x) {
        // 负数，或者数字不是0，但是以0结尾的数，都不是回文数
        if (x < 0 || (x % 10 == 0 && x != 0)) {
            return false;
        }
        int reverseNumber = 0;
        while (x > reverseNumber) {
            reverseNumber = reverseNumber * 10 + x % 10;
            x /= 10;
        }
        return x == reverseNumber || x == reverseNumber / 10;
    }

    public static void main(String[] args) {
        System.out.println(isPalindrome(121));
    }
}
