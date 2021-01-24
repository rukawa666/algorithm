package com.rukawa.algorithm.leetcode.other;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with Intellij IDEA
 *
 * @Author：SuperHai
 * @Date：2021-01-24 21:48
 * @Version：1.0
 */
public class Problem_0431_EncodeNaryTreeToBinaryTree {
    /**
     * 设计一个算法，可以将 N 叉树编码为二叉树，并能将该二叉树解码为原 N 叉树。
     * 一个 N 叉树是指每个节点都有不超过 N 个孩子节点的有根树。
     * 类似地，一个二叉树是指每个节点都有不超过 2 个孩子节点的有根树。
     * 你的编码 / 解码的算法的实现没有限制，你只需要保证一个 N 叉树可以编码为二叉树且该二叉树可以解码回原始 N 叉树即可。
     *
     * 例如，你可以将下面的 3-叉 树以该种方式编码：
     * 注意，上面的方法仅仅是一个例子，可能可行也可能不可行。
     * 你没有必要遵循这种形式转化，你可以自己创造和实现不同的方法。
     *
     * 注意：
     * N 的范围在 [1, 1000]
     * 不要使用类成员 / 全局变量 / 静态变量来存储状态。
     * 你的编码和解码算法应是无状态的。
     */

    public static class Node {
        public int val;
        public List<Node> children;

        public Node() {
        }

        public Node(int _val) {
            val = _val;
        }

        public Node(int _val, List<Node> _children) {
            val = _val;
            children = _children;
        }
    }

    public static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int x) {
            val = x;
        }
    }

    /**
     * 思路：
     * 当前节点的孩子，放在当前节点的左树右边界
     */
    class CodeC {

        // 多叉树转二叉树
        public TreeNode encode(Node root) {
            if (root == null) {
                return null;
            }
            TreeNode head = new TreeNode(root.val);
            // 孩子节点挂在左树右边界
            head.left = en(root.children);
            return head;
        }

        private TreeNode en(List<Node> children) {
            TreeNode head = null;
            TreeNode cur = null;
            for (Node child : children) {
                TreeNode tNode = new TreeNode(child.val);
                if (head == null) {
                    head = tNode;
                } else {
                    cur.right = tNode;
                }
                cur = tNode;
                // 深度优先遍历，把当前节点的孩子节点挂到当前节点的左树
                cur.left = en(child.children);
            }
            return head;
        }

        // 二叉树转多叉树
        public Node decode(TreeNode root) {
            if (root == null) {
                return null;
            }
            return new Node(root.val, de(root.left));
        }

        public List<Node> de(TreeNode root) {
            List<Node> children = new ArrayList<>();
            while (root != null) {
                Node cur = new Node(root.val, de(root.left));
                children.add(cur);
                root = root.right;
            }
            return children;
        }
    }
}
