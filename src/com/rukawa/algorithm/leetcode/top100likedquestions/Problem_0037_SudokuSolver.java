package com.rukawa.algorithm.leetcode.top100likedquestions;

/**
 * Created with Intellij IDEA
 *
 * @Author：SuperHai
 * @Date：2020-09-20 8:33
 * @Version：1.0
 */
public class Problem_0037_SudokuSolver {
    /**
     * 解数独
     * 编写一个程序，通过填充空格来解决数独问题。
     * 数独的解法需 遵循如下规则：
     * 数字 1-9 在每一行只能出现一次。
     * 数字 1-9 在每一列只能出现一次。
     * 数字 1-9 在每一个以粗实线分隔的 3x3 宫内只能出现一次。（请参考示例图）
     * 数独部分空格内已填入了数字，空白格用 '.' 表示。
     *  
     *
     * 提示：
     * board.length == 9
     * board[i].length == 9
     * board[i][j] 是一位数字或者 '.'
     * 题目数据 保证 输入数独仅有一个解
     */
    public void solveSudoku(char[][] board) {
        boolean[][] row = new boolean[9][10];
        boolean[][] col = new boolean[9][10];
        boolean[][] bucket = new boolean[9][10];
        fill(board, row, col, bucket);
        process(board, 0, 0, row, col, bucket);
    }

    public void fill(char[][] board, boolean[][] row, boolean[][] col, boolean[][] bucket) {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                int bid = 3 * (i / 3) + (j / 3);
                if (board[i][j] != '.') {
                    int num = board[i][j] - '0';
                    row[i][num] = true;
                    col[j][num] = true;
                    bucket[bid][num] = true;
                }
            }
        }
    }
    // 当前来到(i,j)位置
    // 如果已经有数字，跳到下一个位置
    // 如果没有数字，则需要填写，尝试1～9，不能和row、col、bucket冲突
    public boolean process(char[][] board, int i, int j, boolean[][] row, boolean[][] col, boolean[][] bucket) {
        // 当前来到第9行，说明之前的数独填写正确
        if (i == 9) {
            return true;
        }
        // 当离开i，j位置，下一个位置去哪
        // 从上到下，从左到右填写，如果来到最后一列，跳到下一行
        int nextI = j != 8 ? i : i + 1;
        // 如果当前列没有填完，跳下一列，如果已经来到最后一列，则从0列开始填写
        int nextJ = j != 8 ? j + 1 : 0;
        // 当前格子已经填写
        if (board[i][j] != '.') {
            return process(board, nextI, nextJ, row, col, bucket);
        } else {
            // 当前格子需要尝试填写
            // 当前桶编号
            int bid = 3 * (i / 3) + (j / 3);
            // 去尝试每个数字
            for (int num = 1; num <= 9; num++) {
                // 当前数字没有冲突
                if ((!row[i][num]) && (!col[j][num]) && (!bucket[bid][num])) {
                    row[i][num] = true;
                    col[j][num] = true;
                    bucket[bid][num] = true;
                    board[i][j] = (char) (num + '0');
                    if (process(board, nextI, nextJ, row, col, bucket)) {
                        return true;
                    }
                    row[i][num] = false;
                    col[j][num] = false;
                    bucket[bid][num] = false;
                    board[i][j] = '.';
                }
            }
        }
        return false;
    }
}
