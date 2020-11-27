package com.rukawa.algorithm.trainingcamp.trainingcamp3.class5;

/**
 * Created with Intellij IDEA
 *
 * @Author：SuperHai
 * @Date：2020-08-28 7:16
 * @Version：1.0
 */
public class Code03_CompleteTreeNodeNumber {
    /**
     * 完全二叉树的节点个数
     */
    public static class Node {
        public int value;
        public Node left;
        public Node right;

        public Node(int value) {
            this.value = value;
        }
    }

    /**
     * 请保证head为头的树，是完全二叉树
     * 时间复杂度O((logN)^2)
     * @param head
     * @return
     */
    public static int nodeNum(Node head) {
        if (head == null) {
            return 0;
        }
        return bs(head, 1, mostLeftLevel(head, 1));
    }

    /**
     * node在第level层，h是总的深度(h永远不变，全局变量)
     * 以node为头的完全二叉树，节点个数是多少
     * @param node
     * @param level
     * @param h
     * @return
     */
    public static int bs(Node node, int level, int h) {
        if (level == h) {
            return 1;
        }
        // 满二叉树的节点个数2^h - 1
        // 右树的最左节点到达最深层，此时左树是满二叉树，节点个数为(2^(h-1))
        if (mostLeftLevel(node.right, level + 1) == h) {
            // 1 << (h - level) -> 左树是满二叉树的节点个数，右侧继续去递归
            return (1 << (h - level)) + bs(node.right, level + 1, h);
        } else {   // 右树的最大深度没有来到整棵树的最大高度，说明，右树是满二叉树
            // 此时比左树满的情况少一层
            return (1 << (h - level - 1)) + bs(node.left, level + 1, h);
        }
    }

    /**
     * 如果node在第level层
     * 求以node为头的子树，最大深度是多少
     * node为头的子树，一定是完全二叉树
     * @param node
     * @param level
     * @return
     */
    public static int mostLeftLevel(Node node, int level) {
        while (node != null) {
            level++;
            node = node.left;
        }
        return level - 1;
    }

    public static void main(String[] args) {
        Node head = new Node(1);
        head.left = new Node(2);
        head.right = new Node(3);
        head.left.left = new Node(4);
        head.left.right = new Node(5);
        head.right.left = new Node(6);
        System.out.println(nodeNum(head));

    }
}
