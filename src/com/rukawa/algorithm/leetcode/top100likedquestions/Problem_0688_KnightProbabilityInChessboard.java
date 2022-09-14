package com.rukawa.algorithm.leetcode.top100likedquestions;

/**
 * create by hqh on 2022/9/8
 */
public class Problem_0688_KnightProbabilityInChessboard {
    /**
     * 骑士在棋盘上的概率
     * 在一个 n x n 的国际象棋棋盘上，一个骑士从单元格 (row, column) 开始，并尝试进行 k 次移动。行和列是 从 0 开始 的，
     * 所以左上单元格是 (0,0) ，右下单元格是 (n - 1, n - 1) 。
     * 象棋骑士有8种可能的走法，如下图所示。每次移动在基本方向上是两个单元格，然后在正交方向上是一个单元格。
     *
     * 每次骑士要移动时，它都会随机从8种可能的移动中选择一种(即使棋子会离开棋盘)，然后移动到那里。
     * 骑士继续移动，直到它走了 k 步或离开了棋盘。
     * 返回 骑士在棋盘停止移动后仍留在棋盘上的概率 。
     *  
     *
     * 示例 1：
     * 输入: n = 3, k = 2, row = 0, column = 0
     * 输出: 0.0625
     * 解释: 有两步(到(1,2)，(2,1))可以让骑士留在棋盘上。
     * 在每一个位置上，也有两种移动可以让骑士留在棋盘上。
     * 骑士留在棋盘上的总概率是0.0625。
     *
     * 示例 2：
     * 输入: n = 1, k = 0, row = 0, column = 0
     * 输出: 1.00000
     *  
     *
     * 提示:
     * 1 <= n <= 25
     * 0 <= k <= 100
     * 0 <= row, column <= n
     */
    public static double knightProbability(int n, int k, int row, int column) {
        double[][][] dp = new double[n][n][k + 1];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                dp[i][j][0] = 1;
            }
        }

        for (int rest = 1; rest <= k; rest++) {
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    dp[i][j][rest] = pick(dp, rest - 1, i + 1, j + 2, n);
                    dp[i][j][rest] += pick(dp, rest - 1, i + 2, j + 1, n);
                    dp[i][j][rest] += pick(dp, rest - 1, i + 1, j - 2, n);
                    dp[i][j][rest] += pick(dp, rest - 1, i + 2, j - 1, n);
                    dp[i][j][rest] += pick(dp, rest - 1, i - 2, j - 1, n);
                    dp[i][j][rest] += pick(dp, rest - 1, i - 1, j - 2, n);
                    dp[i][j][rest] += pick(dp, rest - 1, i - 2, j + 1, n);
                    dp[i][j][rest] += pick(dp, rest - 1, i - 1, j + 2, n);
                }
            }
        }
        return dp[row][column][k] / Math.pow(8, k);
    }


    public static double pick(double[][][] dp, int rest, int row, int col, int n) {
        if (row < 0 || row >= n || col < 0 || col >= n) {
            return 0.0;
        }
        return dp[row][col][rest];
    }

    public static void main(String[] args) {
        int n = 3, k = 2, row = 0, column = 0;
        double v = knightProbability(n, k, row, column);
        System.out.println("--------");
        System.out.println(v);
    }
}
