package com.rukawa.algorithm.types.matrix;

/**
 * @className: Code01_PrintMatrixSpiralOrder
 * @description: TODO 类描述
 * @author: 鎏川疯
 * @date: 2021/7/15 0015 21:53
 **/
public class Code01_PrintMatrixSpiralOrder {
    /**
     * 给定一个正方形矩阵matrix，原地调整成顺时针90度转动的样子
     * a b c	g d a
     * d e f	h e b
     * g h i	i f c
     */
    /**
     * 顺时针打印矩阵
     */

    public static void spiralOrderPrint(int[][] matrix) {
        int startRow = 0;
        int startCol = 0;
        int endRow = matrix.length - 1;
        int endCol = matrix[0].length - 1;
        while (startCol <= endCol && startRow <= endRow) {
            printEdge(matrix, startRow++, startCol++, endRow--, endCol--);
        }
    }

    public static void printEdge(int[][] matrix, int sRow, int sCol, int eRow, int eCol) {
        if (sRow == eRow) {     // 只有一行
            for (int i = sCol; i <= eCol; i++) {
                System.out.print(matrix[sRow][i] + " ");
            }
        } else if (sCol == eCol) {  // 只有一列
            for (int i = sRow; i <= eRow; i++) {
                System.out.print(matrix[i][sCol] + " ");
            }
        } else {    // 多行多列
            int curRow = sRow;
            int curCol = sCol;
            while (curCol != eCol) {
                System.out.print(matrix[sRow][curCol] + " ");
                curCol++;
            }

            while (curRow != eRow) {
                System.out.print(matrix[curRow][eCol] + " ");
                curRow++;
            }

            while (curCol != sCol) {
                System.out.print(matrix[eRow][curCol] + " ");
                curCol--;
            }

            while (curRow != sRow) {
                System.out.print(matrix[curRow][sCol] + " ");
                curRow--;
            }
        }
    }

    public static void main(String[] args) {
        int[][] matrix = {{ 1, 2, 3, 4 },
                          { 5, 6, 7, 8 },
                          { 9, 10, 11, 12 }};
        spiralOrderPrint(matrix);

    }
}
