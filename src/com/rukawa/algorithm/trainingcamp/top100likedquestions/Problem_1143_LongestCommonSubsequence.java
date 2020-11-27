package com.rukawa.algorithm.trainingcamp.top100likedquestions;

/**
 * Created with Intellij IDEA
 *
 * @Author：SuperHai
 * @Date：2020-09-25 22:10
 * @Version：1.0
 */
public class Problem_1143_LongestCommonSubsequence {
    /**
     * 最长公共子序列
     * 给定两个字符串 text1 和 text2，返回这两个字符串的最长公共子序列的长度。
     * 一个字符串的 子序列 是指这样一个新的字符串：它是由原字符串在不改变字符的相对顺序的情况下删除某些字符（也可以不删除任何字符）后组成的新字符串。
     * 例如，"ace" 是 "abcde" 的子序列，但 "aec" 不是 "abcde" 的子序列。两个字符串的「公共子序列」是这两个字符串所共同拥有的子序列。
     * 若这两个字符串没有公共子序列，则返回 0。
     *
     * 示例 1:
     * 输入：text1 = "abcde", text2 = "ace"
     * 输出：3
     * 解释：最长公共子序列是 "ace"，它的长度为 3。
     *
     * 示例 2:
     * 输入：text1 = "abc", text2 = "abc"
     * 输出：3
     * 解释：最长公共子序列是 "abc"，它的长度为 3。
     *
     * 示例 3:
     * 输入：text1 = "abc", text2 = "def"
     * 输出：0
     * 解释：两个字符串没有公共子序列，返回 0。
     *
     * 提示:
     * 1 <= text1.length <= 1000
     * 1 <= text2.length <= 1000
     * 输入的字符串只含有小写英文字符。
     */
    public int longestCommonSubsequence(String text1, String text2) {
        if (text1 == null || text1.length() == 0 || text2 == null || text2.length() == 0) {
            return 0;
        }
        char[] str1 = text1.toCharArray();
        char[] str2 = text2.toCharArray();
        int N = str1.length;
        int M = str2.length;
        int[][] dp = new int[N][M];
        dp[0][0] = str1[0] == str2[0] ? 1 : 0;
        for (int col = 1; col < M; col++) {
            dp[0][col] = str1[0] == str2[col] ? 1 : dp[0][col - 1];
        }
        for (int row = 1; row < N; row++) {
            dp[row][0] = str1[row] == str2[0] ? 1 : dp[row - 1][0];
        }

        for (int row = 1; row < N; row++) {
            for (int col = 1; col < M; col++) {
                dp[row][col] = Math.max(dp[row - 1][col], dp[row][col - 1]);
                if (str1[row] == str2[col]) {
                    dp[row][col] = Math.max(dp[row][col], dp[row - 1][col - 1] + 1);
                }
            }
        }
        return dp[N - 1][M - 1];
    }


    // 此时会超时时间限制
    public int lcs(String text1, String text2) {
        if (text1 == null || text1.length() == 0 || text2 == null || text2.length() == 0) {
            return 0;
        }
        char[] str1 = text1.toCharArray();
        char[] str2 = text2.toCharArray();
        int N = str1.length;
        int M = str2.length;
        return process(str1, str2, N - 1, M - 1);
    }

    public static int process(char[] str1, char[] str2, int i, int j) {
        if (i == 0 && j == 0) {
            return str1[i] == str2[j] ? 1 : 0;
        }
        if (i == 0) {
            return (str1[i] == str2[j] || process(str1, str2, i, j - 1) == 1) ? 1 : 0;
        }

        if (j == 0) {
            return (str1[i] == str2[j] || process(str1, str2, i - 1, j) == 1) ? 1 : 0;
        }

        int p1 = process(str1, str2, i - 1, j - 1);
        int p2 = process(str1, str2, i - 1, j);
        int p3 = process(str1, str2, i, j - 1);
        int p4 = 0;
        if (str1[i] == str2[j]) {
            p4 = p1 + 1;
        }
        return Math.max(Math.max(p1, p2), Math.max(p3, p4));
    }
}
