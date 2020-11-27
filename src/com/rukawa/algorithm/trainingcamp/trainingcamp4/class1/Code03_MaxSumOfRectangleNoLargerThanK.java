package com.rukawa.algorithm.trainingcamp.trainingcamp4.class1;

import java.util.TreeSet;

/**
 * Created with Intellij IDEA
 *
 * @Author：SuperHai
 * @Date：2020-10-10 7:31
 * @Version：1.0
 */
public class Code03_MaxSumOfRectangleNoLargerThanK {
    /**
     * 给定一个二维数组matrix，在给定一个k值
     * 返回累加和小于等于k，但是离k最近的子矩阵的累加和
     */
    public static int maxSumSubMatrix(int[][] matrix, int k) {
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

    // arr -> m
    // m  logM
    public static int test(int[] arr, int K) {
        if (arr == null || arr.length < 1) {
            return 0;
        }
        TreeSet<Integer> sums = new TreeSet<>();
        sums.add(0);
        int sum = 0;
        int ans = Integer.MIN_VALUE;
        for (int i = 0; i < arr.length; i++) {
            sum += arr[i];
            Integer tmp = sums.ceiling(sum - K);
            if (tmp != null) {
                ans = Math.max(ans, sum - tmp);
            }
            sums.add(sum);
        }
        return ans;
    }
}
