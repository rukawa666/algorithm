package com.rukawa.algorithm.trainingcamp.trainingcamp2.class1;

/**
 * Created with Intellij IDEA
 *
 * @Author：SuperHai
 * @Date：2020-08-08 10:58
 * @Version：1.0
 */
public class Code05_PrintMatrixSpiralOrder {

    /**
     * 转圈打印矩阵
     * @param matrix
     */
    public static void spiralOrderPrint(int[][] matrix) {
        int a = 0;
        int b = 0;
        int c = matrix.length - 1;
        int d = matrix[0].length - 1;
        while (a <= c && b <= d) {
            printEdge(matrix, a++, b++, c--, d--);
        }
    }

    public static void printEdge(int[][] m, int a, int b, int c, int d) {
        if (a == c) {
            for (int i = b; i <= d; i++) {
                System.out.print(m[a][i] + " ");
            }
        } else if (b == d) {
            for (int i = a; i <= c; i++) {
                System.out.print(m[i][b] + " ");
            }
        } else {
            // 当前列
            int curC = b;
            // 当前行
            int curR = a;
            while (curC != d) {
                System.out.print(m[a][curC] + " ");
                curC++;
            }

            while (curR != c) {
                System.out.print(m[curR][d] + " ");
                curR++;
            }

            while (curC != b) {
                System.out.print(m[c][curC] + " ");
                curC--;
            }

            while (curR != a) {
                System.out.print(m[curR][b] + " ");
                curR--;
            }
        }
    }

    public static void main(String[] args) {
        int[][] matrix = { { 1, 2, 3, 4 }, { 5, 6, 7, 8 }, { 9, 10, 11, 12 },
                { 13, 14, 15, 16 } };
        spiralOrderPrint(matrix);

    }
}
