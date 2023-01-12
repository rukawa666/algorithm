package com.rukawa.algorithm.greater1.class38;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class Problem_0739_DailyTemperatures {
	/**
	 * 每日温度
	 *
	 * 给定一个整数数组 temperatures ，表示每天的温度，返回一个数组 answer ，其中 answer[i] 是指对于第 i 天，
	 * 下一个更高温度出现在几天后。如果气温在这之后都不会升高，请在该位置用 0 来代替。
	 */
	public static int[] dailyTemperatures(int[] arr) {
		if (arr == null || arr.length == 0) {
			return new int[0];
		}
		int N = arr.length;
		int[] ans = new int[N];
		Stack<List<Integer>> stack = new Stack<>();
		for (int i = 0; i < N; i++) {
			while (!stack.isEmpty() && arr[stack.peek().get(0)] < arr[i]) {
				List<Integer> popIs = stack.pop();
				for (Integer popi : popIs) {
					ans[popi] = i - popi;
				}
			}
			if (!stack.isEmpty() && arr[stack.peek().get(0)] == arr[i]) {
				stack.peek().add(Integer.valueOf(i));
			} else {
				ArrayList<Integer> list = new ArrayList<>();
				list.add(i);
				stack.push(list);
			}
		}
		return ans;
	}

}
