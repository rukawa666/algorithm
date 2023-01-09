package com.rukawa.algorithm.base.class19_kmp;

/**
 * create by hqh on 2022/9/16
 */
public class Code01_KMP {

    public static int getIndexOf(String s1, String s2) {
        if (s1 == null || s2 == null || s2.length() < 0 || s1.length() < s2.length()) {
            return -1;
        }
        char[] str = s1.toCharArray();
        char[] match = s2.toCharArray();
        int[] next = getNext(match);
        int x = 0;
        int y = 0;
        // O(N)
        // 如果y越界了，则说明肯定找到答案了
        while (x < str.length && y < match.length) {
            if (str[x] == match[y]) {
                x++;
                y++;
            } else if (next[y] == -1){ // y == 0
                x++;
            } else {
                // a a b a a t
                // a a b a a c
                y = next[y];  // y 跳到前缀串的后一个位置和x比较,此时y从c跳到b和t比较
            }
        }
        // 如果y越界了，则开始的位置是x位置往前推y个距离
        return y == match.length ? x - y : -1;
    }

    // 某个位置的前缀串和后缀串相等的最大长度

    /**
     * 流程：
     * acdbstacd txe acdbstacd b k
     * 此时求i位置k字符的最大长度
     * 前一个位置i-1的b，前缀串和后缀串是acdbstacd，长度是8，如果此时前缀串的后一个位置的字符和i-1的字符b相等，则i的最大长度是8
     * 但是此时t和b不等，则找到t字符的前缀串和后缀串是acd，长度是3，此时前缀穿acd的后一个字符是b和i-1位置的b相等，则i位置的最大长度4
     *
     */
    public static int[] getNext(char[] match) {
        if (match.length == 1) {
            return new int[]{-1};
        }
        int[] next = new int[match.length];
        next[0] = -1;
        next[1] = 0;
        int i = 2;
        int cn = 0;
        while (i < match.length) {
            if (match[i - 1] == match[cn]) {
                next[i++] = ++cn;
                // next[i++] = cn + 1;
                // cn++;
            } else if (cn > 0) {
                cn = next[cn];
            } else {
                next[i++] = 0;
            }
        }
        return next;
    }

    public static void main(String[] args) {
        String s = "abcdeabced";
        String glob = "abced";
        System.out.println(getIndexOf(s, glob));
    }
}
