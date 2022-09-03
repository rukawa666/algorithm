package com.rukawa.algorithm.base.class01;

import java.util.Arrays;

/**
 * create by hqh on 2022/8/25
 */
public class Code02_BubbleSort {

    public static void bubbleSort(int[] arr) {
        if (arr == null || arr.length <  2) {
            return;
        }

        boolean hasChange = true;
        for (int i = 0; i < arr.length - 1 && hasChange; ++i) {
            hasChange = false;
            for (int j = 0; j < arr.length - 1 - i; ++j) {
                if (arr[j] > arr[j + 1]) {
                    swap(arr, j, j + 1);
                    hasChange = true;
                }
            }
        }
    }

    public static void swap(int[] arr, int i, int j) {
        arr[i] = arr[i] ^ arr[j];
        arr[j] = arr[i] ^ arr[j];
        arr[i] = arr[i] ^ arr[j];
    }

    public static void main(String[] args) {
        int[] nums = {7,1,2,6,5,3,4};
        bubbleSort(nums);
        System.out.println(Arrays.toString(nums));
    }
}
