package com.rukawa.algorithm.greater1.class41;

public class Problem_0031_NextPermutation {
	/**
	 * 下一个排列
	 *
	 * 整数数组的一个 排列  就是将其所有成员以序列或线性顺序排列。
	 *
	 * 例如，arr = [1,2,3] ，以下这些都可以视作 arr 的排列：[1,2,3]、[1,3,2]、[3,1,2]、[2,3,1] 。
	 * 整数数组的 下一个排列 是指其整数的下一个字典序更大的排列。更正式地，如果数组的所有排列根据其字典顺序从小到大排列在一个容器中，
	 * 那么数组的 下一个排列 就是在这个有序容器中排在它后面的那个排列。如果不存在下一个更大的排列，
	 * 那么这个数组必须重排为字典序最小的排列（即，其元素按升序排列）。
	 *
	 * 例如，arr = [1,2,3] 的下一个排列是 [1,3,2] 。
	 * 类似地，arr = [2,3,1] 的下一个排列是 [3,1,2] 。
	 * 而 arr = [3,2,1] 的下一个排列是 [1,2,3] ，因为 [3,2,1] 不存在一个字典序更大的排列。
	 * 给你一个整数数组 nums ，找出 nums 的下一个排列。
	 *
	 * 必须 原地 修改，只允许使用额外常数空间。
	 */
	public static void nextPermutation(int[] nums) {
		int N = nums.length;
		// 从右往左第一次降序的位置
		int firstLess = -1;
		for (int i = N - 2; i >= 0; i--) {
			if (nums[i] < nums[i + 1]) {
				firstLess = i;
				break;
			}
		}
		if (firstLess < 0) {
			reverse(nums, 0, N - 1);
		} else {
			int rightClosestMore = -1;
			// 找最靠右的、同时比nums[firstLess]大的数，位置在哪
			// 这里其实也可以用二分优化，但是这种优化无关紧要了
			for (int i = N - 1; i > firstLess; i--) {
				if (nums[i] > nums[firstLess]) {
					rightClosestMore = i;
					break;
				}
			}
			swap(nums, firstLess, rightClosestMore);
			reverse(nums, firstLess + 1, N - 1);
		}
	}

	public static void reverse(int[] nums, int L, int R) {
		while (L < R) {
			swap(nums, L++, R--);
		}
	}

	public static void swap(int[] nums, int i, int j) {
		int tmp = nums[i];
		nums[i] = nums[j];
		nums[j] = tmp;
	}

}
