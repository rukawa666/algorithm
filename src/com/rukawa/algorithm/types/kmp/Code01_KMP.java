package com.rukawa.algorithm.types.kmp;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @Author: Administrator
 * @Date: 2021/6/8 0008 22:05
 */
public class Code01_KMP {

    public static int kmp(String s, String m) {
        if (s == null || m == null || s.length() == 0 || m.length() > s.length()) {
            return 0;
        }
        char[] str = s.toCharArray();
        char[] match = m.toCharArray();
        int x = 0;
        int y = 0;
        int max = 0;
        int[] nextArr = getNextArray(match);
        while (x < str.length && y < nextArr.length) {
            if (str[x] == match[y]) {
                x++;
                y++;
            } else if (nextArr[y] != -1) {
                y = nextArr[y];
            } else {
                x++;
            }
        }
        return y == match.length ? x - y : -1;
    }

    public static int[] getNextArray(char[] match) {
        if (match.length == 1) {
            return new int[]{-1};
        }
        int[] ans = new int[match.length];
        ans[0] = -1;
        ans[1] = 0;
        int index = 2;
        int cn = 0;
        while (index < ans.length) {
            if (match[cn] == match[index - 1]) {
                ans[index++] = ++cn;
            } else if (cn > 0) {
                cn = ans[cn];
            } else {
                ans[index++] = 0;
            }
        }
        return ans;
    }
}
