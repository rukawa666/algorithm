package com.rukawa.algorithm.leetcode.top100likedquestions;

/**
 * Created with Intellij IDEA
 *
 * @Author：SuperHai
 * @Date：2020-10-11 9:10
 * @Version：1.0
 */
public class Problem_0340_LongestSubstringWithAtMostKDistinctCharacters {
    /**
     * 至多包含 K 个不同字符的最长子串
     * give a string, find the length of the
     */
    public static int lengthOfLongestSubstringKDistinct(String s, int k) {
        if (s == null || s.length() == 0 || k < 1) {
            return 0;
        }
        char[] str = s.toCharArray();
        int n = str.length;
        int[] count = new int[256];
        int diff = 0;
        int r = 0;
        int res = 0;
        for (int i = 0; i < n; i++) {
            while (r < n && (diff < k || (diff == k && count[str[r]] > 0))) {
                diff += count[str[r]] == 0 ? 1 : 0;
                count[str[r++]]++;
            }

            res = Math.max(res, r - i);
            diff -= count[str[i]] == 1 ? 1 : 0;
            count[str[i]]--;
        }
        return res;
    }
}
