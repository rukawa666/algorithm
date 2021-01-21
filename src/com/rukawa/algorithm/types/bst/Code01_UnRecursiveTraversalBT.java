package com.rukawa.algorithm.types.bst;

import java.util.Stack;

/**
 * Created with Intellij IDEA
 *
 * @Author：SuperHai
 * @Date：2021-01-21 23:52
 * @Version：1.0
 */
public class Code01_UnRecursiveTraversalBT {
    /**
     * 非递归方式实现二叉树的先序、中序、后序遍历
     * 1、任何递归函数都可以改为非递归
     * 2、自己设计压栈实现
     */

    public static class Node {
        public int value;
        public Node left;
        public Node right;

        public Node(int val) {
            this.value = val;
        }
    }

    // 非递归先序遍历  头 -> 左 -> 右
    public static void pre(Node head) {
        System.out.println("pre - order");
        if (head != null) {
            Stack<Node> stack = new Stack<>();
            stack.push(head);
            while (!stack.isEmpty()) {
                head = stack.pop();
                System.out.print(head.value + " ");
                if (head.right != null) {
                    stack.push(head.right);
                }
                if (head.left != null) {
                    stack.push(head.left);
                }
            }
            System.out.println("end...");
        }
    }

    // 非递归实现后序遍历  左 -> 右 -> 头
    public static void pos1(Node head) {
        System.out.println("pos - order");
        if (head != null) {
            Stack<Node> s1 = new Stack<>();
            Stack<Node> s2 = new Stack<>();
            s1.push(head);
            while (!s1.isEmpty()) {
                head = s1.pop();
                s2.push(head);
                if (head.left != null) {
                    s1.push(head.left);
                }
                if (head.right != null) {
                    s1.push(head.right);
                }
            }

            while (!s2.isEmpty()) {
                System.out.print(s2.pop().value + " ");
            }
            System.out.println("end...");
        }
    }

    // 后序遍历，极客写法，用一个栈实现
    public static void pos2(Node head) {
        /**
         * 思路：      1
         *          /  \
         *         2    3
         *        / \  / \
         *       4  5 6  7
         */
        System.out.println("pos - order");
        if (head != null) {
            Stack<Node> stack = new Stack<>();
            stack.push(head);
            Node c = null;
            while (!stack.isEmpty()) {
                c = stack.peek();
                if (c.left != null && head != c.left && head != c.right) {  // 左树没处理完，先处理完左树
                    stack.push(c.left);
                } else if (c.right != null && head != c.right) {    // 右树没处理完，去处理右树
                    stack.push(c.right);
                } else {    // 如果左右两颗子树都处理完，处理头节点  head = c 用来跟踪左右子树有无处理完毕，如果右树处理完，则说明左树也处理完毕
                    System.out.print(stack.pop().value + " ");
                    head = c;   // 此处用head跟踪弹出的节点
                }
            }
        }
        System.out.println("end...");
    }


    // 中序遍历 左 -> 头 -> 右
    public static void in(Node head) {
        /**
         * 流程：1、从头节点开始，把整条左边界依次压入栈中(弹出的时候先弹出左，再弹出头)
         *      2、如果头节点为null，来到右节点，有右节点则加入，如果没有右节点则继续弹出
         */
        System.out.println("in - order");
        if (head != null) {
            Stack<Node> stack = new Stack<>();
            while (!stack.isEmpty() || head != null) {
                if (head != null) {
                    stack.push(head);
                    head = head.left;
                } else {
                    head = stack.pop();
                    System.out.print(head.value + " ");
                    head = head.right;
                }
            }
        }
        System.out.println("end...");
    }
}
