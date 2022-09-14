package com.rukawa.algorithm.leetcode.top100likedquestions;

/**
 * Created with Intellij IDEA
 *
 * @Author：SuperHai
 * @Date：2020-09-21 8:41
 * @Version：1.0
 */
public class Problem_0091_DecodeWays {
    /**
     * 解码方法
     * 一条包含字母 A-Z 的消息通过以下映射进行了 编码 ：
     * 'A' -> "1"
     * 'B' -> "2"
     * ...
     * 'Z' -> "26"
     * 要 解码 已编码的消息，所有数字必须基于上述映射的方法，反向映射回字母（可能有多种方法）。例如，"11106" 可以映射为：
     * "AAJF" ，将消息分组为 (1 1 10 6)
     * "KJF" ，将消息分组为 (11 10 6)
     * 注意，消息不能分组为  (1 11 06) ，因为 "06" 不能映射为 "F" ，这是由于 "6" 和 "06" 在映射中并不等价。
     * 给你一个只含数字的 非空 字符串 s ，请计算并返回 解码 方法的 总数 。
     *
     * 题目数据保证答案肯定是一个 32 位 的整数。
     * 示例 1：
     * 输入：s = "12"
     * 输出：2
     * 解释：它可以解码为 "AB"（1 2）或者 "L"（12）。
     *
     * 示例 2：
     * 输入：s = "226"
     * 输出：3
     * 解释：它可以解码为 "BZ" (2 26), "VF" (22 6), 或者 "BBF" (2 2 6) 。
     *
     * 示例 3：
     * 输入：s = "0"
     * 输出：0
     * 解释：没有字符映射到以 0 开头的数字。
     * 含有 0 的有效映射是 'J' -> "10" 和 'T'-> "20" 。
     * 由于没有字符，因此没有有效的方法对此进行解码，因为所有数字都需要映射。
     *  
     *
     * 提示：
     * 1 <= s.length <= 100
     * s 只包含数字，并且可能包含前导零。
     */
    // 暴力递归方法
    // leetcode会超时
    public static int numDecodings1(String s) {
        if (s == null || s.length() == 0) {
            return 0;
        }
        char[] str = s.toCharArray();
        return process(str, 0);
    }

    // str[0...i-1]转化无需过程
    // str[i...n-1]去转化，有多少中转化方法
    public static int process(char[] str, int i) {
        // 0...i-1已经转化完了，i位置就找到一种转化方法
        if (i == str.length) {
            return 1;
        }
        // 之前的决定无效
        if (str[i] == '0') {
            return 0;
        }
        // 可能性1：i位置的方法单转
        int ways = process(str, i + 1);
        // 可能性2：i位置和i+1的数字一起转
        if (i + 1 < str.length && (str[i] - '0') * 10 + (str[i + 1] - '0') < 27) {
            ways += process(str, i + 2);
        }
        return ways;
    }

    // 暴力递归改成动态规划
    public static int numDecodings2(String s) {
        if (s == null || s.length() == 0) {
            return 0;
        }
        char[] str = s.toCharArray();
        int n = str.length;
        int[] dp = new int[n + 1];
        // dp[n] 为1
        dp[n] = 1;
        for (int i = str.length - 1; i >= 0; i--) {
            if (str[i] == '0') {
                continue;
            }
            dp[i] = dp[i + 1];
            if (i + 1 < n && (str[i] - '0') * 10 + str[i + 1] - '0' < 27) {
                dp[i] += dp[i + 2];
            }
        }
        return dp[0];
    }
}
