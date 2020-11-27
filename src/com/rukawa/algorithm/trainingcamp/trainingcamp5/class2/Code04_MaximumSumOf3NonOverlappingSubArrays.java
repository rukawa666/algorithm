package com.rukawa.algorithm.trainingcamp.trainingcamp5.class2;

/**
 * Created with Intellij IDEA
 *
 * @Author：SuperHai
 * @Date：2020-10-31 23:36
 * @Version：1.0
 */
public class Code04_MaximumSumOf3NonOverlappingSubArrays {
    /**
     * 给定一个数组arr，给定一个正数k。选出3个不重叠的子数组，每个子数组长度都是k，
     * 返回最大的三子数组的最大和
     */

    public static int[] maxSumOfThreeSubArrays(int[] nums, int k) {
        /**
         * 其他题
         * dp1[i]:代表0~i的子数组必须以i结尾的情况下，最大的累加和是多少
         * dp2[i]:代表0~i的子数组不要求以i结尾，最大的累加和是多少
         * dp1[i]可以加速得到dp2[i]
         * dp2[i]不以i结尾：dp2[i-1]
         * dp2[i]以i结尾：dp1[i]
         */

        /**
         * 本题思路：
         * dp[i]:代表0~i的子数组不要求以i结尾，选择k个数最大的累加和是多少
         * dp[i]不以i结尾：dp[i-1]
         * dp[i]以i结尾：arr从i位置往左推k个数，累加
         *
         * dp'[i]:代表i~N-1的子数组不要求以i开头，选择k个数最大的累加和是多少,从右到左
         * dp'[i]不以i开头：dp[i-1]
         * dp'[i]以i开头：arr[i]从i位置往右推k个数，累加
         *
         * 然后枚举中间的数，从dp[i]和dp'[i]获取值
         */
        int N = nums.length;
        int[] range = new int[N];
        int[] left = new int[N];
        int sum = 0;
        for (int i = 0; i < k; i++) {
            sum += nums[i];
        }
        range[0] = sum;
        left[k - 1] = 0;
        int max = sum;
        for (int i = k; i < N; i++) {
            sum = sum - nums[i - k] + nums[i];
            range[i - k + 1] = sum;
            left[i] = left[i - 1];
            if (sum > max) {
                max = sum;
                left[i] = i - k + 1;
            }
        }
        sum = 0;
        for (int i = N - 1; i >= N- k; i--) {
            sum += nums[i];
        }
        max = sum;
        int[] right = new int[N];
        right[N - k] = N - k;
        for (int i = N - k - 1; i >= 0; i--) {
            sum = sum - nums[i + k] + nums[i];
            right[i] = right[i + 1];
            if (sum >= max) {
                max = sum;
                right[i] = i;
            }
        }


        int a = 0;
        int b = 0;
        int c = 0;
        max = 0;
        // 为什么要-(2*k): 中间位置要留够k个，后面位置也要留够k个
        for (int i = k; i < N - 2 * k + 1; i++) { // 中间一块的起始点
            int part1 = range[left[i - 1]];
            int part2 = range[i];
            int part3 = range[right[i + k]];
            if (part1 + part2 + part3 > max) {
                max = part1 + part2 + part3;
                a = left[i - 1];
                b = i;
                c = right[i + k];
            }
        }
        return new int[]{a, b, c};
    }

    public static void main(String[] args) {
        int[] nums = { 9, 8, 7, 6, 2, 2, 2, 2 };
        int k = 2;
        int[] ans = maxSumOfThreeSubArrays(nums, k);
        System.out.println(ans[0] + "," + ans[1] + "," + ans[2]);

    }
}
