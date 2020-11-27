package com.rukawa.algorithm.trainingcamp.trainingcamp4.class2;

/**
 * Created with Intellij IDEA
 *
 * @Author：SuperHai
 * @Date：2020-10-13 0:01
 * @Version：1.0
 */
public class Code04_DistinctSubsequences {
    /**
     * 给定两个字符串S和T，返回S子序列等于T的不同子序列个数有多少个？如果得到子序列A删除的位置与得到子序列B删除的位置不同，
     * 那么认为A和B就是不同的
     * 例子：
     *  S="rabbbit", T="rabbit"
     *  返回：3
     *  是以下三个S的不同子序列，有^的位置表示删除的位置，因为删除的位置不同，所以这三个子序列是不一样的
     *  rabbbit
     *    ^
     *  rabbbit
     *     ^
     *  rabbbit
     *      ^
     */

    /**
     * 尝试模型：
     *   给定两个字符串，使用一个样本做行，一个样本做列的
     */
    /**
     * S[...i]的所有子序列中，包含多少个字面值等于T[...j]这个字符串的子序列
     * 记为dp[i][j]
     * 可能性1：S[...i]的所有子序列中，都不以s[i]结尾，则dp[i][j]肯定包含dp[i-1][j]
     * 可能性2：S[...i]的所有子序列中，都必须以s[i]结尾
     * 这要求s[i]==t[j],则dp[i][j] = dp[i-1][j-1]
     */

    public static int numDistinct(String S, String T) {
        char[] s = S.toCharArray();
        char[] t = T.toCharArray();
        int[][] dp = new int[s.length][t.length];
        dp[0][0] = s[0] == t[0] ? 1 : 0;
        // 第0行，怎么用s[0]拼出t[...j]，只有在s[0]==t[0]情况下，才能为1，其余情况都是0

        // 第0列，遇到s[j] == t[0], 则为1
        for (int j = 1; j < s.length; j++) {
            dp[j][0] = dp[j - 1][0] + s[j] == t[0] ? 1 : 0;
        }
        for (int i = 1; i < s.length; i++) {
            for (int j = 1; j < t.length; j++) {
                dp[i][j] = dp[i - 1][j] + (s[i - 1] == t[j - 1] ? dp[i - 1][j - 1] : 0);
            }
        }
        return dp[s.length - 1][t.length - 1];
    }

    public static void main(String[] args) {
        String S = "casrct";
        String T = "cact";
        System.out.println(numDistinct(S, T));
    }
}
