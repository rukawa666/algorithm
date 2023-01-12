package com.rukawa.algorithm.greater1.class14;

// 测试链接：https://leetcode.com/problems/first-missing-positive/
public class Code06_MissingNumber {
	/**
	 * 缺失的第一个正数
	 * 给你一个未排序的整数数组 nums ，请你找出其中没有出现的最小的正整数。
	 *
	 * 请你实现时间复杂度为 O(n) 并且只使用常数级别额外空间的解决方案。
	 */
	public static int firstMissingPositive(int[] arr) {
		// l是盯着的位置
		// 0 ~ L-1有效区
		int L = 0;
		int R = arr.length;
		while (L != R) {
			if (arr[L] == L + 1) {
				L++;
			} else if (arr[L] <= L || arr[L] > R || arr[arr[L] - 1] == arr[L]) { // 垃圾的情况
				swap(arr, L, --R);
			} else {
				swap(arr, L, arr[L] - 1);
			}
		}
		return L + 1;
	}

	public static void swap(int[] arr, int i, int j) {
		int tmp = arr[i];
		arr[i] = arr[j];
		arr[j] = tmp;
	}

}
