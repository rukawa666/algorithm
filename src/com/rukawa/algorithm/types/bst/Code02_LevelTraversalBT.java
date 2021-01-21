package com.rukawa.algorithm.types.bst;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Created with Intellij IDEA
 *
 * @Author：SuperHai
 * @Date：2021-01-22 7:53
 * @Version：1.0
 */
public class Code02_LevelTraversalBT {

    public static class Node {
        public int value;
        public Node left;
        public Node right;

        public Node(int val) {
            this.value = val;
        }
    }

    /**
     * 二叉树按层遍历
     */
    public static void level(Node head) {
        if (head == null) {
            return;
        }
        Queue<Node> queue = new LinkedList<>();
        queue.add(head);
        while (!queue.isEmpty()) {
            Node cur = queue.poll();
            System.out.print(cur.value + " ");
            if (cur.left != null) {
                queue.add(cur.left);
            }
            if (cur.right != null) {
                queue.add(cur.right);
            }
        }
    }
}
