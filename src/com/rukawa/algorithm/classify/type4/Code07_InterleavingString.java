package com.rukawa.algorithm.classify.type4;

/**
 * create by hqh on 2022/9/27
 */
public class Code07_InterleavingString {
    /**
     * https://leetcode.cn/problems/interleaving-string/
     * 交错字符串
     * 给定三个字符串 s1、s2、s3，请你帮忙验证 s3 是否是由 s1 和 s2 交错 组成的。
     *
     * 两个字符串 s 和 t 交错 的定义与过程如下，其中每个字符串都会被分割成若干 非空 子字符串：
     * s = s1 + s2 + ... + sn
     * t = t1 + t2 + ... + tm
     * |n - m| <= 1
     * 交错 是 s1 + t1 + s2 + t2 + s3 + t3 + ... 或者 t1 + s1 + t2 + s2 + t3 + s3 + ...
     * 注意：a + b 意味着字符串 a 和 b 连接。
     */
    public boolean isInterleave(String s1, String s2, String s3) {
        // 样本对应模型：
        // dp[n+1][m+1] 含义：str1拿i个字符，str2拿j个字符，能否交错组成str的i+j个字符
        /**
         * str1: aaabsk
         * str2: aacfk
         * str:  aaacaabfskk
         *       12221112121
         * 分析可能性：
         *  1.str[i+j-1]==str1[i-1]的时候，dp[i-1][j]的结果是true，此时dp[i][j]也等于true
         *  2.str[i+j-1]==str2[j-1]的时候，dp[i][j-1]的结果是true，此时dp[i][j]也等于true
         *  最后两种可能性求'||'
         */
        if (s1 == null || s2 == null || s3 == null) {
            return false;
        }
        if (s1.length() + s2.length() != s3.length()) {
            return false;
        }
        char[] str1 = s1.toCharArray();
        char[] str2 = s2.toCharArray();
        char[] str = s3.toCharArray();
        boolean[][] dp = new boolean[s1.length() + 1][s2.length() + 1];
        dp[0][0] = true;
        // 填写0行
        for (int j = 1; j < str2.length; j++) {
            if (str2[j - 1] != str[j - 1]) {
                break;
            }
            dp[0][j] = true;
        }
        // 填写0列
        for (int i = 1; i <= str1.length; i++) {
            if (str1[i - 1] != str[i - 1]) {
                break;
            }
            dp[i][0] = true;
        }

        for (int i = 1; i <= str1.length; i++) {
            for (int j = 1; j <= str2.length; j++) {
                boolean p1 = str[i + j - 1] == str1[i - 1] && dp[i - 1][j];
                boolean p2 = str[i + j - 1] == str2[j - 1] && dp[i][j - 1];
                dp[i][j] = (p1 || p2);
            }
        }
        return dp[str1.length][str2.length];
    }
}
