package com.rukawa.algorithm.trainingcamp.trainingcamp1.class6;

/**
 * Created with Intellij IDEA
 *
 * @Author：SuperHai
 * @Date：2020-08-04 23:29
 * @Version：1.0
 */
public class Code05_MinHeight {

    public static class Node {
        public int value;
        public Node left;
        public Node right;

        public Node(int value) {
            this.value = value;
        }
    }

    public static int minHeight1(Node head) {
        if (head == null) {
            return 0;
        }
        return process1(head);
    }

    public static int process1(Node node) {
        if (node.left == null && node.right == null) {
            return 1;
        }
        // 左右子树起码有一个不为空
        int leftH = Integer.MAX_VALUE;
        if (node.left != null) {
            leftH = process1(node.left);
        }
        int rightH = Integer.MAX_VALUE;
        if (node.right != null) {
            rightH = process1(node.right);
        }
        return 1 + Math.min(leftH, rightH);
    }

    /**
     * 1、当前节点的高度 -> curLevel
     * 2、如果此时是叶节点，记录此时的高度 -> minHeight
     *
     * curLevel:1、如果是第一次来到当前节点，则curLevel++
     *          2、如果是第二次来到当前节点，则需要curLevel - 左树最大高度
     * @param head
     * @return
     */
    public static int minHeight2(Node head) {
        if (head == null) {
            return 0;
        }
        Node cur = head;
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
}
