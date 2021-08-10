package com.rukawa.algorithm.types.advanced;

/**
 * @name: Code_D3_SubMatrixMaxSum
 * @description: 描述类的用途
 * @date: 2021/8/10 13:47
 * @auther: hanqinghai
 */
public class Code_D3_SubMatrixMaxSum {
    /**
     * 二维数组中，子矩阵的最大累加和
     */
    /**
     * 思路：矩阵从第0最大累加和是多少，矩阵必须包含(0,1)两行的情况，最大累加和是多少，矩阵必须包含(0,1,2)三行的情况最大累加和是多少。。。
     * 然后(1,1),(1,2),(1,3),...(1,n-1)行的各自累加和求出来
     * ...
     * 答案一定在其中
     */

    public static int maxSum(int[][] m) {
        if (m == null || m.length == 0 || m[0].length == 0) {
            return 0;
        }
        // O(N^2 * M)
        int N = m.length;
        int M = m[0].length;
        int max = Integer.MIN_VALUE;
        int cur = 0;
        for (int i = 0; i < N; i++) {
            // (i,j) 包含i行到j行，答案是什么
            int[] s = new int[M];
            for (int j = i; j < N; j++) {
                cur = 0;
                for (int k = 0; k < M; k++) {
                    s[k] += m[j][k];
                    cur += s[k];
                    max = Math.max(max, cur);
                    cur = cur < 0 ? 0 : cur;
                }
            }
        }
        return max;
    }
}
