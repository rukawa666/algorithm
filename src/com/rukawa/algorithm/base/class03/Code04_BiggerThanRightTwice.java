package com.rukawa.algorithm.base.class03;

/**
 * Created with Intellij IDEA
 *
 * @Author：SuperHai
 * @Date：2021-01-11 0:41
 * @Version：1.0
 */
public class Code04_BiggerThanRightTwice {

    /**
     * 数组中右边的数*2小于左边数的个数
     */
    public static int biggerTwice(int[] arr) {
        if (arr == null || arr.length < 2) {
            return 0;
        }
        return process(arr, 0, arr.length - 1);
    }

    public static int process(int[] arr, int L, int R) {
        if (L == R) {
            return 0;
        }

        int M = L + ((R - L) >>> 1);
        return process(arr, L, M) + process(arr, M + 1, R) + merge(arr, L, M, R);
    }

    public static int merge(int[] arr, int L, int M, int R) {
        int res = 0;
        // 目前囊括进来的数，是从[M + 1, start)  左闭右开的区间
        /**
         * 不回退，所以此处是O(N)
         * 先算清东西再进行merge
         * 左右两个区间，分别是：[L,M] [M+1,R]
         * 目前囊括出来的数是从[M+1, start)，不包含start
         * 1,2,3  |  1,1
         * 刚开始start再M+1位置，此时滑不动，统计左侧1的数量为0
         */
        int start = M + 1;
        for (int i = L; i <= M; i++) {      // 考察左组的情况
            while (start <= R && arr[i] > (arr[start] << 1)) { // 右侧滑动，没有滑越界，当时左侧的情况大于右侧的数*2，继续滑动
                start++;
            }
            res += start - (M + 1);
        }

        int[] help = new int[R - L + 1];
        int p1 = L;
        int p2 = M + 1;
        int index = 0;
        while (p1 <= M && p2 <= R) {
            help[index++] = arr[p1] <= arr[p2] ? arr[p1++] : arr[p2++];
        }

        while (p1 <= M) {
            help[index++] = arr[p1++];
        }

        while (p2 <= R) {
            help[index++] = arr[p2++];
        }

        for (index = 0; index < help.length; index++) {
            arr[L + index] = help[index];
        }
        return res;
    }

    public static int biggerTwice1(int[] arr) {
        if (arr == null || arr.length < 2) {
            return 0;
        }
        int N = arr.length;
        int mergeSize = 1;
        int res = 0;
        while (mergeSize < N) {
            int L = 0;
            while (L < N) {
                int M = L + mergeSize - 1;
                if (M >= N) {
                    break;
                }
                int R = Math.min(mergeSize + M, N - 1);
                res += merge1(arr, L, M, R);
                L = R + 1;
            }

            if (mergeSize > N / 2) {
                break;
            }
            mergeSize <<= 1;
        }
        return res;
    }

    public static int merge1(int[] arr, int L, int M, int R) {
        int res = 0;
        int window = M + 1;
        for (int i = L; i <= M; i++) {
            while (window <= R && arr[i] > (arr[window] << 1)) {
                window++;
            }
            res += window - (M + 1);
        }
        int[] help = new int[R - L + 1];
        int index = 0;
        int p1 = L;
        int p2 = M + 1;
        while (p1 <= M && p2 <= R) {
            help[index++] = arr[p1] <= arr[p2] ? arr[p1++] : arr[p2++];
        }

        while (p1 <= M) {
            help[index++] = arr[p1++];
        }

        while (p2 <= R) {
            help[index++] = arr[p2++];
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
                if (arr[i] > (arr[j] << 1)) {
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
            arr[i] = (int) ((maxValue + 1) * Math.random()) - (int) ((maxValue + 1) * Math.random());
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
            if (biggerTwice(arr1) != biggerTwice1(arr2)) {
                System.out.println("Oops!");
                printArray(arr1);
                printArray(arr2);
                break;
            }
        }
        System.out.println("测试结束");
    }
}
