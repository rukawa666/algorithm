package com.rukawa.algorithm.types.bst;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

/**
 * Created with Intellij IDEA
 *
 * @Author：SuperHai
 * @Date：2021-01-22 8:08
 * @Version：1.0
 */
public class Code03_SerializeAndReconstructTree {

    public static class Node {
        public int value;
        public Node left;
        public Node right;

        public Node(int val) {
            this.value = val;
        }
    }

    /**
     * 二叉树的序列化和反序列化
     */


    // 二叉树的先序序列化
    public static Queue<String> preSerial(Node head) {
        Queue<String> res = new LinkedList<>();
        pres(head, res);
        return res;
    }

    public static void pres(Node head, Queue<String> res) {
        if (head == null) {
            res.add(null);
        } else {
            res.add(String.valueOf(head.value));
            pres(head.left, res);
            pres(head.right, res);
        }
    }
    // 二叉树先序 反序列化
    public static Node buildByPreQueue(Queue<String> preList) {
        if (preList == null || preList.size() == 0) {
            return null;
        }
        return buildPre(preList);
    }

    public static Node buildPre(Queue<String> preList) {
        String value = preList.poll();
        if (value == null) {
            return null;
        }
        Node head = new Node(Integer.valueOf(value));
        head.left = buildPre(preList);
        head.right = buildPre(preList);
        return head;
    }

    // 二叉树的中序 序列化

    /**
     *           2
     *         /  \
     *        1   null
     *      /  \
     *   null  null
     * ---->中序结果[null,1,null,2,null]
     *          1
     *        /   \
     *      null   2
     *           /   \
     *          null null
     *---->中序结果[null,1,null,2,null]
     *
     * 上面两棵树中序结果一样，所以中序序列化有歧义，也无中序的反序列化
     */
    public static Queue<String> inSerial(Node head) {
        Queue<String> res = new LinkedList<>();
        ins(head, res);
        return res;
    }

    public static void ins(Node head, Queue<String> res) {
        if (head == null) {
            res.add(null);
        } else {
            ins(head.left, res);
            res.add(String.valueOf(head.value));
            ins(head.right, res);
        }
    }

    // 二叉树的后序系列化
    public static Queue<String> posSerial(Node head) {
        Queue<String> res = new LinkedList<>();
        poss(head, res);
        return res;
    }

    public static void poss(Node head, Queue<String> res) {
        if (head == null) {
            res.add(null);
        } else {
            poss(head.left, res);
            poss(head.right, res);
            res.add(String.valueOf(head.value));
        }
    }
    // 二叉树后序 反序列化
    public static Node buildByPosQueue(Queue<String> preList) {
        if (preList == null || preList.size() == 0) {
            return null;
        }
        // 左 右 头  -> 头 右 左
        Stack<String> posStack = new Stack<>();
        while (!preList.isEmpty()) {
            posStack.push(posStack.pop());
        }
        return buildPos(posStack);

    }

    public static Node buildPos(Stack<String> posStack) {
        String value = posStack.pop();
        if (value == null) {
            return null;
        }
        Node head = new Node(Integer.valueOf(value));
        head.right = buildPos(posStack);
        head.left = buildPos(posStack);
        return head;
    }


    // 二叉树 按层序列化
    public static Queue<String> levelSerial(Node head) {
        Queue<String> res = new LinkedList<>();
        if (head == null) {
            res.add(null);
        } else {
            res.add(String.valueOf(head.value));
            Queue<Node> queue = new LinkedList<>();
            queue.add(head);

            if (!queue.isEmpty()) {
                head = queue.poll();
                if (head.left != null) {
                    res.add(String.valueOf(head.left.value));
                    queue.add(head.left);
                } else {
                    res.add(null);
                }

                if (head.right != null) {
                    res.add(String.valueOf(head.right.value));
                    queue.add(head.right);
                } else {
                    res.add(null);
                }
            }
        }
        return res;
    }

}
