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
        char[] str = manacherString(s);
        // 回文半径
        int[] pArr = new int[str.length];
        int C = -1;
        // 最右扩成功的位置，在下一个位置
        int R = -1;
        int max = Integer.MIN_VALUE;
        int index = 0;
        for (int i = 0; i < str.length; i++) {
            pArr[i] = i < R ? Math.min(R - i, pArr[2 * C - i]) : 1;
            while (i + pArr[i] < str.length && i - pArr[i] > -1) {
                if (str[i + pArr[i]] == str[i - pArr[i]]) {
                    pArr[i]++;
                } else {
                    break;
                }
            }

            if (i + pArr[i] > R) {
                R = i + pArr[i];
                C = i;
            }

            if (max < pArr[i]) {
                max = pArr[i];
                index = i;
            }
        }
        max = max - 1;
        index = (index - 1) / 2;
        return s.substring((max & 1) == 0 ? index - (max / 2) + 1 : index - (max / 2), index + (max / 2) + 1);
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
