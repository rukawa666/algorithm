package com.rukawa.algorithm.leetcode.top100likedquestions;

/**
 * Created with Intellij IDEA
 *
 * @Author：SuperHai
 * @Date：2020-10-31 23:33
 * @Version：1.0
 */
public class Problem_1000_MinimumCostToMergeStones {
    /**
     * 合并石头的最低成本
     * 有 N 堆石头排成一排，第 i 堆中有 stones[i] 块石头。
     * 每次移动（move）需要将连续的 K 堆石头合并为一堆，而这个移动的成本为这 K 堆石头的总数。
     * 找出把所有石头合并成一堆的最低成本。如果不可能，返回 -1 。
     *
     * 示例 1：
     * 输入：stones = [3,2,4,1], K = 2
     * 输出：20
     * 解释：
     * 从 [3, 2, 4, 1] 开始。
     * 合并 [3, 2]，成本为 5，剩下 [5, 4, 1]。
     * 合并 [4, 1]，成本为 5，剩下 [5, 5]。
     * 合并 [5, 5]，成本为 10，剩下 [10]。
     * 总成本 20，这是可能的最小值。
     *
     * 示例 2：
     * 输入：stones = [3,2,4,1], K = 3
     * 输出：-1
     * 解释：任何合并操作后，都会剩下 2 堆，我们无法再进行合并。所以这项任务是不可能完成的。
     *
     * 示例 3：
     * 输入：stones = [3,5,1,2,6], K = 3
     * 输出：25
     * 解释：
     * 从 [3, 5, 1, 2, 6] 开始。
     * 合并 [5, 1, 2]，成本为 8，剩下 [3, 8, 6]。
     * 合并 [3, 8, 6]，成本为 17，剩下 [17]。
     * 总成本 25，这是可能的最小值。
     *  
     *
     * 提示：
     * 1 <= stones.length <= 30
     * 2 <= K <= 30
     * 1 <= stones[i] <= 100
     */
    public int mergeStones(int[] stones, int K) {
        // 定义一个函数：在l...r范围上变成k份最小代价是多少  范围尝试+业务限制模型
        // f(l,r,1) 最终l...r范围上合成1份  依赖f(l,r,k) l...r范围上合成k份，然后k份最终合并成一堆

        int n = stones.length;
        // n个数，到底能不能由K个相邻的数合并，最终变成一个数
        // 观察得到
        if ((n - 1) % (K - 1) > 0) {
            return -1;
        }
        int[] preSum = new int[n + 1];
        for (int i = 0; i < n; i++) {
            preSum[i + 1] = preSum[i] + stones[i];
        }
        int[][][] dp = new int[n][n][K + 1];
        return process(stones, 0, n - 1, K, 1, preSum, dp);
    }

    // 优化
    // 在l...r范围上合成p份最小代价是多少
    public int process(int[] stones, int l, int r, int K, int P, int[] preSum, int[][][] dp) {
        if (dp[l][r][P] != 0) {
            return dp[l][r][P];
        }

        if (l == r) {
            // 只有一个数分成一份的代价是0，否则无效解
            return P == 1 ? 0 : -1;
        }

        // l != r
        if (P == 1) {
            int next = process(stones, l, r, K, K, preSum, dp);
            if (next == -1) {
                dp[l][r][P] = -1;
                return -1;
            } else {
                dp[l][r][P] = next + preSum[r + 1] - preSum[l];
                return next + preSum[r + 1] - preSum[l];
            }
        } else {
            int res = Integer.MAX_VALUE;
            // mid += K - 1 观察出来的
            for (int mid = l; mid < r; mid += K - 1) {
                // l...mid是第一块，上下的部分是p-1块
                int left = process(stones, l, mid, K, 1, preSum, dp);
                int right = process(stones, mid + 1, r, K, P - 1, preSum, dp);
                if (left == -1 || right == -1) {
                    dp[l][r][P] = -1;
                    return -1;
                } else {
                    res = Math.min(res, left + right);
                }
            }
            dp[l][r][P] = res;
            return res;
        }
    }

    // 初始
//    public int process1(int[] stones, int l, int r, int K, int P, int[] preSum) {
//        if (l == r) {
//            // 只有一个数分成一份的代价是0，否则无效解
//            return P == 1 ? 0 : -1;
//        }
//        // l != r
//        if (P == 1) {
//            int next = process1(stones, l, r, K, K, preSum);
//            if (next == -1) {
//                return -1;
//            } else {
//                return next + preSum[r + 1] - preSum[l];
//            }
//        } else {
//            int res = Integer.MAX_VALUE;
//            // l...mid是第一块，上下的部分是p-1块
//            for (int mid = l; mid < r; mid += K - 1) {
//                int left = process1(stones, l, mid, K, 1, preSum);
//                int right = process1(stones, mid + 1, r, K, P - 1, preSum);
//                if (left != -1 && right != -1) {
//                    res = Math.min(res, left + right);
//                }
//            }
//            return res;
//        }
//    }

    // 在l...r范围上合成p份最小代价是多少
//    public int func(int[] stones, int l, int r, int k, int p) {
//        if (l...r 根本弄不出p份) {
//            return Integer.MAX_VALUE;
//        }
//        if (p == 1) {
//            return l == r ? 0 : func(null, l,r, k, k) + 最后一次大合并的代价;
//        }
//
//        int res = Integer.MAX_VALUE;
//        for (int i = l; i < r; i++) {
//            // L...i分成一份 i+1...r分成p-1份
//            int left = func(stones, l, i, k, 1);
//            if (left == Integer.MAX_VALUE) {
//                continue;
//            }
//            int right = func(stones, i + 1, r, k, p - 1);
//            if (right == Integer.MAX_VALUE) {
//                continue;
//            }
//            int all = left + right;
//            res = Math.max(res, all);
//        }
//        return res;
//    }
}
