package com.rukawa.algorithm.base.class03;

import sun.security.provider.Sun;

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
     * 给定一个整数数组nums，返回区间和在[lower, upper]之间的个数，包含lower和upper。
     * 区间和S(i, j)表示在nums中，位置从i到j的元素之和，包含i和j(i ≤ j)。
     *
     * 说明:
     * 最直观的算法复杂度是O(n2) ，请在此基础上优化你的算法。
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
     * 转化：
     * 求0～17范围有多少个累加和在(10，40)范围上，已知sum(0,17)=100
     * 如果知道0～7范围上的累加和是70，那么8～17的累加和是30，刚好在10～40范围内，符合条件 -> 如果0～7的累加和在(100-60,100-10)范围内就是达标
     *
     * 求以i位置结尾的子数组，有多少个在[lower,upper]范围上 == i之前的所有前缀和中有多少前缀和在[x-upper, x-lower]
     *
     * 假设0～17的整体前缀和是100，求以17结尾的子数组中有多少个数在[10,40]范围内？
     * 0～16范围上有多少前缀和在[60,90]范围上
     *
     * 假设0～17的整体前缀和是100，假设0～5的前缀和在70，可以推出6～17的前缀和是30，此时在[10, 40]
     */
    public static int merge(long[] sum, int L, int M, int R, int lower, int upper) {
        int ans = 0;
        int windowL = L;
        int windowR = L;
        // 窗口是不可回退的，时间复杂度O(N)
        // 对于右组中的每个数，求在左组中有多少个数，位于[x-upper,x-lower]
        /**
         * 2,5,8,9,11,15      6,7,7,8,10,11      [-1,2]
         * 此时：右边窗口的数是6，范围为[4, 7],窗口的右边界不能大于7，窗口的左边界不能超过4，此时只有5
         *      右边窗口的数是7，范围为[5,8],窗口的右边界不能大于8，窗口的左边界不能超过5，此时有5，8
         *      右边窗口的数是7，。。。                                             此时有5，8
         *      右边窗口的数是8，范围是[6,9]，窗口的右边界不能大于9，窗口的左边界不能超过6，此时有8，9
         *      右边窗口的数是10，范围是[8,11]，窗口的右边界不能大于11，窗口的左边界不能超过8，此时有8，9，11
         *      右边窗口的数是11，范围是[9,12]，窗口的右边界不能大于12，窗口的左边界不能超过9，此时有9，11
         */
        for (int i = M + 1; i <= R; i++) {
            long min = sum[i] - upper;
            long max = sum[i] - lower;
            // 窗口上限不能比max大
            while (windowR <= M && sum[windowR] <= max) {
                windowR++;
            }
            // 窗口下限不能比min大
            while (windowL <= M && sum[windowL] < min) {
                windowL++;
            }
//            ans += Math.max(0, windowR - windowL);
            ans += windowR - windowL;
        }

        long[] help = new long[R - L + 1];
        int index =0;
        int p1 = L;
        int p2 = M + 1;
        while (p1 <= M && p2 <= R) {
            help[index++] = sum[p1] <= sum[p2] ? sum[p1++] : sum[p2++];
        }
        while (p1 <= M) {
            help[index++] = sum[p1++];
        }
        while (p2 <= R) {
            help[index++] = sum[p2++];
        }
        for (index = 0; index < help.length; index++) {
            sum[L + index] = help[index];
        }
        return ans;
    }
}
