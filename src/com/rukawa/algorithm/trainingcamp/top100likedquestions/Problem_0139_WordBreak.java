package com.rukawa.algorithm.trainingcamp.top100likedquestions;

import java.util.HashSet;
import java.util.List;

/**
 * Created with Intellij IDEA
 *
 * @Author：SuperHai
 * @Date：2020-09-19 11:06
 * @Version：1.0
 */
public class Problem_0139_WordBreak {
    /**
     * 单词拆分
     * 给定一个非空字符串 s 和一个包含非空单词的列表 wordDict，判定 s 是否可以被空格拆分为一个或多个在字典中出现的单词。
     * 说明：
     * 拆分时可以重复使用字典中的单词。
     * 你可以假设字典中没有重复的单词。
     *
     * 示例 1：
     * 输入: s = "leetcode", wordDict = ["leet", "code"]
     * 输出: true
     * 解释: 返回 true 因为 "leetcode" 可以被拆分成 "leet code"。
     *
     * 示例 2：
     * 输入: s = "applepenapple", wordDict = ["apple", "pen"]
     * 输出: true
     * 解释: 返回 true 因为 "applepenapple" 可以被拆分成 "apple pen apple"。
     *      注意你可以重复使用字典中的单词。
     *
     * 示例 3：
     * 输入: s = "catsandog", wordDict = ["cats", "dog", "sand", "and", "cat"]
     * 输出: false
     */

    public boolean wordBreak2(String s, List<String> wordDict) {
        return process(s, 0, wordDict) != 0;
    }
    public static int process(String s, int index, List<String> wordDict) {
        if (index == s.length()) {
            return 1;
        }

        int ways = 0;
        for (int end = index; end < s.length(); end++) {
            String pre = s.substring(index, end + 1);
            if (wordDict.contains(pre)) {
                ways += process(s, end + 1, wordDict);
            }
        }
        return ways;
    }

    /**
     * 时间复杂度：O(N^3)
     * @param s
     * @param wordDict
     * @return
     */
    public boolean wordBreak3(String s, List<String> wordDict) {
        HashSet<String> set = new HashSet<>(wordDict);
        int N = s.length();
        int[] dp = new int[N + 1];
        dp[N] = 1;
        for (int index = N - 1; index >= 0; index--) {
            int ways = 0;
            for (int end = index; end < N; end++) {
                String pre = s.substring(index, end + 1);
                if (set.contains(pre)) {
                    ways += dp[end + 1];
                }
            }
            dp[index] = ways;
        }
        // dp[i] = process(s, index, wordDict)
        return dp[0] != 0;
    }

    public boolean wordBreak4(String s, List<String> wordDict) {
        Node root = new Node();
        for (String word : wordDict) {
            char[] chs = word.toCharArray();
            Node node = root;
            int index = 0;
            for (int i = 0; i < chs.length; i++) {
                index = chs[i] - 'a';
                if (node.nextS[index] == null) {
                    node.nextS[index] = new Node();
                }
                node = node.nextS[index];
            }
            node.end = true;
        }

        char[] chs = s.toCharArray();
        int N = chs.length;
        int[] dp = new int[N + 1];
        for (int i = N - 1; i >= 0; i--) {
            Node cur = root;
            for (int end = i; end < N; end++) {
                cur = cur.nextS[chs[end] - 'a'];
                if (cur == null) {
                    break;
                }
                if (cur.end) {
                    dp[i] += dp[end + 1];
                }
            }
        }
        return dp[0] != 0;
    }

    public static class Node {
        public boolean end;
        public Node[] nextS;

        public Node() {
            this.end = false;
            this.nextS = new Node[26];
        }
    }
}
