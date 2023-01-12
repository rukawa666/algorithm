package com.rukawa.algorithm.greater1.class33;

public class Problem_0238_ProductOfArrayExceptSelf {
	/**
	 * 除自身以外数组的乘积
	 *
	 * 给你一个整数数组 nums，返回 数组 answer ，其中 answer[i] 等于 nums 中除 nums[i] 之外其余各元素的乘积 。
	 * 题目数据 保证 数组 nums之中任意元素的全部前缀元素和后缀的乘积都在  32 位 整数范围内。
	 * 请不要使用除法，且在 O(n) 时间复杂度内完成此题。
	 */
	public int[] productExceptSelf(int[] nums) {
		int n = nums.length;
		int[] ans = new int[n];
		ans[0] = nums[0];
		for (int i = 1; i < n; i++) {
			ans[i] = ans[i - 1] * nums[i];
		}
		int right = 1;
		for (int i = n - 1; i > 0; i--) {
			ans[i] = ans[i - 1] * right;
			right *= nums[i];
		}
		ans[0] = right;
		return ans;
	}

	// 扩展 : 如果仅仅是不能用除号，把结果直接填在nums里呢？
	// 解法：数一共几个0；每一个位得到结果就是，a / b，位运算替代 /，之前的课讲过（新手班）

}
