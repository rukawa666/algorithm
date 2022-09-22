package com.rukawa.algorithm.leetcode.highfrequency;

/**
 * create by hqh on 2022/9/8
 */
public class Problem_0064_MinimumPathSum {
    /**
     * 最小路径和
     * 给定一个包含非负整数的 m x n 网格 grid ，请找出一条从左上角到右下角的路径，使得路径上的数字总和为最小。
     * 说明：每次只能向下或者向右移动一步。
     *
     * 示例 1：
     * 输入：grid = [[1,3,1],[1,5,1],[4,2,1]]
     * 输出：7
     * 解释：因为路径 1→3→1→1→1 的总和最小。
     *
     * 示例 2：
     * 输入：grid = [[1,2,3],[4,5,6]]
     * 输出：12
     *  
     *
     * 提示：
     * m == grid.length
     * n == grid[i].length
     * 1 <= m, n <= 200
     * 0 <= grid[i][j] <= 100
     */
    public int minPathSum(int[][] grid) {
        int m = grid.length;
        int n = grid[0].length;
        int[][] dp = new int[m][n];
        dp[0][0] = grid[0][0];
        for (int j = 1; j < n; j++) {
            dp[0][j] = dp[0][j - 1] + grid[0][j];
        }

        for (int i = 1; i < m; i++) {
            dp[i][0] = dp[i - 1][0] + grid[i][0];
        }

        for (int i = 1; i < m; i++) {
            for (int j = 1; j < n; j++) {
                dp[i][j] = Math.min(dp[i - 1][j], dp[i][j - 1]) + grid[i][j];
            }
        }
        return dp[m - 1][n - 1];
    }

    public int minPathSum2(int[][] grid) {
        int m = grid.length;
        int n = grid[0].length;
        /**
         * 省空间：此时想得到dp[i][j]位置的值，它依赖dp[i-1][j]和dp[i][j-1]位置的值
         * 比如来到17行，此时只需要16行，15行往上的行不需要，所以此处可以优化空间
         *
         * 假设二维dp更新结果为
         * [
         *  [a][b][c][d]
         *  [A][B][C][D]
         * ]
         * 第0行的值已经得到是a b c d
         * 更新第一行，A=a+grid[1][0]
         * 如果此时把a的值更新为A
         * 则B的值只依赖于A和b，所以只用一维的表就行
         * 第0行：[a,b,c,d]
         * 第1行：0位置更新只依赖上个位置的值a,此时更新为A
         *      [A,b,c,d]
         *      此时更新1位置的值，A是左边的值，b是上面的值，可以更新为B
         */
        int[] dp = new int[n];
        dp[0] = grid[0][0];
        for (int j = 1; j < n; j++) {
            dp[j] = dp[j - 1] + grid[0][j];
        }

        for (int i = 1; i < m; i++) {
            dp[0] += grid[i][0];
            for (int j = 1; j < n; j++) {
                dp[j] = Math.min(dp[j - 1], dp[j]) + grid[i][j];
            }
        }
        return dp[n - 1];
    }
}
