package com.rukawa.algorithm.types.matrix;

/**
 * @className: Code03_ZigZagPrintMatrix
 * @description: TODO 类描述
 * @author: 鎏川疯
 * @date: 2021/7/15 0015 21:53
 **/
public class Code03_ZigZagPrintMatrix {
    /**
     * ZigZag(之字形)打印矩阵
     */
    public static void printMatrixZigZag(int[][] matrix) {
        int tR = 0;
        int tC = 0;
        int dR = 0;
        int dC = 0;
        int endR = matrix.length - 1;
        int endC = matrix[0].length - 1;
        boolean fromUp = false;
        while (tR != endR + 1) {
            printLevel(matrix, tR, tC, dR, dC, fromUp);
            tR = tC == endC ? tR + 1 : tR;
            tC = tC == endC ? tC : tC + 1;
            dC = dR == endR ? dC + 1 : dC;
            dR = dR == endR ? dR : dR + 1;
            fromUp = !fromUp;
        }
        System.out.println();
    }

    public static void printLevel(int[][] matrix, int a, int b, int c, int d, boolean fromUp) {
        if (fromUp) {
            while (a != c + 1) {
                System.out.print(matrix[a++][b--] + " ");
            }
        } else {
            while (c != a - 1) {
                System.out.print(matrix[c--][d++] + " ");
            }
        }
    }

    public static void main(String[] args) {
        int[][] matrix = { { 1, 2, 3, 4 }, { 5, 6, 7, 8 }, { 9, 10, 11, 12 } };
        printMatrixZigZag(matrix);

    }
}
