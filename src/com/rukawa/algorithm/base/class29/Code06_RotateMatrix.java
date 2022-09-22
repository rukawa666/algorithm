package com.rukawa.algorithm.base.class29;

/**
 * create by hqh on 2022/9/19
 */
public class Code06_RotateMatrix {

    /**
     * 给定一个长方形矩阵matrix，转圈打印打印
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

    public static void printEdge(int[][] matrix, int a, int b, int c, int d) {
        if (a == c) {
            for (int col = b; col <= d; col++) {
                System.out.print(matrix[a][col] + " ");
            }
        } else if (b == d) {
            for (int row = a; row <= c; row++) {
                System.out.print(matrix[row][b] + " ");
            }
        } else {
            int curRow = a;
            int curCol = b;
            while (curCol != d) {
                System.out.print(matrix[a][curCol] + " ");
                curCol++;
            }

            while (curRow != c) {
                System.out.print(matrix[curRow][d] + " ");
                curRow++;
            }

            while (curCol != b) {
                System.out.print(matrix[c][curCol] + " ");
                curCol--;
            }

            while (curRow != a) {
                System.out.print(matrix[curRow][b] + " ");
                curRow--;
            }
        }
    }



    public static void main(String[] args) {
        int[][] matrix = { { 1, 2, 3, 4 }, { 5, 6, 7, 8 }, { 9, 10, 11, 12 },
                { 13, 14, 15, 16 } };
        spiralOrderPrint(matrix);

    }
}
