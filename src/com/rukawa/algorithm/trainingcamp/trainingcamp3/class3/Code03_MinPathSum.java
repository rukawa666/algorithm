package com.rukawa.algorithm.trainingcamp.trainingcamp3.class3;

/**
 * Created with Intellij IDEA
 *
 * @Author：SuperHai
 * @Date：2020-08-25 0:20
 * @Version：1.0
 */
public class Code03_MinPathSum {
    /**
     * 给定一个二维数组matrix，其中每个数都是正数，要求从左上到右下
     * 每一步只能向右或者向下，沿途经过的数字要累加起来
     * 最后请返回最小的路径和
     *
     * 动态规划的空间压缩技巧
     */
    public static int minPathSum1(int[][] matrix) {
        if (matrix == null || matrix.length == 0 || matrix[0] == null || matrix[0].length == 0) {
            return 0;
        }
        int row = matrix.length;
        int col = matrix[0].length;
        int[][] dp = new int[row][col];
        dp[0][0] = matrix[0][0];
        for (int i = 1; i < row; i++) {
            dp[i][0] = dp[i - 1][0] + matrix[i][0];
        }

        for (int j = 1; j < col; j++) {
            dp[0][j] = dp[0][j - 1] + matrix[0][j];
        }

        for (int i = 1;i < row; i++) {
            for (int j = 1; j < col; j++) {
                dp[i][j] = Math.min(dp[i - 1][j], dp[i][j - 1]) + matrix[i][j];
            }
        }

        return dp[row - 1][col - 1];
    }


    public static int minPathSum2(int[][] grid) {
        if (grid == null || grid.length == 0 || grid[0] == null || grid[0].length == 0) {
            return 0;
        }
        int more = Math.max(grid.length, grid[0].length);
        int less = Math.min(grid.length, grid[0].length);
        boolean rowMore = more == grid.length;
        int[] arr = new int[less];
        arr[0] = grid[0][0];
        for (int i = 1; i < less; i++) {
            arr[i] = arr[i - 1] + (rowMore ? grid[0][i] : grid[i][0]);
        }
        for (int i = 1; i < more; i++) {
            arr[0] = arr[0] + (rowMore ? grid[i][0] : grid[0][i]);
            for (int j = 1; j < less; j++) {
                arr[j] = Math.min(arr[j - 1], arr[j])
                        + (rowMore ? grid[i][j] : grid[j][i]);
            }
        }
        return arr[less - 1];
    }

    public static int minPathSum3(int[][] grid) {
        if (grid == null || grid.length == 0 || grid[0] == null || grid[0].length == 0) {
            return 0;
        }
        int N = grid.length;
        int M = grid[0].length;
        int[] dp = new int[M];
        dp[0] = grid[0][0];
        for (int col = 1; col < M; col++) {
            dp[col] = dp[col - 1] + grid[0][col];
        }

        for (int row = 1; row < N; row++) {
            dp[0] = dp[0] + grid[row][0];
            for (int col = 1; col < M; col++) {
                dp[col] = Math.min(dp[col - 1], dp[col]) + grid[row][col];
            }
        }
        return dp[M - 1];
    }

    // for test
    public static int[][] generateRandomMatrix(int rowSize, int colSize) {
        if (rowSize < 0 || colSize < 0) {
            return null;
        }
        int[][] result = new int[rowSize][colSize];
        for (int i = 0; i != result.length; i++) {
            for (int j = 0; j != result[0].length; j++) {
                result[i][j] = (int) (Math.random() * 10);
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
        // int[][] m = generateRandomMatrix(3, 4);
        int[][] m = { { 1, 3, 5, 9 }, { 8, 1, 3, 4 }, { 5, 0, 6, 1 },
                { 8, 8, 4, 0 } };
        printMatrix(m);
        System.out.println(minPathSum1(m));
        System.out.println(minPathSum2(m));
        System.out.println(minPathSum3(m));

    }
}
