package com.rukawa.algorithm.trainingcamp.trainingcamp3.class5;

/**
 * Created with Intellij IDEA
 *
 * @Author：SuperHai
 * @Date：2020-08-26 23:02
 * @Version：1.0
 */
public class Code02_EditCost {
    /**
     * 给定两个字符串str1和str2，再给定三个整数ic、dc和rc，分别代表插入、删除和替换一个字符串的代价，
     * 返回将str1编辑成str2的最小代价
     *
     * 举例：
     *  str1="abc"，str2="adc"，ic=5,dc=3,rc=2，从"abc"编辑为"adc"，把'b'替换成'd'的代价是最小的，所以返回2
     *  str1="abc"，str2="adc"，ic=5,dc=3,rc=100，从"abc"编辑为"adc"，先删除'b',然后插入'd'的代价是最小的，所以返回8
     *  str1="abc"，str2="abc"，ic=5,dc=3,rc=2，不用编辑了，本来就是一样的字符串，所以返回0
     */
    public static int minCost1(String s1, String s2, int ic, int dc, int rc) {
        if (s1 == null || s2 == null) {
            return 0;
        }
        char[] str1 = s1.toCharArray();
        char[] str2 = s2.toCharArray();
        int row = str1.length + 1;
        int col = str2.length + 1;
        int[][] dp = new int[row][col];
        for (int i = 1; i < row; i++) {
            dp[i][0] = i * dc;
        }

        for (int j = 1; j < col; j++) {
            dp[0][j] = j * ic;
        }

        for (int i = 1; i < row; i++) {
            for (int j = 1; j < col; j++) {
                // i-1和j-1位置字符相等，则判断前缀串代价+替换的代价
                if (str1[i - 1] == str2[j - 1]) {
                    dp[i][j] = dp[i - 1][j - 1];
                } else {    // 如果不相等，则在前缀串的代价上+替换的代价
                    dp[i][j] = dp[i - 1][j - 1] + rc;
                }
                // 情况3：i-1位置的字符是str2的前缀串，+添加j-1字符的代价
                // eg:word1="ab12ks",word2="ab2kst",先让word1变为Word2的前缀->"ab2ks"，需要在j-1位置处添加t
                dp[i][j] = Math.min(dp[i][j], dp[i][j - 1] + ic);
                // 情况4：i-2位置的字符是str2的最后一个字符，+删除i-1字符的代价
                // eg:word1="ab12ks", word2="ab2ks",word1变为word2的代价是，i-1位置处删除1
                dp[i][j] = Math.min(dp[i][j], dp[i - 1][j] + dc);
            }
        }
        return dp[row - 1][col - 1];
    }

    public static void main(String[] args) {
        String str1 = "ab12cd3";
        String str2 = "abcdf";
        System.out.println(minCost1(str1, str2, 5, 3, 2));
//        System.out.println(minCost2(str1, str2, 5, 3, 2));

        str1 = "abcdf";
        str2 = "ab12cd3";
        System.out.println(minCost1(str1, str2, 3, 2, 4));
//        System.out.println(minCost2(str1, str2, 3, 2, 4));

        str1 = "";
        str2 = "ab12cd3";
        System.out.println(minCost1(str1, str2, 1, 7, 5));
//        System.out.println(minCost2(str1, str2, 1, 7, 5));

        str1 = "abcdf";
        str2 = "";
        System.out.println(minCost1(str1, str2, 2, 9, 8));
//        System.out.println(minCost2(str1, str2, 2, 9, 8));

    }

}
