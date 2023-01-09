package com.rukawa.algorithm.base.class08_tree;

import com.rukawa.algorithm.base.class07_tree.Node;

/**
 * Created with Intellij IDEA
 *
 * @Author：SuperHai
 * @Date：2020-07-21 23:34
 * @Version：1.0
 */
public class Code06_MaxSubBSTHead {

    /**
     * 给定一个二叉树的头节点head
     * 返回这颗二叉树中最大的二叉搜索子树的头节点
     */
    /**
     * 最大的二叉搜索子树分析：
     *   1.不包含head，左树上最大的二叉搜索子树的头节点，右树上最大的二叉搜索子树的头节点
     *   2.包含head头节点，左树是二叉搜索子树，右树也是二叉搜索子树，左树的最大值小于head的值，右树的最小值大于head的值
     *
     * 需要以下几个信息：
     *  1.左树是否是二叉搜索树
     *  2.右树是否是二叉搜索树
     *  3.左树的最大值小于当前节点的值
     *  4.右树的最小值大于当前节点的值
     *  5.二叉搜索树的节点数量
     *  6.当前二叉树的节点数量
     *
     *  二叉搜素树的节点数量==当前二叉树的节点数量  验证是否是搜索二叉树
     */
    public static Node maxSubBSTHead02(Node node) {
        if (node == null) {
            return null;
        }

        return null;
    }

    public static Info process(Node node) {
        if (node == null) {
            return null;
        }
        Info leftInfo = process(node.left);
        Info rightInfo = process(node.right);
        int max = node.value;
        int min = node.value;
        if (leftInfo != null) {
            max = Math.max(max, leftInfo.max);
            min = Math.min(min, rightInfo.min);
        }

        if (rightInfo != null) {
            max = Math.max(max, rightInfo.max);
            min = Math.min(min, rightInfo.min);
        }


        return new Info(null, 0, 0, 0);
    }

    public static class Info {
        public Node maxSubBSTHead;
        public int maxSubBSTSize;
        public int max;
        public int min;

        public Info(Node head, int size, int max, int min) {
            this.maxSubBSTHead = head;
            this.maxSubBSTSize = size;
            this.max = max;
            this.min = min;
        }
    }



    public static Info process2(Node node) {
        if (node == null) {
            return null;
        }

        Info leftInfo = process(node.left);
        Info rightInfo = process(node.right);

        Node maxSubBSTHead = null;
        int maxSubBSTSize = 0;
        int max = node.value;
        int min = node.value;

        if (leftInfo != null) {
            max = Math.max(max, leftInfo.max);
            min = Math.min(min, leftInfo.min);
            maxSubBSTHead = leftInfo.maxSubBSTHead;
            maxSubBSTSize = leftInfo.maxSubBSTSize;
        }

        if (rightInfo != null) {
            max = Math.max(max, rightInfo.max);
            min = Math.min(min, rightInfo.min);
            if (rightInfo.maxSubBSTSize > maxSubBSTSize) {
                maxSubBSTHead = rightInfo.maxSubBSTHead;
                maxSubBSTSize = rightInfo.maxSubBSTSize;
            }
        }

        if ((leftInfo == null ? true : (leftInfo.maxSubBSTHead == node.left && leftInfo.max < node.value))
                && (rightInfo == null ? true : (rightInfo.maxSubBSTHead == node.right && rightInfo.min > node.value))) {
            maxSubBSTHead = node;
            maxSubBSTSize = (leftInfo == null ? 0 : leftInfo.maxSubBSTSize)
                    + (rightInfo == null ? 0 : rightInfo.maxSubBSTSize)
                    + 1;
        }

        return new Info(maxSubBSTHead, maxSubBSTSize, max, min);



    }
}
