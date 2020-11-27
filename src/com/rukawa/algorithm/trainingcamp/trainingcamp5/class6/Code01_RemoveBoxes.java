package com.rukawa.algorithm.trainingcamp.trainingcamp5.class6;

/**
 * Created with Intellij IDEA
 *
 * @Author：SuperHai
 * @Date：2020-11-12 8:06
 * @Version：1.0
 */
public class Code01_RemoveBoxes {
    /**
     * 移除盒子
     * 给出一些不同颜色的盒子，盒子的颜色由数字表示，即不同的数字表示不同的颜色。
     * 你将经过若干轮操作去去掉盒子，直到所有的盒子都去掉为止。每一轮你可以移除具有相同颜色的连续 k 个盒子（k >= 1），
     * 这样一轮之后你将得到 k*k 个积分。
     * 当你将所有盒子都去掉之后，求你能获得的最大积分和。
     *
     * 示例：
     * 输入：boxes = [1,3,2,2,2,3,4,3,1]
     * 输出：23
     * 解释：
     * [1, 3, 2, 2, 2, 3, 4, 3, 1]
     * ----> [1, 3, 3, 4, 3, 1] (3*3=9 分)
     * ----> [1, 3, 3, 3, 1] (1*1=1 分)
     * ----> [1, 1] (3*3=9 分)
     * ----> [] (2*2=4 分)
     *
     * 提示：
     * 1 <= boxes.length <= 100
     * 1 <= boxes[i] <= 100
     */
    public int removeBoxes(int[] boxes) {
        int N = boxes.length;
        int[][][] dp = new int[N][N][N];
        return process(boxes, 0, N - 1, 0, dp);
    }

    // boxes[L...R]，前面还跟着K个boxes[L]
    // 前面的包袱和L...R所有的数据都消掉，最好得分是什么
    public static int process(int[] boxes, int L, int R, int K, int[][][] dp) {
        if (L > R) {
            return 0;
        }
        if (dp[L][R][K] != 0) {
            return dp[L][R][K];
        }
        if (L == R) {
            return (K + 1) * (K + 1);
        }
        while (L < R && boxes[L] == boxes[L + 1]) {
            L++;
            K++;
        }
        int res = (K + 1) * (K + 1) + process(boxes, L + 1, R, 0, dp);
        for (int M = L + 1; M <= R; M++) {
            if (boxes[L] == boxes[M]) {
                res = Math.max(res, process(boxes, L + 1, M - 1, 0, dp) + process(boxes, M, R, K + 1, dp));
            }
        }
        dp[L][R][K] = res;
        return res;
    }
}
