package com.rukawa.algorithm.types.bst;

/**
 * Created with Intellij IDEA
 *
 * @Author：SuperHai
 * @Date：2021-01-26 7:51
 * @Version：1.0
 */
public class Code_006_MaxSubBSTSize {

    public static class Node {
        public int value;
        public Node left;
        public Node right;

        public Node(int val) {
            this.value = val;
        }
    }

    /**
     * 整棵二叉树找到最大的搜索子树，返回其多少个节点
     */
    public static int maxSubBSTSize(Node head) {
        if (head == null) {
            return 0;
        }
        return process(head).maxBSTSubTreeSize;
    }

    public static class Info {
        public int maxBSTSubTreeSize;
        public int allSize;
        public int max;
        public int min;

        public Info(int maxBSTSubTreeSize, int allSize, int max, int min) {
            this.maxBSTSubTreeSize = maxBSTSubTreeSize;
            this.allSize = allSize;
            this.max = max;
            this.min = min;
        }
    }

    /**
     * 思路：
     *    1. X无关
     *      a、左树上最大搜索二叉树的size
     *      b、右树上最大搜索二叉树的size
     *    2. X有关
     *      a、左树必须是搜索二叉树
     *      b、右树也必须是搜索二叉树
     *      c、左树最大值小于当前节点的值
     *      d、右树最小值大于当前节点的值
     *      e、须知道左树的大小，右树的大小，再加1得出答案
     * @param x
     * @return
     */
    public static Info process(Node x) {
        if (x == null) {
            return null;
        }
        Info leftInfo = process(x.left);
        Info rightInfo = process(x.right);
        int max = x.value;
        int min = x.value;
        int allSize = 1;
        if (leftInfo != null) {
            min = Math.min(min, leftInfo.min);
            max = Math.max(max, leftInfo.max);
            allSize += leftInfo.allSize;
        }
        if (rightInfo != null) {
            min = Math.min(min, rightInfo.min);
            max = Math.max(max, rightInfo.max);
            allSize += rightInfo.allSize;
        }
        int p1 = -1;
        if (leftInfo != null) {
            p1 = leftInfo.maxBSTSubTreeSize;
        }
        int p2 = -1;
        if (rightInfo != null) {
            p2 = rightInfo.maxBSTSubTreeSize;
        }
        int p3 = -1;
        boolean leftBST = leftInfo == null ? true : (leftInfo.maxBSTSubTreeSize == leftInfo.allSize);
        boolean rightBST = rightInfo == null ? true : (rightInfo.maxBSTSubTreeSize == rightInfo.allSize);
        if (leftBST && rightBST) {
            boolean leftMaxLessX = leftInfo == null ? true : (leftInfo.max < x.value);
            boolean rightMinMoreX = rightInfo == null ? true : (rightInfo.min > x.value);
            if (leftMaxLessX && rightMinMoreX) {
                int leftSize = leftInfo == null ? 0 : leftInfo.allSize;
                int rightSize = rightInfo == null ? 0 : rightInfo.allSize;
                p3 = leftSize + rightSize + 1;
            }
        }

        return new Info(Math.max(p1, Math.max(p2, p3)), allSize, max, min);
    }
}
