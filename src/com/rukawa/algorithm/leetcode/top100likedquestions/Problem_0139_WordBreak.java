package com.rukawa.algorithm.leetcode.top100likedquestions;

import java.util.ArrayList;
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

    // s[0...index-1]这一段，已经分解过了，不用在操心
    // s[index...]这一段字符串，能够被分解的方法数，返回
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
        // s从i出发及其后面的所有，能不能被分解
        int[] dp = new int[N + 1];
        // ""能不能被分解，可以
        dp[N] = 1;
        for (int index = N - 1; index >= 0; index--) {
            int ways = 0;
            for (int end = index; end < N; end++) {
                // s[index...end]
                String pre = s.substring(index, end + 1);
                // 如果set存整形，O(1)没问题
                // 如果set存短字符串，O(1)没问题
                // 如果set中存大文本，首先对这个文本遍历一遍求hashcode，然后在去查
                if (set.contains(pre)) {    // O(N)
                    ways += dp[end + 1];
                }
            }
            dp[index] = ways;
        }
        // dp[i] = process(s, index, wordDict)
        return dp[0] != 0;
    }

    public boolean wordBreak(String s, List<String> wordDict) {
        TrieNode root = new TrieNode();
        // 字典建立前缀树
        for (String str : wordDict) {
            char[] chs = str.toCharArray();
            TrieNode node = root;
            int index = 0;
            for (int i = 0; i < chs.length; i++) {
                index = chs[i] - 'a';
                if (node.nextS[index] == null) {
                    node.nextS[index] = new TrieNode();
                }
                node = node.nextS[index];
            }
            node.end = true;
        }

        char[] str = s.toCharArray();
        int N = str.length;
        // s从i出发及其后面的所有，能不能被分解
        int[] dp = new int[N + 1];
        // ""能不能被分解，可以
        dp[N] = 1;
        for (int index = N - 1; index >= 0; index--) {
            TrieNode cur = root;
            // 假设出a出发的这一段 a a a b
            // a a a b
            // i
            // end 从i出发，当前节点从root出发
            for (int end = index; end < N; end++) {
                // 前缀串没路了
                // 前缀树：abc
                // "abcdef" 到d没路了，后面都不用验证，直接跳过
                cur = cur.nextS[str[end] - 'a'];
                if (cur == null) {
                    break;
                }

                if (cur.end) {
                    dp[index] += dp[end + 1];
                }
            }
        }
        return dp[0] != 0;
    }

    public static boolean wordBreak1(String s, List<String> wordDict) {
        TrieNode root = new TrieNode();
        // 建立前缀树
        for (String word : wordDict) {
            TrieNode cur = root;
            char[] str = word.toCharArray();
            int path = 0;
            for (int i = 0; i < str.length; i++) {
                path = str[i] - 'a';
                if (cur.nextS[path] == null) {
                    cur.nextS[path] = new TrieNode();
                }
                cur = cur.nextS[path];
            }
            cur.end = true;
        }
        char[] str = s.toCharArray();
        int n = str.length;
        // s从start出发及其往后的所有可能，能不能被分解
        boolean[] dp = new boolean[n + 1];
        // ""能不能被分解
        dp[n] = true;
        for (int start = n - 1; start >= 0; start--) {
            // s[start...]能不能被分解
            TrieNode cur = root;
            for (int end = start; end < n; end++) {
                // 下级的路
                cur = cur.nextS[str[end] - 'a'];
                // 没有下级的路直接停
                if (cur == null) {
                    break;
                }
                // 有路，且s[start...end]是在字典中的有效串，看从end+1...及其往后能不能被分解
                if (cur.end) {
                    dp[start] |= dp[end + 1];
                }
                if (dp[start]) {
                    break;
                }
            }
        }
        return dp[0];
    }

    public static class TrieNode {
        public boolean end;
        public TrieNode[] nextS;

        public TrieNode() {
            this.end = false;
            this.nextS = new TrieNode[26];
        }
    }

    public static void main(String[] args) {
        String s = "aaaaaaa";
        List<String> wordDict = new ArrayList<>();
        wordDict.add("aaaa");
        wordDict.add("aa");
        System.out.println(wordBreak1(s, wordDict));
    }
}
