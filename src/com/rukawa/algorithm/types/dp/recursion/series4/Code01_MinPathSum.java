package com.rukawa.algorithm.types.dp.recursion.series4;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @Author: Administrator
 * @Date: 2021/4/11 0011 22:43
 */
public class Code01_MinPathSum {
    /**
     * 给定一个二维数组matrix，一个人必须从左上角出发，最后到达右下角
     * 沿途只可以向下或者向右走，沿途的数字都累加就是距离累加和
     * 返回最小距离累加和
     *
     */

    public static int minPathSum1(int[][] m) {
        if (m == null || m.length == 0 || m[0] == null || m[0].length == 0) {
            return 0;
        }
        int row = m.length;
        int col = m[0].length;
        int[][] dp = new int[row][col];
        // (0,0)位置到(0,0)位置的距离
        dp[0][0] = m[0][0];
        for (int i = 1; i < row; i++) {
            dp[i][0] = m[i][0] + dp[i - 1][0];
        }

        for (int j = 1; j < col; j++) {
            dp[0][j] = m[0][j] + dp[0][j - 1];
        }

        for (int i = 1; i < row; i++) {
            for (int j = 1; j < col; j++) {
                dp[i][j] = Math.min(dp[i - 1][j], dp[i][j - 1]) + m[i][j];
            }
        }
        return dp[row - 1][col - 1];
    }

    // 空间压缩
    public static int minPathSum2(int[][] m) {
        if (m == null || m.length == 0 || m[0] == null || m[0].length == 0) {
            return 0;
        }
        int row = m.length;
        int col = m[0].length;
        int[] arr = new int[col];
        arr[0] = m[0][0];
        // 填写第0行
        for (int j = 1; j < col; j++) {
            arr[j] = arr[j - 1] + m[0][j];
        }

        // 下一行依赖上一行
        // dp[0][...] -> arr
        // arr[0] -> 代表dp[上一行][0]
        //              dp[这一行][0]
        for (int i = 1; i < row; i++) {
            arr[0] += m[i][0];
            for (int j = 1; j < col; j++) {
                // arr[j - 1] -> 代表dp[这一行][j - 1]的值
                // arr[j] -> 代表dp[上一行][j]位置的纸
                arr[j] = Math.min(arr[j - 1], arr[j]) + m[i][j];
            }
        }
        return arr[col - 1];
    }

    // for test
    public static int[][] generateRandomMatrix(int rowSize, int colSize) {
        if (rowSize < 0 || colSize < 0) {
            return null;
        }
        int[][] result = new int[rowSize][colSize];
        for (int i = 0; i != result.length; i++) {
            for (int j = 0; j != result[0].length; j++) {
                result[i][j] = (int) (Math.random() * 100);
            }
        }
        return result;
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
        int rowSize = 10;
        int colSize = 10;
        int[][] m = generateRandomMatrix(rowSize, colSize);
        System.out.println(minPathSum1(m));
        System.out.println(minPathSum2(m));

    }
}
