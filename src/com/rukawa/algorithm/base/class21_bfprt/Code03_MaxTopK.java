package com.rukawa.algorithm.base.class21_bfprt;

import java.util.Arrays;

/**
 * create by hqh on 2022/9/18
 */
public class Code03_MaxTopK {
    /**
     * 给定一个无序数组中，长度为N，给定一个正数k，返回top k个最大的数
     */

    // 时间复杂度O(n*logN)
    // 排序+收集
    public static int[] maxTopK1(int[] arr, int k) {
        if (arr == null || arr.length == 0) {
            return null;
        }
        int n = arr.length;
        k = Math.min(n, k);
        Arrays.sort(arr);
        int[] res = new int[k];
        for (int i = n - 1, j = 0; j < k; i--,j++) {
            res[j] = arr[i];
        }
        return res;
    }

    // 时间复杂度O(n + k*logN)
    // 堆
    public static int[] maxTopK2(int[] arr, int k) {
        if (arr == null || arr.length == 0) {
            return null;
        }
        int n = arr.length;
        k = Math.min(n, k);
        for (int i = n - 1; i >= 0; i--) {
            heapify(arr, i, n);
        }
        int heapSize = n;
        swap(arr, 0, --heapSize);
        int count = 1;
        while (heapSize > 0 && count < k) {
            heapify(arr, 0, heapSize);
            swap(arr, 0, --heapSize);
            count++;
        }
        int[] res = new int[k];
        for (int i = n - 1, j = 0; j < k; i--, j++) {
            res[j] = arr[i];
        }
        return res;
    }

    public static void heapInsert(int[] arr, int index) {
        while (arr[index] > arr[(index - 1) >> 1]) {
            swap(arr, index, (index - 1) >> 1);
            index = (index - 1) >> 1;
        }
    }

    public static void heapify(int[] arr, int index, int heapSize) {
        int left = (index << 1) + 1;
        while (left < heapSize) {
            int best = left + 1 < heapSize && arr[left + 1] > arr[left] ? left + 1 : left;
            best = arr[best] > arr[index] ? best : index;
            if (best == index) {
                break;
            }
            swap(arr, best, index);
            index = best;
            left = (index << 1) + 1;
        }
    }

    public static void swap(int[] arr, int i, int j) {
        int tmp = arr[i];
        arr[i] = arr[j];
        arr[j] = tmp;
    }

    // 时间复杂度O(n + k*logK)
    public static int[] maxTopK3(int[] arr, int k) {
        if (arr == null || arr.length == 0) {
            return null;
        }
        int n = arr.length;
        k = Math.min(n, k);

        int num = minKth(arr, n - k);
        int[] res = new int[k];
        int index = 0;
        for (int i = 0; i < n; i++) {
            if (arr[i] > num) {
                res[index++] = arr[i];
            }
        }
        for (; index < k; index++) {
            res[index] = num;
        }
        // O(k*logK)
        Arrays.sort(res);
        for (int i = 0, j = k - 1; i < j; i++, j--) {
            swap(res, i, j);
        }
        return res;
    }

    // 时间复杂度O(N)
    public static int minKth(int[] arr, int index) {
        int l = 0;
        int r = arr.length - 1;
        int[] range = null;
        int pivot = 0;
        while (l < r) {
            pivot = arr[l + (int) (Math.random() * (r - l + 1))];
            range = partition(arr, l, r, pivot);
            if (index < range[0]) {
                r = range[0] - 1;
            } else if (index > range[1]) {
                l = range[1] + 1;
            } else {
                return pivot;
            }
        }
        return arr[l];
    }

    public static int[] partition(int[] arr, int l, int r, int pivot) {
        int less = l - 1;
        int more = r + 1;
        int cur = l;
        while (cur < more) {
            if (arr[cur] < pivot) {
                swap(arr, cur++, ++less);
            } else if (arr[cur] > pivot) {
                swap(arr, cur, --more);
            } else {
                cur++;
            }
        }
        return new int[]{less + 1, more - 1};
    }

    // for test
    public static int[] generateRandomArray(int maxSize, int maxValue) {
        int[] arr = new int[(int) ((maxSize + 1) * Math.random())];
        for (int i = 0; i < arr.length; i++) {
            // [-? , +?]
            arr[i] = (int) ((maxValue + 1) * Math.random()) - (int) (maxValue * Math.random());
        }
        return arr;
    }

    // for test
    public static int[] copyArray(int[] arr) {
        if (arr == null) {
            return null;
        }
        int[] res = new int[arr.length];
        for (int i = 0; i < arr.length; i++) {
            res[i] = arr[i];
        }
        return res;
    }

    // for test
    public static boolean isEqual(int[] arr1, int[] arr2) {
        if ((arr1 == null && arr2 != null) || (arr1 != null && arr2 == null)) {
            return false;
        }
        if (arr1 == null && arr2 == null) {
            return true;
        }
        if (arr1.length != arr2.length) {
            return false;
        }
        for (int i = 0; i < arr1.length; i++) {
            if (arr1[i] != arr2[i]) {
                return false;
            }
        }
        return true;
    }

    // for test
    public static void printArray(int[] arr) {
        if (arr == null) {
            return;
        }
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i] + " ");
        }
        System.out.println();
    }

    // 生成随机数组测试
    public static void main(String[] args) {
        int testTime = 500000;
        int maxSize = 100;
        int maxValue = 100;
        boolean pass = true;
        System.out.println("测试开始，没有打印出错信息说明测试通过");
        for (int i = 0; i < testTime; i++) {
            int k = (int) (Math.random() * maxSize) + 1;
            int[] arr = generateRandomArray(maxSize, maxValue);

            int[] arr1 = copyArray(arr);
            int[] arr2 = copyArray(arr);
            int[] arr3 = copyArray(arr);

            int[] ans1 = maxTopK1(arr1, k);
            int[] ans2 = maxTopK2(arr2, k);
            int[] ans3 = maxTopK3(arr3, k);
            if (!isEqual(ans1, ans2) || !isEqual(ans1, ans3)) {
                pass = false;
                System.out.println("出错了！");
                printArray(ans1);
                printArray(ans2);
                printArray(ans3);
                break;
            }
        }
        System.out.println("测试结束了，测试了" + testTime + "组，是否所有测试用例都通过？" + (pass ? "是" : "否"));
    }

}
