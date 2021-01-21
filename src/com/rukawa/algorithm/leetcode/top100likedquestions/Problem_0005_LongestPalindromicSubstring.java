package com.rukawa.algorithm.leetcode.top100likedquestions;

/**
 * Created with Intellij IDEA
 *
 * @Author：SuperHai
 * @Date：2020-09-20 8:29
 * @Version：1.0
 */
public class Problem_0005_LongestPalindromicSubstring {
    /**
     * 最长回文子串
     * 给你一个字符串 s，找到 s 中最长的回文子串。
     *
     * 示例 1：
     * 输入：s = "babad"
     * 输出："bab"
     * 解释："aba" 同样是符合题意的答案。
     *
     * 示例 2：
     * 输入：s = "cbbd"
     * 输出："bb"
     *
     * 示例 3：
     * 输入：s = "a"
     * 输出："a"
     *
     * 示例 4：
     * 输入：s = "ac"
     * 输出："a"
     *  
     * 提示：
     * 1 <= s.length <= 1000
     * s 仅由数字和英文字母（大写和/或小写）组成
     */
    public String longestPalindrome(String s) {
        /**
         * 最长回文子串，用Manacher算法实现
         */
        if (s == null || s.length() == 0) {
            return "";
        }
        char[] chs = manacherString(s);
        // 回文半径
        int[] pArr = new int[chs.length];
        int C = -1;
        // 最右扩成功的位置，在下一个位置
        int R = -1;
        int max = Integer.MIN_VALUE;

        for (int i = 0; i < chs.length; i++) {

        }
        return "";

    }

    public static char[] manacherString(String str) {
        char[] chs = str.toCharArray();
        char[] res = new char[chs.length * 2 + 1];
        int index = 0;
        for (int i = 0; i < res.length; i++) {
            res[i] = (i & 1) == 0 ? '#' : chs[index++];
        }
        return res;
    }
}
