package com.rukawa.algorithm.leetcode.top100likedquestions;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with Intellij IDEA
 *
 * @Author：SuperHai
 * @Date：2020-09-21 19:56
 * @Version：1.0
 */
public class Problem_0145_BinaryTreePostOrderTraversal {
    /**
     *  二叉树的后序遍历
     *  给定一个二叉树，返回它的 后序 遍历。
     *
     * 示例:
     * 输入: [1,null,2,3]
     *    1
     *     \
     *      2
     *     /
     *    3
     *
     * 输出: [3,2,1]
     * 进阶: 递归算法很简单，你可以通过迭代算法完成吗？
     */

    /**
     * morris后序遍历: 整棵树可以由左树的右边界整体划分，逆序打印
     * 打印时机：能回到自己两次，且第二次回到自己的时候，打印它的左树右边界，逆序打印
     *      1
     *    /   \
     *   2    3
     * /  \  / \
     *4   5 6   7
     *
     *1 2 4 2 5 1 3 6 3 7
     *      ^   ^     ^
     * 2 左树右边界：逆序打印：4
     * 1 左树右边界：逆序打印：5 2
     * 3 左树右边界：逆序打印：6
     * 所有过程结束后：逆序打印整棵树的右边界：7 3 1
     * 后序遍历：4 5 2 6 7 3 1
     * @param root
     */
    public List<Integer> postorderTraversal(TreeNode root) {
        List<Integer> res = new ArrayList<>();
        if (root == null) {
            return res;
        }
        TreeNode cur = root;
        TreeNode mostRight = null;
        while (cur != null) {
            mostRight = cur.left;
            if (mostRight != null) {
                while (mostRight.right != null && mostRight.right != cur) {
                    mostRight = mostRight.right;
                }

                if (mostRight.right == null) {
                    mostRight.right = cur;
                    cur = cur.left;
                    continue;
                } else {
                    mostRight.right = null;
                    // 第二次来到的节点，逆序打印它的左树右边界
                    printEdge(cur.left, res);
                }
            }
            cur = cur.right;
        }
        // 最后打印整棵树的最右边界
        printEdge(root, res);
        return res;
    }

    public static void printEdge(TreeNode head, List<Integer> res) {
        TreeNode tail = reverseEdge(head);
        TreeNode cur = tail;
        while (cur != null) {
            res.add(cur.val);
            cur = cur.right;
        }
        reverseEdge(tail);
    }

    public static TreeNode reverseEdge(TreeNode from) {
        TreeNode pre = null;
        TreeNode next = null;
        while (from != null) {
            next = from.right;
            from.right = pre;
            pre = from;
            from = next;
        }
        return pre;
    }

    public static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        public TreeNode() {
        }

        public TreeNode(int val) {
            this.val = val;
        }

        public TreeNode(int val, TreeNode left, TreeNode right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }
    }
}
