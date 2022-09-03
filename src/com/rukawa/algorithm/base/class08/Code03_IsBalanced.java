package com.rukawa.algorithm.base.class08;

import com.rukawa.algorithm.base.class07.Node;

/**
 * create by hqh on 2022/9/1
 */
public class Code03_IsBalanced {

    /**
     * 给定一个二叉树的头节点head，返回这颗二叉树是不是平衡二叉树
     * 平衡二叉树：每一个子树的左树的高度和右树的高度相差的绝对值相差不超过1
     */

    public static boolean isBalances(Node head) {
        return process02(head).isBalanced;
    }

    // 左、右要求返回一样，Info信息返回的结构体
    public static class Info {
        public boolean isBalanced;
        public int height;

        public Info(boolean b, int h) {
            this.isBalanced = b;
            this.height = h;
        }
    }

    public static Info process02(Node X) {
        if (X == null) {
            return new Info(true, 0);
        }

        Info leftInfo = process02(X.left);
        Info rightInfo = process02(X.right);

        int height = Math.max(leftInfo.height, rightInfo.height) + 1;

        boolean isBalanced = true;
        if (!leftInfo.isBalanced || !rightInfo.isBalanced || Math.abs(leftInfo.height - rightInfo.height) > 1) {
            isBalanced = false;
        }
        return new Info(isBalanced, height);
    }
}
