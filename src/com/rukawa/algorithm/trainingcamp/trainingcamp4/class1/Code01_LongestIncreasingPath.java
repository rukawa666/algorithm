package com.rukawa.algorithm.trainingcamp.trainingcamp4.class1;

/**
 * Created with Intellij IDEA
 *
 * @Author：SuperHai
 * @Date：2020-10-10 7:28
 * @Version：1.0
 */
public class Code01_LongestIncreasingPath {
    /**
     * 给定一个二维数组matrix，可以从任意位置出发，每一步可以走向上、下、左、右
     * 四个方向。返回最大递增链的长度。
     * 例如：
     *  matrix = {
     *     {5, 4, 3}
     *     {3, 1, 2}
     *     {2, 1, 3}
     *  }
     * 从最中心的1出发，是可以走出1 2 3 4 5的链的，而且这是最长的递增链。所以返回长度5
     */

    // 傻缓存
    public static int maxPath2(int[][] matrix) {
        int res = Integer.MIN_VALUE;

        int[][] dp = new int[matrix.length][matrix[0].length];

        for (int row = 0; row < matrix.length; row++) {
            for (int col = 0; col < matrix[0].length; col++) {
                res = Math.max(res, process(matrix, row, col, dp));
            }
        }
        return res;
    }

    // 假设在matrix中，从i行，j列出发，能走出的最长的递增路径，返回
    public static int process(int[][] matrix, int i, int j, int[][] dp) {
        if (i < 0 || i >= matrix.length || j < 0 || j > matrix[0].length) {
            return -1;
        }
        // dp[i][j] == 0 process(i, j) 之前没遇到过
        // dp[i][j] != 0 process(i, j) 之前已经算过了
        if (dp[i][j] != 0) {
            return dp[i][j];
        }

        int up = 0;
        int down = 0;
        int left = 0;
        int right = 0;
        if (i - 1 >= 0 && matrix[i - 1][j] > matrix[i][j]) {
            up += process(matrix, i - 1, j, dp);
        }
        if (i + 1 < matrix.length && matrix[i + 1][j] > matrix[i][j]) {
            down += process(matrix, i + 1, j, dp);
        }
        if (j - 1 >= 0 && matrix[i][j - 1] > matrix[i][j]) {
            left += process(matrix, i, j - 1, dp);
        }
        if (j + 1 < matrix[0].length && matrix[i][j + 1] > matrix[i][j]) {
            right += process(matrix, i, j + 1, dp);
        }
        int res = 1 + Math.max(Math.max(up, down), Math.max(left, right));
        dp[i][j] = res;
        return res;
    }

    // 暴力求解
    public static int maxPath1(int[][] matrix) {
        int res = Integer.MIN_VALUE;
        for (int row = 0; row < matrix.length; row++) {
            for (int col = 0; col < matrix[0].length; col++) {
                res = Math.max(res, process(matrix, row, col));
            }
        }
        return res;
    }

    public static int process(int[][] matrix, int i, int j) {
        if (i < 0 || i >= matrix.length || j < 0 || j > matrix[0].length) {
            return -1;
        }
        int up = 0;
        int down = 0;
        int left = 0;
        int right = 0;
        if (i - 1 >= 0 && matrix[i - 1][j] > matrix[i][j]) {
            up += process(matrix, i - 1, j);
        }
        if (i + 1 < matrix.length && matrix[i + 1][j] > matrix[i][j]) {
            down += process(matrix, i + 1, j);
        }
        if (j - 1 >= 0 && matrix[i][j - 1] > matrix[i][j]) {
            left += process(matrix, i, j - 1);
        }
        if (j + 1 < matrix[0].length && matrix[i][j + 1] > matrix[i][j]) {
            right += process(matrix, i, j + 1);
        }
        return 1 + Math.max(Math.max(up, down), Math.max(left, right));
    }
}
