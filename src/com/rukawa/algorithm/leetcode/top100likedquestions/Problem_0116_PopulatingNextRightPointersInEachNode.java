package com.rukawa.algorithm.leetcode.top100likedquestions;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Created with Intellij IDEA
 *
 * @Author：SuperHai
 * @Date：2020-09-21 8:39
 * @Version：1.0
 */
public class Problem_0116_PopulatingNextRightPointersInEachNode {
    /**
     * 填充每个节点的下一个右侧节点指针
     * 给定一个 完美二叉树 ，其所有叶子节点都在同一层，每个父节点都有两个子节点。二叉树定义如下：
     *
     * struct Node {
     *   int val;
     *   Node *left;
     *   Node *right;
     *   Node *next;
     * }
     * 填充它的每个 next 指针，让这个指针指向其下一个右侧节点。如果找不到下一个右侧节点，则将 next 指针设置为 NULL。
     * 初始状态下，所有 next 指针都被设置为 NULL。
     *
     * 示例 1：
     * 输入：root = [1,2,3,4,5,6,7]
     * 输出：[1,#,2,3,#,4,5,6,7,#]
     * 解释：给定二叉树如图 A 所示，你的函数应该填充它的每个 next 指针，以指向其下一个右侧节点，如图 B 所示。序列化的输出按层序遍历排列，同一层节点由 next 指针连接，'#' 标志着每一层的结束。
     *
     * 示例 2:
     * 输入：root = []
     * 输出：[]
     *
     * 提示：
     * 树中节点的数量在 [0, 212 - 1] 范围内
     * -1000 <= node.val <= 1000
     *
     * 进阶：
     * 你只能使用常量级额外空间。
     * 使用递归解题也符合要求，本题中递归程序占用的栈空间不算做额外的空间复杂度。
     */

    public class Node {
        public int val;
        public Node left;
        public Node right;
        public Node next;

        public Node() {}
        public Node(int x) {
            val = x;
        }
        public Node(int x, Node left, Node right, Node next) {
            this.val = x;
            this.left = left;
            this.right = right;
            this.next = next;
        }
    }

    public Node connect(Node root) {
        if (root == null) {
            return null;
        }
        Queue<Node> queue = new LinkedList<>();
        queue.add(root);
        int size = 0;
        Node cur = null;
        while (!queue.isEmpty()) {
            size = queue.size();
            for (int i = 0; i < size; i++) {
                cur = queue.poll();
                if (i < size - 1) {
                    cur.next = queue.peek();
                }

                if (cur.left != null) {
                    queue.add(cur.left);
                }

                if (cur.right != null) {
                    queue.add(cur.right);
                }

            }
        }
        return root;
    }
}
