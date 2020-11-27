package com.rukawa.algorithm.trainingcamp.trainingcamp3.class3;

/**
 * Created with Intellij IDEA
 *
 * @Author：SuperHai
 * @Date：2020-08-25 7:09
 * @Version：1.0
 */
public class Code05_LongestCommonSubString {
    /**
     * 请注意区分子串和子序列的不同
     * 给定两个字符串str1和str2，请两个字符串的最长公共子串
     *
     * 动态规划的空间压缩技巧
     */
    public static String lcsString1(String s1, String s2) {
        if (s1 == null || s1.equals("") || s2 == null || s2.equals("")) {
            return "";
        }

        char[] str1 = s1.toCharArray();
        char[] str2 = s2.toCharArray();
        int[][] dp = getDP(str1, str2, str1.length, str2.length);
        int end = 0;
        int max = 0;
        for (int i = 0; i < str1.length; i++) {
            for (int j = 0; j < str2.length; j++) {
                if (dp[i][j] > max) {
                    end = i;
                    max = dp[i][j];
                }
            }
        }
        return s1.substring(end - max + 1, end + 1);
    }

    public static int[][] getDP(char[] str1, char[] str2, int N, int M) {
        int[][] dp = new int[N][M];
        for (int row = 0; row < N; row++) {
            if (str1[row] == str2[0]) {
                dp[row][0] = 1;
            }
        }

        for (int col = 0; col < M; col++) {
            if (str2[col] == str1[0]) {
                dp[0][col] = 1;
            }
        }

        for (int row = 1; row < N; row++) {
            for (int col = 1; col < M; col++) {
                if (str1[row] == str2[col]) {
                    dp[row][col] = dp[row - 1][col - 1] + 1;
                }
            }
        }
        return dp;
    }


    public static String lcsString2(String s1, String s2) {
        if (s1 == null || s1.equals("") || s2 == null || s2.equals("")) {
            return "";
        }

        char[] str1 = s1.toCharArray();
        char[] str2 = s2.toCharArray();
        int row = 0;
        int col = str2.length - 1;
        int max = 0;
        int end = 0;
        while (row < str1.length) {
            int i = row;
            int j = col;
            int len = 0;
            while (i < str1.length && j < str2.length) {
                if (str1[i] != str2[j]) {
                    len = 0;
                } else {
                    len++;
                }

                if (len > max) {
                    max = len;
                    end = i;
                }
                i++;
                j++;
            }

            if (col > 0) {
                col--;
            } else {
                row++;
            }
        }
        return s1.substring(end - max + 1, end + 1);
    }

    public static void main(String[] args) {
        String str1 = "ABC1234567DEFG";
        String str2 = "HIJKL1234567MNOP";
        System.out.println(lcsString1(str1, str2));
        System.out.println(lcsString2(str1, str2));

    }
}
