package com.rukawa.algorithm.leetcode.other;

/**
 * Created with Intellij IDEA
 *
 * @Author：SuperHai
 * @Date：2021-01-19 20:58
 * @Version：1.0
 */
public class Problem_0805_SplitArrayWithSameAverage {
    /**
     * 数组的均值分割
     * 给定的整数数组 A ，我们要将 A数组 中的每个元素移动到 B数组 或者 C数组中。（B数组和C数组在开始的时候都为空）
     *
     * 返回true ，当且仅当在我们的完成这样的移动后，可使得B数组的平均值和C数组的平均值相等，并且B数组和C数组都不为空。
     *
     * 示例:
     * 输入:
     * [1,2,3,4,5,6,7,8]
     * 输出: true
     * 解释: 我们可以将数组分割为 [1,4,5,8] 和 [2,3,6,7], 他们的平均值都是4.5。
     * 注意:
     *
     * A 数组的长度范围为 [1, 30].
     * A[i] 的数据范围为 [0, 10000].
     */
    public static boolean splitArraySameAverage(int[] nums) {
        if (nums == null || nums.length < 2) {
            return false;
        }
        int sum = 0;
        for (int num : nums) {
            sum += num;
        }
        sum >>= 1;
        int N = nums.length;
        int[][] dp = new int[N + 1][sum + 1];
        for (int i = N - 1; i >= 0; i--) {
            for (int rest = 0; rest <= sum; rest++) {
                int p1 = dp[i + 1][rest];
                int p2 = 0;
                if (nums[i] <= rest) {
                    p2 = dp[i + 1][rest - nums[i]] + nums[i];
                }
                dp[i][rest] = Math.max(p1, p2);
            }
        }
        System.out.println(dp[0][sum] + "--" + sum);
        return dp[0][sum] == sum;
    }

    public static void main(String[] args) {
        int[] arr = {6,8,18,3,1};
        System.out.println(splitArraySameAverage(arr));
    }
}
