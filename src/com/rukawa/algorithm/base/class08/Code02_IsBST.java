package com.rukawa.algorithm.base.class08;

/**
 * Created with Intellij IDEA
 *
 * @Author：SuperHai
 * @Date：2020-07-21 6:53
 * @Version：1.0
 */
public class Code02_IsBST {

    /**
     * 给定一个二叉树的头节点，判断该树是不是搜索二叉树
     * 搜索二叉树（Binary Search Tree）：每一棵子树，左孩子节点的值小于该节点的值，右孩子的值大于该节点的值
     * 经典的搜索二叉树，没有重复值
     */

    public static class TreeNode {
        public int value;
        public TreeNode left;
        public TreeNode right;

        public TreeNode(int val) {
            this.value = val;
        }
    }

    /**
     * x的左树是搜索二叉树
     * x的右树是搜索二叉树
     * x的左树上的最大值必须小于x的值
     * x的右树上的最小值必须大于x的值
     */
    public static boolean isValidBST(TreeNode head) {
        if (head == null) {
            return true;
        }
        return process(head).isBST;
    }

    public static Info process(TreeNode node) {
        if (node == null) {
            return null;
        }
        Info leftInfo = process(node.left);
        Info rightInfo = process(node.right);
        boolean isBST = true;
        int max = node.value;
        int min = node.value;
        if (leftInfo != null) {
            max = Math.max(max, leftInfo.max);
            min = Math.min(min, leftInfo.min);
        }
        if (rightInfo != null) {
            max = Math.max(max, rightInfo.max);
            min = Math.min(min, rightInfo.min);
        }

        if (leftInfo != null && !leftInfo.isBST) {
            isBST = false;
        }

        if (rightInfo != null && !rightInfo.isBST) {
            isBST = false;
        }

        if (leftInfo != null && leftInfo.max >= node.value) {
            isBST = false;
        }

        if (rightInfo != null && rightInfo.min <= node.value) {
            isBST = false;
        }
        return new Info(isBST, min, max);
    }

    // 左、右要求返回一样，Info信息返回的结构体
    public static class Info {
        public boolean isBST;
        public int min;
        public int max;

        public Info(boolean i, int min, int max) {
            this.isBST = i;
            this.min = min;
            this.max = max;
        }
    }
}
