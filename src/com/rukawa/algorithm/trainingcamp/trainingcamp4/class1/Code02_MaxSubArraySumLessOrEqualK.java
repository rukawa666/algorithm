package com.rukawa.algorithm.trainingcamp.trainingcamp4.class1;

import java.util.TreeSet;

/**
 * Created with Intellij IDEA
 *
 * @Author：SuperHai
 * @Date：2020-10-10 7:30
 * @Version：1.0
 */
public class Code02_MaxSubArraySumLessOrEqualK {
    /**
     * 给定一个数组arr，再给定一个k值
     * 返回累加和小于等于k，但是离k最近的子数组的累加和
     */
    public static int getMaxLessOrEqualK(int[] arr, int K) {
        /**
         * 思路：
         *   求子数组必须以i位置结尾，累加和必须小于等于k，但是必须离k最近
         *   在每一个位置结尾的时候求一个答案，其中最接近在其中
         *
         *   1、如果0...i的累加为100，求累加和小于等于20，有没有哪个前缀和离80(>=80)最近的，
         *     此时得出最大累加和和j...i的后缀和
         *   2、把所有的前缀和添加到有序表，查询>=某个值
         *
         * 如果数组有正负(无单调性)，没法用窗口滑动，如果都是正数，可以用滑动窗口
         */
        // 记录i之前的，前缀和，按照有序表组织
        TreeSet<Integer> set = new TreeSet<>();
        // 一个数也没有的时候，已经有一个前缀和是0了
        set.add(0);

        int max = Integer.MIN_VALUE;
        int sum = 0;
        for (int i = 0; i < arr.length; i++) {
           sum += arr[i];
           // 当前的前缀和不为null
           if (set.ceiling(sum - K) != null) {
               // sum - set.celling(sum - K) -> 后缀和
               max = Math.max(max, sum - set.ceiling(sum - K));
           }
           // 当前的前缀和添加到set中去
           set.add(sum);
        }
        return max;
    }

}
