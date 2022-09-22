package com.rukawa.algorithm.leetcode.top100likedquestions;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/**
 * Created with Intellij IDEA
 *
 * @Author：SuperHai
 * @Date：2020-09-19 10:35
 * @Version：1.0
 */
public class Problem_0128_LongestConsecutiveSequence {
    /**
     * 最长连续序列
     * 给定一个未排序的整数数组，找出最长连续序列的长度。
     *
     * 要求算法的时间复杂度为 O(n)。
     *
     * 示例:
     *
     * 输入: [100, 4, 200, 1, 3, 2]
     * 输出: 4
     * 解释: 最长连续序列是 [1, 2, 3, 4]。它的长度为 4。
     */

    public int longestConsecutive(int[] nums) {
        // key：连续区间的头和尾，value：连续区间的长度
        HashMap<Integer, Integer> map = new HashMap<>();
        int len = 0;
        for (int num : nums) {
            if (!map.containsKey(num)) {
                map.put(num, 1);
                int preLen = map.containsKey(num - 1) ? map.get(num - 1) : 0;
                int postLen = map.containsKey(num + 1) ? map.get(num + 1) : 0;
                int all = preLen + postLen + 1;
                // 区间合并
                map.put(num - preLen, all);
                map.put(num + postLen, all);
                len = Math.max(len, all);
            }
        }
        return len;
    }

    public int longestConsecutive1(int[] nums) {
        Set<Integer> set = new HashSet<>();
        for (int num : nums) {
            set.add(num);
        }
        int longestStreak = 0;
        for (int num : set) {
            if (!set.contains(num - 1)) {
                int curNum = num;
                int curStreak = 1;
                while (set.contains(curNum + 1)) {
                    curNum += 1;
                    curStreak += 1;
                }
                longestStreak = Math.max(longestStreak, curStreak);
            }
        }
        return longestStreak;
    }
}
