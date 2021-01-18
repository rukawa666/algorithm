package com.rukawa.algorithm.base.class04;

import java.util.Arrays;

/**
 * Created with Intellij IDEA
 *
 * @Author：SuperHai
 * @Date：2020-07-14 22:24
 * @Version：1.0
 */
public class Code02_HeapSort {

    public static void main(String[] args) {
        int[] arr = {0,9,10,8,7,1,3,4,2};
        heapSort(arr);
        System.out.println(Arrays.toString(arr));
    }

    // 堆排序的额外空间复杂度为O(1)
    public static void heapSort(int[] arr) {
        if (arr == null || arr.length < 2) {
            return;
        }

        // 最初的堆排序  O(N*logN)
//        for (int i = 0; i < arr.length; i++) {  // O(N)
//            heapInsert(arr, i); // O(logN)
//        }

        // 全部给你，只要求大根堆的优化，不要求有序  O(N)
        // 从下往上建立大根堆
        for (int i = arr.length - 1; i >= 0; i--) {
            heapify(arr, i , arr.length);
        }

        int heapSize = arr.length;
        swap(arr, 0, --heapSize);
        // O(N*logN)
        while (heapSize > 0) {  // O(N)
            heapify(arr, 0, heapSize);  // O(logN)
            swap(arr, 0, --heapSize);
        }
    }

    public static void heapInsert(int[] arr, int index) {
        while (arr[index] > arr[(index - 1) / 2]) {
            swap(arr, index, (index - 1) / 2);
            index = (index - 1) / 2;
        }
    }

    public static void heapify(int[] arr, int index, int heapSize) {
        int left = index * 2 + 1;
        while (left < heapSize) {
            int largest = left + 1 < heapSize && arr[left + 1] > arr[left] ? left + 1 : left;
            largest = largest > index ? largest : index;
            if (largest == index) {
                break;
            }
            swap(arr, largest, index);
            index = largest;
            left = index * 2 + 1;
        }
    }

    public static void swap(int[] arr, int i, int j) {
        int tmp = arr[i];
        arr[i] = arr[j];
        arr[j] = tmp;
    }

    // for test
    public static void comparator(int[] arr) {
        Arrays.sort(arr);
    }
}
