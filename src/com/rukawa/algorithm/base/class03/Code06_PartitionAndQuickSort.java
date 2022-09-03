package com.rukawa.algorithm.base.class03;

import java.util.Arrays;

/**
 * Created with Intellij IDEA
 *
 * @Author：SuperHai
 * @Date：2020-07-13 22:23
 * @Version：1.0
 */
public class Code06_PartitionAndQuickSort {

    // 快排
    public static void quickSort01(int[] arr) {
        if (arr == null || arr.length < 2) {
            return;
        }
        process01(arr, 0, arr.length - 1);
    }

    // 随机快排
    // 在arr[L...R]上，以arr[R]位置的数做划分值
    // <=arr[R]放左边  >arr[R]放右边
    public static void process01(int[] arr, int L, int R) {
        if (L >= R) {
            return;
        }
        int M = partition(arr, L, R);
        process01(arr, L, M - 1);
        process01(arr, M + 1, R);
    }

    public static int partition(int[] arr, int L, int R) {
        /**
         * 设置一个<=区域，在-1位置
         * 1.当前数<=目标，当前数和(<=区域)的下一个数交换，<=区域向右扩，当前数跳下一个
         * 2.当前数>目标，当前数跳下一个
         */
        if (L > R) {
            return -1;
        }
        if (L == R) {
            return L;
        }

        int lessEqual = L - 1;  // <=区的第一个位置
        int index = L;
        while (index < R) {
            if (arr[index] <= arr[R]) {
                swap(arr, index, ++lessEqual);
            }
            index++;
        }
        swap(arr, ++lessEqual, R);
        return lessEqual;
    }

    public static void quickSort02(int[] arr) {
        if (arr == null || arr.length < 2) {
            return;
        }
        process02(arr, 0, arr.length - 1);
    }

    public static void process02(int[] arr, int L, int R) {
        if (L >= R) {
            return;
        }
        int[] equalArea = netherlandsFlag(arr, L, R);
        process02(arr, L, equalArea[0] - 1);    // <区的最后一个数
        process02(arr, equalArea[1] + 1, R);    // >区的第一个数
    }

    // 在arr[L ... R]范围玩荷兰国旗问题的划分，以arr[R]为划分值
    // <arr[R]  ==arr[R]  >arr[R]
    public static int[] netherlandsFlag(int[] arr, int L, int R) {
        if (L > R) {
            return new int[]{-1, -1};
        }
        if (L == R) {
            return new int[]{L, R};
        }

        int less = L - 1;   // <区右边界
        int more = R;   // >区左边界
        int index = L;
        while (index < more) {
            if (arr[index] == arr[R]) {
                index++;
            } else if (arr[index] < arr[R]) {
                swap(arr, index++, ++less);
                // swap(arr, index, less + 1)
                // less++
                // index++
            } else {
                swap(arr, index, --more);
                // swap(arr, index, more - 1)
                // more--
            }
        }
        // 最后一个数是属于等于区域的，要和大于区域的第一个数做交换，完成整个区域的划分
        swap(arr, more, R);
        return new int[] {less + 1, more};
    }

    public static void swap(int[] arr, int i, int j) {
        int tmp = arr[i];
        arr[i] = arr[j];
        arr[j] = tmp;
    }

    public static void quickSort03(int[] arr) {
        if (arr == null || arr.length < 2) {
            return;
        }
        process03(arr, 0, arr.length - 1);
    }

    public static void process03(int[] arr, int L, int R) {
        if (L >= R) {
            return;
        }
        swap(arr, L + (int) (Math.random() * (R - L + 1)), R);
        int[] equalArea = netherlandsFlag(arr, L, R);
        process03(arr, L, equalArea[0] - 1);    // <区的最后一个数
        process03(arr, equalArea[1] + 1, R);    // >区的第一个数
    }

    // for test
    public static int[] generateRandomArray(int maxSize, int maxValue) {
        // Math.random() [0, 1)
        // Math.random() * N [0, N)
        // (int) (Math.random() * N) [0, N - 1]
        int[] arr = new int[(int) ((maxSize + 1) * Math.random())];
        for (int i = 0; i < arr.length; i++) {
            // [-?, +?]，如果不做加法，[0, +?]
            arr[i] = (int) (((maxValue + 1) * Math.random()) - (int) (maxValue * Math.random()));
        }
        return arr;
    }

    // for test
    public static void comparator(int[] arr) {
        Arrays.sort(arr);
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
        if ((arr1 != null && arr2 == null) || (arr1 == null && arr2 != null)) {
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

    public static void main(String[] args) {
        int testTime = 500000;
        int maxSize = 100;
        int maxValue = 100;
        boolean succeed = true;
        for (int i = 0; i < testTime; i++) {
            int[] arr1 = generateRandomArray(maxSize, maxValue);
            int[] arr2 = copyArray(arr1);
            int[] arr3 = copyArray(arr1);
            quickSort01(arr1);
            quickSort02(arr2);
            quickSort03(arr3);

            if (!isEqual(arr1, arr2) || !isEqual(arr2, arr3)) {
                succeed = false;
                printArray(arr1);
                printArray(arr2);
                printArray(arr3);
                break;
            }
        }

        System.out.println(succeed ? "Nice!" : "Fucking fucked!");
    }
}
