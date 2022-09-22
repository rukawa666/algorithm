package com.rukawa.algorithm.base.class22;

/**
 * create by hqh on 2022/9/19
 */
public class Code03_MinHeight {
    /**
     * 给定一个二叉树的头节点head
     * 返回以这棵树为头的树中，最小深度是多少
     */

    // 二叉树的递归套路
    public static int minHeight1(Node head) {
        if (head == null) {
            return 0;
        }
        return process1(head);
    }

    // 返回以x为头节点的情况下，最小深度是多少
    public static int process1(Node x) {
        if (x.left == null && x.right == null) {
            return 0;
        }
        int leftHeight = Integer.MAX_VALUE;
        if (x.left != null) {
            leftHeight = process1(x.left);
        }
        int rightHeight = Integer.MAX_VALUE;
        if (x.right != null) {
            rightHeight = process1(x.right);
        }
        return Math.min(leftHeight, rightHeight) + 1;
    }

    // 根据morris遍历改写
    public static int minHeight2(Node head) {
        if (head == null) {
            return 0;
        }
        Node cur = head;
        Node pre = head;
        Node mostRight = null;
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

                if (mostRight.right == null) {
                    curLevel++;
                    mostRight.right = cur;
                    cur = cur.left;
                    continue;
                } else { // 第二次到达
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
        cur = head;
        while (cur.right != null) {
            finalRight++;
            cur = cur.right;
        }

        if (cur.left == null && cur.right == null) {
            minHeight = Math.min(minHeight, finalRight);
        }
        return minHeight;
    }

    public static class Node {
        public int val;
        public Node left;
        public Node right;

        public Node(int x) {
            this.val = x;
        }
    }
}
