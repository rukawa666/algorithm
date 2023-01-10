package com.rukawa.algorithm.base.class08_tree;

import com.rukawa.algorithm.base.class07_tree.Node;

/**
 * Created with Intellij IDEA
 *
 * @Author：SuperHai
 * @Date：2020-07-21 7:49
 * @Version：1.0
 */
public class Code05_MaxSubBSTSize {

    /**
     * 给定一棵二叉树的头结点head，返回这棵二叉树中最大的二叉搜索子树的大小
     */

    /**
     * 分析：
     *  1.以head不为头，左树上的最大的二叉树搜索子树的大小，右树上的最大的二叉搜索子树的大小
     *  2.以head为头，head整体全部是搜索叉树
     *
     *  需要得到以下信息：
     *      1、左树是否是搜索二叉树
     *      2、右树是否是搜索二叉树
     *      3、左树的最大值要小于当前节点的值
     *      4、右树的最小值要大于当前节点的值
     *      5、要知道搜索二叉树的节点数量
     *      6、当前节点的所有节点个数
     *
     *  是否是搜索二叉树=当前节点的节点个数=搜索二叉树的节点个数
     */
    public static int maxSubBSTreeSize02(Node head) {
        if (head == null) {
            return 0;
        }
        return process(head).maxBSTSubTreeSize;
    }

    public static Info process(Node node) {
        if (node == null) {
            return null;
        }
        Info leftInfo = process(node.left);
        Info rightInfo = process(node.right);
        int max = node.value;
        int min = node.value;
        int allSize = 1;
        if (leftInfo != null) {
            max = Math.max(max, leftInfo.max);
            min = Math.min(min, leftInfo.min);
            allSize += leftInfo.allSize;
        }

        if (rightInfo != null) {
            max = Math.max(max, rightInfo.max);
            min = Math.min(min, rightInfo.min);
            allSize += rightInfo.allSize;
        }

        // 可能性1：当前节点的左树的最大二叉搜索子树的大小
        int p1 = -1;
        if (leftInfo != null) {
            p1 = leftInfo.maxBSTSubTreeSize;
        }

        // 可能性2：当前节点的右树的最大二叉搜索子树的大小
        int p2 = -1;
        if (rightInfo != null) {
            p2 = rightInfo.maxBSTSubTreeSize;
        }

        // 可能性3：要把左树和右树的最大搜索树连接起来
        boolean leftBST = leftInfo == null ? true : leftInfo.maxBSTSubTreeSize == leftInfo.allSize;
        boolean rightBST = rightInfo == null ? true : rightInfo.maxBSTSubTreeSize == rightInfo.allSize;
        int p3 = -1;
        if (leftBST && rightBST) {
            boolean leftMaxLessCurValue = leftInfo == null ? true : leftInfo.max < node.value;
            boolean rightMinGreaterCurValue = rightInfo == null ? true : rightInfo.min > node.value;
            if (leftMaxLessCurValue && rightMinGreaterCurValue) {
                int leftSize = leftInfo == null ? 0 : leftInfo.allSize;
                int rightSize = rightInfo == null ? 0 : rightInfo.allSize;
                p3 = leftSize + rightSize + 1;
            }
        }
        int maxBSTSubTreeSize = Math.max(p1, Math.max(p2, p3));
        return new Info(maxBSTSubTreeSize, max, min, allSize);
    }

    public static class Info {
        public int maxBSTSubTreeSize;
        public int max;
        public int min;
        public int allSize;

        public Info(int size, int max, int min, int a) {
            this.maxBSTSubTreeSize = size;
            this.max = max;
            this.min = min;
            this.allSize = a;
        }
    }
}
