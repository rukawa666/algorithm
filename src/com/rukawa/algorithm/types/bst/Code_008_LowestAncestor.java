package com.rukawa.algorithm.types.bst;

/**
 * Created with Intellij IDEA
 *
 * @Author：SuperHai
 * @Date：2021-01-26 9:25
 * @Version：1.0
 */
public class Code_008_LowestAncestor {

    public static class Node {
        public int value;
        public Node left;
        public Node right;

        public Node(int data) {
            this.value = data;
        }
    }

    /**
     * 给定一棵二叉树的头节点head，和另外两个节点a和b
     * 返回a和b的最低公共祖先
     */
    public Node lowestCommonAncestor(Node root, Node p, Node q) {
        return process(root, p, q).res;
    }


    public static class Info {
        public boolean findP;
        public boolean findQ;
        public Node res;

        public Info(boolean findP, boolean findQ, Node res) {
            this.findP = findP;
            this.findQ = findQ;
            this.res = res;
        }
    }

    public static Info process(Node x, Node p, Node q ) {
        if (x == null) {
            return new Info(false, false, null);
        }
        Info leftInfo = process(x.left, p, q);
        Info rightInfo = process(x.right, p, q);
        boolean findP = x == p || leftInfo.findP || rightInfo.findP;
        boolean findQ = x == q || leftInfo.findQ || rightInfo.findQ;
        Node res = null;
        if (leftInfo.res != null) {
            res = leftInfo.res;
        } else if (rightInfo.res != null) {
            res = rightInfo.res;
        } else {
            if (findP && findQ) {
                res = x;
            }
        }
        return new Info(findP, findQ, res);
    }
}
