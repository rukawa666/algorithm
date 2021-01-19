package com.rukawa.algorithm.leetcode;

/**
 * Created with Intellij IDEA
 *
 * @Author：SuperHai
 * @Date：2021-01-20 0:28
 * @Version：1.0
 */
public class Problem0052_NQueensII {
    /**
     * N皇后 II
     * n 皇后问题 研究的是如何将 n 个皇后放置在 n×n 的棋盘上，并且使皇后彼此之间不能相互攻击。
     *
     * 给你一个整数 n ，返回 n 皇后问题 不同的解决方案的数量。
     */

    public int totalNQueens(int n) {
        if (n < 1 | n > 32) {
            return 0;
        }
        int limit = n == 32 ? -1 : (1 << n) - 1;
        return process(limit, 0, 0, 0);
    }

    public static int process(int limit, int colLim, int leftDiaLit, int rightDiaLit) {
       if (limit == colLim) {
           return 1;
       }
       int pos = limit & (~(colLim | leftDiaLit | rightDiaLit));
       int mosRight = 0;
       int res = 0;
       while (pos != 0) {
           mosRight = pos & (~pos + 1);
           pos = pos - mosRight;
           res += process(limit,
                   colLim | mosRight,
                   (leftDiaLit | mosRight) << 1,
                   (rightDiaLit | mosRight) >>> 1);
       }
       return res;
    }


}
