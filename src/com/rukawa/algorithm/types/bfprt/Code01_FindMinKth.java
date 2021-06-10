package com.rukawa.algorithm.types.bfprt;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @Author: Administrator
 * @Date: 2021/6/9 0009 8:32
 */
public class Code01_FindMinKth {

    // 利用bfprt算法，时间复杂度O(N)
    public static int minKth3(int[] array, int k) {
        int[] arr = copyArray(array);
        return bfprt(arr, 0, arr.length - 1, k - 1);
    }

    public static int bfprt(int[] arr, int L, int R, int index) {
        if (L == R) {
            return arr[L];
        }
        int pivot = medianOfMedians(arr, L , R);
        int[] range = partition(arr, L, R, pivot);
        if (index >= range[0] && index <= range[1]) {
            return arr[index];
        } else if (index < range[0]) {
            return bfprt(arr, L, range[0] - 1, index);
        } else {
            return bfprt(arr, range[1] + 1, R, index);
        }
    }


    public static int medianOfMedians(int[] arr, int L, int R) {
        int size = R - L + 1;
        int offset = size % 5 == 0 ? 0 : 1;
        int[] mArr = new int[size / 5 + offset];
        for (int team = 0; team < mArr.length; team++) {
            int teamFirst = L + team * 5;
            // L + 0 ... L + 4
            // L + 5 ... L + 9
            // L + 10 ... L + 14
            mArr[team] = getMedian(arr, teamFirst, Math.min(R, teamFirst + 4));
        }
        return bfprt(mArr, 0, mArr.length - 1, mArr.length / 2);
    }

    public static int getMedian(int[] arr, int L, int R) {
        insertion(arr, L, R);
        return arr[(L + R) / 2];
    }

    public static void insertion(int[] arr, int L, int R) {
        for (int i = L + 1; i <= R; i++) {
            for (int j = i - 1; j >= L && arr[j] > arr[j + 1]; j--) {
                swap(arr, j, j + 1);
            }
        }
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
            if (arr[index] < pivot) {
                swap(arr, index++, ++ less);
            } else if (arr[index] > pivot) {
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
    // for test
    public static int[] generateRandomArray(int maxSize, int maxValue) {
        int[] arr = new int[(int) (Math.random() * maxSize) + 1];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (int) (Math.random() * (maxValue + 1));
        }
        return arr;
    }

    public static void main(String[] args) {
        int testTime = 1000000;
        int maxSize = 100;
        int maxValue = 100;
        System.out.println("test begin");
        for (int i = 0; i < testTime; i++) {
            int[] arr = generateRandomArray(maxSize, maxValue);
            int k = (int) (Math.random() * arr.length) + 1;
            int ans2 = minKth2(arr, k);
            int ans3 = minKth3(arr, k);
            if (ans2 != ans3) {
                System.out.println("Oops!");
            }
        }
        System.out.println("test finish");
    }

}
