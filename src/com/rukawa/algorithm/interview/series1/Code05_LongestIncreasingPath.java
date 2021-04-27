package com.rukawa.algorithm.interview.series1;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @Author: Administrator
 * @Date: 2021/4/20 0020 20:51
 */
public class Code05_LongestIncreasingPath {
    /**
     * 给定一个二位数组matrix
     * 你可以从任何位置出发，走向上下左右四个方向
     * 返回能走出来的最长的递增链的长度
     * https://leetcode-cn.com/problems/longest-increasing-path-in-a-matrix/   329
     */

    public static int longestIncreasingPath1(int[][] matrix) {
        if (matrix == null || matrix.length == 0 || matrix[0] == null || matrix[0].length == 0) {
            return 0;
        }
        int ans = 0;
        int N = matrix.length;
        int M = matrix[0].length;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                ans = Math.max(ans, process(matrix, i, j));
            }
        }
        return ans;
    }

    // 从matrix的i行j列开始走，走出来的递增链， 返回
    public static int process(int[][] matrix, int i, int j) {
        // i > 0代表有上面的位置
        // 当前位置的值小于上面位置的值，此时为递增链，可以走向上面的位置
        int up = i > 0 && matrix[i][j] < matrix[i - 1][j] ? process(matrix, i - 1, j) : 0;

        // i < matrix.length - 1代表有下面的位置
        // 当前位置的值小于下面位置的值，此时为递增链，可以走向下面的位置
        int down = i < matrix.length - 1 && matrix[i][j] < matrix[i + 1][j] ? process(matrix, i + 1, j) : 0;

        int left = j > 0 && matrix[i][j] < matrix[i][j - 1] ? process(matrix, i, j - 1) : 0;

        int right = j < matrix[0].length - 1 && matrix[i][j] < matrix[i][j + 1] ? process(matrix, i, j + 1) : 0;

        // +1 代表当前节点
        return Math.max(Math.max(up, down), Math.max(left, right)) + 1;
    }

    public static int longestIncreasingPath2(int[][] matrix) {
        if (matrix == null || matrix.length == 0 || matrix[0] == null || matrix[0].length == 0) {
            return 0;
        }
        int ans = 0;
        int N = matrix.length;
        int M = matrix[0].length;
        int[][] dp = new int[N][M];
        // dp[i][j] = 0 代表之前没算过  dp[i][j] 不可能是0，本身就是一个长度为1的递增链
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                ans = Math.max(ans, process2(matrix, i, j, dp));
            }
        }
        return ans;
    }

    public static int process2(int[][] matrix, int i, int j, int[][] dp) {

        // 傻缓存
        if (dp[i][j] != 0) {
            // 代表之前已经算过
            return dp[i][j];
        }

        // i > 0代表有上面的位置
        // 当前位置的值小于上面位置的值，此时为递增链，可以走向上面的位置
        int up = i > 0 && matrix[i][j] < matrix[i - 1][j] ? process2(matrix, i - 1, j, dp) : 0;

        // i < matrix.length - 1代表有下面的位置
        // 当前位置的值小于下面位置的值，此时为递增链，可以走向下面的位置
        int down = i < matrix.length - 1 && matrix[i][j] < matrix[i + 1][j] ? process2(matrix, i + 1, j, dp) : 0;

        int left = j > 0 && matrix[i][j] < matrix[i][j - 1] ? process2(matrix, i, j - 1, dp) : 0;

        int right = j < matrix[0].length - 1 && matrix[i][j] < matrix[i][j + 1] ? process2(matrix, i, j + 1, dp) : 0;

        // +1 代表当前节点
        int ans = Math.max(Math.max(up, down), Math.max(left, right)) + 1;
        dp[i][j] = ans;
        return ans;
    }
}
