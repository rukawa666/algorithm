package com.rukawa.algorithm.base.class08;

import com.rukawa.algorithm.base.class07.Node;

/**
 * Created with Intellij IDEA
 *
 * @Author：SuperHai
 * @Date：2020-07-21 7:49
 * @Version：1.0
 */
public class Code04_MaxSubBSTSize {

    /**
     * 给定一棵二叉树的头结点head，返回这棵二叉树中最大的二叉搜索子树的大小
     * @param head
     * @return
     */
    public static int maxSubBSTreeSize02(Node head) {
        if (head == null) {
            return 0;
        }
        return process(head).maxSubBTSize;
    }

    public static class Info {
        public boolean isBST;
        public int maxSubBTSize;
        public int max;
        public int min;

        public Info(boolean isBST, int maxSubBTSize, int max, int min) {
            this.isBST = isBST;
            this.maxSubBTSize = maxSubBTSize;
            this.max = max;
            this.min = min;
        }
    }

    public static Info process(Node head) {
        if (head == null) {
            return null;
        }

        Info leftInfo = process(head.left);
        Info rightInfo = process(head.right);

        int max = head.value;
        int min = head.value;
        if (leftInfo != null) {
            max = Math.max(max, leftInfo.max);
            min = Math.min(min, leftInfo.min);

        }
        if (rightInfo != null) {
            max = Math.max(max, rightInfo.max);
            min = Math.min(min, rightInfo.min);
        }

        int maxSubBTSize = 0;
        if (leftInfo != null) {
            maxSubBTSize = Math.max(maxSubBTSize, leftInfo.maxSubBTSize);
        }
        if (rightInfo != null) {
            maxSubBTSize = Math.max(maxSubBTSize, rightInfo.maxSubBTSize);
        }

        boolean isBST = false;
        if ((leftInfo == null ? true : leftInfo.isBST)
                && (rightInfo == null ? true : rightInfo.isBST)
                && (leftInfo == null ? true : leftInfo.max < head.value)
                && (rightInfo == null ? true : rightInfo.min > head.value)) {
            maxSubBTSize = (leftInfo == null ? 0 : leftInfo.maxSubBTSize)
                    + (rightInfo == null ? 0 : rightInfo.maxSubBTSize)
                    + 1;
            isBST = true;
        }

        return new Info(isBST, maxSubBTSize, max, min);


    }
}
