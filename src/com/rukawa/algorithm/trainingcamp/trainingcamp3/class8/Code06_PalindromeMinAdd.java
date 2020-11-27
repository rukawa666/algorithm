package com.rukawa.algorithm.trainingcamp.trainingcamp3.class8;

/**
 * Created with Intellij IDEA
 *
 * @Author：SuperHai
 * @Date：2020-10-09 0:12
 * @Version：1.0
 */
public class Code06_PalindromeMinAdd {
    /**
     * 给定一个字符串，如果可以在字符串任意位置添加字符，最少添加几个能让字符串整体都是回文串
     */

    public static String getPalindrome1(String s) {
        if (s == null || s.length() < 2) {
            return s;
        }
        char[] str = s.toCharArray();
        int[][] dp = getDP(str);
        // 需要填写的字符总共有原来的字符 + 需要填写的字符长度
        char[] res = new char[str.length + dp[0][str.length - 1]];
        int i = 0;
        int j = str.length - 1;
        // res从左边到右边下标的位置
        int resL = 0;
        // res从右边到左边下标的位置
        int resR = res.length - 1;
        while (i <= j) {
            if (str[i] == str[j]) {
                res[resL++] = str[i++];
                res[resR--] = str[j--];
            } else if (dp[i][j - 1] < dp[i + 1][j]) {
                // 如果左边的权重小于下面的权重，则说明，i...j依赖于左边
                // 此时在左边的位置补一个对应j的字符
                // 此时在右边的位置同样的补一个对应j的字符，然后对应j的字符不考虑
                res[resL++] = str[j];
                res[resR--] = str[j--];
            } else {
                // 如果下面的权重小于左边的权重，则说明，i...j依赖于下面
                // 此时在左边补一个i位置的字符，右边也补一个i位置的字符，不考虑对应i位置的字符
                res[resL++] = str[i];
                res[resR--] = str[i++];
            }
        }
        return String.valueOf(res);
    }


    /**
     * 最终的要求结果是0...str.length-1添加多个字符使其成为回文串
     * 返回dp[0][str.length - 1]即可
     * 思路：
     * 一个样本做行，一个样本做列，考虑开头或者结尾是什么
     * 可能性：
     *    1、L > R 的位置不考虑
     *    2、对角线位置L == R，本身就是回文串，所以添加0个字符
     *    3、第二条对角线，如果两个字符相等，不需要添加，如果不相等，只添加一个字符串
     *    4、情况3：i...j位置上，先处理开头，再处理结尾，则保证在i...j-1位置上是回文串，
     *       在i-1位置添加j位置的字符，dp[i][j - 1] + 1
     *    5、情况4：i...j位置上，先处理结尾，再处理开头，则保证在i+1...j位置上是回文串，
     *       在j+1位置添加i位置的字符，dp[i + 1][j] + 1
     *    6、情况5：i...j位置上，恰巧i位置的字符等于j位置的字符，则只需要
     *      保证i+1...j-1位置上是回文串即可，dp[i + 1][j - 1]
     *
     * 所以总结下来，dp[i][j]位置的可能依赖于下方，左方，和左下方位置的取值
     * @param str
     * @return
     */
    public static int[][] getDP(char[] str) {
        int[][] dp = new int[str.length][str.length];
        for (int j = 1; j < str.length; j++) {
            // 第二条对角线，两个字符相等则为0，不等则补充一个字符
            dp[j - 1][j] = str[j - 1] == str[j] ? 0 : 1;
            for (int i = j - 2; i > -1; i--) {
                if (str[i] == str[j]) {
                    dp[i][j] = dp[i + 1][j - 1];
                } else {
                    dp[i][j] = Math.min(dp[i + 1][j], dp[i][j - 1]) + 1;
                }
            }
        }
        return dp;
    }


    public static void main(String[] args) {
//        String str = "AB1CD2EFG3H43IJK2L1MN";
        String str = "abcd";
        System.out.println(getPalindrome1(str));
        System.out.println(getDP(str.toCharArray())[0][str.length() - 1]);
    }
}
