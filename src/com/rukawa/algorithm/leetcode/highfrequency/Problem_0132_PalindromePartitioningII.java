package com.rukawa.algorithm.leetcode.highfrequency;

import java.util.List;

/**
 * Created with Intellij IDEA
 *
 * @Author：SuperHai
 * @Date：2020-09-19 11:05
 * @Version：1.0
 */
public class Problem_0132_PalindromePartitioningII {
    /**
     * 分割回文串
     * 给定一个字符串 s，将 s 分割成一些子串，使每个子串都是回文串。
     * 返回符合要求的 最少分割次数 。
     *
     * 示例 1：
     * 输入：s = "aab"
     * 输出：1
     * 解释：只需一次分割就可将 s 分割成 ["aa","b"] 这样两个回文子串。
     *
     * 示例 2：
     * 输入：s = "a"
     * 输出：0
     *
     * 示例 3：
     * 输入：s = "ab"
     * 输出：1
     *
     * 提示：
     * 1 <= s.length <= 2000
     * s 仅由小写英文字母组成
     */

    // 一个字符串至少要切分几刀能让切分出来的子串都是回文串
    // 从左到右的尝试模型
    public int minCut(String s) {
        if (s == null || s.length() < 2) {
            return 0;
        }
        char[] str = s.toCharArray();
        int n = str.length;
        // 预处理表map 正方形表，告诉l...r是否是回文
        boolean[][] checkMap = createCheckMap(str, n);
        int[] dp = new int[n + 1];
        dp[n] = 0;
        for (int i = n - 1; i >= 0; i--) {
            // 从i～n-1已经是回文了，一个部分
            if (checkMap[i][n - 1]) {
                dp[i] = 1;
            } else {
                // 尝试i～j
                int next = Integer.MAX_VALUE;
                for (int j = i; j < n; j++) {
                    // i～j已经是回文了，j+1往后能分为几块
                    if (checkMap[i][j]) {
                        next = Math.min(next, dp[j + 1]);
                    }
                }
                // j+1往后能最少分为几块，然后+i～j的这一块回文
                dp[i] = next + 1;
            }
        }
        // dp[0]是部分数，比如三个部分，只需要切两刀，所以返回两刀
        return dp[0] - 1;
    }

    // str[l...r]范围上是否是回文
    public boolean[][] createCheckMap(char[] str, int n) {
        boolean[][] map = new boolean[n][n];
        for (int i = 0; i < n - 1; i++) {
            map[i][i] = true;
            map[i][i + 1] = str[i] == str[i + 1];
        }
        // 从l...r是否是回文
        map[n - 1][n - 1] = true;
        for (int l = n - 3; l >= 0; l--) {
            for (int r = l + 2; r < n; r++) {
                map[l][r] = str[l] == str[r] && map[l + 1][r - 1];
            }
        }
        return map;
    }

//    // str[i...]至少分成几个部分会使每个部分都是回文
//    public int process(char[] str, int index) {
//        // 没字符串，不需要分
//        if (index == str.length) {
//            return 0;
//        }
//
//        int next = Integer.MAX_VALUE;
//        for (int end = index; end < str.length; end++) {
//            // str[index...end]已经是回文了，剩下部分怎么切分
////            if (str[index...end]) {
////                next = process(str, end + 1);
////            }
//        }
//        return next + 1;
//    }

}
