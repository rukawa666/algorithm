package com.rukawa.algorithm.trainingcamp.trainingcamp1.class5;

/**
 * Created with Intellij IDEA
 *
 * @Author：SuperHai
 * @Date：2020-08-04 7:27
 * @Version：1.0
 */
public class Code01_Manacher {


    /**
     * s：121aaaa232aa
     * ms：#1#2#1#a#a#a#a#2#3#2#a#a
     * abc12321def
     * 回文半径：3(123)
     * 回文直径：5(12321)
     * 回文半径数组：处理串从0位置，求解每个位置的回文半径
     * 最右回文右边界(R)：前面扩到的最右边界
     * 最右回文右边界的中心(R):前面扩到的最右边界的中心
     *
     *
     * 四种情况：
     *    1、如果此时i在R外面，则直接暴力扩
     *    2、如果此时i在R里面，此时分为三种情况
     *       a、i对应的位置i'，对应的回文串，如果在[L...R]内，说明i是不需要扩
     *       b、i对应的位置i'，对应的回文串，如果在[L...R]外，此时i不需要扩
     *       c、i对应的位置i'，对应的回文串，如果在[L...R]压线位置，此时是需要判断是否需要扩
     * @param s
     * @return
     */
    public static int manacher(String s) {
        if (s == null || s.length() == 0) {
            return 0;
        }
        char[] str = manacherString(s);
        // 回文半径
        int[] pArr = new int[str.length];
        int C = -1;
        // 最右边扩成功的位置，再下一个位置(失败的位置)
        // 还没开始扩，-1
        int R = -1;
        int max = Integer.MIN_VALUE;
        for (int i = 0; i < str.length; i++) {

            /**
             * 2 * C + 1：回文串的长度减去i的位置，得到的是对应的i'的位置。
             * 例子，C如果在7的位置，整个回文长度为14，此时i的位置在11，则对应的i'位置一定在3
             *
             * 如果是情况1，i自己不用验，至少有1种不用验
             * 如果是情况2a，至少不用验证的区域是，i'的半径对应的i的区域
             * 如果是情况2b，此时i~R位置是不需要验证
             * 如果是情况2c，此时i~R位置是不需要验证
             * R - i：为当前的回文半径
             * pArr[2 * C - i] = i'，两者谁小取哪个
             */
            pArr[i] = R > i ? Math.min(pArr[2 * C - i], R - i) : 1;
            // 如果i + pArr[i]在字符串区域内&& i-pArr[i]的区域不越界
            // 去向两边扩
            while (i + pArr[i] < str.length && i - pArr[i] > - 1) {
                // 如果i左右是回文，则直接扩
                if (str[i + pArr[i]] == str[i - pArr[i]]) {
                    pArr[i]++;
                } else {
                    break;
                }
            }
            // i + pArr[i]就是此时i位置对应的右边界
            if (i + pArr[i] > R) {
                R = i + pArr[i];
                C = i;
            }

            max = Math.max(max, pArr[i]);
        }
        return max - 1;
    }

    public static char[] manacherString(String str) {
        char[] strArr = str.toCharArray();
        char[] res = new char[strArr.length * 2 + 1];
        int index = 0;
        for (int i = 0; i < res.length; i++) {
            res[i] = (i & 1) == 0 ? '#' : strArr[index++];
        }
        return res;
    }

    public static int right(String s) {
        if (s == null || s.length() == 0) {
            return 0;
        }
        char[] str = manacherString(s);
        int max = 0;
        for (int i = 0; i < str.length; i++) {
            int L = i - 1;
            int R = i + 1;
            while (L >= 0 && R < str.length && str[L] == str[R]) {
                L--;
                R++;
            }
            max = Math.max(max, R - L - 1);
        }
        return max / 2;
    }

    // for test
    public static String getRandomString(int possibilities, int size) {
        char[] ans = new char[(int) (Math.random() * size) + 1];
        for (int i = 0; i < ans.length; i++) {
            ans[i] = (char) ((int) (Math.random() * possibilities) + 'a');
        }
        return String.valueOf(ans);
    }

    public static void main(String[] args) {
        int possibilities = 5;
        int strSize = 20;
        int testTimes = 5000000;
        System.out.println("test begin");
        for (int i = 0; i < testTimes; i++) {
            String str = getRandomString(possibilities, strSize);
            if (manacher(str) != right(str)) {
                System.out.println("Oops!");
            }
        }
        System.out.println("test finish");
    }
}
