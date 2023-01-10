package com.rukawa.algorithm.base.class30_subarray;

/**
 * create by hqh on 2022/9/19
 */
public class Code07_ZigZagPrintMatrix {
    /**
     * ZigZag(之字形)打印矩阵
     * @param matrix
     */
    public static void printMatrixZigZag(int[][] matrix) {
        int Ar = 0;
        int Ac = 0;
        int Br = 0;
        int Bc = 0;
        int endR = matrix.length - 1;
        int endC = matrix[0].length - 1;
        boolean fromUp = false; // 是不是从右上往坐下打印
        while (Ar != endR + 1) {
            // 斜线的两端，A和B，方向也告诉你，去打印
            printLevel(matrix, Ar, Ac, Br, Bc, fromUp);
            /**
             * A先向右走，再向下走。所以向右走，判断是否到达最后一列
             * 如果还没到达最后一列，A向右走，行不变，列+1
             * 如果到达最后一列，A向下走，A行+1,列不变
             */
            // 最后一列，行+1，否则，行不变
            Ar = Ac == endC ? Ar + 1 : Ar;
            // 最后一列，列不变，否则，列+1
            Ac = Ac == endC ? Ac : Ac + 1;
            /**
             * B先向下走，再向右走。所以向下走，先判断是否到达最后一行
             * 如果没有到达最后一行，B向下走，列不变，行+1
             * 如果到达最后一行，B向右走，行不变，列+1
             */
            // 最后一行，列+1，否则不变
            Bc = Br == endR ? Bc + 1 : Bc;
            // 最后一行，行不变，否则，行+1
            Br = Br == endR ? Br : Br + 1;
            fromUp = !fromUp;

            /**
             * A先向右走，如果没有到达最后一列，行不变，此时列+1
             * A再向下走，此时已经到达最后一列，行+1，但是此时的列不变
             * Ar = Ac != endC ? Ar : Ar + 1;
             * Ac = Ac == endC ? Ac : Ac + 1;
             * B先向下走，如果没有到达最后一行，行+1，此时的列不变
             * B再先右走，此时已经到达最后一行，行不变，此时列+1
             * Bc = Br != endR ? Bc : Bc + 1;
             * Br = Br == endR ? Br : Br + 1;
             */
        }
        System.out.println();
    }

    public static void printLevel(int[][] m, int Ar, int Ac, int Br, int Bc, boolean f) {
        if (f) {
            while (Ar != Br + 1) {
                System.out.print(m[Ar++][Ac--] + " ");
            }
        } else {
            while (Br != Ar - 1) {
                System.out.print(m[Br--][Bc++] + " ");
            }
        }
    }

    public static void main(String[] args) {
        int[][] matrix = { { 1, 2, 3, 4 }, { 5, 6, 7, 8 }, { 9, 10, 11, 12 } };
        printMatrixZigZag(matrix);

    }
}
