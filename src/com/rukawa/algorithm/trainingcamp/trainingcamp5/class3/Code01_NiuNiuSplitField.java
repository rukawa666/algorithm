package com.rukawa.algorithm.trainingcamp.trainingcamp5.class3;

import java.util.Scanner;

/**
 * Created with Intellij IDEA
 *
 * @Author：SuperHai
 * @Date：2020-11-09 7:55
 * @Version：1.0
 */
public class Code01_NiuNiuSplitField {
    /**
     * 牛牛和15个朋友来玩打土豪分田地的游戏，牛牛决定让你来分田地，地主的田地可以看成是一个矩形，每个位置有一个价值。分割田地的方法是横竖各切三刀，
     * 分成16份，作为领导干部，牛牛总是会选择其中总价值最小的一份田地，作为牛牛最好的朋友，你希望牛牛取得的田地的价值和尽可能大，你知道这个值最大
     * 可以是多少吗?
     * 输入描述:
     * 每个输入包含1个测试用例。每个测试用例的第一行包含两个整数n和m(1 <= n, m <= 75)，表示田地的大小，接下来的n行，每行包含m个0-9之间的数字，
     * 表示每块位置的价值。
     * 输出描述: 输出一行表示牛牛所能取得的最大的价值。
     * 输入例子: 4 4
     * 3 3 3 2
     * 3 2 3 3
     * 3 3 3 2
     * 2 3 2 3
     * 输出例子: 2
     */

    /**
     * 思路：
     *  1、矩阵的累加和矩阵 -> help[i][j]，规定help[i][j]的左上角点(0,0)，右下角点(i,j)
     *    help[i][j]= help[i-1][j] + help[i][j-1] - help[i-1][j-1] + arr[i][j]
     *    (0,0)--------|--------------
     *      |          |             |
     *      |   甲     |    乙       |
     *      |          |             |
     *      |----------a-------------b
     *      |          |             |
     *      |   丙     |    丁       |
     *      |          |             |
     *      |----------c-------------d
     *      d = 甲 + 乙 + 丙 + 丁
     *      c = 甲 + 丙
     *      b = 甲 + 乙
     *      a = 甲
     *      甲乙丙= c+b-a
     * -->  丁 = d-c-b+a
     *
     *  2、竖着三刀已经规定好了，横着切一刀，形成8块，怎么切一刀，能让8块中最小值尽量大？
     *     a、从0~i行，横着切一刀，出来的8块累加和最小值最大是多少？  -> up[i]
     *     b、从i~N-1行，横着切一刀，出来的8块累加和最小值最大是多少？  -> down[i]
     *     c、从2~N-3行，枚举中间一刀，列举所有的可能性
     *     d、求解up[i]和down[i], 横切一刀不会回退
     *     
     */
    public static int maxMinSumIn16(int[][] matrix) {
        if (matrix == null || matrix.length < 4 || matrix[0].length < 4) {
            return 0;
        }
        // help[i][j]：左上角(0,0) 右下角(i,j)的子矩阵的累加和是多少？
        int[][] help = generateSumRecord(matrix);
        // 通过help，得到任意子矩阵的累加和
        int col = matrix[0].length;
        int res = Integer.MIN_VALUE;
        // 3个for循环在暴力枚举纵向三刀
        for (int c1 = 0; c1 < col - 3; c1++) {
            for (int c2 = c1 + 1; c2 < col - 2; c2++) {
                for (int c3 = c2 + 1; c3 < col - 1; c3++) {
                    res = Math.max(res, getBestDecision(help, c1, c2, c3));
                }
            }
        }
        return res;
    }

    // help[i][j]：左上角(0,0) 右下角(i,j)，矩阵的累加和
    public static int[][] generateSumRecord(int[][] matrix) {
        int row = matrix.length;
        int col = matrix[0].length;
        int[][] record = new int[row][col];
        record[0][0] = matrix[0][0];
        for (int i = 1; i < row; i++) {
            record[i][0] = matrix[i][0] + record[i - 1][0];
        }

        for (int j = 1; j < col; j++) {
            record[0][j] = matrix[0][j] + record[0][j - 1];
        }

        for (int i = 1; i < row; i++) {
            for (int j = 1; j < col; j++) {
                record[i][j] = record[i - 1][j] + record[i][j - 1] - record[i - 1][j - 1] + matrix[i][j];
            }
        }
        return record;
    }

