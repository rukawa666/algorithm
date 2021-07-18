package com.rukawa.algorithm.leetcode.top100likedquestions;

/**
 * Created with Intellij IDEA
 *
 * @Author：SuperHai
 * @Date：2020-09-20 8:34
 * @Version：1.0
 */
public class Problem_0048_RotateImage {


    /**
     * 旋转图像
     * 给定一个 n×n 的二维矩阵matrix 表示一个图像。请你将图像顺时针旋转 90 度。
     * 你必须在 原地 旋转图像，这意味着你需要直接修改输入的二维矩阵。请不要 使用另一个矩阵来旋转图像。
     *
     * 示例 1：
     * 输入：matrix = [[1,2,3],[4,5,6],[7,8,9]]
     * 输出：[[7,4,1],[8,5,2],[9,6,3]]
     *
     * 示例 2：
     * 输入：matrix = [[5,1,9,11],[2,4,8,10],[13,3,6,7],[15,14,12,16]]
     * 输出：[[15,13,2,5],[14,3,4,1],[12,6,8,9],[16,7,10,11]]
     * 示例 3：
     * 输入：matrix = [[1]]
     * 输出：[[1]]
     *
     * 示例 4：
     * 输入：matrix = [[1,2],[3,4]]
     * 输出：[[3,1],[4,2]]
     *
     * 提示：
     * matrix.length == n
     * matrix[i].length == n
     * 1 <= n <= 20
     * -1000 <= matrix[i][j] <= 1000
     */
    public void rotate(int[][] matrix) {
        int sRow = 0;
        int sCol = 0;
        int eRow = matrix.length - 1;
        int eCol = matrix[0].length - 1;
        while (sRow < eRow) {
            swap(matrix, sRow++, sCol++, eRow--, eCol--);
        }
    }

    public static void swap(int[][] matrix, int sRow, int sCol, int eRow, int eCol) {
        int tmp = 0;
        for (int i = 0; i < eCol - sCol; i++) {
            tmp = matrix[sRow][sCol + i];
            matrix[sRow][sCol + i] = matrix[eRow - i][sCol];
            matrix[eRow - i][sCol] = matrix[eRow][eCol - i];
            matrix[eRow][eCol - i] = matrix[sRow + i][eCol];
            matrix[sRow + i][eCol] = tmp;
        }
    }
}
