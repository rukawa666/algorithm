package com.rukawa.algorithm.types.bst;

/**
 * Created with Intellij IDEA
 *
 * @Author：SuperHai
 * @Date：2021-01-26 7:40
 * @Version：1.0
 */
public class Code_005_MaxDistance {

    public static class Node {
        public int value;
        public Node left;
        public Node right;

        public Node(int data) {
            this.value = data;
        }
    }

    /**
     * 给定一棵二叉树的头节点head，任何两个节点之间都存在距离，
     * 返回整棵树的最大距离
     */
    public static int maxDistance(Node head) {
        /**
         * 思路：
         *   和X无关：
         *      1、左树的最大距离
         *      2、右树的最大距离
         *   和X有关：
         *      1、X左树与x最远距离(左树高度) + X右树与x最远距离(右树高度) + 1
         */
        return process(head).maxDistance;
    }

    public static class Info {
        public int maxDistance;
        public int height;

        public Info(int maxDistance, int height) {
            this.maxDistance = maxDistance;
            this.height = height;
        }
    }

    public static Info process(Node x) {
        if (x == null) {
            return new Info(0, 0);
        }
        Info leftInfo = process(x.left);
        Info rightInfo = process(x.right);
        int height = Math.max(leftInfo.height, rightInfo.height) + 1;
        int maxDistance = Math.max(Math.max(leftInfo.maxDistance, rightInfo.maxDistance), leftInfo.height + rightInfo.height + 1);
        return new Info(maxDistance, height);
    }
}
