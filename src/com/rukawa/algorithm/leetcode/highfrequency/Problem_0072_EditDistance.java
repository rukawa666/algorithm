package com.rukawa.algorithm.leetcode.highfrequency;

/**
 * Created with Intellij IDEA
 *
 * @Author：SuperHai
 * @Date：2020-09-28 22:38
 * @Version：1.0
 */
public class Problem_0072_EditDistance {
    /**
     * 编辑距离
     * 给你两个单词 word1 和 word2，请你计算出将 word1 转换成 word2 所使用的最少操作数 。
     * 你可以对一个单词进行如下三种操作：
     * 插入一个字符
     * 删除一个字符
     * 替换一个字符
     *
     * 示例 1：
     * 输入：word1 = "horse", word2 = "ros"
     * 输出：3
     * 解释：
     * horse -> rorse (将 'h' 替换为 'r')
     * rorse -> rose (删除 'r')
     * rose -> ros (删除 'e')
     *
     * 示例 2：
     * 输入：word1 = "intention", word2 = "execution"
     * 输出：5
     * 解释：
     * intention -> inention (删除 't')
     * inention -> enention (将 'i' 替换为 'e')
     * enention -> exention (将 'n' 替换为 'x')
     * exention -> exection (将 'n' 替换为 'c')
     * exection -> execution (插入 'u')
     */
    public static int minDistance(String word1, String word2) {
        if (word1 == null || word2 == null) {
            return 0;
        }
        char[] str1 = word1.toCharArray();
        char[] str2 = word2.toCharArray();
        int N = str1.length + 1;
        int M = str2.length + 1;
        int[][] dp = new int[N][M];
        for (int row = 1; row < N; row++) {
            dp[row][0] = row;
        }

        for (int col = 1; col < M; col++) {
            dp[0][col] = col;
        }

        for (int row = 1; row < N; row++) {
            for (int col = 1; col < M; col++) {
                if (str1[row - 1] == str2[col - 1]) {
                    dp[row][col] = dp[row - 1][col - 1];
                } else {
                    dp[row][col] = dp[row - 1][col - 1] + 1;    // 替换的代价
                }
                dp[row][col] = Math.min(dp[row][col], dp[row - 1][col] + 1);    // 删除row-1的元素
                dp[row][col] = Math.min(dp[row][col], dp[row][col - 1] + 1);    // row和col-1匹配，即str1是str2的前缀串，则add
            }
        }
        return dp[N - 1][M - 1];
    }

    public static void main(String[] args) {
        System.out.println(minDistance("horse", "ros"));
    }
}
