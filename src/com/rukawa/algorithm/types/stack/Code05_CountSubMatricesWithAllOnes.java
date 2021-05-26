package com.rukawa.algorithm.types.stack;

import java.util.Stack;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @Author: Administrator
 * @Date: 2021/5/24 0024 7:37
 */
public class Code05_CountSubMatricesWithAllOnes {

    /**
     * 给定一个二维数组matrix，其中的值不是0就是1
     * 返回全部由1组成的子矩阵的数量
     */
    public static int numSubMat(int[][] mat) {
        if (mat == null || mat.length == 0 || mat[0].length == 0) {
            return 0;
        }
        int nums = 0;
        int[] height = new int[mat[0].length];
        for (int i = 0; i < mat.length; i++) {
            for (int j = 0; j < mat[0].length; j++) {
                height[j] = mat[i][j] == 0 ? 0 : height[j] + 1;
            }
            nums += countFromBottom(height);
        }
        return nums;
    }

    public static int countFromBottom(int[] height) {
        if (height == null || height.length == 0) {
            return 0;
        }
        int nums = 0;
        Stack<Integer> stack = new Stack<>();
        for (int i = 0; i < height.length; i++) {
            while (!stack.isEmpty() && height[stack.peek()] >= height[i]) {
                int cur = stack.pop();
                if (height[cur] > height[i]) {
                    int left = stack.isEmpty() ? -1 : stack.peek();
                    int n = i - left - 1;
                    int down = Math.max(left == -1 ? 0 :height[left], height[i]);
                    nums += (height[cur] - down) * num(n);
                }
            }
            stack.push(i);
        }
        while (!stack.isEmpty()) {
            int cur = stack.pop();
            int left = stack.isEmpty() ? -1 : stack.peek();
            int n = height.length - left - 1;
            int down = left == -1 ? 0 : height[left];
            nums += (height[cur] - down) * num(n);
        }
        return nums;
    }

    public static int num(int n) {
        return ((n * (n + 1)) >> 1);
    }
}
