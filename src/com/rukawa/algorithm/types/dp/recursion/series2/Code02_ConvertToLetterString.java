package com.rukawa.algorithm.types.dp.recursion.series2;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @Author: Administrator
 * @Date: 2021/4/1 0001 8:16
 */
public class Code02_ConvertToLetterString {

    /**
     * 规定1和A对应，2和B对应，3和C对应...
     * 那么一个数字字符串比如"111"就可以转化为："AAA"、"KA"和"AK"
     * 给定一个只有数字字符组成的字符串str，返回有多少种转化结果
     */
    public static int number(String s) {
        if (s == null || s.length() == 0) {
            return 0;
        }
        return process(s.toCharArray(), 0);
    }

    // str[0...i-1]转化无需过问
    // str[i...]去转化，返回有多少种转化方法
    public static int process(char[] str, int index) {
        // [0,i-1]已经转化完毕，无需过问，终止位置找到一种转化方法
        if (index == str.length) {
            return 1;
        }
        // 如果是0开头，是错误决定
        if (str[index] == '0') {
            return 0;
        }

        int ways = process(str, index + 1);
        if (index + 1 < str.length && (str[index] - '0') * 10 + str[index + 1] - '0' < 27) {
            ways += process(str, index + 2);
        }
        return ways;
    }

    public static int dp(String s) {
        if (s == null || s.length() == 0) {
            return 0;
        }
        char[] str = s.toCharArray();
        int N = str.length;
        int[] dp = new int[N + 1];
        dp[N] = 1;
        for (int index = N - 1; index >= 0; index--) {
            if (str[index] != '0') {
                int ways = dp[index + 1];
                if (index + 1 < str.length && (str[index] - '0') * 10 + str[index + 1] - '0' < 27) {
                    ways += dp[index + 2];
                }
                dp[index] = ways;
            }
        }
        return dp[0];
    }

    public static void main(String[] args) {
        System.out.println(number("454512547782124"));
        System.out.println(dp("454512547782124"));
    }
}
