package com.rukawa.algorithm.base.class07_tree;

/**
 * Created with Intellij IDEA
 *
 * @Author：SuperHai
 * @Date：2020-07-20 23:41
 * @Version：1.0
 */
public class Code07_SuccessorNode {

    /**
     * 给定如下二叉树的结构定义
     * 给你二叉树的某个节点，返回该节点的后继节点
     */
    public static class Node {
        public int value;
        public Node left;
        public Node right;
        public Node parent;

        public Node(int value) {
            this.value = value;
        }
    }

    /**
     * 什么是后继节点？
     *  如果生成一个二叉树的中序遍历，x节点的在一个下一个节点是x节点的后继节点。如果中序遍历查后继节点，时间复杂度是O(N)
     */

    // 中序遍历，获取一个节点的后继节点
    // O(k) k的长度是x节点到后继节点的真实距离
    public static Node getSuccessorNode(Node node) {
        if (node == null) {
            return null;
        }
        /**
         *        a
         *      /   \
         *     b     c
         *   / \    / \
         * e   f   g   h
         *
         * 中序遍历：e, b, f, a, g, c, h
         * a的后继节点是g，如果有右树，找到右树最左的节点
         * f的后继节点是b，往上找，parent不为空且parent.right=node截止，此时的parent是后继节点
         */
        // 如果有右子树，则该节点的后继节点是右树最左子树
        if (node.right != null) {
            return getLeftMost(node.right);
        } else {    // 如果该节点没有右树，则只要该节点是父节点的左孩子即可
            Node parent = node.parent;
            while (parent != null && parent.right == node) {
                node = parent;
                parent = node.parent;
            }
            return parent;
        }
    }

    // 中序遍历，获取节点的前驱结点
    public static Node getPrecursorNode(Node node) {
        if (node == null) {
            return null;
        }

        if (node.left != null) {
            return getRightMost(node.left);
        } else {
            Node parent = node.parent;      // 如果该节点没有左子树，则只要该节点是父节点的右孩子即可
            while (parent != null && parent.left == node) {
                node = parent;
                parent = node.parent;
            }
            return parent;
        }
    }

    public static Node getLeftMost(Node node) {
        if (node == null) {
            return null;
        }

        while (node.left != null) {
            node = node.left;
        }
        return node;
    }

    public static Node getRightMost(Node node) {
        if (node == null) {
            return null;
        }

        while (node.right != null) {
            node = node.right;
        }
        return node;
    }

    public static void main(String[] args) {
        Node head = new Node(6);
        head.parent = null;
        head.left = new Node(3);
        head.left.parent = head;
        head.left.left = new Node(1);
        head.left.left.parent = head.left;
        head.left.left.right = new Node(2);
        head.left.left.right.parent = head.left.left;
        head.left.right = new Node(4);
        head.left.right.parent = head.left;
        head.left.right.right = new Node(5);
        head.left.right.right.parent = head.left.right;
        head.right = new Node(9);
        head.right.parent = head;
        head.right.left = new Node(8);
        head.right.left.parent = head.right;
        head.right.left.left = new Node(7);
        head.right.left.left.parent = head.right.left;
        head.right.right = new Node(10);
        head.right.right.parent = head.right;

        Node test = head.left.left;
        System.out.println(test.value + " next: " + getSuccessorNode(test).value);
        test = head.left.left.right;
        System.out.println(test.value + " next: " + getSuccessorNode(test).value);
        test = head.left;
        System.out.println(test.value + " next: " + getSuccessorNode(test).value);
        test = head.left.right;
        System.out.println(test.value + " next: " + getSuccessorNode(test).value);
        test = head.left.right.right;
        System.out.println(test.value + " next: " + getSuccessorNode(test).value);
        test = head;
        System.out.println(test.value + " next: " + getSuccessorNode(test).value);
        test = head.right.left.left;
        System.out.println(test.value + " next: " + getSuccessorNode(test).value);
        test = head.right.left;
        System.out.println(test.value + " next: " + getSuccessorNode(test).value);
        test = head.right;
        System.out.println(test.value + " next: " + getSuccessorNode(test).value);
        test = head.right.right; // 10's next is null
        System.out.println(test.value + " next: " + getSuccessorNode(test));

    }
}
