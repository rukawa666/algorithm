package com.rukawa.algorithm.trainingcamp.trainingcamp1.class2;

/**
 * Created with Intellij IDEA
 *
 * @Author：SuperHai
 * @Date：2020-09-09 23:06
 * @Version：1.0
 */
public class Code02_ZeroLeftOneStringNumber {
    /**
     * 给定一个数N，想象只有0和1两种字符，组成的所有长度为N的字符串
     * 如果某个字符串，任何0字符的左边都有1紧挨着，认为这个字符串达标
     * 返回有多少达标的字符串
     */

    public static int getNum1(int n) {
        if (n < 1) {
            return 0;
        }

        return process(1, n);
    }

    public static int process(int i, int n) {
        if (i == n - 1) {
            return 2;
        }

        if (i == n) {
            return 1;
        }

        return process(i + 1, n) + process(i + 2, n);
    }

    public static int getNum2(int n) {
        if (n < 1) {
            return 0;
        }

        if (n == 1) {
            return 1;
        }

        int pre = 1;
        int cur = 1;
        int tmp = 0;
        for (int i = 2; i < n + 1; i++) {
            tmp = cur;
            cur += pre;
            pre = tmp;
        }
        return cur;
    }

    public static int getNum3(int n) {
        if (n < 1) {
            return 0;
        }

        if (n == 1 || n == 2) {
            return n;
        }

        int[][] base = {{1,1},{1,0}};
        int[][] res = matrixPower(base, n - 2);
        return res[0][0] + res[1][0];
    }


    public static int[][] matrixPower(int[][] m, int p) {
        int[][] res = new int[m.length][m[0].length];
        for (int i = 0; i < res.length; i++) {
            res[i][i] = 1;
        }
        int[][] tmp = m;
        for (; p != 0; p >>= 1) {
            if ((p & 1) != 0) {
                res = muliMatrix(res, tmp);
            }
            tmp = muliMatrix(tmp, tmp);
        }
        return res;
    }

    public static int[][] muliMatrix(int[][] m1, int[][] m2) {
        int[][] res = new int[m1.length][m2[0].length];
        for (int i = 0; i < m1.length; i++) {
            for (int j = 0; j < m2[0].length; j++) {
                for (int k = 0; k < m2.length; k++) {
                    res[i][j] += m1[i][k] * m2[k][j];
                }
            }
        }
        return res;
    }

}
