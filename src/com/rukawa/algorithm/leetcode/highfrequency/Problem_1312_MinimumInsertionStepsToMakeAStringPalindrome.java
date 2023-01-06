package com.rukawa.algorithm.leetcode.highfrequency;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * create by hqh on 2022/9/24
 */
public class Problem_1312_MinimumInsertionStepsToMakeAStringPalindrome {
    /**
     * 让字符串成为回文串的最少插入次数
     * 给你一个字符串 s ，每一次操作你都可以在字符串的任意位置插入任意字符。
     * 请你返回让 s 成为回文串的 最少操作次数 。
     * 「回文串」是正读和反读都相同的字符串。
     *
     * 示例 1：
     * 输入：s = "zzazz"
     * 输出：0
     * 解释：字符串 "zzazz" 已经是回文串了，所以不需要做任何插入操作。
     *
     * 示例 2：
     * 输入：s = "mbadm"
     * 输出：2
     * 解释：字符串可变为 "mbdadbm" 或者 "mdbabdm" 。
     *
     * 示例 3：
     * 输入：s = "leetcode"
     * 输出：5
     * 解释：插入 5 个字符后字符串变为 "leetcodocteel" 。
     *
     * 提示：
     * 1 <= s.length <= 500
     * s 中所有字符都是小写字母。
     */
    public int minInsertions(String s) {
        // 范围上的尝试模型
        if (s == null || s.length() == 0) {
            return 0;
        }
        char[] str = s.toCharArray();
        int n = str.length;
        int[][] dp = new int[n][n];
        for (int i = 0; i < n - 1; i++) {
            dp[i][i + 1] = str[i] == str[i + 1] ? 0 : 2;
        }

        for (int i = n - 3; i >= 0; i--) {
            for (int j = i + 2; j < n; j++) {
                dp[i][j] = Math.min(dp[i + 1][j], dp[i][j - 1]) + 1;
                if (str[i] == str[j]) {
                    dp[i][j] = Math.min(dp[i][j], dp[i + 1][j - 1]);
                }
            }
        }
        return dp[0][n - 1];
    }

    // 返回一种添加结果
    public static String minInsertionsOneWay(String s) {
        // 利用动态规划表回溯
        if (s == null || s.length() == 0) {
            return s;
        }
        char[] str = s.toCharArray();
        int n = str.length;
        int[][] dp = new int[n][n];
        // dp[i][i] = 0
        for (int i = 0; i < n - 1; i++) {
            dp[i][i + 1] = str[i] == str[i + 1] ? 0 : 1;
        }

        for (int l = n - 3; l >= 0; l--) {
            for (int r = l + 2; r < n; r++) {
                dp[l][r] = Math.min(dp[l + 1][r], dp[l][r - 1]) + 1;
                if (str[l] == str[r]) {
                    dp[l][r] = Math.min(dp[l][r], dp[l + 1][r - 1]);
                }
            }
        }
        // 从左上角开始回溯
        // dp[0][n - 1] 是需要添加的字符个数
        int l = 0;
        int r = n - 1;
        char[] res = new char[n + dp[l][r]];
        int resL = 0;
        int resR = res.length - 1;
        while (l < r) {
            // 如果str[l...r-1]自己去搞定，r位置的字符要在之前搞定的基础上左边填写一个堆成的str[r]
            // 然后把当前str[r]位置填到最后的位置
            if (dp[l][r - 1] == dp[l][r] - 1) {
                res[resL++] = str[r];
                res[resR--] = str[r--];
            } else if (dp[l + 1][r] == dp[l][r] - 1) {
                res[resL++] = str[l];
                res[resR--] = str[l++];
            } else {
                res[resL++] = str[l++];
                res[resR--] = str[r--];
            }
        }
        // b a b
        // 此时把b和b都填到对应的格子，此时l==r，要把a也填入答案
        if (l == r) {
            res[resL] = str[l];
        }
        // l > r 说明全部搞定了
        return String.valueOf(res);
    }

