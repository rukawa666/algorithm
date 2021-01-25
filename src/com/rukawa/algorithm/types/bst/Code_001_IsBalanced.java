package com.rukawa.algorithm.types.bst;

/**
 * Created with Intellij IDEA
 *
 * @Author：SuperHai
 * @Date：2021-01-25 7:50
 * @Version：1.0
 */
public class Code_001_IsBalanced {

    public static class Node {
        public int value;
        public Node left;
        public Node right;

        public Node(int val) {
            this.value = val;
        }
    }

    /**
     * 判断二叉树是否是平衡二叉树
     *
     * 平衡二叉树？在二叉树中，每一棵子树的左树最大高度和右树的最大高度相差的绝对值不超过1
     */

    public static boolean isBST(Node head) {
        return process(head).isBalanced;
    }

    public static Info process(Node x) {
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
