package com.rukawa.algorithm.leetcode.offer;

/**
 * @className: Problem_0029
 * @description: TODO 类描述
 * @author: 鎏川疯
 * @date: 2021/7/18 0018 13:44
 **/
public class Problem_0029 {
    /**
     * 输入一个矩阵，按照从外向里以顺时针的顺序依次打印出每一个数字。
     *
     * 示例 1：
     * 输入：matrix = [[1,2,3],[4,5,6],[7,8,9]]
     * 输出：[1,2,3,6,9,8,7,4,5]
     *
     * 示例 2：
     * 输入：matrix =[[1,2,3,4],[5,6,7,8],[9,10,11,12]]
     * 输出：[1,2,3,4,8,12,11,10,9,5,6,7]
     *
     * 限制：
     * 0 <= matrix.length <= 100
     * 0 <= matrix[i].length<= 100
     */
    public static int[] spiralOrder(int[][] matrix) {
        if (matrix == null || matrix.length == 0) {
            return new int[0];
        }
        int sRow = 0;
        int sCol = 0;
        int eRow = matrix.length - 1;
        int eCol = matrix[0].length - 1;
        int[] ans = new int[matrix.length * matrix[0].length];
        int index = 0;
        while (sRow <= eRow && sCol <= eCol) {
            if (sRow == eRow) {
                for (int i = sCol; i <= eCol; i++) {
                    ans[index++] = matrix[sRow][i];
                }
            } else if (sCol == eCol) {
                for (int i = sRow; i <= eRow; i++) {
                    ans[index++] = matrix[i][sCol];
                }
            } else {
                int curRow = sRow;
                int curCol = sCol;
                while (curCol != eCol) {
                    ans[index++] = matrix[sRow][curCol++];
                }

                while (curRow != eRow) {
                    ans[index++] = matrix[curRow++][eCol];
                }

                while (curCol != sCol) {
                    ans[index++] = matrix[eRow][curCol--];
                }

                while (curRow != sRow) {
                    ans[index++] = matrix[curRow--][sCol];
                }
            }
            sRow++;
            sCol++;
            eRow--;
            eCol--;
        }
        return ans;
    }

    public static void main(String[] args) {
        int[][] matrix = {
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 9}
        };
        int[] ans = spiralOrder(matrix);
        for (int an : ans) {
            System.out.print(an + " ");
        }
    }
}
