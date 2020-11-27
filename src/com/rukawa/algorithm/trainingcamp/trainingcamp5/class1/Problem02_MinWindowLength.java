package com.rukawa.algorithm.trainingcamp.trainingcamp5.class1;

/**
 * Created with Intellij IDEA
 *
 * @Author：SuperHai
 * @Date：2020-10-31 23:35
 * @Version：1.0
 */
public class Problem02_MinWindowLength {
    /**
     * 给定字符串str1和str2，求str1的子串中含有str2所有字符的最小子串长度.
     * 【举例】
     * str1="abcde"，str2="ac"
     * 因为"abc"包含 str2 所有的字符，并且在满足这一条件的str1的所有子串中，"abc"是 最短的，返回3。
     * str1="12345"，str2="344" 最小包含子串不存在，返回0。
     */

    public static int minLength(String s1, String s2) {
        if (s1 == null || s2 == null || s1.length() < s2.length()) {
            return Integer.MAX_VALUE;
        }

        char[] str1 = s1.toCharArray();
        char[] str2 = s2.toCharArray();
        // 账本记录
        int[] map = new int[256];  // map[37] = 4 37字符出现了4次
        for (int i = 0; i != str2.length; i++) {
            map[str2[i]]++;
        }

        int left = 0;
        int right = 0;
        int all = str2.length;
        int minLen = Integer.MAX_VALUE;
        // [left, right) 等效于[left, right - 1] [0,0)
        // right右扩 left=0
        while (right != str1.length) {
            map[str1[right]]--;
            // 有效还款
            if (map[str1[right]] >= 0) {
                all--;
            }
            // 还完款，开始缩左侧窗口
            if (all == 0) {
                // 开头字符<0,还多了
                while (map[str1[left]] < 0) {
                    map[str1[left++]]++;
                }
                minLen = Math.min(minLen, right - left + 1);
                all++;
                map[str1[left++]]++;
            }

            right++;
        }
        return minLen == Integer.MAX_VALUE ? 0 : minLen;
    }

    public static void main(String[] args) {
        String str1 = "adabbca";
        String str2 = "acb";
        System.out.println(minLength(str1, str2));
    }
}
