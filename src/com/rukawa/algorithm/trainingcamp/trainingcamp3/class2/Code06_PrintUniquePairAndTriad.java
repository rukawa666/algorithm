package com.rukawa.algorithm.trainingcamp.trainingcamp3.class2;

/**
 * Created with Intellij IDEA
 *
 * @Author：SuperHai
 * @Date：2020-08-22 10:07
 * @Version：1.0
 */
public class Code06_PrintUniquePairAndTriad {

    /**
     * 给定一个有序数组arr，给定一个正整数aim
     * 1、返回累加和为aim的，所有不同二元组
     * 2、返回累加和为aim的，所有不同三元组
     */

    // 返回累加和为aim的，所有不同二元组
    public static void printUniquePair(int[] arr, int aim) {
        if (arr == null || arr.length < 2) {
            return;
        }
        int L = 0;
        int R = arr.length - 1;
        while (L < R) {
            if (arr[L] + arr[R] > aim) {
                R--;
            } else if (arr[L] + arr[R] < aim) {
                L++;
            } else {
                // L == 0 为了防止L-1越界
                if (L == 0 || arr[L] != arr[L - 1]) {
                    System.out.println(arr[L] +", " + arr[R]);
                }
                L++;
                R--;
            }
        }
    }

    // 返回累加和为aim的，所有不同三元组
    public static void printUniqueTriad(int[] arr, int aim) {
        if (arr == null || arr.length < 3) {
            return;
        }
        for (int i = 0; i < arr.length - 2; i++) {
            if (i == 0 || arr[i] != arr[i - 1]) {
                printRest(arr, i, i + 1, arr.length - 1, aim - arr[i]);
            }
        }
    }

    public static void printRest(int[] arr, int first, int L, int R, int aim) {
        while (L < R) {
            if (arr[L] + arr[R] > aim) {
                R--;
            } else if (arr[L] + arr[R] < aim) {
                L++;
            } else {
                if (L == first + 1 || arr[L - 1] != arr[L]) {
                    System.out.println(arr[first] + ", " + arr[L] + ", " + arr[R]);
                }
                L++;
                R--;
            }
        }
    }

    public static void printArray(int[] arr) {
        for (int i = 0; i != arr.length; i++) {
            System.out.print(arr[i] + " ");
        }
        System.out.println();
    }

    public static void main(String[] args) {
        int sum = 10;
        int[] arr1 = { 1, -4, -3, 0, 1, 2, 4, 5, 8, 9 };
        printArray(arr1);
        System.out.println("====");
        printUniquePair(arr1, sum);
        System.out.println("====");
        printUniqueTriad(arr1, sum);

    }
}
