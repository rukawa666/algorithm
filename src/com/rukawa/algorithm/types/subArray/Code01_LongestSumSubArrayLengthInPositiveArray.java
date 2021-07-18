package com.rukawa.algorithm.types.subArray;

/**
 * @className: Code01_LongestSumSubArrayLengthInPositiveArray
 * @description: TODO 类描述
 * @author: 鎏川疯
 * @date: 2021/7/15 0015 8:00
 **/
public class Code01_LongestSumSubArrayLengthInPositiveArray {
    /**
     * 给定一个正数数组组成的无序数组arr，给定一个正整数值K
     * 找到arr的所有子数组中，哪个子数组的累加和等于K，并且是长度最大的返回其长度
     *
     * 思路：使用单调栈
     * 窗口内的累加和如果小于目标值，往右扩，如果大于目标值，则往左扩
     * 实际上就是更新某一个位置开头的窗口累加和是多少？
     * [3 1 1 2 1 3 1 1 1 2 1]      k=6
     *  0 1 2 3 4 5 6 7 8 9 10
     */
    public static int maxLength(int[] arr, int K) {
        if (arr == null || arr.length == 0 || K <= 0) {
            return 0;
        }
        int max = 0;
        int sum = arr[0];
        int left = 0;
        int right = 0;
        while (right < arr.length) {
            if (sum == K) {
                max = Math.max(max, (right - left + 1));
                sum -= arr[left++];
            } else if (sum < K) {
                right++;
                if (right == arr.length) {
                    break;
                }
                sum += arr[right];
            } else {
                sum -= arr[left++];
            }
        }
        return max;
    }

    public static void main(String[] args) {
        int[] arr = {3, 1, 1, 2, 1, 3, 1, 1, 1, 2, 1};
        int target = 6;
        System.out.println(maxLength(arr, target));
    }
}
