package com.rukawa.algorithm.leetcode.top100likedquestions;

/**
 * Created with Intellij IDEA
 *
 * @Author：SuperHai
 * @Date：2020-09-19 11:05
 * @Version：1.0
 */
public class Problem_0130_SurroundedRegions {
    /**
     * 被围绕的区域
     * 给定一个二维的矩阵，包含 'X' 和 'O'（字母 O）。
     * 找到所有被 'X' 围绕的区域，并将这些区域里所有的 'O' 用 'X' 填充。
     *
     * 示例:
     * X X X X
     * X O O X
     * X X O X
     * X O X X
     *
     * 运行你的函数后，矩阵变为：
     * X X X X
     * X X X X
     * X X X X
     * X O X X
     * 解释:
     * 被围绕的区间不会存在于边界上，换句话说，任何边界上的 'O' 都不会被填充为 'X'。 任何不在边界上，
     * 或不与边界上的 'O' 相连的 'O' 最终都会被填充为 'X'。如果两个元素在水平或垂直方向相邻，则称它们是“相连”的。
     */
    public void solve(char[][] board) {
        if (board == null || board.length == 0 || board[0] == null || board[0].length == 0) {
            return;
        }
        int n = board.length;
        int m = board[0].length;
        for (int j = 0; j < m; j++) {
            if (board[0][j] == 'O') {
                inject(board, 0 , j);
            }
            if (board[n - 1][j] == 'O') {
                inject(board, n - 1, j);
            }
        }
        for (int i = 0; i < n - 1; i++) {
            if (board[i][0] == 'O') {
                inject(board, i, 0);
            }
            if (board[i][m - 1] == 'O') {
                inject(board, i, m - 1);
            }
        }

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                // 表示'O'的区域全被'X'包围，只要有一个没包围，都不感染
                if (board[i][j] == 'O') {
                    board[i][j] = 'X';
                }
                if (board[i][j] == 'Y') {
                    board[i][j] = 'O';
                }
            }
        }

    }

    public void inject(char[][] board, int i, int j) {
        if (i < 0 || i == board.length || j < 0 || j == board[0].length || board[i][j] != 'O') {
            return;
        }
        board[i][j] = 'Y';
        inject(board, i - 1, j);
        inject(board, i + 1, j);
        inject(board, i, j - 1);
        inject(board, i, j + 1);
    }
}
