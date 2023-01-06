package com.rukawa.algorithm.leetcode.top100likedquestions;

import java.util.Stack;

/**
 * Created with Intellij IDEA
 *
 * @Author：SuperHai
 * @Date：2020-09-21 8:41
 * @Version：1.0
 */
public class Problem_0084_LargestRectangleInHistogram {
    /**
     * 柱状图中最大的矩形
     * 给定 n 个非负整数，用来表示柱状图中各个柱子的高度。每个柱子彼此相邻，且宽度为 1 。
     * 求在该柱状图中，能够勾勒出来的矩形的最大面积。
     *
     * 提示：
     * 1 <= heights.length <=105
     * 0 <= heights[i] <= 104
     */
    public int largestRectangleArea(int[] heights) {
        /**
         * 2,1,5,6,2,3
         * 0 1 2 3 4 5
         * 栈中先加2
         * 1到来，1比2小，结算2，2最远能到-1，1  此时1+1-1  结算2的值2*1，栈中加入1
         * 5到来，比1大，直接压入栈
         * 6到来，比5大，直接压入栈
         * 2到来，此时2小于栈顶元素，弹出6结算，最远到2，4， 此时4-2-1 结算6的值1*6
         *       此时2小于栈顶元素，弹出5，最远到1，4， 此时4-1-1 结算5的值2*5 栈中压入2
         * 3到来，直接入栈
         *
         * 结算栈中的位置
         *
         */
        if (heights == null || heights.length == 0) {
            return 0;
        }
        Stack<Integer> stack = new Stack<>();
        int max = 0;
        for (int r = 0; r < heights.length; r++) {
            while (!stack.isEmpty() && heights[stack.peek()] > heights[r]) {
                int i = stack.pop();
                int l = stack.isEmpty() ? -1 : stack.peek();
                max = Math.max(max, (r - l - 1) * heights[i]);
            }
            stack.push(heights[r]);
        }
        while (!stack.isEmpty()) {
            int i = stack.pop();
            int l = stack.isEmpty() ? -1 : stack.peek();
            max = Math.max(max, (heights.length - l - 1) * heights[i]);
        }
        return max;
    }
}
