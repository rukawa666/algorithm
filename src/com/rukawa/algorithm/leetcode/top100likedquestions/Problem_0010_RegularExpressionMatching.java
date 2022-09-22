package com.rukawa.algorithm.leetcode.top100likedquestions;

/**
 * Created with Intellij IDEA
 *
 * @Author：SuperHai
 * @Date：2020-09-20 8:30
 * @Version：1.0
 */
public class Problem_0010_RegularExpressionMatching {
    /**
     * 正则表达式匹配
     * 给你一个字符串 s 和一个字符规律 p，请你来实现一个支持 '.' 和 '*' 的正则表达式匹配。
     * '.' 匹配任意单个字符
     * '*' 匹配零个或多个前面的那一个元素
     * 所谓匹配，是要涵盖 整个 字符串 s的，而不是部分字符串。
     *  
     * 示例 1：
     * 输入：s = "aa", p = "a"
     * 输出：false
     * 解释："a" 无法匹配 "aa" 整个字符串。
     *
     * 示例 2:
     * 输入：s = "aa", p = "a*"
     * 输出：true
     * 解释：因为 '*' 代表可以匹配零个或多个前面的那一个元素, 在这里前面的元素就是 'a'。因此，字符串 "aa" 可被视为 'a' 重复了一次。
     *
     * 示例 3：
     * 输入：s = "ab", p = ".*"
     * 输出：true
     * 解释：".*" 表示可匹配零个或多个（'*'）任意字符（'.'）。
     *
     * 提示：
     * 1 <= s.length <= 20
     * 1 <= p.length <= 30
     * s 只包含从 a-z 的小写字母。
     * p 只包含从 a-z 的小写字母，以及字符 . 和 *。
     * 保证每次出现字符 * 时，前面都匹配到有效的字符
     */
    public boolean isMatch(String s, String p) {
        if (s == null || p == null) {
            return false;
        }
        char[] str = s.toCharArray();
        char[] match = p.toCharArray();
        return isValid(str, match) && process(str, 0, match, 0);
    }

    public boolean isValid(char[] str, char[] match) {
        // s中不能有'.'和'*'
        for (int i = 0; i < str.length; i++) {
            if (str[i] == '*' || str[i] == '.') {
                return false;
            }
        }
        // 开头的e[0]不能是'*'，两个'*'不能一起出现
        for (int i = 0; i < match.length; i++) {
            if (match[i] == '*' && (i == 0 || match[i - 1] == '*')) {
                return false;
            }
        }
        return true;
    }

    // str[si...]从si出发及其后面的所有，能不能被match[mi...]mi出发及其后面的所有，能不能被匹配出来
    public boolean process(char[] str, int si, char[] match, int mi) {
        /**
         * 逻辑划分：
         *   mi的下一个位置是不是'*'做划分
         *   1.如果mi的下一个位置不是*，代表si和mi的位置必须能对上，mi后面没有操作空间，只能有以下两种情况
         *     a. str[si] = match[mi]
         *     b. match[mi] = '.'
         *   2.如果mi的下一个位置是*，此时str[si]=a, match[mi+1]='*'
         *     a. 如果str[si] != match[mi]，只能选择让match[ei,ei+1]变成0个match[ei] => process(si, mi+2)
         *     b. 如果str[si] == match[mi]，可以让match[ei,ei+1]变成0个match[ei] => process(si, mi+2)
         *     c. 如果str[si] == match[mi]，可以让match[ei,ei+1]变成1个match[ei] => process(si+1, mi+2)
         *     d. 根str[si]的后面位置数对比
         */
        // 必须所有位置完全匹配
        if (mi == match.length) {
            return si == str.length;
        }

        // mi+1位置不是'*'，两种情况，后面没有位置了或者有位置但是下一个位置不是'*'
        if (mi + 1 == match.length || match[mi + 1] != '*') {
            // si的字符还有位置且后续的位置能全部匹配
            return si != str.length && (str[si] == match[mi] || match[mi] == '.') && process(str, si + 1, match, mi +1);
        }

        // match[ei+1]='*'
        // a a a a a
        // a *
        while (si != str.length && (match[mi] == str[si] || match[mi] == '.')) {
            // match[mi,mi+1]变成0个a，1个a。。。去尝试
            if (process(str, si, match, mi + 2)) {
                return true;
            }
            si++;
        }
        return process(str, si, match, mi + 2);
    }

