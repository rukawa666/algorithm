package com.rukawa.algorithm.leetcode.top100likedquestions;


/**
 * create by hqh on 2022/9/23
 */
public class Problem_0639_DecodeWaysII {
    /**
     * 解码方法 II
     * 一条包含字母 A-Z 的消息通过以下的方式进行了 编码 ：
     * 'A' -> "1"
     * 'B' -> "2"
     * ...
     * 'Z' -> "26"
     * 要 解码 一条已编码的消息，所有的数字都必须分组，然后按原来的编码方案反向映射回字母（可能存在多种方式）。例如，"11106" 可以映射为：
     *
     * "AAJF" 对应分组 (1 1 10 6)
     * "KJF" 对应分组 (11 10 6)
     * 注意，像 (1 11 06) 这样的分组是无效的，因为 "06" 不可以映射为 'F' ，因为 "6" 与 "06" 不同。
     * 除了 上面描述的数字字母映射方案，编码消息中可能包含 '*' 字符，可以表示从 '1' 到 '9' 的任一数字（不包括 '0'）。
     * 例如，编码字符串 "1*" 可以表示 "11"、"12"、"13"、"14"、"15"、"16"、"17"、"18" 或 "19" 中的任意一条消息。对 "1*" 进行解码，
     * 相当于解码该字符串可以表示的任何编码消息。
     * 给你一个字符串 s ，由数字和 '*' 字符组成，返回 解码 该字符串的方法 数目 。
     * 由于答案数目可能非常大，返回 109 + 7 的 模 。
     *
     * 提示：
     * 1 <= s.length <= 105
     * s[i] 是 0 - 9 中的一位数字或字符 '*'
     */
    public int numDecodings(String s) {
        if (s == null || s.length() == 0) {
            return 0;
        }
        char[] str = s.toCharArray();
        long mod = (long) 1e9 + 1;
        return process(str, 0);
    }

    public int process(char[] str, int index) {
        if (index == str.length) {
            return 1;
        }

        if (str[index] == '0') {
            return 0;
        }

        if (str[index] != '*') {
            int p1 = process(str, index + 1);
            if (index + 1 == str.length) {
                return p1;
            }

            if (str[index + 1] != '*') {
                int p2 = 0;
                if ((str[index] - '0') * 10 + (str[index + 1] - '0') < 27) {
                    p2 = process(str, index + 2);
                }
                return p1 + p2;
            } else { // str[index+1]='*'
                // 1~26的组成情况
                int p2 = 0;
                if (str[index] < '3') {
                    // 如果str[index]=1 str[index+1] 可以选择1～9
                    // 如果str[index]=2 str[index+2] 只能选择1～6
                    p2 = process(str, index + 2) * (str[index] == '1' ? 9 : 6);
                }
                return p1 + p2;
            }

        } else { // str[index]=='*'
            // index的字符单独转，有1～9
            int p1 = 9 * process(str, index + 1);
            if (index + 1 == str.length) {
                return p1;
            }
            // index和index+1的位置一起转
            if (str[index + 1] != '*') {
                // *1 11 21
                // *2 12 22
                // ...
                // *6 16 26
                // *7 17
                // *8 18
                // *9 19
                // *0 10
                int p2 = (str[index + 1] < '7' ? 2 : 1) * process(str, index + 2);
                return p1 + p2;
            } else { // str[index+1]=='*'
                // 11~19 9个数字可以转
                // 21~26 6个数字可以转
                int p2 = 15 * process(str, index + 2);
                return p1 + p2;
            }
        }
    }

    // 傻缓存
    public int numDecodings1(String s) {
        if (s == null || s.length() == 0) {
            return 0;
        }
        long[] dp = new long[s.length()];
        char[] str = s.toCharArray();
        return (int) process1(str, 0, dp);
    }

    public long process1(char[] str, int index, long[] dp) {
        long mod = 1000000007;
        if (index == str.length) {
            return 1;
        }

        if (str[index] == '0') {
            return 0;
        }

        if (dp[index] != 0) {
            return dp[index];
        }

        long res = process1(str, index + 1, dp) * (str[index] != '*' ? 1 : 9);
        if (str[index] == '1' || str[index] == '2' || str[index] == '*') {
            if (index + 1 < str.length) {
                if (str[index + 1] == '*') {
                    res += process1(str, index + 2, dp) * (str[index] == '1' ? 9 : str[index] == '2' ? 6 : 15);
                } else {
                    if (str[index] == '*') {
                        res += process1(str, index + 2, dp) * (str[index + 1] < '7' ? 2 : 1);
                    } else {
                        res += ((str[index] - '0') * 10 + str[index + 1] - '0') < 27 ? process1(str, index + 2, dp) : 0;
                    }
                }
            }
        }
        res %= mod;
        dp[index] = res;
        return res;
    }

    public int numDecodings2(String s) {
        if (s == null || s.length() == 0) {
            return 0;
        }
        long mod = 1000000007;
        int n = s.length();
        char[] str = s.toCharArray();
        long[] dp = new long[n + 1];
        dp[n] = 1;
        for (int index = n - 1; index >= 0; index--) {
            if (str[index] == '0') {
                continue;
            }
            // 单独转
            dp[index] = dp[index + 1] * (str[index] != '*' ? 1 : 9);
            // index和index+1一起转
            if (str[index] == '1' || str[index] == '2' || str[index] == '*') {
                if (index + 1 < n) {
                    if (str[index + 1] == '*') {
                        dp[index] += dp[index + 2] * (str[index] == '1' ? 9 : str[index] == '2' ? 6 : 15);
                    } else {
                        // *1 11 21
                        // *2 12 22
                        // ...
                        // *6 16 26
                        // *7 17
                        // *8 18
                        // *9 19
                        // *0 10
                        if (str[index] == '*') {
                            dp[index] += dp[index + 2] * (str[index + 1] < '7' ? 2 : 1);
                        } else {
                            if ((str[index] - '0') * 10 + str[index + 1] - '0' < 27) {
                                dp[index] += dp[index + 2];
                            }
                        }
                    }
                }
                dp[index] %= mod;
            }
        }
        return (int) dp[0];
    }
}
