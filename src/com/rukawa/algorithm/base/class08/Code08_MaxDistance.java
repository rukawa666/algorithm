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
     * @param head
     * @return
     */
    public static int maxDistance02(Node head) {
        return process(head).maxDistance;
    }

    public static class Info {
        public int maxDistance;
        public int height;

        public Info(int maxDistance, int height) {
            this.maxDistance = maxDistance;
            this.height = height;
        }
    }

    public static Info process(Node X) {
        if (X == null) {
            return new Info(0, 0);
        }

        Info leftInfo = process(X.left);
        Info rightInfo = process(X.right);

        int height = Math.max(leftInfo.height, rightInfo.height) + 1;
        int maxDistance = Math.max(Math.max(leftInfo.maxDistance, rightInfo.maxDistance), leftInfo.height + rightInfo.height + 1);
        return new Info(maxDistance, height);
    }
}
