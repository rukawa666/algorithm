package com.rukawa.algorithm.leetcode.top100likedquestions;

/**
 * create by hqh on 2022/9/1
 */
public class Problem_0510_InorderSuccessorInBSTII {
    /**
     * 二叉搜索树中的中序后继 II
     * 给定一棵二叉搜索树和其中的一个节点 node ，找到该节点在树中的中序后继。如果节点没有中序后继，请返回 null 。
     *
     * 一个节点 node 的中序后继是键值比 node.val 大所有的节点中键值最小的那个。
     *
     * 你可以直接访问结点，但无法直接访问树。每个节点都会有其父节点的引用。节点 Node 定义如下：
     *
     * class Node {
     *     public int val;
     *     public Node left;
     *     public Node right;
     *     public Node parent;
     * }
     *  
     *
     * 示例 1：
     * 输入：tree = [2,1,3], node = 1
     * 输出：2
     * 解析：1 的中序后继结点是 2 。注意节点和返回值都是 Node 类型的。
     *
     * 示例 2：
     * 输入：tree = [5,3,6,2,4,null,null,1], node = 6
     * 输出：null
     * 解析：该结点没有中序后继，因此返回 null 。
     *
     * 示例 3：
     * 输入：tree = [15,6,18,3,7,17,20,2,4,null,13,null,null,null,null,null,null,null,null,9], node = 15
     * 输出：17
     *
     * 示例 4：
     * 输入：tree = [15,6,18,3,7,17,20,2,4,null,13,null,null,null,null,null,null,null,null,9], node = 13
     * 输出：15
     *
     * 示例 5：
     * 输入：tree = [0], node = 0
     * 输出：null
     *
     * 提示：
     * 树中节点的数目在范围 [1, 104] 内。
     * -105 <= Node.val <= 105
     * 树中各结点的值均保证唯一。
     */

    class Node {
        public int val;
        public Node left;
        public Node right;
        public Node parent;
    }

    public Node inorderSuccessor(Node node) {
        if (node == null) {
            return null;
        }

        if (node.right != null) {
            return getLeftMost(node.right);
        } else {
            Node parent = node.parent;
            while (parent != null && parent.right == node) {
                node = parent;
                parent = node.parent;
            }
            return parent;
        }
    }

    public Node getLeftMost(Node node) {
        if (node == null) {
            return null;
        }
        while (node.left != null) {
            node = node.left;
        }
        return node;
    }
}
