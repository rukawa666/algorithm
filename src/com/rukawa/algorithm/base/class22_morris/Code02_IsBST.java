package com.rukawa.algorithm.base.class22_morris;

/**
 * create by hqh on 2022/9/19
 */
public class Code02_IsBST {
    /**
     * 判断一棵树是否是搜索二叉树
     * 实质：二叉树的中序遍历是升序
     */

    public static boolean isBST(Node head) {
        if (head == null) {
            return true;
        }
        Node cur = head;
        Node mostRight = null;
        Integer pre = null;
        boolean res = true;
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
                }
            }
            if (pre != null && pre >= cur.val) {
                // 此处不能直接return，树有改动，如果直接return，树就变了
                res = false;
            }
            pre = cur.val;
            cur = cur.right;
        }
        return res;
    }

    public static class Node {
        public int val;
        public Node left;
        public Node right;

        public Node(int x) {
            this.val = x;
        }
    }
}
