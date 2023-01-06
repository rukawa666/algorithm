package com.rukawa.algorithm.leetcode.top100likedquestions;

/**
 * Created with Intellij IDEA
 *
 * @Author：SuperHai
 * @Date：2020-09-21 8:42
 * @Version：1.0
 */
public class Problem_0079_WordSearch {
    /**
     * 单词搜索
     * 给定一个 m x n 二维字符网格 board 和一个字符串单词 word 。如果 word 存在于网格中，返回 true ；否则，返回 false 。
     * 单词必须按照字母顺序，通过相邻的单元格内的字母构成，其中“相邻”单元格是那些水平相邻或垂直相邻的单元格。同一个单元格内的字母不允许被重复使用。
     *
     * 提示：
     * m == board.length
     * n = board[i].length
     * 1 <= m, n <= 6
     * 1 <= word.length <= 15
     * board 和 word 仅由大小写英文字母组成
     *  
     * 进阶：你可以使用搜索剪枝的技术来优化解决方案，使其在 board 更大的情况下可以更快解决问题？
     */
    public boolean exist(char[][] board, String word) {
        char[] match = word.toCharArray();
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                if (process(board, i, j, match, 0)) {
                    return true;
                }
            }
        }
        return false;
    }

    // 已经达到board[i][j]  match[k]位置
    // 从board[i][j]出发，能不能搞定match[k...]的所有字符
    public boolean process(char[][] board, int i, int j, char[] match, int k) {
        // k来到终止位置，说明前面所有位置都搞定了
        if (k == match.length) {
            return true;
        }

        // 范围越界
        if (i < 0 || i == board.length || j < 0 || j == board[0].length) {
            return false;
        }

        if (board[i][j] != match[k]) {
            return false;
        }

        char tmp  = board[i][j];
        // 把能搞定的位置全部改为0，防止走回头路
        board[i][j] = 0;
        // 上下左右位置去尝试
        boolean res = process(board, i - 1, j, match, k + 1)
                        || process(board, i + 1, j, match, k + 1)
                        || process(board, i,j -1, match, k + 1)
                        || process(board, i , j + 1, match, k + 1);
        // 恢复现场
        board[i][j] = tmp;
        return res;
    }
}
