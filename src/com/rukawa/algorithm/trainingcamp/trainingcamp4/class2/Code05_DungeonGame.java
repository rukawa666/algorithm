package com.rukawa.algorithm.trainingcamp.trainingcamp4.class2;

/**
 * Created with Intellij IDEA
 *
 * @Author：SuperHai
 * @Date：2020-10-13 0:01
 * @Version：1.0
 */
public class Code05_DungeonGame {
    /**
     * 给定一个二维数组map，含义是一张地图，例如，如下矩阵：
     * -2  -3   3
     * -5  -10  1
     *  0  30  -5
     *  游戏的规则如下：
     *  骑士从左上角出发，每次只能向右或者向下走，最后到达右下角见到公主。
     *  地图中每个位置的值代表骑士要遭遇的事情。
     *  如果是负数，说明此处有怪兽，要让骑士损失血量。
     *  如果是非负数，代表此处有血瓶，能让骑士回血。
     *  骑士从左上角到右下角的过程中，走到任何一个位置时，血量都不能少于1.
     *  为了保证骑士能见到公主，初始血量至少是多少？根据map，返回至少的初始血量。
     */

    public static int knightInitialHP2(int[][] matrix) {
        if (matrix == null || matrix.length == 0 || matrix[0] == null || matrix[0].length == 0) {
            return 1;
        }
        return process(matrix, matrix.length, matrix[0].length, 0, 0);
    }

    // 来到了matrix[row][col]，还没登上去，到达右下角，返回至少的初始血量
    public static int process(int[][] matrix, int N, int M, int row, int col) {
        if (row == N - 1 && col == M - 1) {  // 已经到达了右下角
            // 如果最后一个血量是-3，前面血量至少是4
            // 如果最后一个血量是7，前面血量至少是1(保证还活着)
            return matrix[N - 1][M - 1] < 0 ? -matrix[N - 1][M - 1] + 1 : 1;
        }

        // 到达最后一行
        if (row == N - 1) {
            // 如果右侧为3，你登上去活下来至少有3的血量可以往后进行到底
            int rightNeed = process(matrix, N, M, row, col + 1);
            /**
             * 讨论当前位置的血量
             * 1、如果当前位置是-7，右侧需要的血量是3，所以-7之前最少需要的血量为-(-7)+3才能通关
             * 2、如果当前位置是5，因为5>3,所以只要保证前面只要1点血量就可以通关
             * 3、如果当前位置是1，右侧需要血量是3，所以前面至少要2点血才能通过
             */
            if (matrix[row][col] < 0) { // 情况1
                return -matrix[row][col] + rightNeed;
            } else if (matrix[row][col] >= rightNeed) { // 情况2
                return 1;
            } else {    // 情况3
                return rightNeed - matrix[row][col];
            }

        }

        // 到达最后一列，和上面到达最后一行情况一致
        if (col == M - 1) {
            int downNeed = process(matrix, N, M, row + 1, col);
            if (matrix[row][col] < 0) { // 情况1
                return -matrix[row][col] + downNeed;
            } else if (matrix[row][col] >= downNeed) { // 情况2
                return 1;
            } else {    // 情况3
                return downNeed - matrix[row][col];
            }
        }

        // 最经济的方式到达
        int minNeed = Math.min(process(matrix, N, M, row, col + 1),
                process(matrix, N, M, row + 1, col));
        if (matrix[row][col] < 0) { // 情况1
            return -matrix[row][col] + minNeed;
        } else if (matrix[row][col] >= minNeed) { // 情况2
            return 1;
        } else {    // 情况3
            return minNeed - matrix[row][col];
        }
    }

//    // 暴力递归改动态规划
//    public static int knightInitialHP1(int[][] matrix) {
//        if (matrix == null || matrix.length == 0 || matrix[0] == null || matrix[0].length == 0) {
//            return 1;
//        }
//        int row = matrix.length;
//        int col = matrix[0].length;
//        int[][] dp = new int[row][col];
//    }

}