    // 返回所有的添加结果
    public static List<String> minInsertionsAllWays(String s) {
        List<String> res = new ArrayList<>();
        // 利用动态规划表回溯
        if (s == null || s.length() == 0) {
            return res;
        }
        char[] str = s.toCharArray();
        int n = str.length;
        int[][] dp = new int[n][n];
        // dp[i][i] = 0
        for (int i = 0; i < n - 1; i++) {
            dp[i][i + 1] = str[i] == str[i + 1] ? 0 : 1;
        }

        for (int l = n - 3; l >= 0; l--) {
            for (int r = l + 2; r < n; r++) {
                dp[l][r] = Math.min(dp[l + 1][r], dp[l][r - 1]) + 1;
                if (str[l] == str[r]) {
                    dp[l][r] = Math.min(dp[l][r], dp[l + 1][r - 1]);
                }
            }
        }
        int l = 0;
        int r = n - 1;
        char[] path = new char[n + dp[l][r]];
        int pl = 0;
        int pr = path.length - 1;
        process(str, dp, l, r, path, pl, pr, res);
        return res;
    }

    // 当前来到动态规划表的l，r位置
    // 填写一个路径path，填写pl和pr之间的位置，其余格子已经全部填完了
    public static void process(char[] str, int[][] dp, int l, int r, char[] path, int pl, int pr, List<String> res) {
        if (l >= r) {
            // l > r，说明之前的位置已经填完了
            // b a b的时候，把b都已经填完了，此时来到a的位置，l==r，把a填到答案中
            if (l == r) {
                path[pl] = str[l];
            }
            res.add(String.valueOf(path));
        } else {
            // path[l,r-1]自己去搞定，r位置的字符填到path中最后的位置，在开头位置填一个r位置的字符
            if (dp[l][r - 1] == dp[l][r] - 1) {
                path[pl] = str[r];
                path[pr] = str[r];
                process(str, dp, l, r - 1, path, pl + 1, pr - 1, res);
                // 要恢复现场，但是下面直接覆盖了答案，所以不需要恢复
            }
            if (dp[l + 1][r] == dp[l][r] - 1) {
                path[pl] = str[l];
                path[pr] = str[l];
                process(str, dp, l + 1, r, path, pl + 1, pr - 1, res);
            }
            // l == r - 1 代表就剩下两个字符且两个位置的值相等，直接拷贝下来
            if (str[l] == str[r] && (l == r - 1 || dp[l + 1][r - 1] == dp[l][r])) {
                path[pl] = str[l];
                path[pr] = str[r];
                process(str, dp, l + 1, r - 1, path, pl + 1, pr - 1, res);
            }
        }
    }

    public static void main(String[] args) {
        String s = null;
        String ans2 = null;
        List<String> ans3 = null;

        System.out.println("本题第二问，返回其中一种结果测试开始");
        s = "mbadm";
        ans2 = minInsertionsOneWay(s);
        System.out.println(ans2);

        s = "leetcode";
        ans2 = minInsertionsOneWay(s);
        System.out.println(ans2);

        s = "aabaa";
        ans2 = minInsertionsOneWay(s);
        System.out.println(ans2);
        System.out.println("本题第二问，返回其中一种结果测试结束");

        System.out.println();

        System.out.println("本题第三问，返回所有可能的结果测试开始");
        s = "mbadm";
        ans3 = minInsertionsAllWays(s);
        for (String way : ans3) {
            System.out.println(way);
        }
        System.out.println();

        s = "leetcode";
        ans3 = minInsertionsAllWays(s);
        for (String way : ans3) {
            System.out.println(way);
        }
        System.out.println();

        s = "aabaa";
        ans3 = minInsertionsAllWays(s);
        for (String way : ans3) {
            System.out.println(way);
        }
        System.out.println();
        System.out.println("本题第三问，返回所有可能的结果测试结束");
    }
}
