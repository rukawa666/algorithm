package com.rukawa.algorithm.base.class18;

/**
 * create by hqh on 2022/9/16
 */
public class Code_Module {
    /**
     * 一个数的N次方怎么最快
     *
     * 求10的75次方。75的二进制是64+8+2+1     1001011
     * 10^75 = 10^1 + 10^2 + 10^8 + 10^64
     */

    // O(logN)
    public double power(double x, int n) {
        /**
         * 如果n是负数情况：先转成正数，然后 1/正数
         * 但是系统最小值不能转正数
         * 系统最小值的解决办法：先求x的系统最大次方，然后结果乘x
         */
        if (n == 0) {
            return 1D;
        }
        double t = x;
        double res = 1D;
        // 系统最小值：-2147483648 系统最大值：2147483647
        // 如果是系统最小值，先转成系统最大值
        int pow = Math.abs(n == Integer.MIN_VALUE ? n + 1 : n);
        for (; pow != 0; pow >>= 1) {
            // 判断二进制末尾是否是1，有1需要把x乘到结果中
            if ((pow & 1) != 0) {
                res *= t;
            }
            // 此时t变成原来t的平方
            t *= t;
        }

        if (n == Integer.MIN_VALUE) {
            res *= x;
        }
        return n < 0 ? (1D / res) : res;
    }

    /**
     * 一个矩阵的N次方怎么最快
     *
     * 单位矩阵：对角线全是1，其他全是0，代表矩阵中1的概念
     */
    public int[][] matrixPower(int[][] matrix, int n) {
        // 2*2矩阵的n-2次方也是2*2的矩阵
        int[][] res = new int[matrix.length][matrix[0].length];
        // 单位矩阵的形成
        for (int i = 0; i < res.length; i++) {
            res[i][i] = 1;
        }
        // 矩阵的1次方
        int[][] t = matrix;
        for (; n != 0; n >>= 1) {
            // 判断二进制末尾是否是1，把t乘到矩阵中
            if ((n & 1) != 0) {
                res = multiMatrix(res, t);
            }
            // 此时t变成原来t的平方
            t = multiMatrix(t, t);
        }
        return res;
    }

    // 两个矩阵相乘
    public int[][] multiMatrix(int[][] m1, int[][] m2) {
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

    public static void main(String[] args) {
        System.out.println(Integer.MIN_VALUE);
        System.out.println(Integer.MAX_VALUE);
        System.out.println();
    }
}
