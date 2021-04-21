package com.rukawa.algorithm.types.window;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @Author: Administrator
 * @Date: 2021/4/19 0019 23:19
 */
public class Code02_AllLessNumSubArray {
    /**
     * 给定一个整形数组arr，和一个整数num
     * 某个arr中的子数组sub，如果想达标，必须满足：
     * sub中的最大值-sub中的最小值 <= num,
     * 返回arr中达标子数组的数量
     */

    // 暴力方法
    // O(N^3)
    public static int right(int[] arr, int sum) {
        if (arr == null || arr.length == 0 || sum < 0) {
            return 0;
        }
        int N = arr.length;
        int count = 0;
        for (int L = 0; L < N; L++) {
            for (int R = L; R < N; R++) {
                int max = arr[L];
                int min = arr[L];
                for (int i = L + 1; i <= R; i++) {
                    max = Math.max(max, arr[i]);
                    min = Math.min(min, arr[i]);
                }
                if (max - min <= sum) {
                    count++;
                }
            }
        }
        return count;
    }
}
