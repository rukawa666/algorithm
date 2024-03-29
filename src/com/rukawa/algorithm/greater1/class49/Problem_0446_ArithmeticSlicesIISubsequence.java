package com.rukawa.algorithm.greater1.class49;

import java.util.ArrayList;
import java.util.HashMap;

public class Problem_0446_ArithmeticSlicesIISubsequence {
	/**
	 * 等差数列划分 II - 子序列
	 * 给你一个整数数组 nums ，返回 nums 中所有 等差子序列 的数目。
	 *
	 * 如果一个序列中 至少有三个元素 ，并且任意两个相邻元素之差相同，则称该序列为等差序列。
	 *
	 * 例如，[1, 3, 5, 7, 9]、[7, 7, 7, 7] 和 [3, -1, -5, -9] 都是等差序列。
	 * 再例如，[1, 1, 2, 5, 7] 不是等差序列。
	 * 数组中的子序列是从数组中删除一些元素（也可能不删除）得到的一个序列。
	 *
	 * 例如，[2,5,10] 是 [1,2,1,2,4,1,5,10] 的一个子序列。
	 * 题目数据保证答案是一个 32-bit 整数。
	 */
	// 时间复杂度是O(N^2)，最优解的时间复杂度
	public static int numberOfArithmeticSlices(int[] arr) {
		int N = arr.length;
		int ans = 0;
		ArrayList<HashMap<Integer, Integer>> maps = new ArrayList<>();
		for (int i = 0; i < N; i++) {
			maps.add(new HashMap<>());
			//  ....j...i（结尾）
			for (int j = i - 1; j >= 0; j--) {
				long diff = (long) arr[i] - (long) arr[j];
				if (diff <= Integer.MIN_VALUE || diff > Integer.MAX_VALUE) {
					continue;
				}
				int dif = (int) diff;
				int count = maps.get(j).getOrDefault(dif, 0);
				ans += count;
				maps.get(i).put(dif, maps.get(i).getOrDefault(dif, 0) + count + 1);
			}
		}
		return ans;
	}

}
