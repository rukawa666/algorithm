package com.rukawa.algorithm.base.class32_statusCompression;

/**
 * create by hqh on 2023/1/12
 */
public class Code03_PavingTile {
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
        // pre 代表上一行的瓷砖状况
        int[] pre = new int[M];
        for (int i = 0; i < pre.length; i++) {
            pre[i] = 1;
        }
        return func1(pre, 0, N);
    }

    // pre 表示level-1行的状态
    // 正在level行做决定
    // 一共有多少行，固定参数
    // level-2行及其之上所有行，都摆满瓷砖了
    // level做决定，让所有区域都铺满，方法数返回
    public static int func1(int[] pre, int level, int n) {
        if (level == n) {
            for (int i = 0; i < pre.length; i++) {
                if (pre[i] == 0) {
                    return 0;
                }
            }
            return 1;
        }
        // 没有到终止行，可以选择在当前的level行摆放瓷砖
        // op[i] == 0 可以考虑放砖，op[i] == 1只能跟i-1对齐竖着放一块，没有任何选择性
        int[] op = getOp(pre);
        // op 1 0 0 1 0 0 1 1
        // 0位置的磁砖只能往右放磁砖，做深度优先便利
        // 把每一种出现的决定忘下行传递
        return dfs(op, 0, level, n);
    }

    // op[i]==0 可以考虑摆砖
    // op[i]==1 只能竖着向上摆
    private static int dfs(int[] op, int col, int level, int n) {
        // 在列上自由发挥，玩深度优先遍历，当col来到终止列，i行的决定做完了
        // 轮到i+1行做决定
        // 到达终止列，当前行的样子是大函数中当前行的上一行
        /**
         *    0 1 2 3 4 5 6 7
         * op 1 1 0 0 0 0 1 1  在2，3摆放磁砖
         * op 1 1 1 1 0 0 1 1
         * 在第2列向右摆放一个磁砖，第3摆放一个砖，无法自由发挥，第4，5都不摆放
         * 此时level层已经决定，看level+1怎么决定
         */
        if (col == op.length) {
            return func1(op, level + 1, n);
        }

        int res = 0;
        // 决定在col位置不横着摆放瓷砖
        res += dfs(op, col + 1, level, n); // col位置上不摆放瓷砖

        // 在col位置横着摆放瓷砖，向右横摆
        if (col + 1 < op.length && op[col] == 0 && op[col + 1] == 0) {
            // col和col+1横着摆放一块磁砖
            op[col] = 1;
            op[col + 1] = 1;
            res += dfs(op, col + 2, level, n);
            // 恢复现场
            op[col] = 0;
            op[col + 1] = 0;
        }
        return res;

    }

    private static int[] getOp(int[] pre) {
        int[] cur = new int[pre.length];
        for (int i = 0; i < pre.length; i++) {
            // 上一行的状态是0，下一行状态是1
            // 上一行的状态是1，下一行状态是0
            // pre 0 0 0 1 1 1 0 0
            // cur 1 1 1 0 0 0 1 1
            cur[i] = pre[i] ^ 1;
        }
        return cur;
    }

    // 改成记忆化搜索
    // min(N,M)不超过32
    public static int ways2(int N, int M) {
        if (N < 1 || M < 1 || ((N * M) & 1) != 0) {
            return 0;
        }
        if (N == 1 || M == 1) {
            return 1;
        }
        // max 做行  min做列
        int max = Math.max(N, M);
        int min = Math.min(N, M);

        // min=7 ,需要7个1
        // ((1 << 7) - 1)  -> 1111111
        // pre代表-1行的状况
        int pre = (1 << (min)) - 1;
        return process2(pre, 0 , max, min);
    }

    /**
     *
     * @param pre 表示level-1行的状态
     * @param level 正在level行做决定
     * @param N 一共有多少行，固定参数
     * @return level-2行及其之上所有行，都摆满瓷砖了， level做决定，让所有区域都铺满，方法数返回
     */
    public static int process2(int pre, int level, int N, int M) {
        // base case
        if (level == N) {
            // 上一行如果全满，返回一种方法，否则0种方法
            return pre == ((1 << M) - 1) ? 1 : 0;
        }
        // 通过上一行是否有磁砖，得出下一行的磁砖情况
        // pre 0 1 1 0 1 1 0 0  1代表有磁砖
        // op  1 0 0 1 0 0 1 1
        // 如果pre行位置没有磁砖，则op行位置必须竖着放磁砖，上一行0位置的磁砖被填满

        // 没有到终止行，可以选择在当前的level行摆瓷砖
        // op[i] == 0 可以考虑放砖，op[i] == 1只能跟i-1对齐竖着放一块，没有任何选择性
        int op = ((~pre) & ((1 << M) - 1));
        // op 1 0 0 1 0 0 1 1
        // 0位置的磁砖只能往右放磁砖，做深度优先便利
        // 把每一种出现的决定忘下行传递
        // 最后一列开始做决定
        return dfs2(op, M - 1, level, N, M);
    }

    // op[i]==0 可以考虑摆砖
    // op[i]==1 只能竖着向上摆
    public static int dfs2(int op, int col, int level, int N, int M) {
        // col 0列可以做决定，在-1列往下一行做决定
        if (col == -1) {
            return process2(op, level + 1, N, M);
        }

        int res = 0;
        // 第一个决定，col列不动，从col-1的位置开始做决定，col从高列往低列走
        res += dfs2(op, col - 1, level, N, M);

        // 当前列是0状态
        // 下一列也是0
        if ((op & (1 << col)) == 0 && col - 1 >= 0 && ((op & (1 << (col - 1))) == 0)) {
            // 3的二进制11
            // 当前位置来到7位置，所以11向左移动6位
            res += dfs2((op | (3 << (col - 1))), col - 2, level, N, M);
        }
        return res;
    }

    public static void main(String[] args) {
        int N = 10;
        int M = 6;
        System.out.println(ways1(N, M));
        System.out.println(ways2(N, M));
    }
}
