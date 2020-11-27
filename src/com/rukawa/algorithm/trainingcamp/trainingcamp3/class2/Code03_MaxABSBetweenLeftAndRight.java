package com.rukawa.algorithm.trainingcamp.trainingcamp3.class2;

/**
 * Created with Intellij IDEA
 *
 * @Author：SuperHai
 * @Date：2020-08-21 8:12
 * @Version：1.0
 */
public class Code03_MaxABSBetweenLeftAndRight {

    /**
     * 给定一个数组arr长度为N，你可以把任意长度大于0且小于N的前缀作为左部分，剩下的作为右部分
     * 但是每种划分下都有左部分的最大值和右部分的最大值，请返回最大的，左部分最大值减去右部分最大值
     * 的绝对值
     */

    public static int maxABS1(int[] arr) {
        int res = Integer.MIN_VALUE;
        int maxLeft = 0;
        int maxRight = 0;
        for (int i = 0; i != arr.length - 1; i++) {
            maxLeft = Integer.MIN_VALUE;
            for (int j = 0; j != i + 1; j++) {
                maxLeft = Math.max(arr[j], maxLeft);
            }
            maxRight = Integer.MIN_VALUE;
            for (int j = i + 1; j != arr.length; j++) {
                maxRight = Math.max(arr[j], maxRight);
            }
            res = Math.max(Math.abs(maxLeft - maxRight), res);
        }
        return res;
    }


    public static int maxABS2(int[] arr) {
        if (arr == null || arr.length < 2) {
            return 0;
        }
        int N = arr.length;
        int[] leftMax = new int[N];
        int[] rightMax = new int[N];
        leftMax[0] = arr[0];
        rightMax[N - 1] = arr[N -1];
        for (int i = 1; i < N; i++) {
            leftMax[i] = Math.max(arr[i - 1], arr[i]);
        }

        for (int i = N - 2; i >= 0; i--) {
            rightMax[i] = Math.max(arr[i], arr[i + 1]);
        }

        int max = 0;
        for (int i = 0; i < N; i++) {
            max = Math.max(max, Math.abs(leftMax[i] - rightMax[i + 1]));
        }
        return max;
    }

    public static int maxABS3(int[] arr) {
        int max = Integer.MIN_VALUE;
        for (int i = 0; i < arr.length; i++) {
            max = Math.max(max, arr[i]);
        }

        return max - Math.min(arr[0], arr[arr.length - 1]);
    }
}
