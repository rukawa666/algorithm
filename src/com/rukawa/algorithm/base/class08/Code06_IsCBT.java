package com.rukawa.algorithm.base.class08;

import com.rukawa.algorithm.base.class07.Node;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Created with Intellij IDEA
 *
 * @Author：SuperHai
 * @Date：2020-07-21 23:51
 * @Version：1.0
 */
public class Code06_IsCBT {

    /**
     *
     * 给定一个二叉树的头节点head，返回这颗二叉树是不是完全二叉树
     * 完全二叉树CBT(complete binary tree)
     *
     * @param head
     * @return
     */
    public static boolean isCBT01(Node head) {
        if (head == null) {
            return true;
        }

        Queue<Node> queue = new LinkedList<>();
        // 是否遇到过左右两个孩子不双全的节点
        boolean leaf = false;
        Node l = null;
        Node r = null;
        queue.add(head);
        while (!queue.isEmpty()) {
            head = queue.poll();
            l = head.left;
            r = head.right;

            if (leaf && !(l == null && r == null)   // 一旦我找到左右孩子不双全的节点并且孩子节点不同时为空
                    || (l == null && r != null)) {  // 有右孩子无左孩子
                return false;
            }

            if (l != null) {
                queue.add(l);
            }
            if (r != null) {
                queue.add(r);
            }

            if (l == null || r == null) {
                leaf = true;
            }
        }
        return true;
    }

    public static boolean isCBT02(Node head) {
        if (head == null) {
            return true;
        }
        return process(head).isCBT;
    }

    public static class Info {
        public boolean isFull;
        public boolean isCBT;
        public int height;

        public Info(boolean isFull, boolean isCBT, int height) {
            this.isFull = isFull;
            this.isCBT = isCBT;
            this.height = height;
        }
    }

    public static Info process(Node node) {
        if (node == null) {
            return new Info(true, true, 0);
        }

        Info leftInfo = process(node.left);
        Info rightInfo = process(node.right);

        int height = Math.max(leftInfo.height, rightInfo.height) + 1;
        boolean isFull = leftInfo.isFull && rightInfo.isFull && leftInfo.height == rightInfo.height;
        boolean isCBT = false;
        if (isFull) {
            isCBT = true;
        } else {
            if (leftInfo.isCBT && rightInfo.isCBT) {
                if (leftInfo.isCBT && rightInfo.isFull && leftInfo.height == rightInfo.height + 1) {
                    isCBT = true;
                }

                if (leftInfo.isFull && rightInfo.isFull && leftInfo.height == rightInfo.height + 1) {
                    isCBT = true;
                }

                if (leftInfo.isFull && rightInfo.isCBT && leftInfo.height == rightInfo.height) {
                    isCBT = true;
                }
            }
        }

        return new Info(isFull, isCBT, height);
    }

    // for test
    public static Node generateRandomBST(int maxLevel, int maxValue) {
        return generate(1, maxLevel, maxValue);
    }

    // for test
    public static Node generate(int level, int maxLevel, int maxValue) {
        if (level > maxLevel || Math.random() < 0.5) {
            return null;
        }
        Node head = new Node((int) (Math.random() * maxValue));
        head.left = generate(level + 1, maxLevel, maxValue);
        head.right = generate(level + 1, maxLevel, maxValue);
        return head;
    }

    public static void main(String[] args) {
        int maxLevel = 5;
        int maxValue = 100;
        int testTimes = 1000000;
        for (int i = 0; i < testTimes; i++) {
            Node head = generateRandomBST(maxLevel, maxValue);
            if (isCBT01(head) != isCBT02(head)) {
                System.out.println("Oops!");
            }
        }
        System.out.println("finish!");
    }

}
