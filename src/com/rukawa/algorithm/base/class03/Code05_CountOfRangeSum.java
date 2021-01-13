package com.rukawa.algorithm.base.class03;

/**
 * Created with Intellij IDEA
 *
 * @Author：SuperHai
 * @Date：2021-01-12 23:23
 * @Version：1.0
 */
public class Code05_CountOfRangeSum {

    /**
     * 区间和的个数
     * 给定一个整数数组 nums，返回区间和在 [lower, upper] 之间的个数，包含 lower 和 upper。
     * 区间和 S(i, j) 表示在 nums 中，位置从 i 到 j 的元素之和，包含 i 和 j (i ≤ j)。
     *
     * 说明:
     * 最直观的算法复杂度是 O(n2) ，请在此基础上优化你的算法。
     *
     * 示例:
     * 输入: nums = [-2,5,-1], lower = -2, upper = 2,
     * 输出: 3
     * 解释: 3个区间分别是: [0,0], [2,2], [0,2]，它们表示的和分别为: -2, -1, 2。
     */
    // O(N*logN)
    public int countRangeSum(int[] nums, int lower, int upper) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        long[] sum = new long[nums.length];
        sum[0] = nums[0];
        for (int i = 1; i < sum.length; i++) {
            sum[i] = sum[i - 1] + nums[i];
        }
        return process(sum, 0, sum.length - 1, lower, upper);
    }

    /**
     * arr[L...R] 不传进来，只传进来sum(前缀和数组)
     * 在原始的arr[L...R]中，有多少个子数组累加和在[lower, upper]上
     * @param sum
     * @param L
     * @param R
     * @param lower
     * @param upper
     * @return
     */
    public static int process(long[] sum, int L, int R, int lower, int upper) {
        if (L == R) {
            return sum[L] >= lower && sum[L] <= upper ? 1 : 0;
        }
        int M = L + ((R - L) >>>1);
        return process(sum, L, M, lower, upper) + process(sum, M + 1, R, lower, upper) + merge(sum, L, M, R, lower, upper);
    }


    /**
     * 假设0~i整体累加和是x，范围是[lower,upper], 求必须从i位置结尾的子数组，目标有多少个在[lower,upper]范围上
     * 等同于，求i之前的所有前缀和中有多少个前缀和在[x-upper, x-lower]上
     */
    public static int merge(long[] arr, int L, int M, int R, int lower, int upper) {
        int ans = 0;
        int windowL = L;
        int windowR = L;
        // 窗口是不可回退的，时间复杂度O(N)
        for (int i = M + 1; i <= R; i++) {
            long min = arr[i] - upper;
            long max = arr[i] - lower;
            while (windowR <= M && arr[windowR] <= max) {
                windowR++;
            }

            while (windowL <= M && arr[windowL] < min) {
                windowL++;
            }
            ans += Math.max(0, windowR - windowL);
        }

        long[] help = new long[R - L + 1];
        int index =0;
        int p1 = L;
        int p2 = M + 1;
        while (p1 <= M && p2 <= R) {
            help[index++] = arr[p1] <= arr[p2] ? arr[p1++] : arr[p2++];
        }
        while (p1 <= M) {
            help[index++] = arr[p1++];
        }
        while (p2 <= R) {
            help[index++] = arr[p2++];
        }
        for (index = 0; index < help.length; index++) {
            arr[L + index] = help[index];
        }
        return ans;
    }
}