    public static int getBestDecision(int[][] help, int c1, int c2, int c3) {
        // 从(0~i]行，横着切一刀，出来的8块累加和最小值最大值
        int[] up = getUpSplitArray(help, c1, c2, c3);

        // 从[i~N-1)行，横着切一刀，出来的8块累加和最小值最大值
        int[] down = getDownSplitArray(help, c1, c2, c3);


        int res = Integer.MIN_VALUE;
        // 枚举中间一刀
        for (int mid = 1; mid < help.length - 2; mid++) {
            res = Math.max(res, Math.min(up[mid], down[mid + 1]));
        }
        return res;
    }

    public static int[] getUpSplitArray(int[][] help, int c1, int c2, int c3) {
        int size = help.length;
        int[] up = new int[size];
        int split = 0;
        up[1] = Math.min(value(help, c1, c2, c3, 0, 0), value(help, c1, c2, c3, 1, 1));
        for (int i = 2; i < size; i++) {
            int minSMax = towSubMatrixMin(help, c1, c2, c3, 0, split, i);
            while (split < i) {
                if (split == i - 1) {
                    break;
                }
                int moved = towSubMatrixMin(help, c1, c2, c3, 0, split + 1, i);
                if (moved < minSMax) {
                    break;
                } else {
                    minSMax = moved;
                    split++;
                }
            }
            up[i]= minSMax;
        }
        return up;
    }

    public static int[] getDownSplitArray(int[][] help, int c1, int c2, int c3) {
        int size = help.length;
        int[] down = new int[size];
        int split = size - 1;
        down[size - 2] = Math.min(value(help, c1, c2, c3, size - 2, size - 2),
                value(help, c1, c2, c3, size - 1, size - 1));
        for (int i = size - 3; i >= 0; i--) {
            int minSMax = towSubMatrixMin(help, c1, c2, c3, i, split - 1, size - 1);
            while (split > i) {
                if (split == i + 1) {
                    break;
                }
                int moved = towSubMatrixMin(help, c1, c2, c3, i, split - 2, size - 1);
                if (moved < minSMax) {
                    break;
                } else {
                    minSMax = moved;
                    split--;
                }
            }
            down[i] = minSMax;
        }
        return down;
    }

    // 竖着三刀知道，知道开始行和结束行,得出四块区域a、b、c、d，返回最小值
    // 也可以得出8块的最小值，
    /**
     *         c1         c2        c3
     * --------|----------|---------|------- startRow
     *         |          |         |
     *    a    |    b     |   c     |    d
     *         |          |         |
     * --------|----------|---------|-------- endRow
     * @param help 矩阵累加和
     * @param c1 竖切第1刀
     * @param c2 竖切第2刀
     * @param c3 竖切第3刀
     * @param startRow 横切一刀的开始行
     * @param endRow 横切一刀的结束行
     * @return
     */
    public static int value(int[][] help, int c1, int c2, int c3, int startRow, int endRow) {
        int value1 = area(help, startRow, 0, endRow, c1);
        int value2 = area(help, startRow, c1 + 1, endRow, c2);
        int value3 = area(help, startRow, c2 + 1, endRow, c3);
        int value4 = area(help, startRow, c3 + 1, endRow, help[0].length - 1);
        return Math.min(Math.min(value1, value2), Math.min(value3, value4));
    }

    public static int area(int[][] help, int i1, int j1, int i2, int j2) {
        int all = help[i2][j2];
        int left = j1 > 0 ? help[i2][j1 - 1] : 0;
        int up = i1 > 0 ? help[i1 - 1][j2] : 0;
        int makeUp = (i1 > 0 && j1 > 0) ? help[i1 - 1][j1 - 1] : 0;
        return all - left - up + makeUp;
    }

    public static int towSubMatrixMin(int[][] help, int c1, int c2, int c3, int i, int split, int j) {
        return Math.min(value(help, c1, c2, c3, i, split), value(help, c1, c2, c3, split + 1, j));
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        while (in.hasNext()) {
            int n = in.nextInt();
            int m = in.nextInt();
            int[][] matrix = new int[n][m];
            for (int i = 0; i < n; i++) {
                char[] chas = in.next().toCharArray();
                for (int j = 0; j < m; j++) {
                    matrix[i][j] = chas[j] - '0';
                }
            }
            System.out.println(maxMinSumIn16(matrix));
        }
        in.close();
    }
}
