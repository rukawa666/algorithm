package com.rukawa.algorithm.base.class16;

import java.util.LinkedList;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @Author: Administrator
 * @Date: 2021/4/19 0019 23:19
 */
public class Code02_AllLessNumSubArray {
    /**
     * 给定一个整形数组arr，和一个整数num
     * 某个arr中的子数组sub，如果想达标，必须满足：
     * sub中的最大值-sub中的最小值 <= num,
     * 返回arr中达标子数组的数量
     */

    // 暴力方法
    // O(N^3)
    public static int right(int[] arr, int sum) {
        if (arr == null || arr.length < 0 || sum < 0) {
            return 0;
        }
        int res = 0;
        for (int L = 0; L < arr.length; L++) {
            for (int R = L; R < arr.length; R++) {
                int max = arr[L];
                int min = arr[L];
                for (int i = L + 1; i <= R; i++) {
                    max = Math.max(max, arr[i]);
                    min = Math.min(min, arr[i]);
                }
                if (max - min <= sum) {
                    res++;
                }
            }
        }
        return res;
    }

    /**
     * 结论：假设某一个子数组arr，arr[L...R]，这个范围的子数组已经是达标的情况，max - min <= sum，则内部所有的子数组都达标
     */
    // 滑动窗口优化
    // O(N)
    public static int num(int[] arr, int sum) {
        /**
         * 分析：
         *  假设[L...R]范围上，最大值-最小值达标，则[L...R]的子数组一定都是达标的，
         *  因为子数组的min只能比原min大，子数组的的max只能比原max小，所以都达标
         *
         *  假设[L...R]范围上，最大值-最小值不达标，则往左扩和往右扩的子数组都不达标
         */
        if (arr == null || arr.length < 0 || sum < 0) {
            return 0;
        }
        int N = arr.length;
        int count = 0;
        // 队列中 从大到小
        LinkedList<Integer> maxWindow = new LinkedList<>();
        // 队列中，从小到大
        LinkedList<Integer> minWindow = new LinkedList<>();
        int R = 0;
        // 窗口尝试
        for (int L = 0; L < N; L++) {
            while (R < N) {
                while (!maxWindow.isEmpty() && arr[maxWindow.peekLast()] <= arr[R]) {
                    maxWindow.pollLast();
                }
                maxWindow.addLast(R);

                while (!minWindow.isEmpty() && arr[minWindow.peekLast()] >= arr[R]) {
                    minWindow.pollLast();
                }
                minWindow.addLast(R);
                // 如果达标一直扩，不达标了就停止
                if (arr[maxWindow.peekFirst()] - arr[minWindow.peekFirst()] > sum) {
                    break;
                } else {
                    R++;
                }
            }
            count += R - L;
            // 如果尝试下一个位置，则需要把当前位置从窗口中移除
            if (maxWindow.peekFirst() == L) {
                maxWindow.pollFirst();
            }
            if (minWindow.peekFirst() == L) {
                minWindow.pollFirst();
            }
        }
        return count;
    }

    // for test
    public static int[] generateRandomArray(int maxLen, int maxValue) {
        int len = (int) (Math.random() * (maxLen + 1));
        int[] arr = new int[len];
        for (int i = 0; i < len; i++) {
            arr[i] = (int) (Math.random() * (maxValue + 1)) - (int) (Math.random() * (maxValue + 1));
        }
        return arr;
    }

    // for test
    public static void printArray(int[] arr) {
        if (arr != null) {
            for (int i = 0; i < arr.length; i++) {
                System.out.print(arr[i] + " ");
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        int maxLen = 100;
        int maxValue = 200;
        int testTime = 100000;
        System.out.println("测试开始");
        for (int i = 0; i < testTime; i++) {
            int[] arr = generateRandomArray(maxLen, maxValue);
            int sum = (int) (Math.random() * (maxValue + 1));
            int ans1 = right(arr, sum);
            int ans2 = num(arr, sum);
            if (ans1 != ans2) {
                System.out.println("Oops!");
                printArray(arr);
                System.out.println(sum);
                System.out.println(ans1);
                System.out.println(ans2);
                break;
            }
        }
        System.out.println("测试结束");

    }

}
