package com.rukawa.algorithm.leetcode.interview;

/**
 * create by hqh on 2022/9/27
 */
public class Problem_17_24_MaxSubMatrixLCCI {
    /**
     * 最大子矩阵
     * 给定一个正整数、负整数和 0 组成的 N × M 矩阵，编写代码找出元素总和最大的子矩阵。
     * 返回一个数组 [r1, c1, r2, c2]，其中 r1, c1 分别代表子矩阵左上角的行号和列号，r2, c2 分别代表右下角的行号和列号。
     * 若有多个满足条件的子矩阵，返回任意一个均可。
     * 注意：本题相对书上原题稍作改动
     *
     * 示例：
     * 输入：
     * [
     *    [-1,0],
     *    [0,-1]
     * ]
     * 输出：[0,1,0,1]
     * 解释：输入中标粗的元素即为输出所表示的矩阵
     *  
     * 说明：
     * 1 <= matrix.length, matrix[0].length <= 200
     */
    public int[] getMaxMatrix(int[][] matrix) {
        int n = matrix.length;
        int m = matrix[0].length;
        int maxSum = Integer.MIN_VALUE;
        int curSum = 0;
        int r1 = 0;
        int c1 = 0;
        int r2 = 0;
        int c2 = 0;
        for (int i = 0; i < n; i++) {
            int[] arr = new int[m];
            for (int j = i; j < n; j++) {
                curSum = 0;
                int begin = 0;
                for (int k = 0; k < m; k++) {
                    arr[k] += matrix[j][k];
                    curSum += arr[k];
                    if (curSum > maxSum) {
                        maxSum = curSum;
                        r1 = i;
                        c1 = begin;
                        r2 = j;
                        c2 = k;

                    }
                    if (curSum < 0) {
                        curSum = 0;
                        begin = k + 1;
                    }
                }
            }
        }
        return new int[] {r1, c1, r2, c2};
    }
}
