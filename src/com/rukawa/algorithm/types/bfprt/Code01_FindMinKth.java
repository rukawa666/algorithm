package com.rukawa.algorithm.types.bfprt;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @Author: Administrator
 * @Date: 2021/6/9 0009 8:32
 */
public class Code01_FindMinKth {

    /**
     * 改写快排找第K小的数，迭代方式
     * 时间复杂度O(N)
     */
    public static int minKth(int[] array, int k) {
        int L = 0;
        int R = array.length - 1;
        int pivot = 0;
        int[] range = null;
        while (L < R) {
            pivot = array[L + (int) (Math.random() * (R - L + 1))];
            range = partition(array, L, R, pivot);
            if (k < range[0]) {
                R = range[0] - 1;
            } else if (k > range[1]) {
                L = range[1] + 1;
            } else {
                return pivot;
            }
        }
        return array[L];
    }

    /**
     * 改写快排找第K小的数，递归方式
     */
    public static int minKth2(int[] array, int k) {
        int[] ans = copyArray(array);
        return process2(ans, 0, ans.length - 1, k - 1);
    }

    public static int[] copyArray(int[] array) {
        int[] ans = new int[array.length];
        for (int i = 0; i < ans.length; i++) {
            ans[i] = array[i];
        }
        return ans;
    }

    /**
     * 在arr[L...R]范围上，如果排序的话，找到index的数
     * @param arr
     * @param L
     * @param R
     * @param index
     * @return
     */
    public static int process2(int[] arr, int L, int R, int index) {
        if (L == R) {
            return arr[L];
        }
        int pivot = arr[L + (int) (Math.random() * (R - L + 1))];
        int[] range = partition(arr, L, R, pivot);
        if (index >= range[0] && index <= range[1]) {
            return arr[index];
        } else if (index < range[0]) {
            return process2(arr, L, range[0] - 1, index);
        } else {
            return process2(arr, range[1] + 1, R, index);
        }
    }

    public static int[] partition(int[] arr, int L, int R, int pivot) {
        int less = L - 1;
        int more = R + 1;
        int index = L;
        while (index < more) {
            if (arr[index] < arr[pivot]) {
                swap(arr, index++, ++ less);
            } else if (arr[index] > arr[pivot]) {
                swap(arr, index, --more);
            } else {
                index++;
            }
        }
        return new int[]{less + 1, more - 1};

    }

    public static void swap(int[] arr, int x, int y) {
        int t = arr[x];
        arr[x] = arr[y];
        arr[y] = t;
    }

}
