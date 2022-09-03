package com.rukawa.algorithm.leetcode.top100likedquestions;

/**
 * Created with Intellij IDEA
 *
 * @Author：SuperHai
 * @Date：2020-09-25 21:53
 * @Version：1.0
 */
public class Problem_0236_LowestCommonAncestorOfBinaryTree {
    /**
     * 二叉树的最近公共祖先
     * 给定一个二叉树, 找到该树中两个指定节点的最近公共祖先。
     * 百度百科中最近公共祖先的定义为：“对于有根树 T 的两个结点 p、q，最近公共祖先表示为一个结点 x，满足 x 是 p、q
     * 的祖先且 x 的深度尽可能大（一个节点也可以是它自己的祖先）。”
     * 例如，给定如下二叉树:  root = [3,5,1,6,2,0,8,null,null,7,4]
     *
     *
     * 示例 1:
     * 输入: root = [3,5,1,6,2,0,8,null,null,7,4], p = 5, q = 1
     * 输出: 3
     * 解释: 节点 5 和节点 1 的最近公共祖先是节点 3。
     *
     * 示例 2:
     * 输入: root = [3,5,1,6,2,0,8,null,null,7,4], p = 5, q = 4
     * 输出: 5
     * 解释: 节点 5 和节点 4 的最近公共祖先是节点 5。因为根据定义最近公共祖先节点可以为节点本身。
     *
     * 说明:
     * 所有节点的值都是唯一的。
     * p、q 为不同节点且均存在于给定的二叉树中。
     */
    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        /**
         *  x这棵树上，a和b汇聚在哪
         *  x这棵树上是否发现a和b，如果发现则最低公共祖先在x这棵树上
         *  情况分析：
         *    x不是最低的汇聚点：
         *      1.x的左树上有最低汇聚点
         *      2.x的右树上有最低汇聚点
         *      3.x的整棵树上a和b不全
         *    x是最低的汇聚点：
         *      1.左树发现一个，右树发现另外一个，在x这里汇聚
         *      2.x本身就是a节点，左树或者右树发现了b
         *      3.x本身就是b节点，左树或者右树发现了a
         *
         *  根据情况分析需要以下几个要素：
         *      1.这棵树上有没有a节点
         *      2.这棵树上有没有b节点
         *      3.这棵树上有没有发现a和b的最低汇聚点
         */
        return process(root, p, q).res;
    }

    public static Info process(TreeNode x, TreeNode p, TreeNode q) {
        if (x == null) {
            return new Info(false, false, null);
        }
        Info leftInfo = process(x.left, p, q);
        Info rightInfo = process(x.right, p, q);
        boolean findP = x == p || leftInfo.findP || rightInfo.findP;
        boolean findQ = x == q || leftInfo.findQ || rightInfo.findQ;
        TreeNode res = null;
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

    public static class Info {
        public boolean findP;
        public boolean findQ;
        public TreeNode res;

        public Info(boolean findP, boolean findQ, TreeNode res) {
            this.findP = findP;
            this.findQ = findQ;
            this.res = res;
        }
    }

    public class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
        TreeNode(int x) { val = x; }
    }
}
