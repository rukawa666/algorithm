package com.rukawa.algorithm.base.class08;

import com.rukawa.algorithm.base.class07.Node;

/**
 * Created with Intellij IDEA
 *
 * @Author：SuperHai
 * @Date：2020-07-21 6:53
 * @Version：1.0
 */
public class Code01_IsBalanced {


    public static boolean isBalances(Node head) {
        return process02(head).isBalanced;
    }

    // 左、右要求返回一样，Info信息返回的结构体
    public static class Info {
        public boolean isBalanced;
        public int height;

        public Info(boolean b, int h) {
            this.isBalanced = b;
            this.height = h;
        }
    }

    public static Info process02(Node X) {
        if (X == null) {
            return new Info(true, 0);
        }

        Info leftInfo = process02(X.left);
        Info rightInfo = process02(X.right);

        int height = Math.max(leftInfo.height, rightInfo.height) + 1;

        boolean isBalanced = true;
        if (!leftInfo.isBalanced || !rightInfo.isBalanced || Math.abs(leftInfo.height - rightInfo.height) > 1) {
            isBalanced = false;
        }
        return new Info(isBalanced, height);
    }
}
