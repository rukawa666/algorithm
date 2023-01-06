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
     * 问题1：一个字符串至少要切分几刀能让切分出来的子串都是回文串
     * 问题2：返回问题1的其中一种切分结果
     * 问题3：返回问题1的所有切分结果
     */

    // 一个字符串至少要切分几刀能让切分出来的子串都是回文串
    // 从左到右的尝试模型
    public int minCut(String s) {
        if (s == null || s.length() < 2) {
            return 0;
        }
        char[] str = s.toCharArray();
        int n = str.length;
        // 预处理表map 正方形表，告诉l...r是否是回文
        boolean[][] checkMap = createCheckMap(str, n);
        int[] dp = new int[n + 1];
        dp[n] = 0;
        for (int i = n - 1; i >= 0; i--) {
            // 从i～n-1已经是回文了，一个部分
            if (checkMap[i][n - 1]) {
                dp[i] = 1;
            } else {
                // 尝试i～j
                int next = Integer.MAX_VALUE;
                for (int j = i; j < n; j++) {
                    // i～j已经是回文了，j+1往后能分为几块
                    if (checkMap[i][j]) {
                        next = Math.min(next, dp[j + 1]);
                    }
                }
                // j+1往后能最少分为几块，然后+i～j的这一块回文
                dp[i] = next + 1;
            }
        }
        // dp[0]是部分数，比如三个部分，只需要切两刀，所以返回两刀
        return dp[0] - 1;
    }

    // str[l...r]范围上是否是回文
    public boolean[][] createCheckMap(char[] str, int n) {
        boolean[][] map = new boolean[n][n];
        for (int i = 0; i < n - 1; i++) {
            map[i][i] = true;
            map[i][i + 1] = str[i] == str[i + 1];
        }
        // 从l...r是否是回文
        map[n - 1][n - 1] = true;
        for (int l = n - 3; l >= 0; l--) {
            for (int r = l + 2; r < n; r++) {
                map[l][r] = str[l] == str[r] && map[l + 1][r - 1];
            }
        }
        return map;
    }

    // str[i...]至少分成几个部分会使每个部分都是回文
    public int process(char[] str, int index) {
        // 没字符串，不需要分
        if (index == str.length) {
            return 0;
        }

        int next = Integer.MAX_VALUE;
        for (int end = index; end < str.length; end++) {
            // str[index...end]已经是回文了，剩下部分怎么切分
//            if (str[index...end]) {
//                next = process(str, end + 1);
//            }
        }
        return next + 1;
    }


    // 返回其中的一种拆分结果
    public List<String> minCutOneWay(String s) {
        List<String> res = new ArrayList<>();
        if (s == null || s.length() < 2) {
            res.add(s);
        }
        char[] str = s.toCharArray();
        int n = str.length;
        // 预处理表map 正方形表，告诉l...r是否是回文
        boolean[][] checkMap = createCheckMap(str, n);
        int[] dp = new int[n + 1];
        dp[n] = 0;
        for (int i = n - 1; i >= 0; i--) {
            if (checkMap[i][n - 1]) {
                dp[i] = 1;
            } else {
                int next = Integer.MAX_VALUE;
                for (int j = i; j < n; j++) {
                    if (checkMap[i][j]) {
                        next = Math.min(next, dp[j + 1]);
                    }
                }
                dp[i] = next + 1;
            }
        }

        /**
         * dp是把str最少能拆分成几个回文串
         * str: a a e e f f c f f
         * dp:  3 3 2 2 1 2 2 1 1 0
         *      0 1 2 3 4 5 6 7 8 9
         * 目标：0到最后怎么拆？
         * 0～0是回文的情况下。发现dp[0]!=dp[1]+1，说明0～8整体字符串不是0～0部分是最优解
         * 0~1是回文的情况下，发现dp[0]=dp[2]+1，说明0～1部分可能是最优解
         * 2～2是回文情况下，发现dp[2]!=dp[3]+1，说明2～2不是最优解
         * 2～3是回文的情况下，发现dp[2]=dp[4]+1，说明2～3部分可能是最优解决
         * 4～4是回文的情况下，dp[4]!=dp[5]+1，说明4～4不是最优解
         * 4～5的是回文的情况下，dp[4]!=dp[6]+1,说明4～5不是最优解
         * 4～6不是回文
         * 4～7不是回文
         * 4～8是回文，且dp[4]=dp[9]+1,说明4～8部分可能是最优解
         */

        for (int i = 0, j = 1; j <= n; j++) {
            // i...j-1是回文，判断dp[i]==dp[j]+1，如果满足说明i...j-1是最优的划分区域
            if (checkMap[i][j - 1] && dp[i] == dp[j] + 1) {
                res.add(s.substring(i, j));
                i = j;
            }
        }
        return res;
    }

    // 问题3：返回所有的切分结果
    public List<List<String>> partition(String s) {
        List<List<String>> res = new ArrayList<>();
        if (s == null || s.length() < 2) {
            List<String> cur = new ArrayList<>();
            cur.add(s);
            res.add(cur);
        } else {
            char[] str = s.toCharArray();
            int n = str.length;
            // 预处理表map 正方形表，告诉l...r是否是回文
            boolean[][] checkMap = createCheckMap(str, n);
            int[] dp = getDP(n, checkMap);
            process(s, 0, 1, checkMap, dp, new ArrayList<>(), res);
        }
        return res;
    }

    // str最少能拆分成几个回文区域
    public int[] getDP(int n, boolean[][] checkMap) {
        int[] dp = new int[n + 1];
        dp[n] = 0;
        for (int i = n - 1; i >= 0; i--) {
            // 从i～n-1已经是回文了，一个部分
            if (checkMap[i][n - 1]) {
                dp[i] = 1;
            } else {
                // 尝试i～j
                int next = Integer.MAX_VALUE;
                for (int j = i; j < n; j++) {
                    // i～j已经是回文了，j+1往后能分为几块
                    if (checkMap[i][j]) {
                        next = Math.min(next, dp[j + 1]);
                    }
                }
                // j+1往后能最少分为几块，然后+i～j的这一块回文
                dp[i] = next + 1;
            }
        }
        return dp;
    }

    // s[0..i-1] 存到path中去了
    // s[i...j-1]考察的分割出来的第一个区域
    public void process(String s, int i, int j, boolean[][] checkMap, int[] dp, List<String> path,
                        List<List<String>> res) {
        if (j == s.length()) {
            if (checkMap[i][j - 1] && dp[i] == dp[j] + 1) {
                path.add(s.substring(i, j));
                res.add(copyList(path));
                // 恢复现场
                path.remove(path.size() - 1);
            }
        } else {
            if (checkMap[i][j - 1] && dp[i] == dp[j] + 1) {
                path.add(s.substring(i, j));
                process(s, j, j + 1, checkMap, dp, path, res);
                // 恢复现场
                path.remove(path.size() - 1);
            }
            process(s, i, j + 1, checkMap, dp, path, res);
        }
    }

    public List<String> copyList(List<String> path) {
        List<String> res = new ArrayList<>();
        for (String s : path) {
            res.add(s);
        }
        return res;
    }

