package com.rukawa.algorithm.leetcode.top100likedquestions;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with Intellij IDEA
 *
 * @Author：SuperHai
 * @Date：2020-09-20 8:34
 * @Version：1.0
 */
public class Problem_0054_SpiralMatrix {

    /**
     *  螺旋矩阵
     *  给你一个 m 行 n 列的矩阵matrix ，请按照 顺时针螺旋顺序 ，返回矩阵中的所有元素。
     * 示例 1：
     * 输入：matrix = [[1,2,3],[4,5,6],[7,8,9]]
     * 输出：[1,2,3,6,9,8,7,4,5]
     *
     * 示例 2：
     * 输入：matrix = [[1,2,3,4],[5,6,7,8],[9,10,11,12]]
     * 输出：[1,2,3,4,8,12,11,10,9,5,6,7]
     *
     * 提示：
     * m == matrix.length
     * n == matrix[i].length
     * 1 <= m, n <= 10
     * -100 <= matrix[i][j] <= 100
     */

    public List<Integer> spiralOrder(int[][] matrix) {
        if (matrix == null || matrix.length == 0) {
            return new ArrayList<>(0);
        }
        int sRow = 0;
        int sCol = 0;
        int eRow = matrix.length - 1;
        int eCol = matrix[0].length - 1;
        List<Integer> ans = new ArrayList<>();
        int index = 0;
        while (sRow <= eRow && sCol <= eCol) {
            if (sRow == eRow) {
                for (int i = sCol; i <= eCol; i++) {
                    ans.add(matrix[sRow][i]);
                }
            } else if (sCol == eCol) {
                for (int i = sRow; i <= eRow; i++) {
                    ans.add(matrix[i][sCol]);
                }
            } else {
                int curRow = sRow;
                int curCol = sCol;
                while (curCol != eCol) {
                    ans.add(matrix[sRow][curCol++]);
                }

                while (curRow != eRow) {
                    ans.add(matrix[curRow++][eCol]);
                }

                while (curCol != sCol) {
                    ans.add(matrix[eRow][curCol--]);
                }

                while (curRow != sRow) {
                    ans.add(matrix[curRow--][sCol]);
                }
            }
            sRow++;
            sCol++;
            eRow--;
            eCol--;
        }
        return ans;
    }
}
