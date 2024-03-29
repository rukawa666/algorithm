package com.rukawa.algorithm.greater1.class45;

import java.util.HashMap;
import java.util.HashSet;

public class Problem_0403_FrogJump {
	/**
	 * 青蛙过河
	 * 一只青蛙想要过河。 假定河流被等分为若干个单元格，并且在每一个单元格内都有可能放有一块石子（也有可能没有）。
	 * 青蛙可以跳上石子，但是不可以跳入水中。
	 *
	 * 给你石子的位置列表 stones（用单元格序号 升序 表示）， 请判定青蛙能否成功过河（即能否在最后一步跳至最后一块石子上）。
	 * 开始时， 青蛙默认已站在第一块石子上，并可以假定它第一步只能跳跃 1 个单位（即只能从单元格 1 跳至单元格 2 ）。
	 *
	 * 如果青蛙上一步跳跃了 k 个单位，那么它接下来的跳跃距离只能选择为 k - 1、k 或 k + 1 个单位。
	 * 另请注意，青蛙只能向前方（终点的方向）跳跃。
	 */
	public static boolean canCross(int[] stones) {
		HashSet<Integer> set = new HashSet<>();
		for (int num : stones) {
			set.add(num);
		}
		HashMap<Integer, HashMap<Integer, Boolean>> dp = new HashMap<>();
		return jump(1, 1, stones[stones.length - 1], set, dp);
	}

	public static boolean jump(int cur, int pre, int end, HashSet<Integer> set,
			HashMap<Integer, HashMap<Integer, Boolean>> dp) {
		if (cur == end) {
			return true;
		}
		if (!set.contains(cur)) {
			return false;
		}
		if (dp.containsKey(cur) && dp.get(cur).containsKey(pre)) {
			return dp.get(cur).get(pre);
		}
		boolean ans = (pre > 1 && jump(cur + pre - 1, pre - 1, end, set, dp)) 
				|| jump(cur + pre, pre, end, set, dp)
				|| jump(cur + pre + 1, pre + 1, end, set, dp);
		if (!dp.containsKey(cur)) {
			dp.put(cur, new HashMap<>());
		}
		if (!dp.get(cur).containsKey(pre)) {
			dp.get(cur).put(pre, ans);
		}
		return ans;
	}

}
