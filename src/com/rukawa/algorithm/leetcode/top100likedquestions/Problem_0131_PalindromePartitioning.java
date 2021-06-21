package com.rukawa.algorithm.leetcode.top100likedquestions;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created with Intellij IDEA
 *
 * @Author：SuperHai
 * @Date：2020-09-19 11:05
 * @Version：1.0
 */
public class Problem_0131_PalindromePartitioning {
    /**
     * 分割回文串
     * 给定一个字符串 s，将 s 分割成一些子串，使每个子串都是回文串。
     * 返回 s 所有可能的分割方案。
     *
     * 示例:
     * 输入: "aab"
     * 输出:
     * [
     *   ["summary","b"],
     *   ["a","a","b"]
     * ]
     */

    public static List<List<String>> partition(String s) {
        boolean[][] dp = getDP(s.toCharArray());
        LinkedList<String> path = new LinkedList<>();
        List<List<String>> res = new ArrayList<>();
        process(s, 0, path, dp, res);
        return res;
    }


    public static boolean[][] getDP(char[] chs) {
        int N = chs.length;
        boolean[][] dp = new boolean[N][N];
        for (int i = 0; i < N - 1; i++) {
            dp[i][i] = true;
            dp[i][i + 1] = chs[i] == chs[i + 1];
        }
        dp[N - 1][N - 1] = true;
        for (int j = 2; j < N; j++) {
            int row = 0;
            int col = j;
            while (row < N && col < N) {
                dp[row][col] = chs[row] == chs[col] && dp[row + 1][col - 1];
                row++;
                col++;
            }
        }
        return dp;
    }

    public static void process(String s, int index, LinkedList<String> path, boolean[][] dp, List<List<String>> res) {
        if (index == s.length()) {
            res.add(copy(path));
        } else {
            for (int end = index; end < s.length(); end++) {
                if (dp[index][end]) {
                    path.addLast(s.substring(index, end + 1));
                    process(s, end + 1, path, dp, res);
                    path.pollLast();
                }
            }
        }
    }

    public static List<String> copy(List<String> path) {
        List<String> res = new ArrayList<>();
        for (String s : path) {
            res.add(s);
        }
        return res;
    }

    public static void main(String[] args) {
        System.out.println(partition("efe"));
    }

}
