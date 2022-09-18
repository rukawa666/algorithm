package com.rukawa.algorithm.base.class20;

/**
 * create by hqh on 2022/9/18
 */
public class Code02_AddShortestEnd {
    /**
     * 在一个字符串中，在它后面添加字符，使之整体变成回文串，至少需要添加几个字符
     * 实质：必须包含最后一个字符的情况下，最长回文子串是多长
     */

    public static String shortestEnd(String s) {
        char[] str = manacherString(s);
        int[] pArr = new int[str.length];
        int C = -1;
        int R = -1;
        int end = -1;
        for (int i = 0; i < str.length; i++) {
            pArr[i] = R > i ? Math.min(pArr[2 * C - i], R - i) : 1;

            while (i + pArr[i] < str.length && i - pArr[i] > -1) {
                if (str[i + pArr[i]] == str[i - pArr[i]]) {
                    pArr[i]++;
                } else {
                    break;
                }
            }

            if (i + pArr[i] > R) {
                R = i + pArr[i];
                C = i;
            }

            if (R == str.length) {
                end = pArr[i];
                break;
            }
        }

        char[] res = new char[s.length() - end + 1];
        for (int i = 0; i < res.length; i++) {
            res[res.length - 1 - i] = str[(i << 1) + 1];
        }
        return String.valueOf(res);
    }

    public static char[] manacherString(String s) {
        char[] str = s.toCharArray();
        char[] res = new char[(str.length << 1) + 1];
        int index = 0;
        for (int i = 0; i < res.length; i++) {
            res[i] = (i & 1) == 0 ? '#' : str[index++];
        }
        return res;
    }

    public static void main(String[] args) {
        String s = "abcd123321";
        System.out.println(shortestEnd(s));
    }
}
