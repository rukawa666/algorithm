package com.rukawa.algorithm.base.class07;

import java.util.Stack;

/**
 * Created with Intellij IDEA
 *
 * @Author：SuperHai
 * @Date：2020-07-19 16:15
 * @Version：1.0
 */
public class Code02_UnRecursiveTraversalBT {

    public static void pre(Node head) {
        System.out.print("pre-order:");
        if (head != null) {
            Stack<Node> stack = new Stack<>();
            stack.add(head);
            while (!stack.isEmpty()) {
                head = stack.pop();
                System.out.println(head.value + " ");
                if (head.right != null) {
                    stack.push(head.right);
                }

                if (head.left != null) {
                    stack.push(head.left);
                }
            }
        }
        System.out.println();
    }

    /**
     * 1. 二叉树的整条左边界全部进入栈，直到遇到空
     * 2. 栈中弹出节点打印，当前节点的右孩子成为cur回到第一步
     *
     * 整个二叉树可以被左边界分解掉
     */
    public static void in(Node head) {
        System.out.print("in-order: ");
        if (head != null) {
            Stack<Node> stack = new Stack<>();
            while (!stack.isEmpty() || head != null) {
                if (head != null) {
                    stack.push(head);
                    head = head.left;
                } else {
                    head = stack.pop();
                    System.out.println(head.value + " ");
                    head = head.right;
                }
            }
        }
        System.out.println();
    }

    public static void pos01(Node head) {
        System.out.print("pos-order: ");
        if (head != null) {
            Stack<Node> stack = new Stack<>();
            Stack<Node> cache = new Stack<>();
            stack.push(head);
            while (!stack.isEmpty()) {
                head = stack.pop();
                cache.push(head);

                if (head.left != null) {
                    stack.push(head.left);
                }

                if (head.right != null) {
                    stack.push(head.right);
                }
            }

            while (!cache.isEmpty()) {
                System.out.println(cache.pop().value + " ");
            }
        }
        System.out.println();
    }

    public static void pos02(Node head) {
        System.out.println("pos-order:");
        if (head != null) {
            Stack<Node> stack = new Stack<>();
            stack.push(head);
            Node cur = null;
            while (!stack.isEmpty()) {
                cur = stack.peek();
                if (cur.left != null && head != cur.left && head != cur.right) {
                    stack.push(cur.left);
                } else if (cur.right != null && head != cur.right) {
                    stack.push(cur.right);
                } else {
                    System.out.println(stack.pop() + " ");
                    head = cur;
                }
            }
        }
    }
}
