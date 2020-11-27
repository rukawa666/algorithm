package com.rukawa.algorithm.base.class12;

/**
 * Created with Intellij IDEA
 *
 * @Author：SuperHai
 * @Date：2020-07-29 23:46
 * @Version：1.0
 */
public class Code05_LongestCommonSubsequence {

    /**
     * 两个字符串的最长公共子序列问题
     * @param str1
     * @param str2
     * @return
     */
    public static int lcse(char[] str1, char[] str2) {

        int[][] dp = new int[str1.length][str2.length];
        dp[0][0] = str1[0] == str2[0] ? 1 : 0;

        // 填写第0行的位置
        for (int i = 1; i < str1.length; i++) {
            dp[i][0] = Math.max(dp[i - 1][0], str1[i] == str2[0] ? 1 : 0);
        }

        // 填写第0列的位置
        for (int j = 1; j < str2.length; j++) {
            dp[0][j] = Math.max(dp[0][j - 1], str1[0] == str2[j] ? 1 : 0);
        }

        for (int i = 1; i < str1.length; i++) {
            for (int j = 1; j < str2.length; j++) {

                /**
                 * 1、最长公共子序列可能既不以str1[i]结尾，也不以str2[j]结尾
                 * 2、最长公共子序列以str1[i]结尾，但是不以str2[j]结尾
                 * 3、最长公共子序列不以str1[i]结尾，但是以str2[j]结尾
                 * 4、最长公共子序列既是以str1[i]结尾，也以str2[j]结尾
                 */
                // 可能性2和3取最大值
                // 可能性2：dp[i-1][j]
                // 可能性3：dp[i][j-1]
                dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);

                // 如果可能性4存在，则拿上面的最大值和可能性4取最大值
                // 可能性4：子序列是以dp[i][j]
                if (str1[i] == str2[j]) {
                    dp[i][j] = Math.max(dp[i][j], dp[i - 1][j - 1] + 1);
                }

                // 可能性1：dp[i-1][j-1],舍弃1的原因，可能性2和3不可能比1小
            }
        }

        return dp[str1.length - 1][str2.length - 1];
    }

    public static void main(String[] args) {
        String s1 = "a12f3r4k5";
        String s2 = "bv1n2k3l4op5";
        System.out.println(lcse(s1.toCharArray(), s2.toCharArray()));
    }

}
