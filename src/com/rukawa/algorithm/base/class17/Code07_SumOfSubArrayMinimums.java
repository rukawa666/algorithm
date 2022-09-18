package com.rukawa.algorithm.base.class17;

import java.util.Stack;

/**
 * create by hqh on 2022/9/16
 */
public class Code07_SumOfSubArrayMinimums {
    /**
     * 给定一个数组arr，返回所有子数组最小值的累加和
     */
    /**
     * 前提：数组没有重复值
     * 分析：假设以10位置的7作为最小值，左边找比它小离它最近的位置是5值是5，右边找比它小且离它最近的位置是15位置的4
     * 整个6～14位置的子数组是以7作为最小值，但是要跨过10位置
     * 6～10 6～11 6～12 6～13 6～14 的子数组是以10位置的7作为最小值
     * 7～10 7～11 7～12 7～13 7～14 的子数组也是以10位置的7作为最小值
     * 。。。
     * 10～10 10～11 10～12 10～13 10～14 的所有子数组是以7作为最小值
     *
     * 一共有多少个子数组以7为最小值？
     * 分析：6～10有5个位置 10～14有5个位置，所以是5*5个子数组以7作为最小值  所以以7作为最小值公共有25*7的最小值累加和
     *
     * 推广：i位置的x左右两边扩，到扩不动的位置，左边是k位置的y   右边是j位置的z
     *      y           x        z
     *      k k+1      i     j-1 j
     *  所以整个区域是[k+1,j-1] 左边有(i-k)个位置  右边有j-i个位置  子数组数量是(i-k)*(j-i)
     *
     *  问题2：如果有重复值，则需要去重
     *  策略1：开始位置不变，结尾位置在遇到最小值相同的位置停止
     *  策略2：开始位置遇到最小值相同的位置停止，结尾位置不变
     *
     */
    // 不使用单调栈的思路解法
    public static int subArrayMinSums2(int[] arr) {
        // left[i]=x, 代表arr[i]左边，离arr[i]最近且比它小且等的位置在x
        int[] left = leftNearLessEqual2(arr);
        // right[i]=y, 代表arr[i]右边，离arr[i]最近且比它小的位置在y
        int[] right = rightNearLess2(arr);
        long res = 0;
        long mod = (long) 1e9 + 1;
        for (int i = 0; i < arr.length; i++) {
            int start = i - left[i];
            int end = right[i] - i;
            res += start * end * (long)arr[i];
            res %= mod;
        }
        return (int) res;
    }

    public static int[] leftNearLessEqual2(int[] arr) {
        int n = arr.length;
        int[] left = new int[n];
        for (int i = 0; i < n; i++) {
            int res = -1;
            for (int j = i - 1; j >= 0; j--) {
                if (arr[j] <= arr[i]) {
                    res = j;
                    break;
                }
            }
            left[i] = res;
        }
        return left;
    }

    // 使用栈
    public static int[] leftNearLessEqual1(int[] arr) {
        int n = arr.length;
        int[] left = new int[n];
        Stack<Integer> stack = new Stack<>();
        for (int i = n - 1; i >= 0; i--) {
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

    public static int[] rightNearLess2(int[] arr) {
        int n = arr.length;
        int[] right = new int[n];
        for (int i = 0; i < n; i++) {
            int res = n;
            for (int j = i + 1; j < n; j++) {
                if (arr[j] < arr[i]) {
                    res = j;
                    break;
                }
            }
            right[i] = res;
        }
        return right;
    }

    // 使用栈
    public static int[] rightNearLess1(int[] arr) {
        int n = arr.length;
        int[] right = new int[n];
        Stack<Integer> stack = new Stack<>();
        for (int i = 0; i < n; i++) {
            while (!stack.isEmpty() && arr[i] < arr[stack.peek()]) {
                right[stack.pop()] = i;
            }
            stack.push(i);
        }

        while (!stack.isEmpty()) {
            right[stack.pop()] = n;
        }
        return right;
    }

}
