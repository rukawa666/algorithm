package com.rukawa.algorithm.types.bst;

/**
 * Created with Intellij IDEA
 *
 * @Author：SuperHai
 * @Date：2021-01-24 23:36
 * @Version：1.0
 */
public class Code07_SuccessOrNode {
    /**
     * 二叉树的结构如下：
     * class Node {
     *    V value;
     *    Node left;
     *    Node right;
     *    Node parent;
     * }
     * 给你二叉树的某个节点，返回该节点的后继节点
     *
     * 后继节点：在中序遍历中，一个节点的下一个节点称为当前节点的后继节点
     */

    public static class Node {
        int value;
        Node left;
        Node right;
        Node parent;

        public Node(int val) {
            this.value = val;
        }
    }

    public static Node getSuccessorNode(Node node) {
        if (node == null) {
            return node;
        }
        if (node.right != null) {
            return getLeftMost(node.right);
        } else {    // 无右子树
            Node parent = node.parent;
            while (parent != null && parent.right == node) {    // 当前节点是其父亲节点的右孩子
                node = parent;
                parent = node.parent;
            }
            return parent;
        }
    }

    public static Node getLeftMost(Node node) {
        if (node == null) {
            return null;
        }
        while (node.left != null) {
            node = node.left;
        }
        return node;
    }


}
