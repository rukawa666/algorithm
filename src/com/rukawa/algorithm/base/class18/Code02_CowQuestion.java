package com.rukawa.algorithm.base.class18;

/**
 * create by hqh on 2022/9/16
 */
public class Code02_CowQuestion {
    /**
     * 第一年农场有1只成熟的母牛A，往后的每年：
     * 1、每一只成熟的母牛都会生一直母牛
     * 2、每一只新出生的母牛都在出生的第三年成熟
     * 3、每一只母牛永远不会死
     * 返回N年后牛的数量
     */
    public static int cowCount1(int n) {
        if (n < 1) {
            return 0;
        }
        if (n == 1 || n == 2 || n == 3) {
            return n;
        }
        return cowCount1(n - 1) + cowCount1(n - 3);
    }

    public static int cowCount2(int n) {
        /**
         * 第一年：1
         * 第二年：2
         * 第三年：3
         * 第四年：4
         * 第五年：6
         * 第六年：9
         * 。。。
         * f(n) = f(n-1) + f(n-3) => 今年的牛有多少，去年的牛+三年前生的牛
         * ｜f(4),f(3),f(2)｜ = ｜f(3),f(2),f(1)｜ * ｜3*3｜的矩阵
         * ｜f(5),f(4),f(3)｜ = ｜f(4),f(3),f(2)｜ * ｜3*3｜的矩阵
         * ｜4,3,2｜ = |3,2,1| * |3*3|的矩阵
         * ｜6,4,3｜ = |4,3,2| * |3*3|的矩阵
         * ｜9,6,4｜ = |6,4,3| * |3*3|的矩阵
         *
         * 3a+2d+g=4   3b+2e+h=3   3c+2f+i=2
         * 4a+3d+2g=6  4b+3e+2h=4  4c+3f+2i=3
         * 6a+4d+3g=9  6b+4e+3h=6  6c+4f+3i=4
         * 得出三阶矩阵是{
         *               {1, 1, 0},
         *               {0, 0, 1},
         *               {1, 0, 0}
         *             }
         */
        if (n < 1) {
            return 0;
        }
        if (n == 1 || n == 2 || n == 3) {
            return n;
        }
        int[][] base = {{1,1,0},{0,0,1},{1,0,0}};
        int[][] res = matrixPower(base, n - 3);
        return 3 * res[0][0] + 2 * res[1][0] + res[2][0];
    }

    public static int[][] matrixPower(int[][] m, int n) {
        int[][] res = new int[m.length][m[0].length];
        for (int i = 0; i < res.length; i++) {
            res[i][i] = 1;
        }
        int[][] t = m;
        while (n != 0) {
            if ((n & 1) != 0) {
                res = multiMatrix(res, t);
            }
            t = multiMatrix(t, t);
            n >>= 1;
        }
        return res;
    }

    public static int[][] multiMatrix(int[][] m1, int[][] m2) {
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
        System.out.println(cowCount1(6));
        System.out.println(cowCount2(6));
    }

}
