package com.rukawa.algorithm.base.class11;

/**
 * Created with Intellij IDEA
 *
 * @Author：SuperHai
 * @Date：2020-07-26 23:35
 * @Version：1.0
 */
public class Code06_ConvertToLetterString {

    /**
     * 从左往右的尝试模型
     * 规定1和A对应，2和B对应，3和C对应...
     * 那么一个数字字符串比如"111"就可以转换为：
     * "AAA", "AK", "KA"
     * 给定一个只有数字字符串的字符串str，返回有多少种转化结果
     * @param str
     * @return
     */
    public static int number(String str) {
        if (str == null || str.length() == 0) {
            return 0;
        }
        char[] chs = str.toCharArray();
        return process(chs, 0);
    }

    /**
     * chs[0 ... i-1]已经转换完成，固定了
     * i之前的位置，如果转化已经做过决定了，不用在关心
     * i...之后有多少种转换的结果
     * @param chs
     * @param i
     * @return
     */
    public static int process(char[] chs, int i) {
        if (i == chs.length) {  // base case
            return 1;   // 终止位置，返回一个点数，0到i-1有一个点数
        }
        // i没有到终止位置，1-'A',0是没有办法转化
        if (chs[i] == '0') {
            return 0;
        }

        if (chs[i] == '1') {
            int res = process(chs, i + 1);  // i自己作为单据的一部分，后续有多少种方法

            if (i + 1 < chs.length) {
                res += process(chs, i + 2); // (i和i+1)作为单独的一部分，后续有多少种方法
            }
            return res;
        }

        if (chs[i] == '2') {
            int res = process(chs, i + 1);  // i自己作为单据的一部分，后续有多少种方法

            // (i和i+1)作为单独的一部分且没有超过26，后续有多少种方法
            if (i + 1 < chs.length && (chs[i + 1] >= '0' && chs[i + 1] <= '6')) {
                res += process(chs, i + 2);
            }
            return res;
        }
        // chs[i] = '3' ~ '9',超过26，所以只能在i+1位置开始考虑
        return process(chs, i + 1);
    }

    public static int dpWays2(String str) {
        if (str == null || str.length() == 0) {
            return 0;
        }

        char[] chs = str.toCharArray();
        int N = chs.length;
        int[] dp = new int[N + 1];

        dp[N] = 1;
        for (int index = N - 1; index >=0; index--) {

            // i没有到终止位置，1-'A',0是没有办法转化
            if (chs[index] == '0') {
                continue;
            }

            if (chs[index] == '1') {
                dp[index] = dp[index + 1];  // i自己作为单据的一部分，后续有多少种方法

                if (index + 1 < chs.length) {
                    dp[index] += dp[index + 2]; // (i和i+1)作为单独的一部分，后续有多少种方法
                }
            }

            if (chs[index] == '2') {
                dp[index] = dp[index + 1];  // i自己作为单据的一部分，后续有多少种方法

                // (i和i+1)作为单独的一部分且没有超过26，后续有多少种方法
                if (index + 1 < chs.length && (chs[index + 1] >= '0' && chs[index + 1] <= '6')) {
                    dp[index] += dp[index + 2];
                }
            }
        }
        return dp[0];
    }

    public static int dpWays(String str) {
        if (str == null || str.length() == 0) {
            return 0;
        }

        char[] chs = str.toCharArray();
        int N = chs.length;
        int[] dp = new int[N + 1];

        dp[N] = 1;
        for (int index = N - 1; index >=0; index--) {

            // i没有到终止位置，1-'A',0是没有办法转化
            if (chs[index] == '0') {
                dp[index] = 0;
            } else if (chs[index] == '1') {
                dp[index] = dp[index + 1];  // i自己作为单据的一部分，后续有多少种方法

                if (index + 1 < chs.length) {
                    dp[index] += dp[index + 2]; // (i和i+1)作为单独的一部分，后续有多少种方法
                }
            } else if (chs[index] == '2') {
                dp[index] = dp[index + 1];  // i自己作为单据的一部分，后续有多少种方法

                // (i和i+1)作为单独的一部分且没有超过26，后续有多少种方法
                if (index + 1 < chs.length && (chs[index + 1] >= '0' && chs[index + 1] <= '6')) {
                    dp[index] += dp[index + 2];
                }
            } else {
                dp[index] = dp[index + 1];
            }
        }
        return dp[0];
    }


    // 自己手写
    public static int dpWay1(String s) {
        if (s == null || s.length() == 0) {
            return 0;
        }

        char[] chs = s.toCharArray();
        int N = chs.length;
        int[] dp = new int[N + 1];
        dp[N] = 1;
        for (int index = N - 1; index >= 0; index--) {
            if (chs[index] == '0') {
                dp[index] = 0;
            } else if (chs[index] == '1') {
                dp[index] = dp[index + 1];
                if (index + 1 < chs.length) {
                    dp[index] += dp[index + 2];
                }
            } else if (chs[index] == '2') {
                dp[index] = dp[index + 1];
                if (index + 1 < chs.length && (chs[index + 1] >= '0' && chs[index + 1] <= '6')) {
                    dp[index] += dp[index + 2];
                }
            } else {
                dp[index] = dp[index + 1];
            }
        }
        return dp[0];
    }

    public static void main(String[] args) {
        System.out.println(number("11111"));
        System.out.println(dpWays2("11111"));
        System.out.println(dpWays("11111"));
        System.out.println(dpWay1("11111"));

    }
}
