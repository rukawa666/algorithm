package com.rukawa.algorithm.greater1.class38;

public class Problem_0617_MergeTwoBinaryTrees {
	/**
	 * 合并二叉树
	 *
	 * 给你两棵二叉树： root1 和 root2 。
	 * 想象一下，当你将其中一棵覆盖到另一棵之上时，两棵树上的一些节点将会重叠（而另一些不会）。你需要将这两棵树合并成一棵新二叉树。
	 * 合并的规则是：如果两个节点重叠，那么将这两个节点的值相加作为合并后节点的新值；否则，不为 null 的节点将直接作为新二叉树的节点。
	 * 返回合并后的二叉树。
	 * 注意: 合并过程必须从两个树的根节点开始
	 */
	public static class TreeNode {
		public int val;
		public TreeNode left;
		public TreeNode right;

		public TreeNode(int val) {
			this.val = val;
		}
	}

	// 当前，一棵树的头是t1，另一颗树的头是t2
	// 请返回，整体merge之后的头
	public static TreeNode mergeTrees(TreeNode t1, TreeNode t2) {
		if (t1 == null) {
			return t2;
		}
		if (t2 == null) {
			return t1;
		}
		// t1和t2都不是空
		TreeNode merge = new TreeNode(t1.val + t2.val);
		merge.left = mergeTrees(t1.left, t2.left);
		merge.right = mergeTrees(t1.right, t2.right);
		return merge;
	}

}
