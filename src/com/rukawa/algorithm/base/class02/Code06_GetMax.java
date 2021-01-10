package com.rukawa.algorithm.base.class02;

/**
 * Created with Intellij IDEA
 *
 * @Author：SuperHai
 * @Date：2020-07-12 10:49
 * @Version：1.0
 */
public class Code06_GetMax {

    /**
     * 求arr中的最大值，递归实现
     */

    public static int getMax(int[] arr) {
        return process(arr, 0, arr.length - 1);
    }

    // arr[L...R] 范围上求最大值 L...R N
    public static int process(int[] arr, int L, int R) {
        if (L == R) {
            return arr[L];
        }
        int mid = L + ((R - L) >> 1);
        int leftMax = process(arr, L, mid);
        int rightMax = process(arr, mid + 1, R);
        return Math.max(leftMax, rightMax);
    }

    public static void main(String[] args) {
        int[] arr = {2,3,1,9,4,6,5,8};
        System.out.println(getMax(arr));
    }
}
