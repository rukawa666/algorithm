package com.rukawa.algorithm.trainingcamp.trainingcamp1.class2;

/**
 * Created with Intellij IDEA
 *
 * @Author：SuperHai
 * @Date：2020-08-02 8:42
 * @Version：1.0
 */
public class Code01_FibonacciProblem {

    public static int f1(int n) {
        if (n < 1) {
            return 0;
        }

        if (n == 1 || n == 2) {
            return 1;
        }

        return f1(n - 1) + f1(n - 2);
    }

    public static int f2(int n) {
        if (n < 1) {
            return 0;
        }

        if (n == 1 || n == 2) {
            return 1;
        }

        int res = 1;
        int pre = 1;
        int tmp = 0;
        for (int i = 3; i <= n; i++) {
            tmp = res;
            res = res + pre;
            pre = tmp;
        }
        return res;
    }

    public static int f3(int n) {
        if (n < 1) {
            return 0;
        }

        if (n == 1 || n == 2) {
            return 1;
        }

        int[][] base = {{1,1}, {1,0}};
        int[][] res = matrixPower(base, n - 2);
        return res[0][0] + res[1][0];
    }

    public static int c1(int n) {
        if (n < 1) {
            return 0;
        }

        if (n == 1 || n == 2 || n == 3) {
            return n;
        }

        return c1(n - 1) + c1(n - 3);
    }

    public static int c2(int n) {
        if (n < 1) {
            return 0;
        }

        if (n == 1 || n == 2 || n == 3) {
            return n;
        }

        int res = 3;
        int pre = 2;
        int prePre = 1;
        int tmp1 = 0;
        int tmp2 = 0;
        for (int i = 4; i <= n; i++) {
            tmp1 = res;
            tmp2 = pre;
            res = res + prePre;
            pre = tmp1;
            prePre = tmp2;
        }
        return res;
    }

    /**
     * 第一年农场有一只成熟的母牛A，往后的每年：
     * 1、每一只成熟的母牛都会生一只母牛
     * 2、每一只新出生的母牛都会在出生的第三年成熟
     * 3、每一只母牛永远不会死
     * 返回N年后牛的数量
     * @param n
     * @return
     *
     * 第一年：1(A) 1
     * 第二年：1(A) + 11(B) 2
     * 第三年：1(A) + 11(B) + 12(C) 3
     * 第四年：1(A) + 11(B) + 12(C) + 13(D) + 111(E) 5
     * 第五年：1(A) + 11(B) + 12(C) + 13(D) + 14(D) + 111(E) + 112(F) + 121(G) 8
     *
     * 1 2 3 5 8 13 21
     * f3 = f2 + f1
     * f4 = f3 + f2
     */
    public static int c3(int n) {
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

    // 矩阵的乘法
    public static int[][] matrixPower(int[][] m, int p) {
        int[][] res = new int[m.length][m[0].length];
        // 单位矩阵
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

    // 两个矩阵相乘之后的结果返回
    public static int[][] multiMatrix(int[][] a, int[][] b) {
        int[][] res = new int[a.length][b[0].length];
        for (int i = 0; i < a.length; i++) {
            for (int j = 0; j < b[0].length; j++) {
                for (int k = 0; k < b.length; k++) {
                    res[i][j] += a[i][k] * b[k][j];
                }
            }
        }
        return res;
    }

    public static void main(String[] args) {
        int n = 19;
        System.out.println(f1(n));
        System.out.println(f2(n));
        System.out.println(f3(n));
        System.out.println("+++++++++++++++++++");

        System.out.println(c1(n));
        System.out.println(c2(n));
        System.out.println(c3(n));
        System.out.println("+++++++++++++++++++");
    }
}
