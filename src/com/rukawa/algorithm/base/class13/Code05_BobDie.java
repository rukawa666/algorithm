package com.rukawa.algorithm.base.class13;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @Author: Administrator
 * @Date: 2021/4/13 0013 22:12
 */
public class Code05_BobDie {
    /**
     * 给定5个参数，N、M、row、col、k
     * 表示在N * M的区域上，醉汉Bob初始在(row,col)位置
     * Bob一共要迈出k步，且每步都会等概率向上下左右四个方向走一个单位
     * 任何时候Bob只要离开N * M的区域，就会直接死亡
     * 返回k步之后，Bob还在N * M的区域的概率
     */


    public static double livePossibility1(int row, int col, int k, int N, int M) {
        // 假设区域无限大，可以走上下左右，最终的可能性有是k^4
        // 生存的次数/所有可能性就是生存概率
        return (double) process(row, col, k, N, M) / Math.pow(4, k);
    }

    // 目前在row,col位置，还有rest步要走，走完了如果还在棋盘中获得一个生存点，返回总的生存点数
    public static long process(int row, int col, int rest, int N, int M) {
        if (row < 0 || row == N || col < 0 || col == M) {
            return 0;
        }
        if (rest == 0) {
            return 1;
        }
        long up = process(row - 1, col, rest - 1, N, M);
        long down = process(row + 1, col, rest - 1, N, M);
        long left = process(row, col - 1, rest - 1, N, M);
        long right = process(row, col + 1, rest - 1, N, M);
        return up + down + left + right;
    }

    public static double livePossibility2(int row, int col, int k, int N, int M) {
        long[][][] dp = new long[N][M][k + 1];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                dp[i][j][0] = 1;
            }
        }

        for (int rest = 1; rest <= k; rest++) {
            for (int r = 0; r < N; r++) {
                for (int c = 0; c < M; c++) {
                    dp[r][c][rest] = pick(dp, N, M, r - 1, c, rest - 1);
                    dp[r][c][rest] += pick(dp, N, M, r + 1, c, rest - 1);
                    dp[r][c][rest] += pick(dp, N, M, r, c - 1, rest - 1);
                    dp[r][c][rest] += pick(dp, N, M, r, c + 1, rest - 1);
                }
            }
        }

        return (double) dp[row][col][k] / Math.pow(4, k);
    }

    public static long pick(long[][][] dp, int N, int M, int row, int col, int rest) {
        if (row < 0 || row == N || col < 0 || col == M) {
            return 0;
        }
        return dp[row][col][rest];
    }

    public static void main(String[] args) {
        System.out.println(livePossibility1(6, 6, 10, 50, 50));
        System.out.println(livePossibility2(6, 6, 10, 50, 50));
    }
}
