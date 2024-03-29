package com.rukawa.algorithm.greater1.class37;

import java.util.HashMap;

public class Problem_0437_PathSumIII {
	/**
	 * 路径总和 III
	 *
	 * 给定一个二叉树的根节点 root ，和一个整数 targetSum ，求该二叉树里节点值之和等于 targetSum 的 路径 的数目。
	 * 路径 不需要从根节点开始，也不需要在叶子节点结束，但是路径方向必须是向下的（只能从父节点到子节点）。
	 */
	public class TreeNode {
		public int val;
		public TreeNode left;
		public TreeNode right;
	}

	public static int pathSum(TreeNode root, int sum) {
		HashMap<Long, Integer> preSumMap = new HashMap<>();
		preSumMap.put(0L, 1);
		return process(root, sum, 0, preSumMap);
	}

	// 返回方法数
	public static int process(TreeNode x, int sum, long preAll, HashMap<Long, Integer> preSumMap) {
		if (x == null) {
			return 0;
		}
		long all = preAll + x.val;
		int ans = 0;
		if (preSumMap.containsKey(all - sum)) {
			ans = preSumMap.get(all - sum);
		}
		if (!preSumMap.containsKey(all)) {
			preSumMap.put(all, 1);
		} else {
			preSumMap.put(all, preSumMap.get(all) + 1);
		}
		ans += process(x.left, sum, all, preSumMap);
		ans += process(x.right, sum, all, preSumMap);
		if (preSumMap.get(all) == 1) {
			preSumMap.remove(all);
		} else {
			preSumMap.put(all, preSumMap.get(all) - 1);
		}
		return ans;
	}

}
