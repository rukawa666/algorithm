package com.rukawa.algorithm.base.class17_stack;

import java.util.Stack;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @Author: Administrator
 * @Date: 2021/5/25 0025 7:42
 */
public class Code06_SumOfSubarrayMinimums {
    /**
     * 给定一个数组arr
     * 返回所有子数组最小值的累加和
     * leetcode原题:https://leetcode-cn.com/problems/sum-of-subarray-minimums/
     */

    // 暴力解
    public static int subArrayMinSum1(int[] arr) {
        int ans = 0;
        for (int i = 0; i < arr.length; i++) {
            for (int j = i; j < arr.length; j++) {
                int min = arr[i];
                for (int k = i + 1; k <= j; k++) {
                    min = Math.min(min, arr[k]);
                }
                ans += min;
            }
        }
        return ans;
    }

    /**
     * 1、无重复值的计算：
     * 5 10 8 9 8 7  9  8  9  8  4
     * 5 6  7 8 9 10 11 12 13 14 15
     * 以10位置的7作为最小值，左边离我最近比我小的是5位置的5，右边离我最近比我小的是15位置的4
     * 在6~14范围中，以7作为最小值的子数组是哪些？
     *    6~6,6~7等都不是以7作为最小值，必须要跨过10位置的7，才是以7为最小值的子数组
     *    6~10,6~11,6~12,6~13，6~14
     *    7~10,7~11,7~12,7~13，7~14
     *    8~10,8~11,8~12,8~13，8~14
     *    9~10,9~11,9~12,9~13，9~14
     *    10~10,10~11,10~12,10~13，10~14
     *    都是跨过10位置的7，都是以7作为最小值的子数组
     *    (6~10)共5个位置
     *    (10~14)共5个位置
     *    总共25个子数组以7作为最小值
     *  推广：
     *     y    x    z
     *     k    i    j
     *  多少个位置:i-(k+1)+1 * (j-1)-i+1 = (i-k)*(j-i)*arr[i]
     *
     * 2、有重复值的计算
     * 5 10 6 9 8 6  9  8  6  8  4
     * 5 6  7 8 9 10 11 12 13 14 15
     * 以7位置的6作为最小值
     *   5~7，5~8，5~9，5~10，5~11，5~12,5~13，5~14
     * 以10位置的6作为最小值
     *   5~10,5~11,5~12,5~13,5~14
     * 以13位置的6作为最小值
     *   5~13,5~14
     * 以上值为6的位置有算重，所以定一种策略
     * 以7位置的6作为最小值
     *  5~7，5~8，5~9
     *  6~7,6~8,6~9
     *  7~7,7~8,7~9
     * 以10位置的6作为最小值
     *  5~10,5~11,5~12
     *  6~10,6~11,6~12
     *  7~10,..
     *  8~10,..
     *  9~10,...
     *  10~10
     * 以13位置的6作为最小值
     *  5~13,5~14
     *  依次类推
     * 右侧遇到相等，前面计算停止，在后面再算
     *
     *
     * 另外一种方式：本题的思路
     * 左边相等就停，右边全部推完
     * 以7位置的6作为最小值
     *  5~7，5~8，5~9，5~10,5~11,5~12，5~13,5~14
     *  6~7,6~8,6~9，6~10,6~11,6~12，6~13,6~14
     *  7~7,7~8,7~9，7~10,7~11,7~12，7~13,7~14
     * 以10位置的6作为最小值
     * 8~10,8~11,8~12，8~13,8~14
     * 9~10,9~11,9~12，9~13,9~14
     * 10~10,10~11,10~12，10~13,10~14
     * 以13位置的6作为最小值
     *  11~13,11~12,11~13,11~14
     *  12~13,12~12,12~13,12~14
     *  13~13,13~12,13~13,13~14
     *  依次类推
     * 左侧遇到相等停止，在后面再算
     *
     */

