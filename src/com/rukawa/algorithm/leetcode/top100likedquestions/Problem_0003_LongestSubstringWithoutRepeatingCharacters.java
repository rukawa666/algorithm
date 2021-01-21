package com.rukawa.algorithm.leetcode.top100likedquestions;

/**
 * Created with Intellij IDEA
 *
 * @Author：SuperHai
 * @Date：2020-09-20 8:29
 * @Version：1.0
 */
public class Problem_0003_LongestSubstringWithoutRepeatingCharacters {
    /**
     * 无重复字符的最长子串
     * 给定一个字符串，请你找出其中不含有重复字符的 最长子串 的长度。
     *
     * 示例 1:
     * 输入: s = "abcabcbb"
     * 输出: 3
     * 解释: 因为无重复字符的最长子串是 "abc"，所以其长度为 3。
     *
     * 示例 2:
     * 输入: s = "bbbbb"
     * 输出: 1
     * 解释: 因为无重复字符的最长子串是 "b"，所以其长度为 1。
     *
     * 示例 3:
     * 输入: s = "pwwkew"
     * 输出: 3
     * 解释: 因为无重复字符的最长子串是 "wke"，所以其长度为 3。
     *      请注意，你的答案必须是 子串 的长度，"pwke" 是一个子序列，不是子串。
     *
     * 示例 4:
     * 输入: s = ""
     * 输出: 0
     *
     * 提示：
     * 0 <= s.length <= 5 * 104
     * s 由英文字母、数字、符号和空格组成
     */
    public int lengthOfLongestSubstring(String s) {
        /**
         * 假设以i位置结尾的情况下，最长无重复的子串，在所有的可能性中，答案必在其中
         */
        if (s == null || s.equals("")) {
            return 0;
        }
        char[] str = s.toCharArray();
        int[] map = new int[256];
        for (int i = 0; i < 256; i++) {
            map[i] = -1;
        }
        int len = 0;
        // i-1位置结尾的情况下，往左推，推不动的位置
        int pre = -1;
        int cur = 0;
        for (int i = 0; i < str.length; i++) {
            // 上一步推不动的位置和当前字符上次出现的位置离最近的位置
            /**
             * 举例：
             * 当前是17位置，pre推到10位置推不动，17位置的字符在13位置出现过，所以17位置只能推到13位置
             */
            pre = Math.max(pre, map[str[i]]);
            cur = i - pre;
            len = Math.max(len, cur);
            map[str[i]] = i;
        }
        return len;
    }
}
