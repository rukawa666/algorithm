package com.rukawa.algorithm.types.manahcer;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @Author: Administrator
 * @Date: 2021/6/8 0008 7:29
 */
public class Code01_Manacher {

    public static int manacher(String s) {
        if (s == null || s.length() == 0) {
            return 0;
        }
        char[] str = manacherString(s);
        int[] pRadius = new int[str.length];
        int C = -1;
        int R = -1;
        int max = Integer.MIN_VALUE;
        for (int i = 0; i < str.length; i++) {
            pRadius[i] = i < R ? Math.min(pRadius[2 * C - i], R - i) : 1;
            while (i + pRadius[i] < str.length && i - pRadius[i] >  -1) {
                if (str[i + pRadius[i]] == str[i - pRadius[i]]) {
                    pRadius[i]++;
                } else {
                    break;
                }
            }

            if (i + pRadius[i] > R) {
                R = i + pRadius[i];
                C = i;
            }
            max = Math.max(max, pRadius[i]);
        }
        return max - 1;
    }

    public static char[] manacherString(String s) {
        char[] chs = s.toCharArray();
        char[] str = new char[chs.length * 2 + 1];
        int index = 0;
        for (int i = 0; i < str.length; i++) {
            str[i] = ((i & 1) == 0) ? '#' : chs[index++];
        }
        return str;
    }
}
