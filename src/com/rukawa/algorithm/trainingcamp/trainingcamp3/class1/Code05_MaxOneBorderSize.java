package com.rukawa.algorithm.trainingcamp.trainingcamp3.class1;

/**
 * Created with Intellij IDEA
 *
 * @Author：SuperHai
 * @Date：2020-08-18 23:10
 * @Version：1.0
 *
 * 在N*N的矩阵中
 * 存在的矩阵的个数有O(n^4)
 * 存在的正方形的个数有O(n^3): 左上角点枚举，左上角点的可能性有n^2，以及边长的可能性O(n),所以为O(n^2 * n)
 */
public class Code05_MaxOneBorderSize {

    /**
     * 给定一个N*N的矩阵matrix，只有0和1两种值，返回边框全是1的最大正方形的边长长度.
     * 例如：
     *  01111
     *  01001
     *  01001
     *  01111
     *  01011
     * 其中边框都是1的最大正方形的大小为4*4，所以返回4
     * @param matrix
     */
    public static int getMaxSize(int[][] matrix) {
        // right[i][j] 表示(i,j)右侧有多少连续的1
        int[][] right = new int[matrix.length][matrix[0].length];
        // down[i][j]  表示(i,j)下侧有多少连续的1
        int[][] down = new int[matrix.length][matrix[0].length];
        setBorderMap(matrix, right, down);

        for (int size = Math.min(matrix.length, matrix[0].length); size != 0; size--) {
            // 在一个点，判断从该点出发
            /**
             * 从一个点出发
             * 向右到达size距离，是否都是1
             * 向下到达size距离，判断是否都是1
             * 然后跳到i+size处，向下到达size距离，判断是否都是1
             * 然后跳到j+size处，向右到达size距离，判断是否都是1
             */
            if (hasSizeOfBorder(size, right, down)) {
                return size;
            }
        }
        return 0;
    }

    public static void setBorderMap(int[][] m, int[][] right, int[][] down) {
        int row = m.length;
        int col = m[0].length;
        if (m[row - 1][col - 1] == 1) {
            right[row - 1][col - 1] = 1;
            down[row - 1][col - 1] = 1;
        }
        for (int i = row - 2; i != -1; i--) {
            if (m[i][col - 1] == 1) {
                right[i][col - 1] = 1;
                down[i][col - 1] = down[i + 1][col - 1] + 1;
            }
        }
        for (int i = col - 2; i != -1; i--) {
            if (m[row - 1][i] == 1) {
                right[row - 1][i] = right[row - 1][i + 1] + 1;
                down[row - 1][i] = 1;
            }
        }
        for (int i = row - 2; i != -1; i--) {
            for (int j = col - 2; j != -1; j--) {
                if (m[i][j] == 1) {
                    right[i][j] = right[i][j + 1] + 1;
                    down[i][j] = down[i + 1][j] + 1;
                }
            }
        }
    }

    public static boolean hasSizeOfBorder(int size, int[][] right, int[][] down) {
        for (int i = 0; i != right.length - size + 1; i++) {
            for (int j = 0; j != right[0].length - size + 1; j++) {
                if (right[i][j] >= size
                        && down[i][j] >= size
                        && right[i + size - 1][j] >= size
                        && down[i][j + size - 1] >= size) {
                    return true;
                }
            }
        }
        return false;
    }
    // for test
    public static int[][] generateRandom01Matrix(int rowSize, int colSize) {
        int[][] res = new int[rowSize][colSize];
        for (int i = 0; i != rowSize; i++) {
            for (int j = 0; j != colSize; j++) {
                res[i][j] = (int) (Math.random() * 2);
            }
        }
        return res;
    }

    // for test
    public static void printMatrix(int[][] matrix) {
        for (int i = 0; i != matrix.length; i++) {
            for (int j = 0; j != matrix[0].length; j++) {
                System.out.print(matrix[i][j] + " ");
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        int[][] matrix = generateRandom01Matrix(7, 8);
        printMatrix(matrix);
        System.out.println("====================================");
        System.out.println(getMaxSize(matrix));
    }
}
