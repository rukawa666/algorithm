package com.rukawa.algorithm.types.advanced;

/**
 * @className: Code_C1_LongestSubstringWithoutRepeatingCharacters
 * @description: TODO 类描述
 * @author: 鎏川疯
 * @date: 2021/8/9 0009 7:48
 **/
public class Code_C1_LongestSubstringWithoutRepeatingCharacters {
    /**
     * 求一个字符串中，最长无重复字符子串长度
     */
    // 只要是子串或者子数组的问题，去考虑每个位置结尾的时候答案是什么？最终答案一定在其中

    /**
     * 思路：
     * 1、以17位置结尾的字符a，往左能推多远不重复，第一个决定因素是a上一次出现的位置，第二个决定因素是16位置的答案
     */
    public static int lengthOfLongestSubstring(String s) {
        if (s == null || s.equals("")) {
            return 0;
        }
        char[] str = s.toCharArray();
        int[] maps = new int[256];
        for (int i = 0; i < 256; i++) {
            maps[i] = -1;
        }
        maps[str[0]] = 1;
        int N = str.length;
        int len = 0;
        int pre = -1;
        int cur = 0;
        for (int i = 0; i < N; i++) {
            pre = Math.max(pre, maps[str[i]]);
            cur = i - pre;
            len = Math.max(len, cur);
            maps[str[i]] = i;
        }
        return len;
    }
}
