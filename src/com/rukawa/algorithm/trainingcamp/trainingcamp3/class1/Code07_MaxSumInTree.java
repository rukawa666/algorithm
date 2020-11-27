package com.rukawa.algorithm.trainingcamp.trainingcamp3.class1;

/**
 * Created with Intellij IDEA
 *
 * @Author：SuperHai
 * @Date：2020-08-19 8:18
 * @Version：1.0
 */
public class Code07_MaxSumInTree {
    /**
     * 给定一个二叉树的头节点head，路径的规定有以下三种不同的规定
     * 1、路径必须是头节点出发，到叶节点为止，返回最大的路径和
     * 2、路径可以从任意节点出发，但必须往下走到任何节点、返回最大路径和
     * 3、路径可以从任意节点出发，到任意节点，返回最大的路径和
     */

    public static class Node {
        public int value;
        public Node left;
        public Node right;

        public Node(int value) {
            this.value = value;
        }
    }

    public static int maxSum = Integer.MIN_VALUE;

    /**
     * 二叉树的递归求头节点的最大路径和
     * @param head
     * @return
     */
    public static int maxPath(Node head) {
        maxSum = Integer.MIN_VALUE;
        p(head, 0);
        return maxSum;
    }

    public static void p(Node x, int pre) {
        if (x.left == null && x.right == null) {
            maxSum = Math.max(maxSum, pre + x.value);
        }

        if (x.left != null) {
            p(x.left, pre + x.value);
        }

        if (x.right != null) {
            p(x.right, pre + x.value);
        }
    }

    /**
     * 题目1：
     * 二叉树的递归求头节点的最大路径和
     * 递归求解
     * @param head
     * @return
     */
    public static int maxDis(Node head) {
        if (head == null) {
            return 0;
        }
        return process1(head);
    }

    // x为头的整棵树上，最大路径和是多少，返回
    // 路径要求，一定要求是从x出发，到叶节点，算做一个路径
    public static int process1(Node x) {
        if (x.left == null && x.right == null) {
            return x.value;
        }

        Integer next = Integer.MIN_VALUE;
        if (x.left != null) {
            next = process1(x.left);
        }

        if (x.right != null) {
            next = Math.max(next, process1(x.right));
        }
        return next + x.value;
    }

    /**
     * 题目2：
     * 路径可以从任意节点出发，但必须往下走到任何节点、返回最大路径和
     * @param head
     * @return
     */
    public static int maxSum2(Node head) {
        if (head == null) {
            return 0;
        }
        return f(head).allTreeMaxSum;
    }

    public static class Info {
        public int allTreeMaxSum;
        public int fromHeadMaxSum;

        public Info(int all, int from) {
            allTreeMaxSum = all;
            fromHeadMaxSum = from;
        }
    }

    // 和X无关：1、左树上的整体最大路径和  2、右树上的整体最大路径和
    // 和X有关：1、x自己  2、x往左走 3、x往右走
    public static Info f(Node x) {
        if (x == null) {
            return null;
        }

        Info leftInfo = f(x.left);
        Info rightInfo = f(x.right);
        int p1 = Integer.MIN_VALUE;
        if (leftInfo != null) {
            p1 = leftInfo.allTreeMaxSum;
        }

        int p2 = Integer.MIN_VALUE;
        if (rightInfo != null) {
            p2 = rightInfo.allTreeMaxSum;
        }

        int p3 = x.value;

        int p4 = Integer.MIN_VALUE;
        if (leftInfo != null) {
            p4 = x.value + leftInfo.fromHeadMaxSum;
        }

        int p5 = Integer.MIN_VALUE;
        if (rightInfo != null) {
            p5 = x.value + rightInfo.fromHeadMaxSum;
        }

        int allTreeMaxSum = Math.max(Math.max(Math.max(Math.max(p1, p2), p3), p4), p5);
        int fromHeadMaxSum = Math.max(Math.max(p3, p4), p5);
        return new Info(allTreeMaxSum, fromHeadMaxSum);
    }

    /**
     * 题目3：
     * 路径可以从任意节点出发，到任意节点，返回最大的路径和
     * @param head
     * @return
     */
    public static int maxSum3(Node head) {
        if (head == null) {
            return 0;
        }
        return f(head).allTreeMaxSum;
    }


    // 和X无关：1、左树上的整体最大路径和  2、右树上的整体最大路径和
    // 和X有关：1、x自己  2、x往左走 3、x往右走  4、既往走，又往右
    public static Info process(Node x) {
        if (x == null) {
            return null;
        }

        Info leftInfo = f(x.left);
        Info rightInfo = f(x.right);
        int p1 = Integer.MIN_VALUE;
        if (leftInfo != null) {
            p1 = leftInfo.allTreeMaxSum;
        }

        int p2 = Integer.MIN_VALUE;
        if (rightInfo != null) {
            p2 = rightInfo.allTreeMaxSum;
        }

        int p3 = x.value;

        int p4 = Integer.MIN_VALUE;
        if (leftInfo != null) {
            p4 = x.value + leftInfo.fromHeadMaxSum;
        }

        int p5 = Integer.MIN_VALUE;
        if (rightInfo != null) {
            p5 = x.value + rightInfo.fromHeadMaxSum;
        }

        int p6 = Integer.MIN_VALUE;
        if (leftInfo != null && rightInfo != null) {
            p6 = x.value + leftInfo.fromHeadMaxSum + rightInfo.fromHeadMaxSum;
        }

        int allTreeMaxSum = Math.max(Math.max(Math.max(Math.max(p1, p2), p3), p4), Math.max(p5, p6));
        int fromHeadMaxSum = Math.max(Math.max(p3, p4), p5);
        return new Info(allTreeMaxSum, fromHeadMaxSum);
    }


    public static int max = Integer.MIN_VALUE;

    /**
     * 路径可以从任意节点出发，到叶节点为止，返回最大路径和
     * @param head
     * @return
     */
    public static int maxSum4(Node head) {
        if (head.left == null && head.right == null) {
            max = Math.max(max, head.value);
            return head.value;
        }
        int nextMax = 0;
        if (head.left != null) {
            nextMax = maxSum4(head.left);
        }
        if (head.right != null) {
            nextMax = Math.max(nextMax, maxSum4(head.right));
        }
        int ans = head.value + nextMax;
        max = Math.max(max, ans);
        return ans;
    }
}
