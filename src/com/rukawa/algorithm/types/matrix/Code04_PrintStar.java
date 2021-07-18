package com.rukawa.algorithm.types.matrix;

/**
 * @className: Code04_PrintStar
 * @description: TODO 类描述
 * @author: 鎏川疯
 * @date: 2021/7/15 0015 21:53
 **/
public class Code04_PrintStar {
    /**
     * 二维矩阵，从左到右，然后从上到下
     * 往左走，需要左边留一个，走上面，上面留一个，走右边，右边留一个
     * # # # # #
     *         #
     *   # #   #
     *   #     #
     *   # # # #
     *
     *   两层算一个圈
     */
    public static void printStar(int N) {
        int leftUp = 0;
        int rightDown = N - 1;
        char[][] m = new char[N][N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                m[i][j] = ' ';
            }
        }
        while (leftUp <= rightDown) {
            set(m, leftUp, rightDown);
            leftUp += 2;
            rightDown -= 2;
        }
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                System.out.print(m[i][j] + " ");
            }
            System.out.println();
        }
    }

    public static void set(char[][] m, int leftUp, int rightDown) {
        for (int col = leftUp; col <= rightDown; col++) {
            m[leftUp][col] = '*';
        }

        for (int row = leftUp + 1; row <= rightDown; row++) {
            m[row][rightDown] = '*';
        }
        for (int col = rightDown - 1; col > leftUp; col--) {
            m[rightDown][col] = '*';
        }

        for (int row = rightDown - 1; row > leftUp + 1; row--) {
            m[row][leftUp + 1] = '*';
        }
    }

    public static void main(String[] args) {
        printStar(5);
    }
}