    public static int subArrayMinSum2(int[] arr) {
        if (arr == null || arr.length == 0) {
            return 0;
        }
        // left[i] = x, arr[i]左边，离i最近，且<=arr[i]的数,位置在x
        int[] left = leftNearLessEquals2(arr);
        // right[i] = y, arr[i]右边，离i最近，且<arr[i]的数,位置在y
        int[] right = rightNearLess2(arr);
        long ans = 0;
        for (int i = 0; i < arr.length; i++) {
            int start = i - left[i];
            int end = right[i] - i;
            ans += start * end * (long)arr[i];
            ans %= 1000000007;
        }
        return (int) ans;
    }

    public static int[] leftNearLessEquals2(int[] arr) {
        int N = arr.length;
        int[] left = new int[N];
        for (int i = 0; i < N; i++) {
            int ans = -1;
            for (int j = i - 1; j >= 0; j--) {
                if (arr[j] <= arr[i]) {
                    ans = j;
                    break;
                }
            }
            left[i] = ans;
        }
        return left;
    }

    public static int[] rightNearLess2(int[] arr) {
        int N = arr.length;
        int[] right = new int[N];
        for (int i = 0; i < N; i++) {
            int ans = N;
            for (int j = i + 1; j < N; j++) {
                if (arr[j] < arr[i]) {
                    ans = j;
                    break;
                }
            }
            right[i] = ans;
        }
        return right;
    }

    public static int sumSubarrayMins(int[] arr) {
        if (arr == null || arr.length == 0) {
            return 0;
        }
        int[] left = nearLessEqualLeft(arr);
        int[] right = nearLessRight(arr);
        long ans = 0;
        for (int i = 0; i < arr.length; i++) {
            long start = i - left[i];
            long end = right[i] - i;
            ans += start * end * (long) arr[i];
            ans %= 1000000007;
        }
        return (int) ans;
    }

    public static int[] nearLessEqualLeft(int[] arr) {
        int N = arr.length;
        int[] left = new int[N];
        Stack<Integer> stack = new Stack<>();
        for (int i = N - 1; i >= 0; i--) {
            while (!stack.isEmpty() && arr[i] <= arr[stack.peek()]) {
                left[stack.pop()] = i;
            }
            stack.push(i);
        }
        while (!stack.isEmpty()) {
            left[stack.pop()] = -1;
        }
        return left;
    }

    public static int[] nearLessRight(int[] arr) {
        int N = arr.length;
        int[] right = new int[N];
        Stack<Integer> stack = new Stack<>();
        for (int i = 0; i < N; i++) {
            while (!stack.isEmpty() && arr[stack.peek()] > arr[i]) {
                right[stack.pop()] = i;
            }
            stack.push(i);
        }
        while (!stack.isEmpty()) {
            right[stack.pop()] = N;
        }
        return right;
    }

    public static int[] randomArray(int len, int maxValue) {
        int[] ans = new int[len];
        for (int i = 0; i < len; i++) {
            ans[i] = (int) (Math.random() * maxValue) + 1;
        }
        return ans;
    }

    public static void printArray(int[] arr) {
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i] + " ");
        }
        System.out.println();
    }

    public static void main(String[] args) {
        int maxLen = 100;
        int maxValue = 50;
        int testTime = 100000;
        System.out.println("测试开始");
        for (int i = 0; i < testTime; i++) {
            int len = (int) (Math.random() * maxLen);
            int[] arr = randomArray(len, maxValue);
            int ans1 = subArrayMinSum1(arr);
            int ans2 = subArrayMinSum2(arr);
            int ans3 = sumSubarrayMins(arr);
            if (ans1 != ans2 || ans1 != ans3) {
                printArray(arr);
                System.out.println(ans1);
                System.out.println(ans2);
                System.out.println(ans3);
                System.out.println("出错了！");
                break;
            }
        }
        System.out.println("测试结束");
    }

}
