package com.rukawa.algorithm.trainingcamp.trainingcamp3.class3;

/**
 * Created with Intellij IDEA
 *
 * @Author：SuperHai
 * @Date：2020-08-25 7:08
 * @Version：1.0
 */
public class Code04_LongestCommonSubSequence {
    /**
     * 请注意区分子串和子序列的不同，给定两个字符串str1和str2，求两个字符的最长公共子序列
     *
     * 动态规划的空间压缩技巧
     */

    public static int lcs(String text1, String text2) {
        char[] str1 = text1.toCharArray();
        char[] str2 = text2.toCharArray();
        int N = str1.length;
        int M = str2.length;
        return process(str1, str2, N - 1, M -1);
    }

    // str1[0....i1] 与 str2[0......i2]的最长公共子序列长度是多少？
    public static int process(char[] str1, char[] str2, int i1, int i2) {
        if (i1 == 0 && i2 == 0) {
            return str1[i1] == str2[i2] ? 1 : 0;
        }
        // i1 和 i2 不同时为0
        if (i1 == 0) { // str1[0..0] str2[0...i2 - 1]
            return ((str1[i1] == str2[i2]) || process(str1, str2, i1, i2 - 1) == 1) ? 1 : 0;
        }

        if (i2 == 0) {
            return ((str1[i1] == str2[i2]) || process(str1, str2, i1 - 1, i2) == 1) ? 1 : 0;
        }
        // i1 和 i2 都不是0
        // 最长公共子序列结尾，不是以str1[i1]与str2[i2]结尾的
        int p1 = process(str1, str2, i1 - 1, i2 - 1);
        int p2 = process(str1, str2, i1, i2 - 1);
        int p3 = process(str1, str2, i1 - 1, i2);
        int p4 = 0;
        if (str1[i1] == str2[i2]) {
            p4 = p1 + 1;
        }
        return Math.max(Math.max(p1, p2), Math.max(p3, p4));
    }

    public static int dp(String text1, String text2) {

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

    // 最长公共子序列是什么
    public static String lcse(String str1, String str2) {
        if (str1 == null || str2 == null || str1.equals("") || str2.equals("")) {
            return "";
        }
        char[] chs1 = str1.toCharArray();
        char[] chs2 = str2.toCharArray();
        int[][] dp = getDP(chs1, chs2);
        int m = chs1.length - 1;
        int n = chs2.length - 1;
        char[] res = new char[dp[m][n]];
        int index = res.length - 1;
        while (index >= 0) {
            if (n > 0 && dp[m][n] == dp[m][n - 1]) {
                n--;
            } else if (m > 0 && dp[m][n] == dp[m - 1][n]) {
                m--;
            } else {
                res[index--] = chs1[m];
                m--;
                n--;
            }
        }
        return String.valueOf(res);
    }

    public static int[][] getDP(char[] str1, char[] str2) {
        int[][] dp = new int[str1.length][str2.length];
        dp[0][0] = str1[0] == str2[0] ? 1 : 0;
        for (int i = 1; i < str1.length; i++) {
            dp[i][0] = Math.max(dp[i - 1][0], str1[i] == str2[0] ? 1 : 0);
        }
        for (int j = 1; j < str2.length; j++) {
            dp[0][j] = Math.max(dp[0][j - 1], str1[0] == str2[j] ? 1 : 0);
        }
        for (int i = 1; i < str1.length; i++) {
            for (int j = 1; j < str2.length; j++) {
                dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);
                if (str1[i] == str2[j]) {
                    dp[i][j] = Math.max(dp[i][j], dp[i - 1][j - 1] + 1);
                }
            }
        }
        return dp;
    }

    public static void main(String[] args) {
        String str1 = "A1BC23Z4";
        String str2 = "12O3YU4P";
		System.out.println(lcse(str1, str2));

        System.out.println(lcs(str1, str2));
        System.out.println(dp(str1, str2));

    }
}
