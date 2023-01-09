package com.rukawa.algorithm.base.class18_fibonacc;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @Author: Administrator
 * @Date: 2021/5/26 0026 8:27
 */
public class Code03_ZeroLeftOneStringNumber {

    /**
     * 给定一个数N，想象只有0和1两种字符，组成的所有长度为N的字符串
     * 如果某个字符串，任何0字符的左边都有1紧挨着，认为这个字符串达标
     * 返回有多少达标的字符串
     */
    /**
     * 观察法：
     * N=1 有1达标
     * N=2 有10 11达标
     * N=3 有100 101 110 111
     * 所以整体下来是1 2 3 5 8 13 。。。
     */

    public static int getNum1(int n) {
        if (n < 1) {
            return 0;
        }
        if (n < 3) {
            return n;
        }
        int[][] base = {{1, 1}, {1,0}};
        int[][] res = matrixPower(base, n - 2);
        return 2 * res[0][0] + res[1][0];
    }

    public static int[][] matrixPower(int[][] base, int p) {
        int[][] res = new int[base.length][base[0].length];
        for (int i = 0; i < res.length; i++) {
            res[i][i] = 1;
        }
        int[][] tmp = base;
        for (; p != 0; p >>= 1) {
            if ((p & 1) == 1) {
                res = multiMatrix(res, tmp);
            }
            tmp = multiMatrix(tmp, tmp);
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

    /**
     * 尝试：f(i)后面还有i长度的位置需要尝试，可以填写0或者1，潜台词是i-1是1的情况下有多少个达标
     * 第一种选择：i位置填写1，此时满足f(i+1)的潜台词
     * 第二种选择：i位置填写0，此时i+1必须填写1，此时满足f(i+2)的潜台词
     */
    public static int getNum2(int n) {
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
}
