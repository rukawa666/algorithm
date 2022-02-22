package com.rukawa.algorithm.leetcode.top100likedquestions;

/**
 * create by hqh on 2022/2/16
 */
public class Problem_1137_NthFibonacciNumber {
    /**
     * 泰波那契序列 Tn 定义如下： 
     * T0 = 0, T1 = 1, T2 = 1, 且在 n >= 0 的条件下 Tn+3 = Tn + Tn+1 + Tn+2
     * 给你整数 n，请返回第 n 个泰波那契数 Tn 的值。
     *
     * 示例 1：
     * 输入：n = 4
     * 输出：4
     * 解释：
     * T_3 = 0 + 1 + 1 = 2
     * T_4 = 1 + 1 + 2 = 4
     *
     * 示例 2：
     * 输入：n = 25
     * 输出：1389537
     *
     * 提示：
     * 0 <= n <= 37
     * 答案保证是一个 32 位整数，即 answer <= 2^31 - 1。
     */
    public int tribonacci(int n) {
        if (n < 1) {
            return 0;
        }
        if (n == 1 || n == 2) {
            return 1;
        }
        int[][] base = {{1,1,1},{1,0,0},{0,1,0}};
        int[][] res = matrixPower(base, n);
        return res[0][2];
    }

    public int[][] matrixPower(int[][] m, int p) {
        int[][] res = new int[m.length][m[0].length];
        for (int i = 0; i < m.length; i++) {
            res[i][i] = 1;
        }
        int[][] tmp = m;
        for (; p != 0; p >>= 1) {
            if ((p & 1) != 0) {
                res = multiMatrix(res, tmp);
            }
            tmp = multiMatrix(tmp, tmp);
        }
        return res;
    }

    public int[][] multiMatrix(int[][] x, int[][] y) {
        int[][] res = new int[x.length][y[0].length];
        for (int i = 0; i < x.length; i++) {
            for (int j = 0; j < y[0].length; j++) {
                for (int k = 0; k < y.length; k++) {
                    res[i][j] += x[i][k] * y[k][j];
                }
            }
        }
        return res;
    }
}
