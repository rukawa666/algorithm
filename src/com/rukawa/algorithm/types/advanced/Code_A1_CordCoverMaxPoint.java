package com.rukawa.algorithm.types.advanced;

import java.util.Arrays;

/**
 * @className: Code_A1_CordCoverMaxPoint
 * @description: TODO 类描述
 * @author: 鎏川疯
 * @date: 2021/7/31 0031 19:29
 **/
public class Code_A1_CordCoverMaxPoint {
    /**
     * 给定一个有序数组arr，代表坐落在X轴上的点
     * 给定一个正数K，代表绳子的长度
     * 返回绳子最多压中几个点？
     * 即使绳子边缘处盖住点也算盖住
     */
    // 二分  O(N*logN)
    public static int maxPoint1(int[] arr, int K) {
        int ans = 1;
        for (int i = 0; i < arr.length; i++) {
            int nearest = nearestIndex(arr, i, arr[i] - K);
            ans = Math.max(ans, i - nearest + 1);
        }
        return ans;
    }

    public static int nearestIndex(int[] arr, int R, int value) {
        int L = 0;
        int index = R;
        while (L <= R) {
            int mid = L + ((R - L) >> 1);
            if (arr[mid] >= value) {
                index = mid;
                R = mid - 1;
            } else {
                L = mid + 1;
            }
        }
        return index;
    }

    // 滑动窗口 O(N)
    public static int maxPoint2(int[] arr, int K) {
        int left = 0;
        int right = 0;
        int N = arr.length;
        int ans = 0;
        while (left < N) {
            while (right < N && arr[right] - arr[left] <= K) {
                right++;
            }
            ans = Math.max(ans, right - (left++));
        }
        return ans;
    }

    // for test
    public static int test(int[] arr, int L) {
        int max = 0;
        for (int i = 0; i < arr.length; i++) {
            int pre = i - 1;
            while (pre >= 0 && arr[i] - arr[pre] <= L) {
                pre--;
            }
            max = Math.max(max, i - pre);
        }
        return max;
    }

    // for test
    public static int[] generateArray(int len, int max) {
        int[] ans = new int[(int) (Math.random() * len) + 1];
        for (int i = 0; i < ans.length; i++) {
            ans[i] = (int) (Math.random() * max);
        }
        Arrays.sort(ans);
        return ans;
    }

    public static void main(String[] args) {
        int len = 100;
        int max = 1000;
        int testTime = 100000;
        System.out.println("测试开始");
        for (int i = 0; i < testTime; i++) {
            int L = (int) (Math.random() * max);
            int[] arr = generateArray(len, max);
            int ans1 = maxPoint1(arr, L);
            int ans2 = maxPoint2(arr, L);
            int ans3 = test(arr, L);
            if (ans1 != ans2 || ans2 != ans3) {
                System.out.println("oops!");
                break;
            }
        }

    }
}
