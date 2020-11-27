package com.rukawa.algorithm.trainingcamp.top100likedquestions;

/**
 * Created with Intellij IDEA
 *
 * @Author：SuperHai
 * @Date：2020-10-11 0:55
 * @Version：1.0
 */
public class Problem_0395_LongestSubstringWithAtLeastKRepeatingCharacters {
    /**
     * 至少有K个重复字符的最长子串
     * 找到给定字符串（由小写字符组成）中的最长子串 T ， 要求 T 中的每一字符出现次数都不少于 k 。输出 T 的长度。
     *
     * 示例 1:
     * 输入:
     * s = "aaabb", k = 3
     * 输出:
     * 3
     * 最长子串为 "aaa" ，其中 'a' 重复了 3 次。
     *
     * 示例 2:
     * 输入:
     * s = "ababbc", k = 2
     * 输出:
     * 5
     * 最长子串为 "ababb" ，其中 'a' 重复了 2 次， 'b' 重复了 3 次。
     */

    public int longestSubstring1(String s, int k) {
        char[] str = s.toCharArray();
        int N = str.length;
        int max = 0;
        for (int i = 0; i < N; i++) {
            int[] count = new int[256];
            int collect = 0;
            int satisfy = 0;
            for (int j = i; j < N; j++) {
                if (count[str[j]] == 0) {
                    collect++;
                }
                if (count[str[j]] == k - 1) {
                    satisfy++;
                }
                count[str[j]]++;
                if (collect == satisfy) {
                    max = Math.max(max, j - i + 1);
                }
            }
        }
        return max;
    }

    public int longestSubstring2(String s, int k) {
        return 0;
    }
}
