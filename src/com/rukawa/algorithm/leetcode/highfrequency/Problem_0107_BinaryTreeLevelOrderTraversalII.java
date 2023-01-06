package com.rukawa.algorithm.leetcode.highfrequency;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * create by hqh on 2022/8/31
 */
public class Problem_0107_BinaryTreeLevelOrderTraversalII {

    /**
     * 给你二叉树的根节点 root ，返回其节点值 自底向上的层序遍历 。 （即按从叶子节点所在层到根节点所在的层，逐层从左向右遍历）
     *
     * 示例 1：
     * 输入：root = [3,9,20,null,null,15,7]
     * 输出：[[15,7],[9,20],[3]]
     *
     * 示例 2：
     * 输入：root = [1]
     * 输出：[[1]]
     *
     * 示例 3：
     * 输入：root = []
     * 输出：[]
     *
     * 提示：
     * 树中节点数目在范围 [0, 2000] 内
     * -1000 <= Node.val <= 1000
     */

    public class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
        TreeNode() {}
        TreeNode(int x) {
            val = x;
        }
        TreeNode(int x, TreeNode left, TreeNode right) {
            this.val = x;
            this.left = left;
            this.right = right;
        }
    }

    public List<List<Integer>> levelOrderBottom(TreeNode root) {
        List<List<Integer>> res = new ArrayList<>();
        if (root == null) {
            return res;
        }
        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root);
        TreeNode cur = null;
        int size = 0;
        while (!queue.isEmpty()) {
            size = queue.size();
            List<Integer> levelList = new ArrayList<>();
            for (int i = 0; i < size; i++) {
                cur = queue.poll();
                levelList.add(cur.val);

                if (cur.left != null) {
                    queue.add(cur.left);
                }

                if (cur.right != null) {
                    queue.add(cur.right);
                }
            }

            res.add(0, levelList);
        }
        return res;
    }
}
