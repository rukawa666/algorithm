package com.rukawa.algorithm.base.class29;

import java.util.TreeMap;

/**
 * create by hqh on 2022/9/19
 */
public class Code04_AvgLessEqualValueLongestSubarray {
    /**
     * 给定一个数组arr，给定一个值v
     * 求子数组平均值小于等于v的最长子数组长度
     */

    // 暴力解，T(N) = O(N^3)
    public static int ways1(int[] arr, int v) {
        if (arr == null || arr.length == 0) {
            return 0;
        }
        int res = 0;
        for (int l = 0; l < arr.length; l++) {
            for (int r = l; r < arr.length; r++) {
                int sum = 0;
                int k = r - l + 1;
                for (int i = l; i <= r; i++) {
                    sum += arr[i];
                }
                double avg = (double) sum / (double) k;
                if (avg <= v) {
                    res = Math.max(res, k);
                }
            }
        }
        return res;
    }

    // T(N) = O(N*logN)
    public static int ways2(int[] arr, int v) {
        if (arr == null || arr.length == 0) {
            return 0;
        }
        TreeMap<Integer, Integer> origins = new TreeMap<>();
        int ans = 0;
        int modify = 0;
        for (int i = 0; i < arr.length; i++) {
            int p1 = arr[i] <= v ? 1 : 0;
            int p2 = 0;
            int querry = -arr[i] - modify;
            if (origins.floorKey(querry) != null) {
                p2 = i - origins.get(origins.floorKey(querry)) + 1;
            }
            ans = Math.max(ans, Math.max(p1, p2));
            int curOrigin = -modify - v;
            if (origins.floorKey(curOrigin) == null) {
                origins.put(curOrigin, i);
            }
            modify += arr[i] - v;
        }
        return ans;
    }

    // 流程：给数组每个值都减去v，然后求累加和<=0的最长子数组长度
    public static int ways3(int[] arr, int v) {
        if (arr == null || arr.length == 0) {
            return 0;
        }
        for (int i = 0; i < arr.length; i++) {
            arr[i] -= v;
        }
        return maxLengthAwesome(arr, 0);
    }

    public static int maxLengthAwesome(int[] arr, int k) {
        int n = arr.length;
        int[] minSum = new int[n];
        int[] minSumEnd = new int[n];
        minSum[n - 1] = arr[n - 1];
        minSumEnd[n - 1] = n - 1;
        for (int i = n - 2; i >= 0; i--) {
            if (minSum[i + 1] < 0) {
                minSum[i] = arr[i] + minSum[i + 1];
                minSumEnd[i] = minSumEnd[i + 1];
            } else {
                minSum[i] = arr[i];
                minSumEnd[i] = i;
            }
        }

        // 窗口扩不进来的开头
        int end = 0;
        // 窗口累加和
        int sum = 0;
        // 最终结果
        int maxLen = 0;
        for (int i = 0; i < n; i++) {

            while (end < n && sum + minSum[end] <= k) {
                sum += minSum[end];
                end = minSumEnd[end] + 1;
            }
            // while循环结束后
            // 1. 如果以i开头的情况下，累加和<=k的最长子数组是arr[i...end-1]，看看能不能更新结果
            // 2. 如果以i开头的情况下，累加和<=k的最长子数组比arr[i...end-1]短，更新还是不更新都不影响结果
            maxLen = Math.max(maxLen, end - i);

            // 窗口内还有位置可以扩，哪怕窗口内没有数字
            if (i < end) {
                sum -= arr[i];
            } else { // i == end，即将i++，i > end,此时窗口概念维持不住了，所以end跟着i一起走
                end = i + 1;
            }
        }
        return maxLen;
    }

    // 用于测试
    public static int[] randomArray(int maxLen, int maxValue) {
        int len = (int) (Math.random() * maxLen) + 1;
        int[] ans = new int[len];
        for (int i = 0; i < len; i++) {
            ans[i] = (int) (Math.random() * maxValue);
        }
        return ans;
    }

    // 用于测试
    public static int[] copyArray(int[] arr) {
        int[] ans = new int[arr.length];
        for (int i = 0; i < arr.length; i++) {
            ans[i] = arr[i];
        }
        return ans;
    }

    // 用于测试
    public static void printArray(int[] arr) {
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i] + " ");
        }
        System.out.println();
    }

    // 用于测试
    public static void main(String[] args) {
        System.out.println("测试开始");
        int maxLen = 20;
        int maxValue = 100;
        int testTime = 500000;
        for (int i = 0; i < testTime; i++) {
            int[] arr = randomArray(maxLen, maxValue);
            int value = (int) (Math.random() * maxValue);
            int[] arr1 = copyArray(arr);
            int[] arr2 = copyArray(arr);
            int[] arr3 = copyArray(arr);
            int ans1 = ways1(arr1, value);
            int ans2 = ways2(arr2, value);
            int ans3 = ways3(arr3, value);
            if (ans1 != ans2 || ans1 != ans3) {
                System.out.println("测试出错！");
                System.out.print("测试数组：");
                printArray(arr);
                System.out.println("子数组平均值不小于 ：" + value);
                System.out.println("方法1得到的最大长度：" + ans1);
                System.out.println("方法2得到的最大长度：" + ans2);
                System.out.println("方法3得到的最大长度：" + ans3);
                System.out.println("=========================");
            }
        }
        System.out.println("测试结束");
    }
}
