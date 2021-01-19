package com.rukawa.algorithm.dp.series6;

/**
 * Created with Intellij IDEA
 *
 * @Author：SuperHai
 * @Date：2021-01-19 21:46
 * @Version：1.0
 */
public class Code03_NQueens {
    /**
     * N皇后问题是指在N*N的棋盘上要摆N个皇后，并且使每个皇后彼此之间不能相互攻击
     * 给你一个整数n，返回n皇后问题的不同解决方案的数量
     */
    public int totalNQueens1(int n) {
        if (n < 1) {
            return 0;
        }
        int[] record = new int[n];
        return process1(0, record, n);
    }

    // 当前来到i行，一共是0~N-1行
    // 在i行放皇后，所有列都尝试
    // 必须保证跟之前的所有皇后不打架
    // int[] record record[x] = y 之前的第x行的皇后，放在了y列上
    // 返回：不关心i以上发生了什么，i...后续有多少合法的方法数
    public static int process1(int i, int[] record, int n) {
        // 所有皇后填完了
        if (i == n) {
            return 1;
        }
        int res = 0;
        // i行的皇后，放哪一列? j列
        for (int j = 0; j < n; j++) {
            if (isValid(record, i, j)) {
                record[i] = j;
                res += process1(i + 1, record, n);
            }
        }
        return res;
    }

    public static boolean isValid(int[] record, int i, int j) {
        // 检查0...i-1的皇后
        for (int k = 0; k < i; k++) {
            if (j == record[k] || Math.abs(record[k] - j) == Math.abs(i - k)) {
                return false;
            }
        }
        return true;
    }

    // 不要超过32皇后的问题
    public int totalNQueens2(int n) {
        if (n < 1 || n > 32) {
            return 0;
        }
        // 如果你是13皇后问题，limit 最右13个1，其他都是0
        int limit = n == 32 ? -1 : (1 << n) - 1;
        return process2(limit, 0, 0, 0);
    }

    // 7 皇后问题
    // limit 0...0 1 1 1 1 1 1 1
    // 之前皇后的列影响：colLit
    // 之前皇后的左下影响：leftDiaLim
    // 之前皇后的右下影响：rightDiaLim
    public static int process2(int limit, int colLim, int leftDiaLim, int rightDiaLim) {
        // 皇后被填满了
        if (limit == colLim) {
            return 1;
        }

        /**
         * colLim:      0...0 0 0 1 0 0 0
         * leftDiaLim:  0...0 0 1 0 0 0 0
         * rightDiaLim: 0...0 0 0 0 1 0 0
         * (colLim | leftDiaLim | rightDiaLim) ：0...0 0 1 1 1 0 0
         * ~(colLim | leftDiaLim | rightDiaLim) ： 1...1 1 0 0 0 1 1
         * limit & (~(colLim | leftDiaLim | rightDiaLim)) ： 1...1 1 0 0 0 1 1  此时是可以放皇后的位置
         */
        // pos中所有是1的位置，是可以尝试去放皇后的位置
        int pos = limit & (~(colLim | leftDiaLim | rightDiaLim));
        int mosRight = 0;
        int res = 0;
        // 尝试所有可能的1
        while (pos != 0) {
            // 提取最右侧的1
            mosRight = pos & (~pos + 1);
            // 最右侧的1已经尝试过了，减掉
            pos = pos - mosRight;
            res += process2(limit,
                    colLim | mosRight,
                    (leftDiaLim | mosRight) << 1,
                    (rightDiaLim | mosRight) >>> 1);
        }
        return res;
    }
}
