package com.rukawa.algorithm.company.kuaishou;

/**
 * @className: Code01_MaxSumLengthNoMore
 * @description: TODO 类描述
 * @author: 鎏川疯
 * @date: 2021/7/20 0020 16:21
 **/
public class Code01_MaxSumLengthNoMore {
    /**
     * 给定一个数组arr，和一个正数M
     * 返回子数组长度不大于M的情况下，最大的子数组累加和
     */

    public static int maxSum(int[] arr, int M) {
        if (arr == null || arr.length == 0 || M < 1) {
            return 0;
        }
        int N = arr.length;
        int max = Integer.MIN_VALUE;
        for (int L = 0; L < N; L++) {
            int sum = 0;
            for (int R = L; R < N; R++) {
                if (R - L + 1 > M) {
                    break;
                }
                sum += arr[R];
                max = Math.max(max, sum);
            }
        }
        return max;
    }
}
