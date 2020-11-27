package com.rukawa.algorithm.base.class08;

import com.rukawa.algorithm.base.class07.Node;

/**
 * Created with Intellij IDEA
 *
 * @Author：SuperHai
 * @Date：2020-07-21 23:34
 * @Version：1.0
 */
public class Code05_MaxSubBSTHead {

    public static Node maxSubBSTHead02(Node node) {
        if (node == null) {
            return null;
        }

        return process(node).maxSubBSTHead;
    }

    public static class Info {
        public Node maxSubBSTHead;
        public int maxSubBSTSize;
        public int max;
        public int min;

        public Info(Node maxSubBSTHead, int maxSubBSTSize, int max, int min) {
            this.maxSubBSTHead = maxSubBSTHead;
            this.maxSubBSTSize = maxSubBSTSize;
            this.max = max;
            this.min = min;
        }
    }

    public static Info process(Node node) {
        if (node == null) {
            return null;
        }

        Info leftInfo = process(node.left);
        Info rightInfo = process(node.right);

        Node maxSubBSTHead = null;
        int maxSubBSTSize = 0;
        int max = node.value;
        int min = node.value;

        if (leftInfo != null) {
            max = Math.max(max, leftInfo.max);
            min = Math.min(min, leftInfo.min);
            maxSubBSTHead = leftInfo.maxSubBSTHead;
            maxSubBSTSize = leftInfo.maxSubBSTSize;
        }

        if (rightInfo != null) {
            max = Math.max(max, rightInfo.max);
            min = Math.min(min, rightInfo.min);
            if (rightInfo.maxSubBSTSize > maxSubBSTSize) {
                maxSubBSTHead = rightInfo.maxSubBSTHead;
                maxSubBSTSize = rightInfo.maxSubBSTSize;
            }
        }

        if ((leftInfo == null ? true : (leftInfo.maxSubBSTHead == node.left && leftInfo.max < node.value))
        && (rightInfo == null ? true : (rightInfo.maxSubBSTHead == node.right && rightInfo.min > node.value))) {
            maxSubBSTHead = node;
            maxSubBSTSize = (leftInfo == null ? 0 : leftInfo.maxSubBSTSize)
                    + (rightInfo == null ? 0 : rightInfo.maxSubBSTSize)
                    + 1;
        }

        return new Info(maxSubBSTHead, maxSubBSTSize, max, min);



    }
}
