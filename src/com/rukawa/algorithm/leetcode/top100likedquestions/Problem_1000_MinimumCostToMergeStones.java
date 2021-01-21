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
        int n = stones.length;
        if ((n - 1) % (K - 1) > 0) {
            return -1;
        }
        int[] presum = new int[n + 1];
        for (int i = 0; i < n; i++) {
            presum[i + 1] = presum[i] + stones[i];
        }
        int[][][] dp = new int[n][n][K + 1];
        return process(0, n - 1, 1, stones, K, presum, dp);
    }

    public static int process(int i, int j, int part, int[] arr, int K, int[] presum, int[][][] dp) {
        if (dp[i][j][part] != 0) {
            return dp[i][j][part];
        }
        if (i == j) {
            return part == 1 ? 0 : -1;
        }
        if (part == 1) {
            int next = process(i, j, K, arr, K, presum, dp);
            if (next == -1) {
                dp[i][j][part] = -1;
                return -1;
            } else {
                dp[i][j][part] = next + presum[j + 1] - presum[i];
                return next + presum[j + 1] - presum[i];
            }
        } else {
            int ans = Integer.MAX_VALUE;
            // i...mid是第1块，剩下的是part-1块
            for (int mid = i; mid < j; mid += K - 1) {
                int next1 = process(i, mid, 1, arr, K, presum, dp);
                int next2 = process(mid + 1, j, part - 1, arr, K, presum, dp);
                if (next1 == -1 || next2 == -1) {
                    dp[i][j][part] = -1;
                    return -1;
                } else {
                    ans = Math.min(ans, next1 + next2);
                }
            }
            dp[i][j][part] = ans;
            return ans;
        }
    }
}
