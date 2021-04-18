package com.rukawa.algorithm.types.dp.recursion.series3;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @Author: Administrator
 * @Date: 2021/4/7 0007 8:22
 */
public class Code02_PalindromeSubsequence {

    /**
     * 给定一个字符串str，返回这个字符串的最长回文子序列长度
     * 比如：str = "a12b3c43def2ghi1kpm"
     * 最长回文子序列是"1234321"或者"123c321"，返回长度7
     */
    public static int longestPalindromeSubsequence(String s) {
        if (s == null || s.length() == 0) {
            return 0;
        }
        char[] str = s.toCharArray();
        return process(str, 0, str.length - 1);
    }

    // str[L,R]最长回文子序列返回
    public static int process(char[] str, int L, int R) {
        if (L == R) {
            return 1;
        }
        if (L == R - 1) {
            return str[L] == str[R - 1] ? 2 : 1;
        }

        int p1 = process(str, L + 1, R - 1);
        int p2 = process(str, L + 1, R);
        int p3 = process(str, L, R - 1);
        int p4 = str[L] != str[R] ? 0 : 2 + process(str, L + 1, R - 1);
        return Math.max(p1, Math.max(p2, Math.max(p3, p4)));
    }

    public static int dp(String s) {
        if (s == null || s.length() == 0) {
            return 0;
        }
        char[] str = s.toCharArray();
        int N = str.length;
        int[][] dp = new int[N][N];
        dp[N - 1][N - 1] = 1;
        for (int i = 0; i < N - 1; i++) {
            dp[i][i] = 1;
            dp[i][i + 1] = str[i] == str[i + 1] ? 2 : 1;
        }
        for (int L = N - 3; L >= 0; L--) {
            for (int R = L + 2; R < N; R++) {
                dp[L][R] = Math.max(dp[L + 1][R], dp[L][R - 1]);
                if (str[L] == str[R]) {
                    dp[L][R] = Math.max(dp[L][R], 2 + dp[L + 1][R - 1]);
                }
            }
        }
        return dp[0][N - 1];
    }
}
