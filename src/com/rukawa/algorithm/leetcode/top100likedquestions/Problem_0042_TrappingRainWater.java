package com.rukawa.algorithm.leetcode.top100likedquestions;

/**
 * Created with Intellij IDEA
 *
 * @Author：SuperHai
 * @Date：2020-09-20 8:33
 * @Version：1.0
 */
public class Problem_0042_TrappingRainWater {
    /**
     * 接雨水
     * 给定 n 个非负整数表示每个宽度为 1 的柱子的高度图，计算按此排列的柱子，下雨之后能接多少雨水。
     *
     * 上面是由数组 [0,1,0,2,1,0,1,3,2,1,2,1] 表示的高度图，在这种情况下，可以接 6 个单位的雨水（蓝色部分表示雨水）。 感谢 Marcos 贡献此图。
     *
     * 示例:
     * 输入: [0,1,0,2,1,0,1,3,2,1,2,1]
     * 输出: 6
     */

    public int trap(int[] height) {
        /**
         * 思路：求每个i位置能搞定多少水，最终累加
         * 假设i位置是5，不包含i位置，从左边到i-1位置最大位置的高度是17；不包含i位置，从i+1位置及其往后最大位置的高度是23
         * 水平面最高能涨到17
         *
         * i位置的水量=0~i-1位置的max和i+1～n-1位置的max求min - i位置自身的值
         * 只要左边或者右边的最大位置水低于i位置的水，i位置只能存放0的水
         */
        if (height == null || height.length < 3) {
            return 0;
        }
        int N = height.length;
        int leftMax = height[0];
        int rightMax = height[N - 1];
        int L = 1;
        int R = N - 2;
        int water = 0;
        while (L <= R) {
            if (leftMax <= rightMax) {
                water += Math.max(0, leftMax - height[L]);
                leftMax = Math.max(leftMax, height[L++]);
            } else {
                water += Math.max(0, rightMax - height[R]);
                rightMax = Math.max(rightMax, height[R--]);
            }
        }
        return water;
    }
}
