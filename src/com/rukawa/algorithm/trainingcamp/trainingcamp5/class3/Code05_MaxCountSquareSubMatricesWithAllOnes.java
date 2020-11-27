package com.rukawa.algorithm.trainingcamp.trainingcamp5.class3;

/**
 * Created with Intellij IDEA
 *
 * @Author：SuperHai
 * @Date：2020-11-11 20:00
 * @Version：1.0
 */
public class Code05_MaxCountSquareSubMatricesWithAllOnes {
    /**
     * 给你一个m*n的矩阵，矩阵中的元素不是0就是1，请你统计并返回其中完全由1组成的最大正方形子矩阵的个数。
     */
    public static int countSquares(int[][] matrix) {
        /**
         * 思路：
         *  1、建立和matrix一样大的dp，dp[i][j]做右下角的点，往左上角延伸，最大的正方形是多大
         */
        if (matrix == null || matrix[0].length == 0) {
            return 0;
        }
        int N = matrix.length;
        int M = matrix[0].length;
        int[][] dp = new int[N][M];
        int res = 0;
        for (int i = 0; i < N; i++) {
            dp[i][0] = matrix[i][0];
            res = Math.max(res, dp[i][0]);
        }
        for (int j = 1; j < M; j++) {
            dp[0][j] = matrix[0][j];
            res = Math.max(res, dp[0][j]);
        }
        // 普遍位置
        for (int i = 1; i < N; i++) {
            for (int j = 1; j < M; j++) {
                if (matrix[i][j] == 1) {
                    dp[i][j] = Math.min(Math.min(dp[i - 1][j], dp[i][j - 1]), dp[i - 1][j - 1]) + 1;
                    res = Math.max(res, dp[i][j]);
                }
            }
        }
        return res * res;
    }

    public static void main(String[] args) {
        int[][] matrix = {
                            {1,0,1,0,0},
                            {1,0,1,1,1},
                            {1,1,1,1,1},
                            {1,0,0,1,0}
                         };
        System.out.println(countSquares(matrix));
    }
}
