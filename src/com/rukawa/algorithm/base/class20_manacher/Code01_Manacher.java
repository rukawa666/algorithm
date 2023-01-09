package com.rukawa.algorithm.base.class20_manacher;

/**
 * create by hqh on 2022/9/17
 */
public class Code01_Manacher {

    public static int manacher(String s) {
        if (s == null || s.length() == 0) {
            return 0;
        }
        char[] str = manacherString(s);
        // 回文半径数组
        int[] pArr = new int[str.length];
        // 最优回文半径的中心
        int C = -1;
        // 最右回文边界扩成功的位置，代码中表示扩失败的位置
        int R = -1;
        int max = Integer.MIN_VALUE;
        for (int i = 0; i < str.length; i++) {
            // 假设C的半径是5，i的位置是7，i'位置在3
            // i在R内，i'在[L,R]内部，回文半径是i'的回文半径，i'扩出L的区域，则i最多只能扩到R位置
            // i在R外，至少的回文半径是1，包含自己
            // 此处是不需要扩的情况
            pArr[i] = R > i ? Math.min(pArr[2 * C - i], R - i) : 1;

            // i'和L压线和i在R外，此时需要扩
            while (i + pArr[i] < str.length && i - pArr[i] >= 0) {
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
            max = Math.max(max, pArr[i]);
        }
        return max - 1;
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
        String s = "123432";
        System.out.println(manacher(s));
    }
}
