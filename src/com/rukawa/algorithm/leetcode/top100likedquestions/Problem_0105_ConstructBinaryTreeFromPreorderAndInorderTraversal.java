package com.rukawa.algorithm.leetcode.top100likedquestions;

import java.util.HashMap;

/**
 * Created with Intellij IDEA
 *
 * @Author：SuperHai
 * @Date：2020-09-21 8:40
 * @Version：1.0
 */
public class Problem_0105_ConstructBinaryTreeFromPreorderAndInorderTraversal {
    /**
     * 从前序与中序遍历序列构造二叉树
     * 根据一棵树的前序遍历与中序遍历构造二叉树。
     * 注意:
     * 你可以假设树中没有重复的元素。
     *
     * 例如，给出
     * 前序遍历 preorder = [3,9,20,15,7]
     * 中序遍历 inorder = [9,3,15,20,7]
     * 返回如下的二叉树：
     *     3
     *    / \
     *   9  20
     *     /  \
     *    15   7
     */
    public TreeNode buildTree(int[] preorder, int[] inorder) {
        HashMap<Integer, Integer> inMap = new HashMap<>();
        for (int i = 0; i < inorder.length; i++) {
            inMap.put(inorder[i], i);
        }
        return process(preorder, 0, preorder.length - 1, inorder, 0, inorder.length - 1, inMap);
    }
    public static TreeNode process(int[] pre, int L1, int R1, int[] in, int L2, int R2,
                                   HashMap<Integer, Integer> inMap) {

        if(L1 > R1) {
            return null;
        }
        TreeNode head = new TreeNode(pre[L1]);
        if (L1 == R1) {
            return head;
        }
        int findIndex = inMap.get(pre[L1]);

        head.left = process(pre, L1 + 1, L1 + (findIndex - L2), in, L2, findIndex - 1, inMap);
        head.right = process(pre, L1 + (findIndex - L2) + 1, R1, in, findIndex + 1, R2, inMap);
        return head;
    }

    public static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        public TreeNode(int x) {
            this.val = x;
        }
    }
}
