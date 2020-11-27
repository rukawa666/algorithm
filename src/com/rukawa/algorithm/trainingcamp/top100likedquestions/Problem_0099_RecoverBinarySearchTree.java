package com.rukawa.algorithm.trainingcamp.top100likedquestions;

/**
 * Created with Intellij IDEA
 *
 * @Author：SuperHai
 * @Date：2020-10-26 7:47
 * @Version：1.0
 */
public class Problem_0099_RecoverBinarySearchTree {

    /**
     * 恢复二叉搜索树
     * 二叉搜索树中的两个节点被错误地交换。
     * 请在不改变其结构的情况下，恢复这棵树。
     *
     * 示例 1:
     * 输入: [1,3,null,null,2]
     *    1
     *   /
     *  3
     *   \
     *    2
     * 输出: [3,1,null,null,2]
     *    3
     *   /
     *  1
     *   \
     *    2
     *
     * 示例 2:
     * 输入: [3,1,4,null,null,2]
     *   3
     *  / \
     * 1   4
     *    /
     *   2
     *
     * 输出: [2,1,4,null,null,3]
     *   2
     *  / \
     * 1   4
     *    /
     *   3
     *
     * 进阶:
     * 使用 O(n) 空间复杂度的解法很容易实现。
     * 你能想出一个只使用常数空间的解决方案吗？
     * @param root
     */
    public void recoverTree(TreeNode root) {

    }

    public class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
        TreeNode() {}
        TreeNode(int val) { this.val = val; }
        TreeNode(int val, TreeNode left, TreeNode right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }
    }
}
