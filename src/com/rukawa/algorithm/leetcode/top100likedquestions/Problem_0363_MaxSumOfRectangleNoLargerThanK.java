package com.rukawa.algorithm.leetcode.top100likedquestions;

import java.util.TreeSet;

/**
 * Created with Intellij IDEA
 *
 * @Author：SuperHai
 * @Date：2020-09-20 8:29
 * @Version：1.0
 */
public class Problem_0363_MaxSumOfRectangleNoLargerThanK {

    /**
     * 矩形区域不超过 K 的最大数值和
     * 给定一个非空二维矩阵 matrix 和一个整数 k，找到这个矩阵内部不大于 k 的最大矩形和。
     *
     * 示例:
     * 输入: matrix = [[1,0,1],[0,-2,3]], k = 2
     * 输出: 2
     * 解释: 矩形区域 [[0, 1], [-2, 3]] 的数值和是 2，且 2 是不超过 k 的最大数字（k = 2）。
     *
     * 说明：
     * 矩阵内的矩形区域面积必须大于 0。
     * 如果行数远大于列数，你将如何解答呢？
     * @param matrix
     * @param k
     * @return
     */
    public int maxSumSubmatrix(int[][] matrix, int k) {
        if (matrix == null || matrix[0] == null) {
            return 0;
        }
        int row = matrix.length;
        int col = matrix[0].length;
        int res = Integer.MIN_VALUE;
        TreeSet<Integer> sumSet = new TreeSet<>();
        for (int start = 0; start < row; start++) {
            // 矩阵压缩，多列合并为一列
            int[] colSum = new int[col];
            for (int end = start; end < row; end++) {
                // 一个数也没有的时候，已经有一个前缀和是0了
                sumSet.add(0);
                int rowSum = 0;
                for (int c = 0; c < col; c++) {
                    colSum[c] += matrix[end][c];
                    rowSum += colSum[c];
                    // 前缀和
                    Integer it = sumSet.ceiling(rowSum - k);
                    // 有此前缀和，获取后缀和求出最大值
                    if (it != null) {
                        res = Math.max(res, rowSum - it);
                    }
                    sumSet.add(rowSum);
                }
                sumSet.clear();
            }
        }
        return res;
    }
}
