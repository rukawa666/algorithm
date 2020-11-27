package com.rukawa.algorithm.trainingcamp.top100likedquestions;

/**
 * Created with Intellij IDEA
 *
 * @Author：SuperHai
 * @Date：2020-09-19 9:07
 * @Version：1.0
 */
public class Problem_0124_BinaryTreeMaximumPathSum {
    /**
     * 给定一个非空二叉树，返回其最大路径和。
     * 本题中，路径被定义为一条从树中任意节点出发，沿父节点-子节点连接，达到任意节点的序列。该路径至少包含一个节点，且不一定经过根节点。
     *
     * 示例 1：
     * 输入：[1,2,3]
     *        1
     *       / \
     *      2   3
     * 输出：6
     *
     * 示例 2：
     * 输入：[-10,9,20,null,null,15,7]
     *    -10
     *    / \
     *   9  20
     *     /  \
     *    15   7
     * 输出：42
     */
    public static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
        TreeNode(int x) {
          this.val = x;
        }
    }

    public int maxPathSum(TreeNode root) {
        if (root == null) {
            return 0;
        }
        return process(root).maxPathSum;
    }

    public static Info process(TreeNode node) {
        if (node == null) {
            return null;
        }
        Info leftInfo = process(node.left);
        Info rightInfo = process(node.right);

        int p1 = Integer.MIN_VALUE;
        if (leftInfo != null) {
            p1 = leftInfo.maxPathSum;
        }

        int p2 = Integer.MIN_VALUE;
        if (rightInfo != null) {
            p2 = rightInfo.maxPathSum;
        }

        int p3 = node.val;

        int p4 = Integer.MIN_VALUE;
        if (leftInfo != null) {
            p4 = node.val + leftInfo.maxPathSumFromHead;
        }

        int p5 = Integer.MIN_VALUE;
        if (rightInfo != null) {
            p5 = node.val + rightInfo.maxPathSumFromHead;
        }

        int p6 = Integer.MIN_VALUE;
        if (leftInfo != null && rightInfo != null) {
            p6 = node.val + leftInfo.maxPathSumFromHead + rightInfo.maxPathSumFromHead;
        }

        int maxPathSum = Math.max(Math.max(Math.max(p1, p2), Math.max(p3, p4)), Math.max(p5, p6));
        int maxPathSumFromHead = Math.max(Math.max(p3, p4), p5);

        return new Info(maxPathSumFromHead, maxPathSum);
    }

    public static class Info {
        public int maxPathSumFromHead;
        public int maxPathSum;

        public Info(int maxPathSumFromHead, int maxPathSum) {
            this.maxPathSumFromHead = maxPathSumFromHead;
            this.maxPathSum = maxPathSum;
        }
    }
}