//    public static List<List<String>> partition(String s) {
//        boolean[][] dp = getDP(s.toCharArray());
//        LinkedList<String> path = new LinkedList<>();
//        List<List<String>> res = new ArrayList<>();
//        process(s, 0, path, dp, res);
//        return res;
//    }
//
//
//    public static boolean[][] getDP(char[] chs) {
//        int N = chs.length;
//        boolean[][] dp = new boolean[N][N];
//        for (int i = 0; i < N - 1; i++) {
//            dp[i][i] = true;
//            dp[i][i + 1] = chs[i] == chs[i + 1];
//        }
//        dp[N - 1][N - 1] = true;
//        for (int j = 2; j < N; j++) {
//            int row = 0;
//            int col = j;
//            while (row < N && col < N) {
//                dp[row][col] = chs[row] == chs[col] && dp[row + 1][col - 1];
//                row++;
//                col++;
//            }
//        }
//        return dp;
//    }
//
//    public static void process(String s, int index, LinkedList<String> path, boolean[][] dp, List<List<String>> res) {
//        if (index == s.length()) {
//            res.add(copy(path));
//        } else {
//            for (int end = index; end < s.length(); end++) {
//                if (dp[index][end]) {
//                    path.addLast(s.substring(index, end + 1));
//                    process(s, end + 1, path, dp, res);
//                    path.pollLast();
//                }
//            }
//        }
//    }
//
//    public static List<String> copy(List<String> path) {
//        List<String> res = new ArrayList<>();
//        for (String s : path) {
//            res.add(s);
//        }
//        return res;
//    }
//
//    public static void main(String[] args) {
//        System.out.println(partition("efe"));
//    }

}
