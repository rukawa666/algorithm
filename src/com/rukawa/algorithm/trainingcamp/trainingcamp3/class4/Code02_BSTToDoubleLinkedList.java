package com.rukawa.algorithm.trainingcamp.trainingcamp3.class4;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Created with Intellij IDEA
 *
 * @Author：SuperHai
 * @Date：2020-08-26 7:46
 * @Version：1.0
 */
public class Code02_BSTToDoubleLinkedList {
    /**
     * 双向链表节点结构和二叉树节点结构是一样的，如果你把last认为是left，next认为是next的话
     *
     * 给定一个搜索二叉树的头结点head，请转化成一条有序的双向链表，并返回链表的头节点
     */
    public static class Node {
        public int value;
        public Node left;
        public Node right;

        public Node(int data) {
            this.value = data;
        }
    }

    public static Node convert1(Node head) {
        Queue<Node> queue = new LinkedList<>();
        inOrderTpQueue(head, queue);
        if (queue.isEmpty()) {
            return null;
        }
        head = queue.poll();
        Node pre = head;
        pre.left = null;
        Node cur = null;
        while (!queue.isEmpty()) {
            cur = queue.poll();
            pre.right = cur;
            cur.left = pre;
            pre = cur;
        }
        pre.right = null;
        return head;
    }

    public static void inOrderTpQueue(Node head, Queue<Node> queue) {
        if (head == null) {
            return;
        }
        inOrderTpQueue(head.left, queue);
        queue.offer(head);
        inOrderTpQueue(head.right, queue);
    }

    public static Node convert2(Node head) {
        if (head == null) {
            return null;
        }
        return process(head).start;
    }

    // 整棵树，串成双向链表，返回头，尾
    public static class Info {
        public Node start;
        public Node end;

        public Info(Node start, Node end) {
            this.start = start;
            this.end = end;
        }
    }

    // 以node为头的整棵搜索二叉树，请全部以有序双向链表的方式，连好
    // 并且返回，整个有序双向链表的头节点和尾节点
    public static Info process(Node node) {
        if (node == null) {
            return new Info(null, null);
        }

        /**
         *           5
         *         /   \
         *       3       7
         *      / \     / \
         *     2   4   6   8
         *
         *  2 - 3 - 4 - 5 - 6 - 7 - 8
         */
        Info leftInfo = process(node.left);
        Info rightInfo = process(node.right);
        if (leftInfo.end != null) {
            leftInfo.end.right = node;
        }
        node.left = leftInfo.end;
        node.right = rightInfo.start;
        if (rightInfo.start != null) {
            rightInfo.start.left = node;
        }

        return new Info(leftInfo.start != null ? leftInfo.start : node, // 整棵树的头
                rightInfo.end != null ? rightInfo.end : node);  // 整棵树的尾
    }

    public static void printBSTInOrder(Node head) {
        System.out.print("BST in-order: ");
        if (head != null) {
            inOrderPrint(head);
        }
        System.out.println();
    }

    public static void inOrderPrint(Node head) {
        if (head == null) {
            return;
        }
        inOrderPrint(head.left);
        System.out.print(head.value + " ");
        inOrderPrint(head.right);
    }

    public static void printDoubleLinkedList(Node head) {
        System.out.print("Double Linked List: ");
        Node end = null;
        while (head != null) {
            System.out.print(head.value + " ");
            end = head;
            head = head.right;
        }
        System.out.print("| ");
        while (end != null) {
            System.out.print(end.value + " ");
            end = end.left;
        }
        System.out.println();
    }

    public static void main(String[] args) {
        Node head = new Node(5);
        head.left = new Node(2);
        head.right = new Node(9);
        head.left.left = new Node(1);
        head.left.right = new Node(3);
        head.left.right.right = new Node(4);
        head.right.left = new Node(7);
        head.right.right = new Node(10);
        head.left.left = new Node(1);
        head.right.left.left = new Node(6);
        head.right.left.right = new Node(8);

        printBSTInOrder(head);
        head = convert1(head);
        printDoubleLinkedList(head);

        head = new Node(5);
        head.left = new Node(2);
        head.right = new Node(9);
        head.left.left = new Node(1);
        head.left.right = new Node(3);
        head.left.right.right = new Node(4);
        head.right.left = new Node(7);
        head.right.right = new Node(10);
        head.left.left = new Node(1);
        head.right.left.left = new Node(6);
        head.right.left.right = new Node(8);

        printBSTInOrder(head);
        head = convert2(head);
        printDoubleLinkedList(head);

    }
}
