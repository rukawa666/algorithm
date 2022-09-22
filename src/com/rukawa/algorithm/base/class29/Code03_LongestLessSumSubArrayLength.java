package com.rukawa.algorithm.base.class29;

/**
 * create by hqh on 2022/9/19
 */
public class Code03_LongestLessSumSubArrayLength {
    /**
     * 给定一个整数组成的无序数组arr，值可能是正、可能负、可能零
     * 给定一个整数值K
     * 找到arr的所有子数组中，哪个子数组的累加和小于等于K，并且长度是最大的
     * 返回其长度
     */

    /**
     * 生成两个辅助数组：
     *   minSum：子数组必须以i开头，累加和取的最小的累加和，累加和的值就是数组的值
     *   minSumEnd：minSum的右边界在哪
     *   arr从后往前填写
     *         arr:  1  2 -4  3  7 -2  4 -3
     *    position:  0  1  2  3  4  5  6  7
     *      minSum: -1 -2 -4  3  5 -2  1 -3
     *   minSumEnd:  2  2  2  3  5  5  7  7
     */
    // T(N) = O(N)
    public static int maxLengthAwesome(int[] arr, int k) {
        if (arr == null || arr.length == 0) {
            return 0;
        }
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

    // T(N) = O(N*logN)
    public static int maxLength(int[] arr, int k) {
        if (arr == null || arr.length == 0) {
            return 0;
        }
        int[] h = new int[arr.length + 1];
        int sum = 0;
        h[0] = sum;
        for (int i = 0; i < arr.length; i++) {
            sum += arr[i];
            h[i + 1] = Math.max(sum, h[i]);
        }
        sum = 0;
        int res = 0;
        int pre = 0;
        int maxLen = 0;
        for (int i = 0; i < arr.length; i++) {
            sum += arr[i];
            pre = getLessIndex(h, sum - k);
            maxLen = pre == -1 ? 0 : i - pre + 1;
            res = Math.max(res, maxLen);
        }
        return res;
    }

    public static int getLessIndex(int[] arr, int num) {
        int low = 0;
        int high = arr.length - 1;
        int mid = 0;
        int res = -1;
        while (low <= high) {
            mid = (low + high) / 2;
            if (arr[mid] >= num) {
                res = mid;
                high = mid - 1;
            } else {
                low = mid + 1;
            }
        }
        return res;
    }

    // for test
    public static int[] generateRandomArray(int len, int maxValue) {
        int[] res = new int[len];
        for (int i = 0; i != res.length; i++) {
            res[i] = (int) (Math.random() * maxValue) - (maxValue / 3);
        }
        return res;
    }

    public static void main(String[] args) {
        System.out.println("test begin");
        for (int i = 0; i < 10000000; i++) {
            int[] arr = generateRandomArray(10, 20);
            int k = (int) (Math.random() * 20) - 5;
            if (maxLengthAwesome(arr, k) != maxLength(arr, k)) {
                System.out.println("Oops!");
            }
        }
        System.out.println("test finish");
    }
}
