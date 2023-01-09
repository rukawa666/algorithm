package com.rukawa.algorithm.base.class21_bfprt;

/**
 * create by hqh on 2022/9/18
 */
public class Code02_FindMinKth {
    /**
     * 在一个无序数组中找到第K小的数
     */

    // 方法2的递归改迭代
    public static int minKth1(int[] array, int k) {
        int l = 0;
        int r = array.length - 1;
        int pivot = 0;
        int[] range = null;
        while (l < r) {
            pivot = array[l + (int) (Math.random() * (r - l + 1))];
            range = partition(array, l, r, pivot);
            if (k < range[0]) {
                r = range[0] - 1;
            } else if (k > range[1]) {
                l = range[1] + 1;
            } else {
                return pivot;
            }
        }
        return array[l];
    }

    // 改写快排，时间复杂度O(N)
    public static int minKth2(int[] array, int k) {
        int[] arr = copyArray(array);
        return process2(arr, 0, arr.length - 1, k - 1);
    }

    public static int[] copyArray(int[] arr) {
        int[] res = new int[arr.length];
        for (int i = 0; i < res.length; i++) {
            res[i] = arr[i];
        }
        return res;
    }

    // arr数组中第k小的数
    // arr[l...r]数组上如果排好序(不会真的排序)，找到位于第index的数
    public static int process2(int[] arr, int l, int r, int index) {
        if (l == r) {
            return arr[l];
        }
        int pivot = arr[l + (int) (Math.random() * (r - l + 1))];
        int[] range = partition(arr, l, r, pivot);
        if (index >= range[0] && index <= range[1]) {
            return arr[index];
        } else if (index < range[0]) {
            return process2(arr, l, range[0] - 1, index);
        } else {
            return process2(arr, range[1] + 1, r, index);
        }

    }

    public static int[] partition(int[] arr, int l, int r, int pivot) {
        int less = l - 1;
        int more = r + 1;
        int index = l;
        while (index < more) {
            if (arr[index] == pivot) {
                index++;
            } else if (arr[index] < pivot) {
                swap(arr, index++, ++less);
            } else {
                swap(arr, index, --more);
            }
        }
        return new int[]{less + 1, more - 1};
    }

    // 异或自己和自己^，会失效，得到0
    public static void swap(int[] arr, int i, int j) {
        int tmp = arr[i];
        arr[i] = arr[j];
        arr[j] = tmp;
    }

    // 利用bfprt算法，时间复杂度O(N)
    public static int minKth3(int[] array, int k) {
        int[] arr = copyArray(array);
        return bfprt(arr, 0, arr.length - 1, k - 1);
    }

    public static int bfprt(int[] arr, int l, int r, int index) {
        if (l == r) {
            return arr[l];
        }

        int pivot = medianOfMedians(arr, l, r);
        int[] range = partition(arr, l, r, pivot);
        if (index >= range[0] && index <= range[1]) {
            return arr[index];
        } else if (index < range[0]) {
            return bfprt(arr, l, range[0] - 1, index);
        } else {
            return bfprt(arr, range[1] + 1, r, index);
        }
    }

    // arr[l...r]范围上，5个数分为一组，每个小组排好序，然后取出每个小组的中位数形成一个新的数组，然后求出这个新数组的中位数
    public static int medianOfMedians(int[] arr, int l, int r) {
        int size = r - l + 1;
        int offset = size % 5 == 0 ? 0 : 1;
        int[] mArr = new int[size / 5 + offset];
        for (int team = 0; team < mArr.length; team++) {
            int teamFirst = l + team * 5;
            mArr[team] = getMedian(arr, teamFirst, Math.min(r, teamFirst + 4));
        }
        return bfprt(mArr, 0, mArr.length - 1, mArr.length >> 1);
    }

    public static int getMedian(int[] arr, int l, int r) {
        insertionSort(arr, l, r);
        return arr[(r - l) >> 1];
    }

    public static void insertionSort(int[] arr, int l, int r) {
        for (int i = l + 1; i <= r; i++) {
            for (int j = i - 1; j >= l && arr[j] > arr[j + 1]; j--) {
                swap(arr, j, j + 1);
            }
        }
    }

    public static void main(String[] args) {
        int[] arr = {3,1,2,6,4,8};
        System.out.println(minKth2(arr, 3));
    }
}
