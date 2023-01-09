package com.rukawa.algorithm.base.class07_tree;

/**
 * Created with Intellij IDEA
 *
 * @Author：SuperHai
 * @Date：2020-07-20 23:52
 * @Version：1.0
 */
public class Code08_PaperFolding {

    /**
     * 请把一段纸条竖着放着桌子上，然后从纸条的下边向上方对折一次，压出折痕后展开。此时折痕是凹下去的，即折痕凸起的方向指向纸条的背面。
     * 如果从纸条的下边向上方依次连续对折两次，压出折痕后展开，此时有三条折痕，从上到下依次是下折痕，下折痕和上折痕
     *
     * 给定一个参数N，代表纸条从下边向上依次对折的次数。请从上下依次打印所有折痕的方向
     *
     * 题解：经对折发现，是二叉树的中序遍历
     * @param N
     */
    public static void printAllFolds(int N) {
        printProcess(1, N, true);
    }

    // 递归过程，来到了某一个节点
    // i是节点的层数，N一共的层数，down = true : 凹 , down = false : 凸
    public static void printProcess(int i, int N, boolean down) {
        if (i > N) {
            return;
        }
        printProcess(i + 1, N, true);
        System.out.println(down ? "凹 " : "凸 ");
        printProcess(i + 1, N, false);

    }

    public static void main(String[] args) {
        int N = 3;
        printAllFolds(N);
    }
}
