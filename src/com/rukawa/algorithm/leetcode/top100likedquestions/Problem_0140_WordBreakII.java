package com.rukawa.algorithm.leetcode.top100likedquestions;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with Intellij IDEA
 *
 * @Author：SuperHai
 * @Date：2020-09-19 11:06
 * @Version：1.0
 */
public class Problem_0140_WordBreakII {
    /**
     * 单词拆分 II
     *
     * 给定一个非空字符串 s 和一个包含非空单词列表的字典 wordDict，在字符串中增加空格来构建一个句子，
     * 使得句子中所有的单词都在词典中。返回所有这些可能的句子。
     * 说明：
     * 分隔时可以重复使用字典中的单词。
     * 你可以假设字典中没有重复的单词。
     *
     * 示例 1：
     * 输入:
     * s = "catsanddog"
     * wordDict = ["cat", "cats", "and", "sand", "dog"]
     * 输出:
     * [
     *   "cats and dog",
     *   "cat sand dog"
     * ]
     *
     * 示例 2：
     * 输入:
     * s = "pineapplepenapple"
     * wordDict = ["apple", "pen", "applepen", "pine", "pineapple"]
     * 输出:
     * [
     *   "pine apple pen apple",
     *   "pineapple pen apple",
     *   "pine applepen apple"
     * ]
     * 解释: 注意你可以重复使用字典中的单词。
     *
     * 示例 3：
     * 输入:
     * s = "catsandog"
     * wordDict = ["cats", "dog", "sand", "and", "cat"]
     * 输出:
     * []
     */

    public List<String> wordBreak(String s, List<String> wordDict) {
        char[] str = s.toCharArray();
        TrieNode root = generateTrie(wordDict);
        boolean[] dp = getDP(s, root);
        ArrayList<String> path = new ArrayList<>();
        List<String> res = new ArrayList<>();
        process(str, 0, root, dp, path, res);
        return res;
    }


    public void process(char[] str, int index, TrieNode root, boolean[] dp, ArrayList<String> path, List<String> res) {
        if (index == str.length) {
            // pine apple pen apple
            StringBuilder builder = new StringBuilder();
            for (int i = 0; i < path.size() - 1; i++) {
                builder.append(path.get(i) + " ");
            }
            builder.append(path.get(path.size() - 1));
            res.add(builder.toString());
        } else {
            TrieNode cur = root;
            // index...end去尝试
            for (int end = index; end < str.length; end++) {
                int road = str[end] - 'a';
                if (cur.nextS[road] == null) {
                    break;
                }
                cur = cur.nextS[road];
                // str index...end 在前缀树有方案
                // dp[end+1] end+1...及其往后能被分解
                if (cur.end && dp[end + 1]) {
                    path.add(cur.path);
                    process(str,end + 1, root, dp, path, res);
                    path.remove(path.size() - 1);
                }
            }
        }
    }

    // 生成字典表的前缀树
    public TrieNode generateTrie(List<String> wordList) {
        TrieNode root = new TrieNode();
        for (String word : wordList) {
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
            cur.path = word;
        }
        return root;
    }

    // s从0开始及其往后，每个位置拆分出来的部分能不能在前缀树(字典表)找到
    public boolean[] getDP(String s, TrieNode root) {
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
        return dp;
    }

    public static class TrieNode {
        public String path;
        public boolean end;
        public TrieNode[] nextS;

        public TrieNode() {
            path = null;
            end = false;
            nextS = new TrieNode[26];
        }
    }
}
