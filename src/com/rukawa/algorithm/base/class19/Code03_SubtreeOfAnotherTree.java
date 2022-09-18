package com.rukawa.algorithm.base.class19;

import java.util.ArrayList;

/**
 * create by hqh on 2022/9/18
 */
public class Code03_SubtreeOfAnotherTree {

    /**
     * 另一个树的子树
     * 给定两个非空二叉树 s 和 t，检验s 中是否包含和 t 具有相同结构和节点值的子树。
     * s 的一个子树包括 s 的一个节点和这个节点的所有子孙。s 也可以看做它自身的一棵子树。
     *
     * 思路：把二叉树序列化，然后通过kmp查找
     *
     */
    public static class TreeNode {
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

    public static boolean isSubtree(TreeNode root, TreeNode subRoot) {
        if (subRoot == null) {
            return true;
        }
        if (root == null) {
            return false;
        }
        ArrayList<String> bigList = preSerial(root);
        String[] bigStr = new String[bigList.size()];
        for (int i = 0; i < bigStr.length; i++) {
            bigStr[i] = bigList.get(i);
        }

        ArrayList<String> smallList = preSerial(subRoot);
        String[] smallStr = new String[smallList.size()];
        for (int i = 0; i < smallStr.length; i++) {
            smallStr[i] = smallList.get(i);
        }
        return getIndexOf(bigStr, smallStr) != -1;
    }

    public static ArrayList<String> preSerial(TreeNode node) {
        ArrayList<String> res = new ArrayList<>();
        pre(node, res);
        return res;
    }

    public static void pre(TreeNode node, ArrayList<String> res) {
        if (node == null) {
            res.add(null);
        } else {
            res.add(String.valueOf(node.val));
            pre(node.left, res);
            pre(node.right, res);
        }
    }

    public static int getIndexOf(String[] str1, String[] str2) {
        if (str1 == null || str2 == null || str1.length == 0 || str1.length < str2.length) {
            return -1;
        }
        int x = 0;
        int y = 0;
        int[] nextArray = getNextArray(str2);
        while (x < str1.length && y < str2.length) {
            if (isEqual(str1[x], str2[y])) {
                x++;
                y++;
            } else if (nextArray[y] == -1) {
                x++;
            } else {
                y = nextArray[y];
            }
        }
        return y == str2.length ? x - y : -1;
    }

    public static int[] getNextArray(String[] str2) {
        if (str2.length == 0) {
            return new int[]{-1};
        }
        int[] ans = new int[str2.length];
        ans[0] = -1;
        ans[1] = 0;
        int index = 2;
        int cn = 0;
        while (index < ans.length) {
            if (isEqual(str2[index - 1], str2[cn])) {
                ans[index++] = ++cn;
            } else if (cn > 0) {
                cn = ans[cn];
            } else {
                ans[index++] = 0;
            }
        }
        return ans;
    }

    public static boolean isEqual(String s1, String s2) {
        if (s1 == null && s2 == null) {
            return true;
        } else {
            if (s1 == null || s2 == null) {
                return false;
            } else {
                return s1.equals(s2);
            }
        }
    }
}
     
