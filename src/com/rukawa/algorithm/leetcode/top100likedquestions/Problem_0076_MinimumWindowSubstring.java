package com.rukawa.algorithm.leetcode.top100likedquestions;

import com.sun.deploy.panel.ITreeNode;

import java.util.HashMap;
import java.util.Map;

/**
 * Created with Intellij IDEA
 *
 * @Author：SuperHai
 * @Date：2020-09-06 10:26
 * @Version：1.0
 */
public class Problem_0076_MinimumWindowSubstring {
    /**
     * 最小覆盖子串
     * 给你一个字符串 S、一个字符串 T 。请你设计一种算法，可以在 O(n) 的时间复杂度内，从字符串 S 里面找出：包含 T 所有字符的最小子串。
     *
     * 示例：
     * 输入：S = "ADOBECODEBANC", T = "ABC"
     * 输出："BANC"
     *
     * 提示：
     * 如果 S 中不存这样的子串，则返回空字符串 ""。
     * 如果 S 中存在这样的子串，我们保证它是唯一的答案。
     */

    public static String minWindow(String s, String t) {
        if ( s.length() < t.length()) {
            return "";
        }
        char[] str = s.toCharArray();
        char[] target = t.toCharArray();
        int[] map = new int[256];
        // 记录每个字符的次数
        for (char cur : target) {
            map[cur]++;
        }

        int l = 0;
        int r = 0;
        int all = target.length;
        int minLen = Integer.MAX_VALUE;
        int pos1 = -1;
        int pos2 = -1;
        while (r != str.length) {
            map[str[r]]--;
            if (map[str[r]] >= 0) {
                all--;
            }

            // 如果还完了，str1扩出来的位置已经包含整个str2
            if (all == 0) {
                while (map[str[l]] < 0) {
                    map[str[l++]]++;
                }
                if (minLen > r - l + 1) {
                    minLen = r - l + 1;
                    pos1 = l;
                    pos2 = r;
                }
                all++;
                map[str[l++]]++;
            }
            r++;
        }
        return minLen == Integer.MAX_VALUE ? "" : s.substring(pos1, pos2 + 1);
    }

    public static void main(String[] args) {
        String s = "ADOBECODEBANC";
        String t = "ABC";
        System.out.println(minWindow(s, t));
    }
}
