package com.rukawa.algorithm.greater1.class37;

public class Problem_0226_InvertBinaryTree {
	/**
	 * 翻转二叉树
	 *
	 * 给你一棵二叉树的根节点 root ，翻转这棵二叉树，并返回其根节点。
	 */
	public class TreeNode {
		public int val;
		public TreeNode left;
		public TreeNode right;
	}

	public static TreeNode invertTree(TreeNode root) {
		if (root == null) {
			return null;
		}
		TreeNode left = root.left;
		root.left = invertTree(root.right);
		root.right = invertTree(left);
		return root;
	}

}
