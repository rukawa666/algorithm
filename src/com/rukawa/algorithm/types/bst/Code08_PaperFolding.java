package com.rukawa.algorithm.types.bst;

/**
 * Created with Intellij IDEA
 *
 * @Author：SuperHai
 * @Date：2021-01-25 0:14
 * @Version：1.0
 */
public class Code08_PaperFolding {
    /**
     * 请把一段纸条竖着放着桌子上，然后从纸条的下边向上方对折一次，压出折痕后展开。此时折痕是凹下去的，即折痕凸起的方向指向纸条的背面。
     * 如果从纸条的下边向上方依次连续对折两次，压出折痕后展开，此时有三条折痕，从上到下依次是下折痕，下折痕和上折痕
     *
     * 给定一个参数N，代表纸条从下边向上依次对折的次数。请从上下依次打印所有折痕的方向
     * 题解：经对折发现，是二叉树的中序遍历
     *
     * 规则：
     *  1、头节点是凹折痕
     *  2、所有左子树的头都是凹折痕
     *  3、所有右子树的头都是凸折痕
     */

    public static void printAllFolds(int N) {
        printProcess(1, N, true);
    }

    /**
     * 当前来到了一个节点
     * 这个节点在第i层，一共有N层，N固定不变
     * 这个节点如果是凹的话，down = T
     * 这个节点如果是凸的话，down = F
     * 中序打印你想象的节点为头的整棵树
     */
    public static void printProcess(int i, int N, boolean down) {
        if (i > N) {
            return;
        }
//        System.out.print(down ? "凹 " : "凸 "); // 先序遍历
        printProcess(i + 1, N, true);
        System.out.print(down ? "凹 " : "凸 "); // 中序遍历
        printProcess(i + 1, N, false);
    }

    public static void main(String[] args) {
        int N = 3;
        printAllFolds(N);
    }

}
