package com.rukawa.algorithm.trainingcamp.trainingcamp5.class6;

/**
 * Created with Intellij IDEA
 *
 * @Author：SuperHai
 * @Date：2020-11-12 8:06
 * @Version：1.0
 */
public class Code02_StrangePrinter {
    /**
     * 奇怪的打印机
     * 有台奇怪的打印机有以下两个特殊要求：
     * 打印机每次只能打印同一个字符序列。
     * 每次可以在任意起始和结束位置打印新字符，并且会覆盖掉原来已有的字符。
     * 给定一个只包含小写英文字母的字符串，你的任务是计算这个打印机打印它需要的最少次数。
     *
     * 示例 1:
     * 输入: "aaabbb"
     * 输出: 2
     * 解释: 首先打印 "aaa" 然后打印 "bbb"。
     *
     * 示例 2:
     * 输入: "aba"
     * 输出: 2
     * 解释: 首先打印 "aaa" 然后在第二个位置打印 "b" 覆盖掉原来的字符 'a'。
     * 提示: 输入字符串的长度不会超过 100。
     */

    public int strangePrinter(String s) {
        /**
         * 贪心：
         *    1、边缘处[L]的数最先解决
         *    2、第一转推到哪？后面所有的可能性都罗列
         *
         *  例如：
         *    arr = 1 1 1 2 2 2 1 1 1 2
         *  index = 0 1 2 3 4 5 6 7 8 9
         *  有一种可能性：f(L,L+6)位置一起转，f(L+7,R)转，这时候arr[L]==arr[L+7]，此时要让两个相加的结果-1
         *  为什么要减1？因为arr[L]==arr[L+7]，所以各自的第一转可以合并
         */
        /**
         * 动态规划范围上的尝试模型
         * 1、填写一张正方形的表
         * 2、左下角的位置不填写，先填对角线位置，然后再填第二条对角线的位置
         */
        if (s == null || s.length() == 0) {
            return 0;
        }
        char[] str = s.toCharArray();
        int N = str.length;
        int[][] dp = new int[N][N];
        dp[N - 1][N - 1] = 1;
        for (int i = 0; i < N - 1; i++) {
            // i...i 范围上只有一个数，所以只需要一转，转出这个数即可
            dp[i][i] = 1;
            // i...i+1 范围上有两个数，如果相等只需要一转，不相等需要两转
            dp[i][i + 1] = str[i] == str[i + 1] ? 1 : 2;
        }

        for (int L = N - 3; L >= 0; L--) {
            for (int R = L + 2; R < N; R++) {
                // L...R都转掉，需要多少次
                dp[L][R] = R - L + 1;  // 假设每个数都不一样，最差可能也就是个数
                // 枚举可能性
                // 第一转：L...k-1 第二转：k...R
                for (int k = L + 1; k <= R; k++) {
                    dp[L][R] = Math.min(dp[L][R], dp[L][k - 1] + dp[k][R] - (str[L] == str[k] ? 1 : 0));
                }
            }
        }
        return dp[0][N - 1];
    }
}
