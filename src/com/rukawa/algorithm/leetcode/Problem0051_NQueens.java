package com.rukawa.algorithm.leetcode;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with Intellij IDEA
 *
 * @Author：SuperHai
 * @Date：2021-01-20 0:36
 * @Version：1.0
 */
public class Problem0051_NQueens {
    /**
     * N 皇后
     * n 皇后问题 研究的是如何将 n 个皇后放置在 n×n 的棋盘上，并且使皇后彼此之间不能相互攻击。
     * 给你一个整数 n ，返回所有不同的 n 皇后问题 的解决方案。
     * 每一种解法包含一个不同的 n 皇后问题 的棋子放置方案，该方案中 'Q' 和 '.' 分别代表了皇后和空位。
     *
     * 示例 1：
     * 输入：n = 4
     * 输出：[[".Q..","...Q","Q...","..Q."],["..Q.","Q...","...Q",".Q.."]]
     * 解释：如上图所示，4 皇后问题存在两个不同的解法。
     *
     * 示例 2：
     * 输入：n = 1
     * 输出：[["Q"]]
     *  
     *
     * 提示：
     * 1 <= n <= 9
     * 皇后彼此不能相互攻击，也就是说：任何两个皇后都不能处于同一条横行、纵行或斜线上。、
     */
    public List<List<String>> solveNQueens(int n) {
        if (n < 1 || n > 32) {
            return null;
        }
        int limit =  n == 32 ? -1 : (1 << n) - 1;
        List<List<String>> res = new ArrayList<>();
        int[] record = new int[n];
        for (int i = 0; i < n; i++) {
            record[i] = -1;
        }
        process(res, record, n, 0, limit, 0, 0, 0);
        return res;
    }

    public static void process(List<List<String>> res, int[] record, int n, int row, int limit, int colLim, int leftDiaLim, int rightDiaLim) {
        if (row == n) {
            List<String> ans = generateBoard(record, n);
            res.add(ans);
        }
        int pos = limit & (~(colLim | leftDiaLim | rightDiaLim));
        int mosRight = 0;
        while (pos != 0) {
            // 最低位的1
            mosRight = pos & (~pos + 1);
            // 最低位的1置为0
            pos = pos - mosRight;
            // mosRight - 1的补码，有疑惑
            int column = Integer.bitCount(mosRight - 1);
            record[row] = column;
            process(res, record, n, row + 1, limit, colLim | mosRight, (leftDiaLim | mosRight) << 1, (rightDiaLim | mosRight) >> 1);
            // 恢复现场
            record[row] = -1;
        }
    }

    public static List<String> generateBoard(int[] record, int n) {
        List<String> res = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            char[] position = new char[n];
            for (int j = 0; j < n; j++) {
                position[j] = '.';
            }
            position[record[i]] = 'Q';
            res.add(new String(position));
        }
        return res;
    }
}
