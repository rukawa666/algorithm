package com.rukawa.algorithm.base.class17_stack;

import java.util.Stack;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @Author: Administrator
 * @Date: 2021/5/24 0024 7:37
 */
public class Code04_MaximalRectangle {

    /**
     * 给定一个二维数组matrix，其中的值不是0就是1
     * 返回全部由1组成的最大自子矩阵，内部有多少个1,返回面积
     */
    public int maximalRectangle(char[][] matrix) {
        /**
         * 另外一个题：
         *   1、给你一个直方图，问你最大的长方形有几个方块？
         *     举例：
         *     arr=[3, 5, 2, 4, 1],最大长方形有8个块
         *   解题思路：单调栈解决(求每个数离自己左边与自己最近的比它小的数在哪，离自己右边与自己最近的数在哪)
         *           左边离自己最近比自己小的位置 | 右边离自己最近比自己小的位置
         *      0、3              -1                   2               可以扩到[0, 1]，len*arr[0]=2*3=6
         *      1、5               0                   2               可以扩到[1, 1]，len*arr[1]=1*5=5
         *      2、2              -1                   4               可以扩到[0, 3]，len*arr[1]=4*2=8
         *      3、4               2                   4               可以扩到[3, 3]，len*arr[1]=1*4=4
         *      4、1              -1                   5               可以扩到[0, 4]，len*arr[1]=5*1=5
         *
         * 原问题思路：
         *    1、先求长方形必须以第0行做底的情况下，最大的含有1的长方形是几个1？用上面单调栈思路求解
         *    2、必须以第一行做底的情况下，第0行和第1行相加，形成一个直方图，用单调栈思路求解
         *    3、后面都是以i行做底的情况下，前面的行合并形成一个直方图，用单调栈思路求解
         *
         *  时间复杂度: O(N * M)
         */
        if (matrix == null || matrix.length == 0 || matrix[0].length == 0) {
            return 0;
        }
        int maxArea = 0;
        int[] height = new int[matrix[0].length];
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                height[j] = matrix[i][j] == '0' ? 0 : height[j] + 1;
            }
            maxArea = Math.max(maxArea, maxRecFromBottom(height));
        }
        return maxArea;
    }

    public static int maxRecFromBottom(int[] height) {
        if (height == null || height.length == 0) {
            return 0;
        }
        int maxArea = 0;
        Stack<Integer> stack = new Stack<>();
        for (int i = 0; i < height.length; i++) {
            while (!stack.isEmpty() && height[stack.peek()] > height[i]) {
                int index = stack.pop();
                int j = stack.isEmpty() ? -1 : stack.peek();
                int curArea = (i - j - 1) * height[index];
                maxArea = Math.max(curArea, maxArea);
            }
            stack.push(i);
        }

        while (!stack.isEmpty()) {
            int index = stack.pop();
            int j = stack.isEmpty() ? -1 : stack.peek();
            int curArea = (height.length - j - 1) * height[index];
            maxArea = Math.max(curArea, maxArea);
        }
        return maxArea;

    }
}
