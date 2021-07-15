package com.rukawa.algorithm.types.subArray;

import java.util.HashMap;

/**
 * @className: Code02_LongestSumSubArrayLength
 * @description: TODO 类描述
 * @author: 鎏川疯
 * @date: 2021/7/15 0015 8:27
 **/
public class Code02_LongestSumSubArrayLength {
    /**
     * 给定一个整数组成的无序数组arr，值可能正，可能负，可能0
     * 给定一个整数K
     * 找到arr的所有子数组里，哪个子数组的累加和等于K，并且是长度最大的
     * 返回其长度
     *
     * 思路：
     *  1、为什么不能使用单调栈？
     *     数组长度变大，不一定能使累加和不变或者上升
     *  2、看到子数组问题，把问题定义成什么？
     *     定义成每个位置结尾的情况下，答案是什么
     */

    /**
     * 给定一个整数组成的无序数组arr，值可能正，可能负，可能0
     * 给定一个整数K
     * 找到arr的所有子数组里，哪个子数组的累加和等于K，并且是长度最大的
     * 返回其长度
     *
     * 思路：
     *  1、为什么不能使用单调栈？
     *     数组长度变大，不一定能使累加和不变或者上升
     *  2、看到子数组问题，把问题定义成什么？
     *     定义成每个位置结尾的情况下，答案是什么
     *
     *  一个数组中，所有的值任意，有正数，有负数，有0，找到一个子数组，要求子数组中-1的数量和1的数量是一样多的，哪个子数组是最长的？
     * 1和-1维持不变，其余的数都变为0，转化为累加和等于0的情况下，子数组有多长
     */

    public static int maxLength(int[] arr, int k) {
        if (arr == null || arr.length == 0) {
            return 0;
        }
        // key：前缀和
        // value：key这个前缀和在数组中最早出现的位置
        HashMap<Integer, Integer> map = new HashMap<>();
        map.put(0, -1);
        int len = 0;
        int sum = 0;
        for (int i = 0; i < arr.length; i++) {
            sum += arr[i];
            if (map.containsKey(sum - k)) {
                len = Math.max(len, i - map.get(sum - k));
            }
            if (!map.containsKey(sum)) {
                map.put(sum, i);
            }
        }
        return len;
    }

    // for test
    public static int right(int[] arr, int k) {
        int max = 0;
        for (int i = 0; i < arr.length; i++) {
            for (int j = i; j < arr.length; j++) {
                if (valid(arr, i, j, k)) {
                    max = Math.max(max, j - i + 1);
                }
            }
        }
        return max;
    }

    // for test
    public static boolean valid(int[] arr, int L, int R, int k) {
        int sum = 0;
        for (int i = L; i <= R; i++) {
            sum += arr[i];
        }
        return sum == k;
    }

    // for test
    public static int[] generateRandomArray(int size, int value) {
        int[] ans = new int[(int) (Math.random() * size) + 1];
        for (int i = 0; i < ans.length; i++) {
            ans[i] = (int) (Math.random() * value) - (int) (Math.random() * value);
        }
        return ans;
    }

    // for test
    public static void printArray(int[] arr) {
        for (int i = 0; i != arr.length; i++) {
            System.out.print(arr[i] + " ");
        }
        System.out.println();
    }

    public static void main(String[] args) {
        int len = 50;
        int value = 100;
        int testTime = 500000;

        System.out.println("test begin");
        for (int i = 0; i < testTime; i++) {
            int[] arr = generateRandomArray(len, value);
            int K = (int) (Math.random() * value) - (int) (Math.random() * value);
            int ans1 = maxLength(arr, K);
            int ans2 = right(arr, K);
            if (ans1 != ans2) {
                System.out.println("Oops!");
                printArray(arr);
                System.out.println("K : " + K);
                System.out.println(ans1);
                System.out.println(ans2);
                break;
            }
        }
        System.out.println("test end");
    }
}
