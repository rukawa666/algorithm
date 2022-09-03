package com.rukawa.algorithm.leetcode.top100likedquestions;

/**
 * create by hqh on 2022/9/1
 */
public class Problem_0958_CheckCompletenessOfABinaryTree {
    /**
     * 二叉树的完全性检验
     * 给定一个二叉树的 root ，确定它是否是一个 完全二叉树 。
     * 在一个 完全二叉树 中，除了最后一个关卡外，所有关卡都是完全被填满的，并且最后一个关卡中的所有节点都是尽可能靠左的。
     * 它可以包含 1 到 2h 节点之间的最后一级 h 。
     *
     * 示例 1：
     * 输入：root = [1,2,3,4,5,6]
     * 输出：true
     * 解释：最后一层前的每一层都是满的（即，结点值为 {1} 和 {2,3} 的两层），且最后一层中的所有结点（{4,5,6}）都尽可能地向左。
     *
     * 示例 2：
     * 输入：root = [1,2,3,4,5,null,7]
     * 输出：false
     * 解释：值为 7 的结点没有尽可能靠向左侧。
     *
     * 提示：
     * 树的结点数在范围  [1, 100] 内。
     * 1 <= Node.val <= 1000
     */

    public class TreeNode {
        public int val;
        public TreeNode left;
        public TreeNode right;

        public TreeNode() {}
        public TreeNode(int x) {
            val = x;
        }
        public TreeNode(int x, TreeNode left, TreeNode right) {
            this.val = x;
            this.left = left;
            this.right = right;
        }
    }

    /**
     * 完全二叉树分析：
     *  左树是满的，右树也是满的，左树高度等于右树高度
     *  左树是完全，右树是满的，但是左树高度比右树高度>1
     *  左树是满的，右树也是满的，但是左树高度比右树高度>1
     *  左树是满的，右树是完全，左树高度等于右树高度
     */
    public boolean isCompleteTree(TreeNode root) {
        return process(root).isCompleted;
    }

    public Info process(TreeNode node) {
        if (node == null) {
            return new Info(true, true, 0);
        }
        Info leftInfo = process(node.left);
        Info rightInfo = process(node.right);

        int height = Math.max(leftInfo.height, rightInfo.height) + 1;

        boolean isFull = leftInfo.isFull && rightInfo.isFull && leftInfo.height == rightInfo.height;

        // 情况1
        boolean isCompleted = isFull;

        if (leftInfo.isCompleted && rightInfo.isFull && leftInfo.height == rightInfo.height + 1) {
            isCompleted = true;
        } else if (leftInfo.isFull && rightInfo.isFull && leftInfo.height == rightInfo.height + 1) {
            isCompleted = true;
        } else if (leftInfo.isFull && rightInfo.isCompleted && leftInfo.height == rightInfo.height) {
            isCompleted = true;
        }
        return new Info(isFull, isCompleted, height);
    }

    public static class Info {
        public boolean isFull;
        public boolean isCompleted;
        public int height;

        public Info(boolean isFull, boolean isCompleted, int height) {
            this.isFull = isFull;
            this.isCompleted = isCompleted;
            this.height = height;
        }
    }
}
