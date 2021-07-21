package com.rukawa.algorithm.leetcode.top100likedquestions;

/**
 * @className: Problem_0887_superEggDrop
 * @description: TODO 类描述
 * @author: 鎏川疯
 * @date: 2021/7/21 0021 16:42
 **/
public class Problem_0887_superEggDrop {
    /**
     * 鸡蛋掉落
     * 给你 k 枚相同的鸡蛋，并可以使用一栋从第 1 层到第 n 层共有 n 层楼的建筑。
     * 已知存在楼层 f ，满足0 <= f <= n ，任何从 高于 f 的楼层落下的鸡蛋都会碎，从 f 楼层或比它低的楼层落下的鸡蛋都不会破。
     * 每次操作，你可以取一枚没有碎的鸡蛋并把它从任一楼层 x 扔下（满足1 <= x <= n）。如果鸡蛋碎了，你就不能再次使用它。如果某枚鸡蛋扔下后没有摔碎，
     * 则可以在之后的操作中 重复使用 这枚鸡蛋。
     * 请你计算并返回要确定 f 确切的值 的 最小操作次数 是多少？
     *
     * 示例 1：
     * 输入：k = 1, n = 2
     * 输出：2
     * 解释：
     * 鸡蛋从 1 楼掉落。如果它碎了，肯定能得出 f = 0 。
     * 否则，鸡蛋从 2 楼掉落。如果它碎了，肯定能得出 f = 1 。
     * 如果它没碎，那么肯定能得出 f = 2 。
     * 因此，在最坏的情况下我们需要移动 2 次以确定 f 是多少。
     * 
     * 示例 2：
     * 输入：k = 2, n = 6
     * 输出：3
     * 
     * 示例 3：
     * 输入：k = 3, n = 14
     * 输出：4
     *
     * 提示：
     * 1 <= k <= 100
     * 1 <= n <= 104
     */
    public int superEggDrop(int k, int n) {
        if (k < 1 || n < 1) {
            return 0;
        }
        // 棋子只有一颗，只能从第1层开始扔，一直尝试
        if (k == 1) {
            return n;
        }
        int[][] dp = new int[n + 1][k + 1];
        for (int i = 1; i <= n; i++) {
            dp[i][1] = i;
        }
        // 100层楼剩余三颗棋子，把第一颗棋子仍在15层得到的最优解，所以best记为15
        int[][] best = new int[n + 1][k + 1];
        // 只剩下1层楼，最优方案肯定是第1层
        for (int j = 1; j <= k; j++) {
            dp[1][j] = 1;
            best[1][j] = 1;
        }

        // 从上往下，从右往左填写，因为要取得上+右的位置对
        for (int i = 2; i <= n; i++) {
            for (int j = k; j > 1; j--) {
                int ans = Integer.MAX_VALUE;
                int choose = -1;
                // 上方给予一个下限
                int down = best[i -1][j];
                // 右方给予一个上限
                int up = j == k ? i : best[i][j + 1];
                for (int first = down; first <= up; first++) {
                    int cur = Math.max(dp[i - first][j], dp[first - 1][j - 1]);
                    if (cur <= ans) {
                        ans = cur;
                        choose = first;
                    }
                }
                dp[i][j] = ans + 1;
                best[i][j] = choose;
            }
        }
        return dp[n][k];
    }
}
