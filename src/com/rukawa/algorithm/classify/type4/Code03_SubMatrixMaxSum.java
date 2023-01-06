package com.rukawa.algorithm.classify.type4;

/**
 * create by hqh on 2022/9/27
 */
public class Code03_SubMatrixMaxSum {
    /**
     * 返回一个二维矩阵中，子矩阵的最大累加和
     */
    // T(N)=O(N^2*M)
    public int maxSumSubMatrix(int[][] matrix) {
        if (matrix == null || matrix.length == 0 || matrix[0].length == 0) {
            return 0;
        }
        /**
         * 使用数组压缩技巧
         * 其他优化：看二维矩阵的行和列哪个短，选短的进行数据压缩
         */
        int n = matrix.length;
        int m = matrix[0].length;
        int maxSum = Integer.MIN_VALUE;
        int curSum = 0;
        for (int i = 0; i < n; i++) {
            // 包含i～j行的累加和
            int[] arr = new int[m];
            for (int j = i; j < n; j++) {
                curSum = 0;
                for (int k = 0; k < m; k++) {
                    arr[k] += matrix[j][k];
                    curSum += arr[k];
                    maxSum = Math.max(maxSum, curSum);
                    curSum = curSum < 0 ? 0 : curSum;
                }
            }
        }
        return maxSum;
    }

    // 上面代码拆分
    public int maxSumSubMatrix2(int[][] matrix) {
        if (matrix == null || matrix.length == 0 || matrix[0].length == 0) {
            return 0;
        }
        /**
         * 使用数组压缩技巧
         * 其他优化：看二维矩阵的行和列哪个短，选短的进行数据压缩
         */
        int n = matrix.length;
        int m = matrix[0].length;
        int maxSum = Integer.MIN_VALUE;
        for (int i = 0; i < n; i++) {
            // 包含i～j行的累加和
            int[] arr = new int[m];
            for (int j = i; j < n; j++) {
                for (int k = 0; k < m; k++) {
                    arr[k] += matrix[j][k];
                }
                maxSum = Math.max(maxSum, maxSumSubArray(arr));
            }
        }
        return maxSum;
    }

    public int maxSumSubArray(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        /**
         * 以每个位置结尾的时候，往左扩，什么时候累加和最大
         * 可能性1：不向左边扩，当前值就是最大累加和
         * 可能性2：往左扩，需要i-1位置的扩出来的最好
         */
        int max = Integer.MIN_VALUE;
        int curMaxSum = 0;
        for (int i = 0; i < nums.length; i++) {
            curMaxSum += nums[i];
            max = Math.max(max, curMaxSum);
            curMaxSum = curMaxSum < 0 ? 0 : curMaxSum;
        }
        return max;
    }

    // 给定一个正整数、负整数和 0 组成的 N × M 矩阵，编写代码找出元素总和最大的子矩阵。
    // 返回一个数组 [r1, c1, r2, c2]，其中 r1, c1 分别代表子矩阵左上角的行号和列号，r2, c2 分别代表右下角的行号和列号。
    // 若有多个满足条件的子矩阵，返回任意一个均可。
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
