package com.rukawa.algorithm.leetcode.top100likedquestions;

/**
 * create by hqh on 2022/9/18
 */
public class Problem_0796_RotateString {
    /**
     * 旋转字符串
     * 给定两个字符串, s 和 goal。如果在若干次旋转操作之后，s 能变成 goal ，那么返回 true 。
     * s 的 旋转操作 就是将 s 最左边的字符移动到最右边。 
     * 例如, 若 s = 'abcde'，在旋转一次之后结果就是'bcdea' 。
     *
     * 示例 1:
     * 输入: s = "abcde", goal = "cdeab"
     * 输出: true
     *
     * 示例 2:
     * 输入: s = "abcde", goal = "abced"
     * 输出: false
     *
     * 提示:
     * 1 <= s.length, goal.length <= 100
     * s 和 goal 由小写英文字母组成
     */
    public boolean rotateString(String s, String goal) {
        if (s.length() != goal.length()) {
            return false;
        }
        // 思路：s1和s2相加形成一个新的字符串，判断s2是否是新字符串的子串

        char[] match = goal.toCharArray();
        int[] next = getNext(match);

        char[] str = new char[s.length() << 1];
        int index = 0;
        for (int i = 0; i < s.length(); i++) {
            str[index] = s.charAt(i);
            str[index + s.length()] = s.charAt(i);
            index++;
        }

        int x = 0;
        int y = 0;
        while (x < str.length && y < match.length) {
            if (str[x] == match[y]) {
                x++;
                y++;
            } else if (next[y] == -1) {
                x++;
            } else {
                y = next[y];
            }
        }
        int indexPosition = (y == match.length ? x - y : -1);
        return indexPosition != -1;
    }

    public int[] getNext(char[] match) {
        if (match.length == 0) {
            return new int[]{-1};
        }
        int[] next = new int[match.length];
        next[0] = -1;
        next[1] = 0;
        int index = 2;
        int cn = 0;
        while (index < match.length) {
            if (match[index - 1] == match[cn]) {
                next[index++] = ++cn;
            } else if (cn > 0) {
                cn = next[cn];
            } else {
                next[index++] = 0;
            }
        }
        return next;
    }
}
