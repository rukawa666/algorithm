package com.rukawa.algorithm.trainingcamp.trainingcamp4.class8;

/**
 * Created with Intellij IDEA
 *
 * @Author：SuperHai
 * @Date：2020-10-22 7:51
 * @Version：1.0
 */
public class Code01_RegularExpressionMatch {
    /**
     * 判断一个由[a~z]字符构成的字符串和一个包含'.'和'*'通配符的字符串是否匹配。
     * 通配符'.'匹配任意单一字符，'*'匹配任意多个字符包括0个字符。字符串长度
     * 不会超过100，字符串不为空。
     * 输入描述：
     *  字符串str和包含通配符的字符串match。1<=字符串长度<=100
     * 输出描述：
     *  true表示匹配，false表示不匹配。
     */

    public static boolean isMatch(String str, String exp) {
        /**
         * 思路：
         *   1、'.'匹配任意单一字符，不能变成'*'字符
         *   2、'*'不能单独使用，必须和前面一个字符配合使用，不能同时出现两个'*'
         *   3、'.*'，可以把'*'换成任意多个'.'，然后可以和str完全匹配
         *   4、'.*'，可以替换为空
         * 检查：
         *   1、str串不能包含'*'和'.'
         *   2、match的开头不能出现'*'，并且两个'*'不能挨着
         */
        if (str == null || exp == null) {
            return false;
        }
        char[] s = str.toCharArray();
        char[] e = exp.toCharArray();
        return isValid(s, e) && process(s, e, 0, 0);

    }

    public static boolean isValid(char[] s, char[] e) {
        // str中不能有'*' or '.'
        for (int i = 0; i < s.length; i++) {
            if (s[i] == '.' || s[i] == '*') {
                return false;
            }
        }
        // 开头的e[0]不能是'*'，且不能连续出现'*'
        for (int i = 0; i < e.length; i++) {
            if (e[i] == '*' && (i == 0 || e[i - 1] == '*')) {
                return false;
            }
        }
        return true;
    }

    // e[ei...] 能否变成s[si...]
    public static boolean process(char[] s, char[] e, int si, int ei) {
        // base case  exp已经耗尽了
        if (ei == e.length) {
            return si == s.length;   // s是否也耗尽
        }

        // base case si == s.length 未讨论，在下面循环中包含

        /**
         * 普遍情况: 假设si是一个正常位置，它有字符，ei也是一个正常位置，它也有字符，有以下可能性
         *  1、ei+1判断是否是'*'，如果不是'*'，ei位置的字符必须和si位置的字符要匹配，从si+1和ei+1能够匹配就是匹配成功
         *  2、ei+1判断是否是'*'，如果是'*'，比如s=[a a a a a b c d e f]
         *     a、ei位置和si位置的字符不一样，当前si位置是'a',ei位置是'k'，则把ei~ei+1位置变为0个'k'，process(s, e, si, ei + 2)
         *     b、ei位置和si位置的字符一样，ei~ei+1位置的字符变为0~5个'a'，挨个去尝试，只要有一个true，匹配成功
         *     c、如果ei位置的字符是'.',匹配出全部的字符
         */

        // exp[ei]有字符，exp[ei] != '*'
        // 可能性1：ei+1位置不是'*'
        if (ei + 1 == e.length || e[ei + 1] != '*') {
            // si不能提前耗尽，耗尽不能匹配成功
            return si != s.length
                    && (e[ei] == s[si] || e[ei] == '.')
                    && process(s, e, si + 1, ei + 1);
        }

        // 可能性2：ei+1位置的字符是'*'
        // 尝试 ei~ei+1位置的字符，匹配str可能的前缀
        while (si != s.length && (e[ei] == s[si] || e[ei] == '.')) {
            if (process(s, e, si, ei + 2)) {
                return true;
            }
            si++;
        }
        // 因为是先调用process，在si++，所以最后一个字符不会执行，所以最后要执行一次
        // 如果e[ei]!=s[si]，执行匹配0个si的情况
        return process(s, e, si, ei + 2);
    }

    // 动态规划
    public static boolean isMatchDP(String str, String exp) {
        if (str == null || exp == null) {
            return false;
        }
        char[] s = str.toCharArray();
        char[] e = exp.toCharArray();
        if (!isValid(s, e)) {
            return false;
        }
        /**
         * 思路：
         *   1、暴力递归，可以看到普遍一个格子dp[i][j]依赖dp[i+1][j+1]，还有依赖dp[i][j+2]，i++
         *   2、所以总结第一条，普遍一个格子依赖的右下角的格子，和右边第2列的格子，要把最后一行和倒数两列填好
         *   3、根据base case，最后一行，只有str也到最后一个字符才为true，其余都为false
         *   4、倒数第二列最后一行，exp还剩下一个字符，str已经提前耗尽，不考虑最后一个格子是'*'的情况，则dp[s.len][e.len-1]
         *   5、str和exp都剩余一个字符，两个字符相等为true，其余倒数第二列，都是false
         *   6、最后一行，str已经被耗尽，但是exp还剩下若干字符，空串是可以匹配的，如果exp若干字符是'd*a*b*c*'类似这种，可以变为空串
         */
        // 做出一张表，填好倒数两列和最后一行
        boolean[][] dp = initDPMap(s, e);
        for (int i = s.length - 1; i > -1 ; i--) {
            for (int j = e.length - 2; j > -1; j--) {
                if (e[j + 1] != '*') {
                    dp[i][j] = (s[i] == e[j] || e[j] == '.') && dp[i + 1][j + 1];
                } else {
                    int si = i;
                    while (si != s.length && (s[si] == e[j] || e[j] == '.')) {
                        if (dp[si][j + 2]) {
                            dp[i][j] = true;
                            break;
                        }
                        si++;
                    }
                    if (dp[i][j] != true) {
                        dp[i][j] = dp[si][j + 2];
                    }
                }
            }
        }
        return dp[0][0];
    }

    public static boolean[][] initDPMap(char[] s, char[] e) {
        int sLen = s.length;
        int eLen = e.length;
        boolean[][] dp = new boolean[sLen + 1][eLen + 1];
        // 倒数第一列
        dp[sLen][eLen] = true;
        // 最后一行
        for (int j = eLen - 2; j < -1; j--) {
            if (e[j] != '*' && e[j + 1] == '*') {
                dp[sLen][j] = true;
            } else {
                break;
            }
        }
        // 倒数第二列
        if (sLen > 0 && eLen > 0) {
            if ((e[eLen - 1] == '.' || s[sLen - 1] == e[eLen - 1])) {
                dp[sLen - 1][eLen - 1] = true;
            }
        }
        return dp;
    }

    public static void main(String[] args) {
        String str = "abcccdefgmm";
        String exp = "ab.*d.*e.*";
        System.out.println(isMatch(str, exp));
        System.out.println(isMatchDP(str, exp));

    }



}
