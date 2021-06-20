package com.rukawa.algorithm.types.indexTree;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @Author: Administrator
 * @Date: 2021/6/20 0020 23:05
 */
public class Code02_IndexTree2D {

    /**
     * 假设在一个二维数组中，在原数组中，行的二进制是0110100，列的二进制是0111000，对应的位置的被改变
     * 在help中哪些位置的数会被改变？
     * 行管理 -> help 0110001~0110100
     * 列管理 -> help 0110001~0111000
     * 所有这里面的全部组合都受影响
     * 依次类推，三维也是一样
     *
     * 如何得到(3,3)(4,4)的值?
     * 先得到help[4][4] - help[4][2] - help[2,4] + help[2,2]
     */

    // LeetCode 308题
    public static class IndexTree2D {

        private int[][] tree;
        private int[][] nums;
        private int N;
        private int M;

        public IndexTree2D(int[][] matrix) {
            if (matrix.length == 0 || matrix[0].length == 0) {
                return;
            }
            N = matrix.length;
            M = matrix[0].length;
            tree = new int[N + 1][M + 1];
            nums = new int[N][M];
            for (int i = 0; i < N; i++) {
                for (int j = 0; j < M; j++) {
                    update(i, j, matrix[i][j]);
                }
            }
        }

        // 具体请参照一维结构
        public int sum(int row, int col) {
            int sum = 0;
            for (int i = row + 1; i > 0; i -= i & (-i)) {
                for (int j = col + 1; j > 0; j -= j & (-j)) {
                    sum += tree[i][j];
                }
            }
            return sum;
        }


        public void update(int row, int col, int val) {
            if (N == 0 || M == 0) {
                return;
            }
            // 现在的值 - 原来的值 = 增量
            // 更新的val = 原来的值 + 增量
            int add = val - nums[row][col];
            nums[row][col] = val;
            for (int i = row + 1; i <= N; i += i & (-i)) {
                for (int j = col + 1; j <= M; j += j & (-j)) {
                    tree[i][j] += add;
                }
            }
        }

        // [row1, col1] 左上角的行和列
        // [row2, col2] 右上角的行和列
        // 求出之间的累加和
        public int sumRegion(int row1, int col1, int row2, int col2) {
            if (N == 0 || M == 0) {
                return 0;
            }
            return sum(row2, col2) - sum(row1 - 1, col2) - sum(row2, col1 - 1) + sum(row1 - 1,col1 - 1);
        }
    }
}
