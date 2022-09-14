package com.rukawa.algorithm.base.class12;

/**
 * create by hqh on 2022/9/7
 */
public class Code01_PalindromeSubsequence {
    /**
     * 给定一个字符串str，返回这个字符串的最长回文子序列的长度
     * 比如：str = "a12b3c43def2ghi1kpm"
     * 最长回文子序列是"1234321"或者"123c321",返回长度7
     */

    //笨办法 字符串str逆序得到str1，str和str1的最长公共子序列就是str的最长回文子序列的长度

    public static int longestPalindromeSubSeq1(String s) {
        if (s == null || s.length() == 0) {
            return 0;
        }
        char[] str = s.toCharArray();
        return func1(str, 0, str.length - 1);
    }

    // str[l...r]位置上的最长回文子序列
    public static int func1(char[] str, int l, int r) {
        if (l == r) {
            return 1;
        }
        // 有两个字符，aa
        if (l == r - 1) {
            return str[l] == str[r] ? 2 : 1;
        }
        // 普遍情况
        /**
         * 可能性分析：
         * 情况1：最长回文子序列既不以i开头，也不以j结尾  "a12321b"
         * 情况2：以i开头，但是不以j结尾  "123a21b"
         * 情况3：不以i开头，但是以j结尾 "a12321"
         * 情况4：以i开头，也以j结尾 "1ab23cd21"
         * 范围尝试模型：讨论开头和结尾
         */
        int p1 = func1(str, l + 1, r - 1);
        int p2 = func1(str, l, r - 1);
        int p3 = func1(str, l + 1, r);
        // 加2是因为l位置和r位置是回文，长度是2
        int p4 = str[l] == str[r] ? func1(str, l + 1, r - 1) + 2 : 0;
        return Math.max(p1, Math.max(p2, Math.max(p3, p4)));
    }

    // dp优化
    public static int longestPalindromeSubSeq2(String s) {
        if (s == null || s.length() == 0) {
            return 0;
        }
        char[] str = s.toCharArray();
        int n = str.length;
        int[][] dp = new int[n][n];
        dp[n - 1][n - 1] = 1;
        for (int i = 0; i < n - 1; i++) {
            // 对角线位置
            dp[i][i] = 1;
            // 第二条对角线位置
            dp[i][i + 1] = str[i] == str[i + 1] ? 2 : 1;
        }
        // 普遍位置，dp[i][j] 位置依赖dp[l+1][r-1] dp[l][r-1] dp[l+1][r]三个格子的值
        // 需要从下往上，从左到右的顺序填写
        for (int i = n - 3; i >= 0; i--) {
            for (int j = i + 2; j < n; j++) {
                int p1 = dp[i + 1][j - 1];
                int p2 = dp[i][j - 1];
                int p3 = dp[i + 1][j];
                int p4 = str[i] == str[j] ? p1 + 2 : 0;
                dp[i][j] = Math.max(p1, Math.max(p2, Math.max(p3, p4)));
            }
        }
        return dp[0][n - 1];
    }

    // dp终极优化
    public static int longestPalindromeSubSeq3(String s) {
        if (s == null || s.length() == 0) {
            return 0;
        }
        char[] str = s.toCharArray();
        int n = str.length;
        int[][] dp = new int[n][n];
        dp[n - 1][n - 1] = 1;
        for (int i = 0; i < n - 1; i++) {
            // 对角线位置
            dp[i][i] = 1;
            // 第二条对角线位置
            dp[i][i + 1] = str[i] == str[i + 1] ? 2 : 1;
        }
        // 普遍位置，dp[i][j] 位置依赖dp[l+1][r-1] dp[l][r-1] dp[l+1][r]三个格子的值
        // 需要从下往上，从左到右的顺序填写
        for (int i = n - 3; i >= 0; i--) {
            for (int j = i + 2; j < n; j++) {
                // dp[i][j] 位置依赖dp[l+1][r-1] dp[l][r-1] dp[l+1][r]三个格子的值
                // 所以dp[i][j]的值必然大于左下，左和下的值
                // 而求左dp[i][j-1]的位置，左的位置必然大于左下的值dp[i][j-1]>dp[i+1][j-1]
                // 求左dp[i+1][j]的位置，下的位置必然大于左下的值dp[i+1][j]>dp[i+1][j-1]
                // 所以dp[i][j]不需要考虑左下的值
//                int p1 = dp[i + 1][j - 1];
//                int p2 = dp[i][j - 1];
//                int p3 = dp[i + 1][j];
//                int p4 = str[i] == str[j] ? dp[i + 1][j - 1] + 2 : 0;
//                dp[i][j] = Math.max(p2, Math.max(p3, p4));
                dp[i][j] = Math.max(dp[i][j - 1], dp[i + 1][j]);
                if (str[i] == str[j]) {
                    dp[i][j] = Math.max(dp[i][j], dp[i + 1][j - 1] + 2);
                }
            }
        }
        return dp[0][n - 1];
    }
}
