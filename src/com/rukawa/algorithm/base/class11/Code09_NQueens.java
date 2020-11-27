package com.rukawa.algorithm.base.class11;

/**
 * Created with Intellij IDEA
 *
 * @Author：SuperHai
 * @Date：2020-07-27 9:30
 * @Version：1.0
 */
public class Code09_NQueens {

    public static int num01(int n) {
        if (n < 1) {
            return 0;
        }

        int[] record = new int[n];  // 记录i行的皇后，放在第几列
        return process01(0, record, n);
    }

    /**
     * 潜台词：record[0...i-1]的皇后，任何两个皇后一定都不共行，不共斜线
     * 目前来到了第i行
     * record[0...i-1]表示之前的行，放了皇后的位置
     * n代表整体一共有多少行 0~n-1行
     * 返回值是，摆完所有的皇后，合理的摆法有多少种
     * @param i
     * @param record
     * @param n
     * @return
     */
    public static int process01(int i, int[] record, int n) {
        if (i == n) {   // 终止行
            return 1;
        }
        // 没有到终止位置，还有皇后要摆
        int res = 0;
        for (int j = 0; j < n; j++) {   // 当前在第i行，尝试i行所有的列 -> j
            // 当前i行的皇后，放在j列，会不会和之前(0...i-1)的皇后，不共行共列或者共斜线
            if (isValid(record, i, j)) {
                record[i] = j;
                res += process01(i + 1, record, n);
            }
        }
        return res;
    }

    /**
     * record[0...i-1]需要看，是否同列，同斜线
     * 返回i行的皇后放在j列，是否有效
     * @param record
     * @param i
     * @param j
     * @return
     */
    public static boolean isValid(int[] record, int i, int j) {
        for (int k = 0; k < i; k++) {   // 之前的某个k行的皇后
            // k行 record[k]列 和 i行 j列
            if (j == record[k] || Math.abs(i - k) == Math.abs(j - record[k])) {
                return false;
            }
        }
        return true;
    }

    /**
     * 请不要超过32皇后的问题
     * @param n
     * @return
     */
    public static int num02(int n) {
        if (n < 1 || n > 32) {
            return 0;
        }
        // 如果是8皇后，limit的后8位都是1，其余都是0
        int limit = n == 32 ? -1 : (1 << n) - 1;
        return process02(limit, 0, 0, 0);

    }

    /**
     * limit 划分了问题的规模 -> 固定
     * colLim  列的限制，1的位置不能放皇后，0的位置可以
     * leftDiaLim 左斜线的限制，1的位置不能放皇后，0的位置可以
     * rightDiaLim 右斜线的限制，1的位置不能放皇后，0的位置可以
     * @param limit
     * @param colLim
     * @param leftDiaLim
     * @param rightDiaLim
     * @return
     */
    public static int process02(int limit, int colLim, int leftDiaLim, int rightDiaLim) {
        if (colLim == limit) {  // base case
            return 1;
        }
        // 所有可以放皇后的位置，都在pos上
        // colLim | leftDiaLim | rightDiaLim -> 总限制
        // ~(colLim | leftDiaLim | rightDiaLim) -> 左侧的一坨1干扰，右侧每个1，可尝试
        // limit & 可以把左侧的干扰1给除去，左侧偏移出去的1也可以去除
        int pos = limit & (~(colLim | leftDiaLim | rightDiaLim));

        int mostRightOne = 0;
        int res = 0;
        while (pos != 0) {
            // 最右侧的1
            mostRightOne = pos & (~pos + 1);

            pos = pos - mostRightOne;

            res += process02(
                    limit,
                    colLim | mostRightOne,
                    (leftDiaLim | mostRightOne) << 1,
                    (rightDiaLim | mostRightOne) >>> 1);
        }
        return res;
    }

    public static void main(String[] args) {
        int n = 15;

        long start = System.currentTimeMillis();
        System.out.println(num02(n));
        long end = System.currentTimeMillis();
        System.out.println("cost time: " + (end - start) + "ms");

        start = System.currentTimeMillis();
        System.out.println(num01(n));
        end = System.currentTimeMillis();
        System.out.println("cost time: " + (end - start) + "ms");

    }

}