    // 暴力尝试改成记忆化搜索
    public boolean isMatch2(String s, String p) {
        if (s == null || p == null) {
            return false;
        }
        char[] str = s.toCharArray();
        char[] match = p.toCharArray();
        if (!isValid(str, match)) {
            return false;
        }
        int m = str.length;
        int n = match.length;
        int[][] dp = new int[m + 1][n + 1];
        // dp[i][j] = 0 代表没算过  dp[i][j] = 1代表算法，返回true，dp[i][j] = -1代表算过，返回false
        return  process2(str, 0, match, 0, dp);
    }

    // str[si...]从si出发及其后面的所有，能不能被match[mi...]mi出发及其后面的所有，能不能被匹配出来
    public boolean process2(char[] str, int si, char[] match, int mi, int[][] dp) {
        if (dp[si][mi] != 0) {
            return dp[si][mi] == 1;
        }
        /**
         * 逻辑划分：
         *   mi的下一个位置是不是'*'做划分
         *   1.如果mi的下一个位置不是*，代表si和mi的位置必须能对上，mi后面没有操作空间，只能有以下两种情况
         *     a. str[si] = match[mi]
         *     b. match[mi] = '.'
         *   2.如果mi的下一个位置是*，此时str[si]=a, match[mi+1]='*'
         *     a. 如果str[si] != match[mi]，只能选择让match[ei,ei+1]变成0个match[ei] => process(si, mi+2)
         *     b. 如果str[si] == match[mi]，可以让match[ei,ei+1]变成0个match[ei] => process(si, mi+2)
         *     c. 如果str[si] == match[mi]，可以让match[ei,ei+1]变成1个match[ei] => process(si+1, mi+2)
         *     d. 根str[si]的后面位置数对比
         */
        boolean res = false;
        // 必须所有位置完全匹配
        if (mi == match.length) {
            res = si == str.length;
        } else {
            // mi+1位置不是'*'，两种情况，后面没有位置了或者有位置但是下一个位置不是'*'
            if (mi + 1 == match.length || match[mi + 1] != '*') {
                // si的字符还有位置且后续的位置能全部匹配
                res = si != str.length && (str[si] == match[mi] || match[mi] == '.') && process2(str, si + 1, match,
                        mi +1, dp);
            } else {
                // match[ei+1]='*'
                // a a a a a
                // a *
                while (si != str.length && (match[mi] == str[si] || match[mi] == '.')) {
                    // match[mi,mi+1]变成0个a，1个a。。。去尝试
                    if (process2(str, si, match, mi + 2, dp)) {
                        res = true;
                        break;
                    }
                    si++;
                }
                // 如果上面没有匹配上，下面继续匹配
                res = res | process2(str, si, match, mi + 2, dp);
            }
        }
        dp[si][mi] = res ? 1 : -1;
        return res;
    }

    public boolean isMatch3(String s, String p) {
        if (s == null || p == null) {
            return false;
        }
        char[] str = s.toCharArray();
        char[] match = p.toCharArray();
        if (!isValid(str, match)) {
            return false;
        }
        int m = str.length;
        int n = match.length;
        int[][] dp = new int[m + 1][n + 1];
        // dp[i][j] = 0 代表没算过  dp[i][j] = 1代表算法，返回true，dp[i][j] = -1代表算过，返回false
        return  process3(str, 0, match, 0, dp);
    }

