package com.rukawa.algorithm.leetcode.highfrequency;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with Intellij IDEA
 *
 * @Author：SuperHai
 * @Date：2020-09-21 19:55
 * @Version：1.0
 */
public class Problem_0144_BinaryTreePreOrderTraversal {
    /**
     * 二叉树的前序遍历
     * 给定一个二叉树，返回它的 前序 遍历。
     *
     *  示例:
     * 输入: [1,null,2,3]
     *    1
     *     \
     *      2
     *     /
     *    3
     *
     * 输出: [1,2,3]
     * 进阶: 递归算法很简单，你可以通过迭代算法完成吗？
     */
    public List<Integer> preorderTraversal(TreeNode root) {
        List<Integer> res = new ArrayList<>();
        if (root == null) {
            return res;
        }
        TreeNode cur = root;
        TreeNode mostRight = null;
        while (cur != null) {
            mostRight = cur.left;
            if(mostRight != null) {
                while(mostRight.right != null && mostRight.right != cur) {
                    mostRight = mostRight.right;
                }

                if(mostRight.right == null) {
                    mostRight.right = cur;
                    res.add(cur.val);
                    cur = cur.left;
                    continue;
                } else {
                    mostRight.right = null;
                }
            } else{
                res.add(cur.val);
            }
            cur = cur.right;
        }
        return res;
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
