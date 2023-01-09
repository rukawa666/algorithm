package com.rukawa.algorithm.base.class07_tree;

/**
 * Created with Intellij IDEA
 *
 * @Author：SuperHai
 * @Date：2020-07-19 13:00
 * @Version：1.0
 */
public class Code01_RecursiveTraversalBT {

    public static void pre(Node head) {
        if (head == null) {
            return;
        }
        System.out.println(head.value);
        pre(head.left);
        pre(head.right);
    }

    public static void in(Node head) {
        if (head == null) {
            return;
        }
        pre(head.left);
        System.out.println(head.value);
        pre(head.right);
    }

    public static void post(Node head) {
        if (head == null) {
            return;
        }
        pre(head.left);
        pre(head.right);
        System.out.println(head.value);
    }
}
