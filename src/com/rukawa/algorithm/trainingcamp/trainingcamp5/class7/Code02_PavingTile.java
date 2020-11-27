package com.rukawa.algorithm.trainingcamp.trainingcamp5.class7;

/**
 * Created with Intellij IDEA
 *
 * @Author：SuperHai
 * @Date：2020-11-12 8:07
 * @Version：1.0
 */
public class Code02_PavingTile {
    /**
     * 贴瓷砖问题你有无限的1*2的砖块
     * 要铺满2*N的区域，不同的铺法有多少种？
     *
     * 你有无限的1*2的砖块，要铺满M*N的区域，不同的铺法有多少种？
     */
    /**
     *  2 * M块瓷砖铺地问题非常简单，这里是解决N * M铺地的问题
     */
    public static int ways1(int N, int M) {
        if (N < 1 || M < 1 || ((N * M) & 1) != 0) {
            return 0;
        }
        if (N == 1 || M == 1) {
            return 1;
        }
        int[] pre = new int[M];
        for (int i = 0; i < pre.length; i++) {
            pre[i] = 1;
        }
        return process(pre, 0, N);
    }

    /**
     *
     * @param pre 表示level-1行的状态
     * @param level 正在level行做决定
     * @param N 一共有多少行，固定参数
     * @return level-2行及其之上所有行，都摆满瓷砖了， level做决定，让所有区域都铺满，方法数返回
     */
    public static int process(int[] pre, int level, int N) {
        // base case
        if (level == N) {
            for (int i = 0; i < pre.length; i++) {
                if (pre[i] == 0) {
                    return 0;
                }
            }
            return 1;
        }
        // 没有到终止行，可以选择在当前的level行摆瓷砖
        // op[i] == 0 可以考虑放砖，op[i] == 1只能跟i-1对齐竖着放一块，没有任何选择性
        int[] op = getOp(pre);
        return dfs(op, 0, level, N);
    }

    // op[i]==0 可以考虑摆砖
    // op[i]==1 只能竖着向上摆
    public static int dfs(int[] op, int col, int level, int N) {
        // 在列上自由发挥，玩深度优先遍历，当col来到终止列，i行的决定做完了
        // 轮到i+1行做决定
        if (col == op.length) {
            return process(op, level + 1, N);
        }
        int res = 0;
        // 决定在col位置不横着摆放瓷砖
        res += dfs(op, col + 1, level, N); // col位置上不摆放瓷砖

        // 在col位置横着摆放瓷砖，向右横摆
        if (col + 1 < op.length && op[col] == 0 && op[col + 1] == 0) {
            op[col] = 1;
            op[col + 1] = 1;
            res += dfs(op, col + 2, level, N);
            op[col] = 0;
            op[col + 1] = 0;
        }
        return res;
    }

    public static int[] getOp(int[] pre) {
        int[] op = new int[pre.length];
        for (int i = 0; i < pre.length; i++) {
            op[i] = pre[i] == 1 ? 0 : 1;
        }
        return op;
    }
}
