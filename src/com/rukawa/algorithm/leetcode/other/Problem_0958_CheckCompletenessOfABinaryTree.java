package com.rukawa.algorithm.leetcode.other;

/**
 * Created with Intellij IDEA
 *
 * @Author：SuperHai
 * @Date：2021-01-25 7:57
 * @Version：1.0
 */
public class Problem_0958_CheckCompletenessOfABinaryTree {
    /**
     * 二叉树的完全性检验
     * 给定一个二叉树，确定它是否是一个完全二叉树。
     * 百度百科中对完全二叉树的定义如下：
     * 若设二叉树的深度为 h，除第 h 层外，其它各层 (1～h-1) 的结点数都达到最大个数，第 h 层所有的结点都连续集中在最左边，这就是完全二叉树。
     * （注：第 h 层可能包含 1~ 2h 个节点。）
     *
     * 示例 1：
     * 输入：[1,2,3,4,5,6]
     * 输出：true
     * 解释：最后一层前的每一层都是满的（即，结点值为 {1} 和 {2,3} 的两层），且最后一层中的所有结点（{4,5,6}）都尽可能地向左。
     *
     * 示例 2：
     * 输入：[1,2,3,4,5,null,7]
     * 输出：false
     * 解释：值为 7 的结点没有尽可能靠向左侧。
     *
     * 提示：
     * 树中将会有 1 到 100 个结点。
     */

    public class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
        TreeNode() {}
        TreeNode(int val) { this.val = val; }
        TreeNode(int val, TreeNode left, TreeNode right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }
    }

    public boolean isCompleteTree(TreeNode root) {
        return process(root).isBalanced;
    }

    public static Info process(TreeNode x) {
        if (x == null) {
            return new Info(true, 0);
        }
        Info leftInfo = process(x.left);
        Info rightInfo = process(x.right);
        int height = Math.max(leftInfo.height, rightInfo.height) + 1;
        boolean isBalanced = true;
        if (!leftInfo.isBalanced) {
            isBalanced = false;
        }
        if (!rightInfo.isBalanced) {
            isBalanced = false;
        }
        if (Math.abs(leftInfo.height - rightInfo.height) > 1) {
            isBalanced = false;
        }
        return new Info(isBalanced, height);
    }

    public static class Info {
        public boolean isBalanced;
        public int height;

        public Info(boolean b, int h) {
            this.isBalanced = b;
            this.height = h;
        }
    }
}
