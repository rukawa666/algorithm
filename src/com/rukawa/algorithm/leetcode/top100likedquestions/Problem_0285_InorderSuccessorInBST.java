package com.rukawa.algorithm.leetcode.top100likedquestions;

import com.rukawa.algorithm.base.class07.Code07_SuccessorNode;

/**
 * Created with Intellij IDEA
 *
 * @Author：SuperHai
 * @Date：2020-09-25 21:55
 * @Version：1.0
 */
public class Problem_0285_InorderSuccessorInBST {
    /**
     * 二叉搜索树中的中序后继
     * 给定一棵二叉搜索树和其中的一个节点 p ，找到该节点在树中的中序后继。如果节点没有中序后继，请返回 null 。
     * 节点 p 的后继是值比 p.val 大的节点中键值最小的节点。
     *
     * 示例 1：
     * 输入：root = [2,1,3], p = 1
     * 输出：2
     * 解释：这里 1 的中序后继是 2。请注意 p 和返回值都应是 TreeNode 类型。
     *
     * 示例 2：
     * 输入：root = [5,3,6,2,4,null,null,1], p = 6
     * 输出：null
     * 解释：因为给出的节点没有中序后继，所以答案就返回 null 了。
     *
     * 提示：
     * 树中节点的数目在范围 [1, 104] 内。
     * -105 <= Node.val <= 105
     * 树中各节点的值均保证唯一。
     */
    public static class TreeNode {
        public int val;
        public TreeNode left;
        public TreeNode right;

        public TreeNode(int x) {
            this.val = x;
        }
    }

    public TreeNode inorderSuccessor(TreeNode root, TreeNode p) {
        if (root == null || p == null) {
            return null;
        }
        if (p.right != null) {
            return getLeftMost(p.right);
        }

        TreeNode cur = root;
        TreeNode successor = null;
        while (cur != null) {
            if (cur.val > p.val) {
                successor = cur;
                cur = cur.left;
            } else {
                cur = cur.right;
            }
        }
        return successor;
    }

    public TreeNode getLeftMost(TreeNode node) {
        if (node == null) {
            return null;
        }
        while (node.left != null) {
            node = node.left;
        }
        return node;
    }
}
