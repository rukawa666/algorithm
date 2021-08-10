package com.rukawa.algorithm.types.advanced;

/**
 * @name: Code_D2_SubArrayMaxSum
 * @description: 描述类的用途
 * @date: 2021/8/10 11:48
 * @auther: hanqinghai
 */
public class Code_D2_SubArrayMaxSum {
    /**
     * 返回一个子数组中，子数组最大的类加和
     */
    public static int maxSubArray(int[] arr) {
        if (arr == null || arr.length == 0) {
            return 0;
        }
        int pre = arr[0];
        int max = arr[0];
        for (int i = 1; i < arr.length; i++) {
            pre =  Math.max(arr[i], arr[i] + pre);
            max = Math.max(pre, max);
        }
        return max;
    }

}
