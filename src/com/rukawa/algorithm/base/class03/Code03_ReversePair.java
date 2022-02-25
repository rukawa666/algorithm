package com.rukawa.algorithm.base.class03;

/**
 * Created with Intellij IDEA
 *
 * @Author：SuperHai
 * @Date：2021-01-11 0:41
 * @Version：1.0
 */
public class Code03_ReversePair {
    /**
     * 数组中的逆序对
     * 在数组中的两个数字，如果前面一个数字大于后面的数字，则这两个数字组成一个逆序对。输入一个数组，求出这个数组中的逆序对的总数
     */

    public static int reversePairs(int[] nums) {
        if (nums == null || nums.length < 2) {
            return 0;
        }
        return process(nums, 0, nums.length - 1);
    }

    public static int process(int[] arr, int L, int R) {
        if (L == R) {
            return 0;
        }
        int M = L + ((R - L) >>> 1);
        return process(arr, L, M) + process(arr,M + 1, R) + merge(arr, L, M , R);
    }

    public static int merge(int[] arr, int L, int M, int R) {
        int[] help = new int[R - L + 1];
        int index = help.length - 1;
        int p1 = M;
        int p2 = R;
        int res = 0;
        while (p1 >= L && p2 > M) {
            res += arr[p1] > arr[p2] ? (p2 - M) : 0;
            help[index--] = arr[p1] > arr[p2] ? arr[p1--] : arr[p2--];
        }

        while (p1 >= L) {
            help[index--] = arr[p1--];
        }

        while (p2 > M) {
            help[index--] = arr[p2--];
        }

        for (index = 0; index < help.length; index++) {
            arr[L + index] = help[index];
        }
        return res;
    }


    // for test
    public static int comparator(int[] arr) {
        int ans = 0;
        for (int i = 0; i < arr.length; i++) {
            for (int j = i + 1; j < arr.length; j++) {
                if (arr[i] > arr[j]) {
                    ans++;
                }
            }
        }
        return ans;
    }

    // for test
    public static int[] generateRandomArray(int maxSize, int maxValue) {
        int[] arr = new int[(int) ((maxSize + 1) * Math.random())];
        for (int i = 0; i < arr.length; i++) {
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

    // for test
    public static void main(String[] args) {
        int testTime = 500000;
        int maxSize = 100;
        int maxValue = 100;
        System.out.println("测试开始");
        for (int i = 0; i < testTime; i++) {
            int[] arr1 = generateRandomArray(maxSize, maxValue);
            int[] arr2 = copyArray(arr1);
            if (reversePairs(arr1) != reversePairs1(arr2)) {
                System.out.println("Oops!");
                printArray(arr1);
                printArray(arr2);
                break;
            }
        }
        System.out.println("测试结束");
    }


    public static int reversePairs1(int[] nums) {
        if (nums == null || nums.length < 2) {
            return 0;
        }
        int N = nums.length;
        int mergeSize = 1;
        int res = 0;
        while (mergeSize < N) {
            int L = 0;
            while (L < N) {
                int M = L + mergeSize - 1;
                if (M >= N) {
                    break;
                }
                int R = Math.min(M + mergeSize, N - 1);
                res += merge(nums, L, M, R);
                L = R + 1;
            }

            if (mergeSize > N / 2) {
                break;
            }
            mergeSize <<= 1;
        }
        return res;
    }

}
