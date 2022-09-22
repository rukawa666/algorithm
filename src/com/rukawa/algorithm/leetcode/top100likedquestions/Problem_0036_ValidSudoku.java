package com.rukawa.algorithm.leetcode.top100likedquestions;

import java.sql.SQLOutput;

/**
 * Created with Intellij IDEA
 *
 * @Author：SuperHai
 * @Date：2020-09-20 8:33
 * @Version：1.0
 */
public class Problem_0036_ValidSudoku {
    /**
     * 有效的数独
     * 请你判断一个 9 x 9 的数独是否有效。只需要 根据以下规则 ，验证已经填入的数字是否有效即可。
     * 数字 1-9 在每一行只能出现一次。
     * 数字 1-9 在每一列只能出现一次。
     * 数字 1-9 在每一个以粗实线分隔的 3x3 宫内只能出现一次。（请参考示例图）
     *
     * 注意：
     * 一个有效的数独（部分已被填充）不一定是可解的。
     * 只需要根据以上规则，验证已经填入的数字是否有效即可。
     * 空白格用 '.' 表示。
     *
     * 提示：
     * board.length == 9
     * board[i].length == 9
     * board[i][j] 是一位数字（1-9）或者 '.'
     */
    public boolean isValidSudoku(char[][] board) {
        // 1~9的数字
        // row[i][j] 代表i行有没有j这个数字出现过
        boolean[][] row = new boolean[9][10];
        // col[i][j] 代表i列有没有j这个数字出现过
        boolean[][] col = new boolean[9][10];
        // bucket[i][j] 代表3*3的桶，i号桶有没有出现过j这个数字
        boolean[][] bucket = new boolean[9][10];
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                // 桶的编号
                int bid = 3 * (i / 3) + (j / 3);
                if (board[i][j] != '.') {
                    int num = board[i][j] - '0';
                    if (row[i][num] || col[j][num] || bucket[bid][num]) {
                        return false;
                    }
                    row[i][num] = true;
                    col[j][num] = true;
                    bucket[bid][num] = true;
                }
            }
        }
        return true;
    }
}
