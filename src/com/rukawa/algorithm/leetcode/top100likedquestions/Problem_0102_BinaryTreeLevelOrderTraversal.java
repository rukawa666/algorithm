package com.rukawa.algorithm.leetcode.top100likedquestions;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * Created with Intellij IDEA
 *
 * @Author：SuperHai
 * @Date：2020-09-21 8:41
 * @Version：1.0
 */
public class Problem_0102_BinaryTreeLevelOrderTraversal {
    /**
     * 二叉树的层序遍历
     * 给你一个二叉树，请你返回其按 层序遍历 得到的节点值。 （即逐层地，从左到右访问所有节点）。
     *
     * 示例：
     * 二叉树：[3,9,20,null,null,15,7],
     *
     *     3
     *    / \
     *   9  20
     *     /  \
     *    15   7
     * 返回其层序遍历结果：
     *
     * [
     *   [3],
     *   [9,20],
     *   [15,7]
     * ]
     */

    public class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
        TreeNode(int x) { val = x; }
    }

    public List<List<Integer>> levelOrder(TreeNode root) {
        List<List<Integer>> res = new ArrayList<>();
        if (root == null) {
            return res;
        }
        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root);
        int size = 0;
        while (!queue.isEmpty()) {
            size = queue.size();
            List<Integer> levelRes = new ArrayList<>();
            for (int i = 0; i < size; i++) {
                TreeNode cur = queue.poll();
                levelRes.add(cur.val);
                if (cur.left != null) {
                    queue.add(cur.left);
                }
                if (cur.right != null) {
                    queue.add(cur.right);
                }
            }
            res.add(levelRes);
        }
        return res;
    }
}
