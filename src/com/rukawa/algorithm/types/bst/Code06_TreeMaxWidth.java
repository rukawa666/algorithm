package com.rukawa.algorithm.types.bst;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Created with Intellij IDEA
 *
 * @Author：SuperHai
 * @Date：2021-01-24 22:21
 * @Version：1.0
 */
public class Code06_TreeMaxWidth {

    public static class Node {
        private int value;
        public Node left;
        public Node right;

        public Node(int val) {
            this.value = val;
        }
    }

    /**
     * 求二叉树最宽的层有多少个节点
     */
    public static int maxWidth(Node head) {
        if (head == null) {
            return 0;
        }
        Queue<Node> queue = new LinkedList<>();
        queue.add(head);
        // 当前层，最右节点
        Node curEnd = null;
        // 下一层，最右节点
        Node nextEnd = null;
        int max = 0;
        // 当前层的节点个数
        int curLevelNodes = 0;
        while (!queue.isEmpty()) {
            Node node = queue.poll();
            if (node.left != null) {
                queue.add(node.left);
                nextEnd = node.left;
            }
            if (node.right != null) {
                queue.add(node.right);
                nextEnd = node.right;
            }
            curLevelNodes++;
            if (node == curEnd) {
                max = Math.max(max, curLevelNodes);
                curLevelNodes = 0;
                curEnd = nextEnd;
            }
        }
        return max;
    }
}
