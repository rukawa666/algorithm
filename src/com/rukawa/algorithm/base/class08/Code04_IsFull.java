package com.rukawa.algorithm.base.class08;

import com.rukawa.algorithm.base.class07.Node;

/**
 * Created with Intellij IDEA
 *
 * @Author：SuperHai
 * @Date：2020-07-21 9:25
 * @Version：1.0
 */
public class Code04_IsFull {
    /**
     * 给定一个二叉树的头节点，判断该树是不是满二叉树
     * 满二叉树：如果这棵树的高度是h，节点数量一定是2^h-1
     */

    /**
     * 分析：根据满二叉树的性质，需要知道高度和节点数量
     */
    public static boolean isFull(Node head) {
        if (head == null) {
            return true;
        }
        Info info = process(head);
        return info.nodes == 1 << (info.height) - 1;
    }


    public static Info process(Node node) {
        if (node == null) {
            return new Info(0, 0);
        }
        Info leftInfo = process(node.left);
        Info rightInfo = process(node.right);
        int height = Math.max(leftInfo.height, rightInfo.height) + 1;
        int nodes = leftInfo.nodes + rightInfo.nodes;
        return new Info(height, nodes);
    }

    public static class Info {
        public int height;
        public int nodes;

        public Info(int h, int n) {
            this.height = h;
            this.nodes = n;
        }
    }
}
