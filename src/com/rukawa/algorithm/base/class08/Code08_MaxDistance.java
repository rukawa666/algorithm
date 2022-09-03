package com.rukawa.algorithm.base.class08;

import com.rukawa.algorithm.base.class07.Node;

/**
 * Created with Intellij IDEA
 *
 * @Author：SuperHai
 * @Date：2020-07-21 7:24
 * @Version：1.0
 */
public class Code08_MaxDistance {

    /**
     * 给定一棵二叉树的头节点head，任何两个节点之间都存在距离，
     * 返回整棵二叉树的最大距离
     */

    /**
     * 可能性：
     * 1. 和head无关: 左树的最大距离  右树的最大距离
     * 2. 和head有关：左树离head最远的点   右树离head最远的点
     *    最远的点，就是高度
     */
    public static int maxDistance02(Node head) {
        return process(head).maxDistance;
    }

    public static Info process(Node node) {
        if (node == null) {
            return new Info(0, 0);
        }
        Info leftInfo = process(node.left);
        Info rightInfo = process(node.right);

        int height = Math.max(leftInfo.height, rightInfo.height) + 1;

        int p1 = leftInfo.maxDistance;
        int p2 = rightInfo.maxDistance;
        int p3 = leftInfo.height + rightInfo.height + 1;
        int maxDistance = Math.max(p1, Math.max(p2, p3));
        return new Info(maxDistance, height);
    }

    public static class Info {
        public int maxDistance;
        public int height;

        public Info(int m, int h) {
            this.maxDistance = m;
            this.height = h;
        }
    }
}
