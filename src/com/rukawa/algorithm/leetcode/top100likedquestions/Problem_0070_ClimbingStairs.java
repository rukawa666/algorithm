package com.rukawa.algorithm.leetcode.top100likedquestions;

/**
 * Created with Intellij IDEA
 *
 * @Author：SuperHai
 * @Date：2020-09-06 9:39
 * @Version：1.0
 */
public class Problem_0070_ClimbingStairs {
    /**
     * 爬楼梯
     * 假设你正在爬楼梯。需要 n 阶你才能到达楼顶。
     * 每次你可以爬 1 或 2 个台阶。你有多少种不同的方法可以爬到楼顶呢？
     * 注意：给定 n 是一个正整数。
     *
     * 示例 1：
     * 输入： 2
     * 输出： 2
     * 解释： 有两种方法可以爬到楼顶。
     * 1.  1 阶 + 1 阶
     * 2.  2 阶
     *
     * 示例 2：
     * 输入： 3
     * 输出： 3
     * 解释： 有三种方法可以爬到楼顶。
     * 1.  1 阶 + 1 阶 + 1 阶
     * 2.  1 阶 + 2 阶
     * 3.  2 阶 + 1 阶
     */
    public int climbStairs1(int n) {
        // 斐波那锲数列的递归求解
        /**
         * 1：1  1
         * 2：1+1｜2  2
         * 3：1+1+1｜2+1｜1+2  3
         * 4：1+1+1+1｜2+2｜2+1+1｜1+2+1｜1+1+2 5
         * 5：1+1+1+1+1｜2+2+1｜2+1+2｜1+2+2｜+2+1+1+1｜1+2+1+1｜1+1+2+1｜1+1+1+2 8
         * 0 1 2 3 4 5 6
         * 0 1 2 3 5 8
         */
        if(n < 1) {
            return 0;
        }

        if (n == 1 || n == 2 || n == 3) {
            return n;
        }
        int x = 2, y = 3, r = 1;
        for (int i = 4; i <= n; i++) {
            r = x + y;
            x = y;
            y = r;
        }
        return r;
    }

    public int climbStairs(int n) {
        if(n < 1) {
            return 0;
        }

        if (n == 1 || n == 2 || n == 3) {
            return n;
        }
        int[][] base = {{1, 1}, {1, 0}};
        int[][] res = matrixPower(base, n);
        return res[0][0];
    }

    public int[][] matrixPower(int[][] matrix, int p) {
        int[][] res = new int[matrix.length][matrix[0].length];
        for (int i = 0; i < matrix.length; i++) {
            res[i][i] = 1;
        }
        int[][] tmp = matrix;
        while (p != 0) {
            if ((p & 1) != 0) {
                res = multiMatrix(res, tmp);
            }
            tmp = multiMatrix(tmp, tmp);
            p >>= 1;
        }
        return res;
    }

    public int[][] multiMatrix(int[][] x, int[][] y) {
        int[][] res = new int[x.length][y.length];
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