    // str[si...]从si出发及其后面的所有，能不能被match[mi...]mi出发及其后面的所有，能不能被匹配出来
    public boolean process3(char[] str, int si, char[] match, int mi, int[][] dp) {
        if (dp[si][mi] != 0) {
            return dp[si][mi] == 1;
        }
        /**
         * 逻辑划分：
         *   mi的下一个位置是不是'*'做划分
         *   1.如果mi的下一个位置不是*，代表si和mi的位置必须能对上，mi后面没有操作空间，只能有以下两种情况
         *     a. str[si] = match[mi]
         *     b. match[mi] = '.'
         *   2.如果mi的下一个位置是*，此时str[si]=a, match[mi+1]='*'
         *     a. 如果str[si] != match[mi]，只能选择让match[ei,ei+1]变成0个match[ei] => process(si, mi+2)
         *     b. 如果str[si] == match[mi]，可以让match[ei,ei+1]变成0个match[ei] => process(si, mi+2)
         *     c. 如果str[si] == match[mi]，可以让match[ei,ei+1]变成1个match[ei] => process(si+1, mi+2)
         *     d. 根str[si]的后面位置数对比
         */
        boolean res = false;
        // 必须所有位置完全匹配
        if (mi == match.length) {
            res = si == str.length;
        } else {
            // mi+1位置不是'*'，两种情况，后面没有位置了或者有位置但是下一个位置不是'*'
            if (mi + 1 == match.length || match[mi + 1] != '*') {
                // si的字符还有位置且后续的位置能全部匹配
                res = si != str.length && (str[si] == match[mi] || match[mi] == '.') && process3(str, si + 1, match,
                        mi +1, dp);
            } else {
                // match[ei+1]='*'
                // a a a a a
                // a *
                if (si == str.length) { // si结束了，没有位置可以匹配
                    res = process3(str, si, match, mi + 2, dp);
                } else { // si还没结束
                    if (str[si] != match[mi] && match[mi] != '.') {
                        res = process3(str, si, match, mi + 2, dp);
                    } else { // si可以和mi匹配
                        /**
                         * 斜率优化
                         * str   a  a  a  a  b
                         *       5  6  7  8  9
                         * match a  *
                         *       8  9 10  11 12
                         * f(5,8) 需要依赖f(5,10)、f(6,10)、f(7,10)、f(8,10)、f(9,10)
                         * f(4,8) 需要依赖f(4,10)、f(5,10)、f(6,10)、f(7,10)、f(8,10)、f(9,10)
                         * 所以f(4,8) 依赖f(4,10)、f(5,8)
                         */
                        res = process3(str, si, match, mi + 2, dp) || process3(str, si + 1, match, mi, dp);
                    }
                }
//                while (si != str.length && (match[mi] == str[si] || match[mi] == '.')) {
//                    // match[mi,mi+1]变成0个a，1个a。。。去尝试
//                    if (process3(str, si, match, mi + 2, dp)) {
//                        res = true;
//                        break;
//                    }
//                    si++;
//                }
//                // 如果上面没有匹配上，下面继续匹配
//                res = res | process3(str, si, match, mi + 2, dp);
            }
        }
        dp[si][mi] = res ? 1 : -1;
        return res;
    }

    public boolean isMatch4(String s, String p) {
        if (s == null || p == null) {
            return false;
        }
        char[] str = s.toCharArray();
        char[] match = p.toCharArray();
        if (!isValid(str, match)) {
            return false;
        }
        int n = str.length;
        int m = match.length;
        boolean[][] dp = new boolean[n + 1][m + 1];
        dp[n][m] = true;
        for (int j = m - 1; j >= 0; j--) {
            dp[n][j] = (j + 1 < m && match[j + 1] == '*') && dp[n][j + 2];
        }

        if (n > 0 && m > 0) {
            dp[n - 1][m - 1] = (str[n - 1] == match[m - 1] || match[m - 1] == '.');
        }

        for (int i = n - 1; i >= 0; i--) {
            for (int j = m - 2; j >= 0; j--) {
                if (match[j + 1] != '*') {
                    dp[i][j] = ((str[i] == match[j] || (match[j] == '.'))) && dp[i + 1][j + 1];
                } else {
                    if ((str[i] == match[j] || match[j] == '.') && dp[i + 1][j]) {
                        dp[i][j] = true;
                    } else {
                        dp[i][j] = dp[i][j + 2];
                    }
                }
            }
        }
        return dp[0][0];
    }
}
