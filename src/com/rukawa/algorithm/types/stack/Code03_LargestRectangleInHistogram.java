package com.rukawa.algorithm.types.stack;

import java.util.Stack;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @Author: Administrator
 * @Date: 2021/5/19 0019 7:59
 */
public class Code03_LargestRectangleInHistogram {
    /**
     * 给定一个非负数组arr，代表直方图
     * 返回直方图的最大长方形面积
     */

    public static int largestRectangleArea1(int[] heights) {
        if (heights == null || heights.length == 0) {
            return 0;
        }
        int maxArea = 0;
        Stack<Integer> stack = new Stack<>();
        for (int i = 0; i < heights.length; i++) {
            while (!stack.isEmpty() && heights[stack.peek()] > heights[i]) {
                int j = stack.pop();
                int k = stack.isEmpty() ? -1 : stack.peek();
                int curArea = (i - k - 1) * heights[j];
                maxArea = Math.max(maxArea, curArea);
            }
            stack.push(i);
        }

        while (!stack.isEmpty()) {
            int j = stack.pop();
            int k = stack.isEmpty() ? -1 : stack.peek();
            int curArea = (heights.length - k - 1) * heights[j];
            maxArea = Math.max(maxArea, curArea);
        }
        return maxArea;
    }

    public static int largestRectangleArea2(int[] height) {
        if (height == null || height.length == 0) {
            return 0;
        }
        int N = height.length;
        int[] stack = new int[N];
        int si = -1;
        int maxArea = 0;
        for (int i = 0; i < height.length; i++) {
            while (si != -1 && height[i] <= height[stack[si]]) {
                int j = stack[si--];
                int k = si == -1 ? -1 : stack[si];
                int curArea = (i - k - 1) * height[j];
                maxArea = Math.max(maxArea, curArea);
            }
            stack[++si] = i;
        }
        while (si != -1) {
            int j = stack[si--];
            int k = si == -1 ? -1 : stack[si];
            int curArea = (height.length - k - 1) * height[j];
            maxArea = Math.max(maxArea, curArea);
        }
        return maxArea;
    }
}
