package com.rukawa.algorithm.base.class29;

import java.util.HashMap;
import java.util.Map;

/**
 * create by hqh on 2022/9/19
 */
public class Code02_LongestSumSubArrayLength {
    /**
     * 给定一个整数组成的无序数组arr，值可能是正、可能负、可能零
     * 给定一个整数值K
     * 找到arr的所有子数组中，哪个子数组的累加和等于K，并且长度是最大的
     * 返回其长度
     */

    /**
     * 另外一个题：
     *   给定一个整数组成的无序数组arr，值可能是正、可能负、可能零
     *   找到arr的所有子数组中，1和-1一样多的情况下，哪个子数组是最长的
     *   返回其长度
     *
     * 把数组转化，其中，-1和1的值不变，其余变成0，求累加和等于0的情况下，最大子数组长度
     */

    // 以每个位置开头或者每个位置结尾怎么样的情况分析
    /**
     * 求i位置往左推动，推出多少的距离累加和是30
     * 假设0～100的累加和是200，0～j范围的累加和是170，此时j到i的累加和是30
     * 最长的距离就是在找前缀和最早出现在170，可以反着得到最大的长度
     */
    public static int maxLength(int[] arr, int k) {
        if (arr == null || arr.length == 0) {
            return 0;
        }
        int maxLen = 0;
        int sum = 0;
        // 前缀和最早出现的问题
        Map<Integer, Integer> preSumMap = new HashMap<>();
        // 必须初始化一个0位置的信息，不然错过0开头的记录，比如：k=10 [5,5,...]
        preSumMap.put(0, -1);
        for (int i = 0; i < arr.length; i++) {
            sum += arr[i];
            if (preSumMap.containsKey(sum - k)) {
                maxLen = Math.max(maxLen, i - preSumMap.get(sum - k));
            }
            if (!preSumMap.containsKey(sum)) {
                preSumMap.put(sum, i);
            }
        }
        return maxLen;
    }

    // for test
    public static int right(int[] arr, int K) {
        int max = 0;
        for (int i = 0; i < arr.length; i++) {
            for (int j = i; j < arr.length; j++) {
                if (valid(arr, i, j, K)) {
                    max = Math.max(max, j - i + 1);
                }
            }
        }
        return max;
    }

    // for test
    public static boolean valid(int[] arr, int L, int R, int K) {
        int sum = 0;
        for (int i = L; i <= R; i++) {
            sum += arr[i];
        }
        return sum == K;
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
