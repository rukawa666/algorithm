package com.rukawa.algorithm.leetcode.top100likedquestions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

/**
 * Created with Intellij IDEA
 *
 * @Author：SuperHai
 * @Date：2020-09-19 9:07
 * @Version：1.0
 */
public class Problem_0124_BinaryTreeMaximumPathSum {
    /**
     * 给定一个非空二叉树，返回其最大路径和。
     * 本题中，路径被定义为一条从树中任意节点出发，沿父节点-子节点连接，达到任意节点的序列。该路径至少包含一个节点，且不一定经过根节点。
     *
     * 示例 1：
     * 输入：[1,2,3]
     *        1
     *       / \
     *      2   3
     * 输出：6
     *
     * 示例 2：
     * 输入：[-10,9,20,null,null,15,7]
     *    -10
     *    / \
     *   9  20
     *     /  \
     *    15   7
     * 输出：42
     */
    public static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
        TreeNode(int x) {
          this.val = x;
        }
    }

    public int maxPathSum(TreeNode root) {
        if (root == null) {
            return 0;
        }
        return process(root).maxPathSum;
    }

    public Info process(TreeNode x) {
        /**
         * 情况分析：
         *  1. 和x无关
         *      a. 左树上的最大路径和
         *      b. 右树上的最大路径和
         *  2. 和x有关
         *      a. 只包含头节点，左树和左树的最大路径和是负数
         *      b. 从头出发的左树的最大路径和
         *      c. 从头出发的右树的最大路径和
         *      d. 从头出发到左树的最大路径和+到右树的最大路径和
         */
        if (x == null) {
            return null;
        }
        Info leftInfo = process(x.left);
        Info rightInfo = process(x.right);

        // 只有x x往左走 x往右走
        int maxPathSumFromHead = x.val;
        if (leftInfo != null) {
            maxPathSumFromHead = Math.max(maxPathSumFromHead, x.val + leftInfo.maxPathSumFromHead);
        }
        if (rightInfo != null) {
            maxPathSumFromHead = Math.max(maxPathSumFromHead, x.val + rightInfo.maxPathSumFromHead);
        }
        // 只有x 左树的最大路径和 右树的最大路径和 x往左走 x往右走 x往两边走
        int maxPathSum = x.val;
        if (leftInfo != null) {
            maxPathSum = Math.max(maxPathSum, leftInfo.maxPathSum);
        }

        if (rightInfo != null) {
            maxPathSum = Math.max(maxPathSum, rightInfo.maxPathSum);
        }
        maxPathSum = Math.max(maxPathSum, maxPathSumFromHead);
        if (leftInfo != null && rightInfo != null && leftInfo.maxPathSumFromHead > 0 && rightInfo.maxPathSumFromHead > 0) {
            maxPathSum = Math.max(maxPathSum, leftInfo.maxPathSumFromHead + rightInfo.maxPathSumFromHead + x.val);
        }
        return new Info(maxPathSumFromHead, maxPathSum);
    }

    public static class Info {
        // 从头出发的最大路径和
        public int maxPathSumFromHead;
        // 不从头出发的最大路径和
        public int maxPathSum;

        public Info(int maxPathSumFromHead, int maxPathSum) {
            this.maxPathSumFromHead = maxPathSumFromHead;
            this.maxPathSum = maxPathSum;
        }
    }

    // 加强版
    // 给定一个非空二叉树，返回其最大路径和还要返回路径
    // 任意节点出发，沿父节点-子节点连接，达到任意节点的序列
    public List<TreeNode> getMaxSumPath(TreeNode root) {
        List<TreeNode> res = new ArrayList<>();
        if (root != null) {
            Data data = func(root);
            HashMap<TreeNode, TreeNode> map = new HashMap<>();
            fatherMap(root, map);
            fillPath(map, data.from, data.to, res);
        }
        return res;
    }

    public Data func(TreeNode x) {
        if (x == null) {
            return null;
        }
        Data leftData = func(x.left);
        Data rightData = func(x.right);
        // 只有x x往左走 x往右走
        int maxPathSumFromHead = x.val;
        TreeNode end = x;
        if ((leftData != null && leftData.maxPathSumFromHead > maxPathSumFromHead)
                && (rightData == null || leftData.maxPathSumFromHead > rightData.maxPathSumFromHead)) {
            maxPathSumFromHead += leftData.maxPathSumFromHead;
            end = leftData.end;

        }
        if (rightData != null && rightData.maxPathSumFromHead > maxPathSumFromHead
                && (leftData == null || rightData.maxPathSumFromHead > leftData.maxPathSumFromHead)) {
            maxPathSumFromHead += rightData.maxPathSumFromHead;
            end = rightData.end;
        }

        int maxAllPathSum = Integer.MIN_VALUE;
        TreeNode from = null;
        TreeNode to = null;
        if (leftData != null) {
            maxAllPathSum = leftData.maxAllPathSum;
            from = leftData.from;
            to = leftData.to;
        }

        if (rightData != null && rightData.maxAllPathSum > maxAllPathSum) {
            maxAllPathSum = rightData.maxAllPathSum;
            from = rightData.from;
            to = rightData.to;
        }
        // 最大路径和=左树的头节点出发的最大路径和+右树从头节点出发的最大路径和+当前节点的值
        int p3 = x.val + (leftData != null && leftData.maxPathSumFromHead > 0 ? leftData.maxPathSumFromHead : 0)
                + (rightData != null && rightData.maxPathSumFromHead > 0 ? rightData.maxPathSumFromHead : 0);
        if (p3 > maxAllPathSum) {
            maxAllPathSum = p3;
            from = (leftData != null && leftData.maxPathSumFromHead > 0) ? leftData.end : x;
            to = (rightData != null && rightData.maxPathSumFromHead > 0) ? rightData.end : x;
        }
        return new Data(maxAllPathSum, from, to, maxPathSumFromHead, end);
    }

    public static class Data {
        // 整体最大路径和
        public int maxAllPathSum;
        // 最大路径和的开头位置
        public TreeNode from;
        // 最大路径和的结尾位置
        public TreeNode to;
        // 从头节点出发的最大路径和
        public int maxPathSumFromHead;
        // 从头节点出发的情况下最深的位置
        public TreeNode end;

        public Data(int maxAllPathSum, TreeNode f, TreeNode t, int maxPathSumFromHead, TreeNode e) {
            this.maxAllPathSum = maxAllPathSum;
            this.from = f;
            this.to = t;
            this.maxPathSumFromHead = maxPathSumFromHead;
            this.end = e;
        }
    }

    public void fatherMap(TreeNode head, HashMap<TreeNode, TreeNode> map) {
        if (head.left == null && head.right == null) {
            return;
        }
        if (head.left != null) {
            map.put(head.left, head);
            fatherMap(head.left, map);
        }
        if (head.right != null) {
            map.put(head.right , head);
            fatherMap(head.right, map);
        }
    }

    public static void fillPath(HashMap<TreeNode, TreeNode> fMap, TreeNode a, TreeNode b, List<TreeNode> ans) {
        if (a == b) {
            ans.add(a);
        } else {
            HashSet<TreeNode> ap = new HashSet<>();
            TreeNode cur = a;
            while (cur != fMap.get(cur)) {
                ap.add(cur);
                cur = fMap.get(cur);
            }
            ap.add(cur);
            cur = b;
            TreeNode lca = null;
            while (lca == null) {
                if (ap.contains(cur)) {
                    lca = cur;
                } else {
                    cur = fMap.get(cur);
                }
            }
            while (a != lca) {
                ans.add(a);
                a = fMap.get(a);
            }
            ans.add(lca);
            ArrayList<TreeNode> right = new ArrayList<>();
            while (b != lca) {
                right.add(b);
                b = fMap.get(b);
            }
            for (int i = right.size() - 1; i >= 0; i--) {
                ans.add(right.get(i));
            }
        }
    }
}
