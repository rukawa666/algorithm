package com.rukawa.algorithm.leetcode.top100likedquestions;

/**
 * Created with Intellij IDEA
 *
 * @Author：SuperHai
 * @Date：2020-09-21 22:33
 * @Version：1.0
 */
public class Problem_0111_MinimumDepthOfBinaryTree {
    /**
     * 二叉树的最小深度
     * 给定一个二叉树，找出其最小深度。
     * 最小深度是从根节点到最近叶子节点的最短路径上的节点数量。
     * 说明: 叶子节点是指没有子节点的节点。
     *
     * 示例:
     * 给定二叉树 [3,9,20,null,null,15,7],
     *     3
     *    / \
     *   9  20
     *     /  \
     *    15   7
     * 返回它的最小深度  2.
     */
    public int minDepth(TreeNode root) {
        if (root == null) {
            return 0;
        }
        TreeNode cur = root;
        TreeNode mostRight = null;
        int curLevel = 0;
        int minHeight = Integer.MAX_VALUE;
        while (cur != null) {
            mostRight = cur.left;
            if (mostRight != null) {
                int rightBoardSize = 1;
                while (mostRight.right != null && mostRight.right != cur) {
                    rightBoardSize++;
                    mostRight = mostRight.right;
                }

                if (mostRight.right == null) {  // 第一次到达
                    curLevel++;
                    mostRight.right = null;
                    cur = cur.left;
                    continue;
                } else {    // 第二次到达
                    if (mostRight.left == null) {
                        minHeight = Math.min(minHeight, curLevel);
                    }
                    curLevel -= rightBoardSize;
                    mostRight.right = null;
                }
            } else { // 只有一次到达
                curLevel++;
            }
            cur = cur.right;
        }

        int finalRight = 1;
        cur = root;
        while (cur.right != null) {
            finalRight++;
            cur = cur.right;
        }

        if (cur.left == null && cur.right == null) {
            minHeight = Math.min(minHeight, finalRight);
        }
        return minHeight;
    }

    public int minDepth01(TreeNode root) {
        if(root == null) {
            return 0;
        }
        return process(root);
    }

    public static int process(TreeNode head) {
        if (head.left == null && head.right == null) {
            return 1;
        }

        Integer leftH = Integer.MAX_VALUE;
        if(head.left != null) {
            leftH = process(head.left);
        }

        Integer rightH = Integer.MAX_VALUE;
        if(head.right != null) {
            rightH = process(head.right);
        }

        return 1 + Math.min(leftH, rightH);
    }


    public static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        public TreeNode(int val) {
            this.val = val;
        }
    }
}
