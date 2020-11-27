package com.rukawa.algorithm.trainingcamp.trainingcamp4.class5;

/**
 * Created with Intellij IDEA
 *
 * @Author：SuperHai
 * @Date：2020-10-19 7:34
 * @Version：1.0
 */
public class Code01_PalindromeSubsequence {
    /**
     * 给定一个字符串str，求最长回文子序列长度
     */

    public static int maxLen1(String str) {
        /**
         * 思路：
         *  1、原字符串和它自己的逆序串的最长公共子序列就是原串的最长回文子序列
         *  2、属于一个样本做行一个样本做列的对应模型
         */
        if (str == null || str.length() == 0) {
            return 0;
        }
        char[] str1 = str.toCharArray();
        char[] str2 = reverse(str1);
        return ice(str1, str2);
    }

    public static int ice(char[] str1, char[] str2) {
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
        return dp[str1.length - 1][str2.length - 1];
    }

    public static char[] reverse(char[] str) {
        char[] reverse = new char[str.length];
        int index = 0;
        for (int i = 0; i < reverse.length; i++) {
            reverse[i] = str[str.length - 1 - i];
        }
        return reverse;
    }

    public static int maxLen2(String s) {
        /**
         * 思路：
         *  1、[L...R]上最长回文子序列的可能性
         *  2、范围上尝试模型
         *
         * 可能性：
         *  1、因为求的是L...R位置上最长回文子序列，L...R的正方形矩形的左下半区没用
         *  2、只有一个字符的时候是不是回文，是，则对角线位置填1
         *  3、次对角线[L,L+1]判断是否相等，相等有2个，不相等1个
         *     普遍位置
         *  4、既不包含L位置的字符也不包含R位置的字符，dp[L+1][R-1] 举例：str=a12321b，不包含a和b
         *  5、包含L位置的字符，但是不包含R位置的字符，dp[L][R-1]   举例：str=12321b，不包含b
         *  6、不包含L位置的字符，但是包含R位置的字符，dp[L+1][R]   举例：str=a12321，不包含a
         *  7、既包含L位置的字符，也包含R位置的字符，要求L位置的字符等于R位置字符 dp[L+1][R-1]+2  举例：str=a12321a
         */
        if (s == null || s.length() == 0) {
            return 0;
        }
        char[] str = s.toCharArray();
        int[][] dp = new int[str.length][str.length];
        for (int i = 0; i < str.length; i++) {
            dp[i][i] = 1;
        }

        for (int i = 0; i < str.length - 1; i++) {
            dp[i][i + 1] = str[i] == str[i + 1] ? 2 : 1;
        }

        for (int i = str.length - 2; i >= 0; i--) {
            for (int j = i + 2; j < str.length; j++) {
                dp[i][j] = Math.max(dp[i][j - 1], dp[i + 1][j]);
                if (str[i] == str[j]) {
                    dp[i][j] = Math.max(dp[i + 1][j - 1] + 2, dp[i][j]);
                }
            }
        }
        return dp[0][str.length - 1];
    }


    public static void main(String[] args) {
        String test = "A1BC2D33FG2H1I";
        System.out.println(maxLen1(test));
        System.out.println(maxLen2(test));
    }
}
