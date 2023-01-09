package com.rukawa.algorithm.base.class19_kmp;

import java.util.ArrayList;

/**
 * create by hqh on 2022/9/18
 */
public class Code04_SubtreeOfAnotherTree {

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

    public boolean isSubStructure(TreeNode A, TreeNode B) {
        if (A == null || B == null) {
            return false;
        }
        return process(A, B) || isSubStructure(A.left, B) || isSubStructure(A.right, B);
    }

    public boolean process(TreeNode A, TreeNode B) {
        if (B == null) {
            return true;
        }
        if (A == null) {
            return false;
        }
        if (A.val != B.val) {
            return false;
        }
        return process(A.left, B.left) && process(A.right, B.right);
    }

    public static boolean isSubStructure1(TreeNode A, TreeNode B) {
        if (A == null) {
            return false;
        }

        if (B == null) {
            return false;
        }
        ArrayList<String> list1 = preSerial(A);
        String[] str1 = new String[list1.size()];
        for (int i = 0; i < str1.length; i++) {
            str1[i] = list1.get(i);
        }

        ArrayList<String> list2 = preSerial(B);
        String[] str2 = new String[list2.size()];
        for (int i = 0; i < str2.length; i++) {
            str2[i] = list2.get(i);
        }
        return getIndexOf(str1, str2) != -1;
    }

    public static ArrayList<String> preSerial(TreeNode node) {
        ArrayList<String> res = new ArrayList<>();
        morrisPre(node, res);
        return res;
    }

    public static void morrisPre(TreeNode node, ArrayList<String> res) {
        TreeNode cur = node;
        TreeNode mostRight = null;
        while (cur != null) {
            mostRight = cur.left;
            if (mostRight != null) {
                while (mostRight.right != null && mostRight.right != cur) {
                    mostRight = mostRight.right;
                }

                if (mostRight.right == null) {
                    res.add(String.valueOf(cur.val));
                    mostRight.right = cur;
                    cur = cur.left;
                    continue;
                } else {
                    mostRight.right = null;
                }
            } else {
                res.add(String.valueOf(cur.val));
            }
            cur = cur.right;
        }
    }

    public static int getIndexOf(String[] str1, String[] str2) {
        if (str1 == null || str2 == null || str1.length == 0 || str1.length < str2.length) {
            return -1;
        }
        int[] next = getNextArray(str2);
        int x = 0;
        int y = 0;
        while (x < str1.length && y < str2.length) {
            if (isEqual(str1[x], str2[y])) {
                x++;
                y++;
            } else if (next[y] == -1) {
                x++;
            } else {
                y = next[y];
            }
        }
        return y == str2.length ? x - y : -1;
    }

    public static int[] getNextArray(String[] str2) {
        if (str2.length == 1) {
            return new int[]{-1};
        }
        int[] next = new int[str2.length];
        next[0] = -1;
        next[1] = 0;
        int index = 2;
        int cn = 2;
        while (index < str2.length) {
            if (isEqual(str2[index - 1], str2[cn])) {
                next[index++] = ++cn;
            } else if (cn > 0) {
                cn = next[cn];
            } else {
                next[index++] = 0;
            }
        }
        return next;
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

    public static void main(String[] args) {
        TreeNode A = new TreeNode(10);
        A.left = new TreeNode(12);
        A.right = new TreeNode(6);
        A.left.left = new TreeNode(8);
        A.left.right = new TreeNode(3);
        A.left.right.left = new TreeNode(1);

        TreeNode B = new TreeNode(10);
        B.left = new TreeNode(12);
        B.right = new TreeNode(6);
        B.left.left = new TreeNode(8);

        boolean subStructure = isSubStructure1(A, B);
        System.out.println(subStructure);
    }
}
     
