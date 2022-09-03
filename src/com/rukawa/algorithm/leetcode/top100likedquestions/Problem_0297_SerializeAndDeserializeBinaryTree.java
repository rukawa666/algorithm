package com.rukawa.algorithm.leetcode.top100likedquestions;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Created with Intellij IDEA
 *
 * @Author：SuperHai
 * @Date：2020-09-25 21:55
 * @Version：1.0
 */
public class Problem_0297_SerializeAndDeserializeBinaryTree {
    /**
     * 二叉树的层序列化和反序列化
     *
     * 二叉树的序列化与反序列化
     * 序列化是将一个数据结构或者对象转换为连续的比特位的操作，进而可以将转换后的数据存储在一个文件或者内存中，同时也可以通过网络传输到另一个计算机环境，采取相反方式重构得到原数据。
     *
     * 请设计一个算法来实现二叉树的序列化与反序列化。这里不限定你的序列 / 反序列化算法执行逻辑，你只需要保证一个二叉树可以被序列化为一个字符串并且将这个字符串反序列化为原始的树结构。
     *
     * 示例: 
     *
     * 你可以将以下二叉树：
     *
     *     1
     *    / \
     *   2   3
     *      / \
     *     4   5
     *
     * 序列化为 "[1,2,3,null,null,4,5]"
     */

    public static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
        TreeNode(int x) { val = x; }
    }

    // Encodes a tree to a single string.
    public static String serialize(TreeNode root) {
        Queue<String> res = new LinkedList<>();
        if (root == null) {
            res.add("null");
        } else {
            res.add(String.valueOf(root.val));
            Queue<TreeNode> queue = new LinkedList<>();
            queue.add(root);
            while (!queue.isEmpty()) {
                root = queue.poll();
                if (root.left != null) {
                    res.add(String.valueOf(root.left.val));
                    queue.add(root.left);
                } else {
                    res.add("null");
                }

                if (root.right != null) {
                    res.add(String.valueOf(root.right.val));
                    queue.add(root.right);
                } else {
                    res.add("null");
                }
            }
        }
        return res.toString();
    }

    // Decodes your encoded data to tree.
    public TreeNode deserialize(String data) {
        Queue<String> originQueue = new LinkedList<>();
        if (originQueue == null || originQueue.size() == 0) {
            return null;
        }
        TreeNode root = generateTreeNode(originQueue.poll());
        Queue<TreeNode> queue = new LinkedList<>();
        if (root != null) {
            queue.add(root);
        }
        TreeNode node = null;
        while (!queue.isEmpty()) {
            node = queue.poll();
            node.left = generateTreeNode(originQueue.poll());
            node.right = generateTreeNode(originQueue.poll());
            if (node.left != null) {
                queue.add(node.left);
            }

            if (node.right != null) {
                queue.add(node.right);
            }
        }
        return root;
    }

    public TreeNode generateTreeNode(String val) {
        if (val == null) {
            return null;
        }
        return new TreeNode(Integer.valueOf(val));
    }

    public static void main(String[] args) {
        TreeNode root = new TreeNode(1);
        root.left = new TreeNode(2);
        root.right = new TreeNode(3);
        root.right.left = new TreeNode(4);
        root.right.right = new TreeNode(5);

        String serialize = serialize(root);


    }
}
