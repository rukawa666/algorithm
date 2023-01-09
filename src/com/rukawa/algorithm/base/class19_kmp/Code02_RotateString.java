package com.rukawa.algorithm.base.class19_kmp;

/**
 * create by hqh on 2022/9/18
 */
public class Code02_RotateString {
    /**
     * 判断两个字符串是否是旋转串
     *
     * s 的 旋转操作 就是将 s 最左边的字符移动到最右边。
     * 例如, 若 s = 'abcde'，在旋转一次之后结果就是'bcdea' 。
     */

    public static boolean rotateString(String s, String goal) {
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

    public static int[] getNext(char[] match) {
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

    public static void main(String[] args) {
        String s = "abcde";
        String glob = "abced";
        System.out.println(rotateString(s, glob));
    }
}
