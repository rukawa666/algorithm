package com.rukawa.algorithm.trainingcamp.trainingcamp3.class6;

/**
 * Created with Intellij IDEA
 *
 * @Author：SuperHai
 * @Date：2020-09-30 7:49
 * @Version：1.0
 */
public class Code06_StringCross {
    /**
     * 给定三个字符串str1、str2和aim，如果aim包含且仅包含来自str1和str2的所有字符，而且在aim中属于str1的字符
     * 之间保持原来在str1中的顺序，属于arr2的字符之间保持原来在str2的顺序，那么称aim是str1和str2的交错组成。
     * 实现一个函数，判断aim是否是str1和str2交错组成
     *
     * 举例：str1="AB",str2="12"。那么"AB12","A1B2","A12B","1A2B"和"1AB2"等都是str1和str2的交错组成
     */

    // 动态规划，三个字符串对应一张表，还是样本对应的模型
    public static boolean isCross1(String s1, String s2, String ai) {
        if (s1 == null || s2 == null || ai == null) {
            return false;
        }
        char[] str1 = s1.toCharArray();
        char[] str2 = s2.toCharArray();
        char[] aim = ai.toCharArray();
        if (str1.length + str2.length != aim.length) {
            return false;
        }
        /**
         * eg: str1:"aabcb" str2="aacbb" str3="aaabaccbbb"
         *   0 1 2 3 4 5  -> str2
         * 0 T T F F F F
         * 1 T 
         * 2 T
         * 3 F
         * 4 F
         * 5 F
         *
         * |
         * str1
         */
        // dp[i][j] -> str1前缀i长度str1[0...i-1],str2前缀j长度str2[0...j-1]能否交错组成str3前缀i+j长度
        boolean[][] dp = new boolean[str1.length + 1][str2.length + 1];
        // str1一个字符都不拿，str2一个字符都不拿，能不能拼出str3一个字符都不拿
        dp[0][0] = true;
        for (int row = 1; row <= str1.length; row++) {
            if (str1[row - 1] != aim[row - 1]) {
                break;
            }
            dp[row][0] = true;
        }

        for (int col = 1; col <= str2.length; col++) {
            if (str2[col - 1] == aim[col - 1]) {
                break;
            }
            dp[0][col] = true;
        }
        /**
         * 可能性：
         *  dp[i][j] -> str1[0...i-1] len1=i, str2[0...j-2] len2=j, str3[0...i+j-1] len3=i+j
         *  情况1：如果str3[i+j-1]==str1[i-1]成立，还需要dp[i-1][j] 成立，此时dp[i][j]==true
         *  情况2：如果str3[i+j-1]==str2[j-2]成立，还需要dp[i][j-1] 成立，此时dp[i][j]==true
         */
        for (int row = 1; row <= str1.length; row++) {
            for (int col = 1; col <= str2.length; col++) {
                if (
                        aim[row + col - 1] == str1[row - 1] && dp[row - 1][col]
                        ||
                        aim[row + col - 1] == str2[col - 1] && dp[row][col - 1]) {
                    dp[row][col] = true;
                }
            }
        }
        return dp[str1.length][str2.length];
    }


    public static boolean isCross2(String s1, String s2, String ai) {
        if (s1 == null || s2 == null || ai == null) {
            return false;
        }
        char[] str1 = s1.toCharArray();
        char[] str2 = s2.toCharArray();
        char[] aim = ai.toCharArray();
        if (str1.length + str2.length != aim.length) {
            return false;
        }
        char[] longs = str1.length >= str2.length ? str1 : str2;
        char[] shorts = str1.length < str2.length ? str1 : str2;
        boolean[] dp = new boolean[shorts.length + 1];
        dp[0] = true;
        for (int i = 1; i <= shorts.length; i++) {
            if (shorts[i - 1] != aim[i - 1]) {
                break;
            }
            dp[i] = true;
        }

        for (int i = 1; i <= longs.length; i++) {
            dp[0] = dp[0] && longs[i - 1] == aim[i - 1];
            for (int j = 1; j <= shorts.length; j++) {
                if ((longs[i - 1] == aim[i + j - 1] && dp[j]) ||
                        (shorts[j - 1]== aim[i + j - 1] && dp[j - 1])) {
                    dp[j] = true;
                } else {
                    dp[j] = false;
                }
            }
        }
        return dp[shorts.length];
    }

    public static void main(String[] args) {
        String str1 = "1234";
        String str2 = "abcd";
        String aim = "1a23bcd4";
        System.out.println(isCross1(str1, str2, aim));
        System.out.println(isCross2(str1, str2, aim));

    }
}
