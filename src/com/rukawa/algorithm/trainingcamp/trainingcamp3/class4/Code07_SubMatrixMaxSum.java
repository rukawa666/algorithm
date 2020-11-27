package com.rukawa.algorithm.trainingcamp.trainingcamp3.class4;

/**
 * Created with Intellij IDEA
 *
 * @Author：SuperHai
 * @Date：2020-08-26 21:56
 * @Version：1.0
 */
public class Code07_SubMatrixMaxSum {
    /**
     * 给定一个整型矩阵，反回子矩阵的最大累加和
     * @param matrix
     * @return
     */

    /**
     * 时间复杂度O(row^2*col)
     * 优化，行和列的长度哪个小，遍历小的
     * 时间复杂度优化为O(min(row,col) ^ 2 * max(row,col))
     * @param matrix
     * @return
     */
    public static int maxSum(int[][] matrix) {
        if (matrix == null || matrix.length == 0 || matrix[0].length == 0) {
            return 0;
        }
        int max = Integer.MIN_VALUE;
        int cur = 0;
        int[] s = null;
        for (int i = 0; i != matrix.length; i++) {  // 开始的行号
            s = new int[matrix[0].length];
            for (int j = i; j != matrix.length; j++) {  // 结束的行号， i~j行是我讨论的范围
                cur = 0;
                for (int k = 0; k < s.length; k++) {
                    s[k] += matrix[j][k];
                    cur += s[k];
                    max = Math.max(max, cur);
                    cur = cur < 0 ? 0 : cur;
                }
            }
        }
        return max;
    }

    public static int maxSum2(int[][] matrix) {
        if (matrix == null || matrix.length == 0 || matrix[0].length == 0) {
            return 0;
        }
        int max = Integer.MIN_VALUE;
        int cur = 0;
        int[] s = null;
        int maxLen = matrix.length > matrix[0].length ? matrix.length : matrix[0].length;
        int shortLen = maxLen == matrix.length ? matrix[0].length : matrix.length;

        for (int i = 0; i != shortLen; i++) {  // 开始的行号
            s = new int[maxLen];
            for (int j = i; j != shortLen; j++) {  // 结束的行号， i~j行是我讨论的范围
                cur = 0;
                for (int k = 0; k < s.length; k++) {
                    if (shortLen == matrix[0].length) {
                        s[k] += matrix[k][j];
                    } else {
                        s[k] += matrix[j][k];
                    }
                    cur += s[k];
                    max = Math.max(max, cur);
                    cur = cur < 0 ? 0 : cur;
                }
            }
        }
        return max;
    }

    public static void main(String[] args) {
        int[][] matrix = { { -90, 48, 78, 10 },
                           { 64, -40, 64, 20 },
                           { -81, -7, 66, 30 }};
        System.out.println(maxSum(matrix));
        System.out.println(maxSum2(matrix));

    }
}
