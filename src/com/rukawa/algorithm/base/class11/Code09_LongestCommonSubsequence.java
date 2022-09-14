package com.rukawa.algorithm.base.class11;

/**
 * Created with Intellij IDEA
 *
 * @Author：SuperHai
 * @Date：2020-07-29 23:46
 * @Version：1.0
 */
public class Code09_LongestCommonSubsequence {

    /**
     * 两个字符串的最长公共子序列问题
     */
    // 暴力递归
    public static int lcse1(String text1, String text2) {
        if (text1 == null || text2 == null || text1.length() == 0 || text2.length() == 0) {
            return 0;
        }
        char[] str1 = text1.toCharArray();
        char[] str2 = text2.toCharArray();
        return process1(str1, str2, str1.length - 1, str2.length - 1);

    }

    // str1[0...i]和str2[0...j]这一段最长公共子序列是多长？
    // 除此之外不关心
    public static int process1(char[] srt1, char[] str2, int i, int j) {
        if (i == 0 && j == 0) {
            return srt1[i] == str2[j] ? 1 : 0;
        } else if (i == 0) {
            if (srt1[i] == str2[j]) {
                return 1;
            } else {
                return process1(srt1, str2, i, j - 1);
            }
        } else if (j == 0) {
            if (srt1[i] == str2[j]) {
                return 1;
            } else {
                return process1(srt1, str2, i - 1, j);
            }
        } else {
            // 讨论str1[0...i]和str2[0...j]这一段的可能性
            // 样本对应模型 往往讨论当前的结尾该如何组织可能性
            /**
             * 可能性1：子序列不以i位置结尾，但是有可能考虑以j位置结尾  a123d a12e3  以j位置的3结尾
             * 可能性2：子序列有可能考虑以i位置结尾，但是不以j位置结尾  12c3 a123c   以i位置的3结尾
             * 可能性3：子序列以i位置和j位置结尾
             * 可能性1和可能性2有交集：都不以i位置和j位置的结尾考虑，但是求的是长度，不影响
             */
            // 可能性1
            int p1 = process1(srt1, str2, i - 1, j);
            // 可能性2
            int p2 = process1(srt1, str2, i, j - 1);
            // 可能性3
            int p3 = srt1[i] == str2[j] ? (1 + process1(srt1, str2, i - 1, j - 1)) : 0;
            return Math.max(p1, Math.max(p2, p3));
        }
    }

    public static int lcse2(String text1, String text2) {
        if (text1 == null || text2 == null || text1.length() == 0 || text2.length() == 0) {
            return 0;
        }
        char[] str1 = text1.toCharArray();
        char[] str2 = text2.toCharArray();
        int m = str1.length;
        int n = str2.length;
        int[][] dp = new int[m][n];
        dp[0][0] = str1[0] == str2[0] ? 1 : 0;
        for (int j = 1; j < n; j++) {
            dp[0][j] = Math.max(dp[0][j - 1], str1[0] == str2[j] ? 1 : 0);
        }

        for (int i = 1; i < m; i++) {
            dp[i][0] = Math.max(dp[i - 1][0], str1[i] == str2[0] ? 1 : 0);
        }

        // 讨论str1[0...i]和str2[0...j]这一段的可能性
        // 样本对应模型 往往讨论当前的结尾该如何组织可能性
        /**
         * 可能性1：子序列不以i位置结尾，但是有可能考虑以j位置结尾  a123d a12e3  以j位置的3结尾
         * 可能性2：子序列有可能考虑以i位置结尾，但是不以j位置结尾  12c3 a123c   以i位置的3结尾
         * 可能性3：子序列以i位置和j位置结尾
         * 可能性1和可能性2有交集：都不以i位置和j位置的结尾考虑，但是求的是长度，不影响
         */
        for (int i = 1; i < m; i++) {
            for (int j = 1; j < n; j++) {
                int p1 = dp[i][j - 1];
                int p2 = dp[i - 1][j];
                int p3 = str1[i] == str2[j] ? dp[i - 1][j - 1] + 1: 0;
                dp[i][j] = Math.max(p1, Math.max(p2, p3));
            }
        }
        return dp[m - 1][n - 1];
    }


    // 样本对应模型
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
