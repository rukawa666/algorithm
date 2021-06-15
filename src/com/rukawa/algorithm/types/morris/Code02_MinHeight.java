package com.rukawa.algorithm.types.morris;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @Author: Administrator
 * @Date: 2021/6/15 0015 8:52
 */
public class Code02_MinHeight {

    public static class Node {
        public int value;
        Node left;
        Node right;

        public Node(int data) {
            this.value = data;
        }
    }

    /**
     * 二叉树的最小深度
     */

    public static int minHeight1(Node head) {
        if (head == null) {
            return 0;
        }
        return process1(head);
    }

    // 返回node为头的树，最小深度是多少
    public static int process1(Node node) {
        if (node.left == null && node.right == null) {
            return 1;
        }
        int leftH = Integer.MAX_VALUE;
        if (node.left != null) {
            leftH = process1(node.left);
        }

        int rightH = Integer.MAX_VALUE;
        if (node.right != null) {
            rightH = process1(node.right);
        }
        return Math.min(leftH, rightH) + 1;
    }

    // 根据morris遍历改写
    public static int minHeight2(Node head) {
        return 0;
    }
}
