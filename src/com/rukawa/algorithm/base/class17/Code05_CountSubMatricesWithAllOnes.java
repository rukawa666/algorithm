package com.rukawa.algorithm.base.class17;

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
                // 二维数组的上下两行做累加和
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
                // 等于的时候不结算，直接跳过，大于才结算
                if (height[cur] > height[i]) {
                    // 左边扩的最远的位置
                    int left = stack.isEmpty() ? -1 : stack.peek();
                    // 左右两边扩的最远的距离
                    int n = i - left - 1;
                    // 选择左右两边扩的最远的位置的最大值
                    int down = Math.max(left == -1 ? 0 : height[left], height[i]);
                    // 对扩出的最大值的超出部分结算
                    nums += (height[cur] - down) * num(n);
                }
            }
            stack.push(i);
        }

        while (!stack.isEmpty()) {
            int cur = stack.pop();
            // 左边扩的最远的位置
            int left = stack.isEmpty() ? -1 : stack.peek();
            // 左右两边扩的最远的距离
            int n = height.length - left - 1;
            // 选择左右两边扩的最远的位置的最大值
            int down = left == -1 ? 0 : height[left];
            // 对扩出的最大值的超出部分结算
            nums += (height[cur] - down) * num(n);
        }
        return nums;
    }

    // 优化栈结构
    public static int countFromBottom2(int[] height) {
        if (height == null || height.length == 0) {
            return 0;
        }
        int nums = 0;
        int[] stack = new int[height.length];
        int si = -1;
        for (int i = 0; i < height.length; i++) {
            while (si != -1 && height[stack[si]] >= height[i]) {
                int cur = stack[si--];
                // 等于的时候不结算，直接跳过，大于才结算
                if (height[cur] > height[i]) {
                    // 左边扩的最远的位置
                    int left = si == -1 ? -1 : stack[si];
                    // 左右两边扩的最远的距离
                    int n = i - left - 1;
                    // 选择左右两边扩的最远的位置的最大值
                    int down = Math.max(left == -1 ? 0 : height[left], height[i]);
                    // 对扩出的最大值的超出部分结算
                    nums += (height[cur] - down) * num(n);
                }
            }
            stack[++si] = i;
        }

        while (si != -1) {
            int cur = stack[si--];
            // 左边扩的最远的位置
            int left = si == -1 ? -1 : stack[si];
            // 左右两边扩的最远的距离
            int n = height.length - left - 1;
            // 选择左右两边扩的最远的位置的最大值
            int down = left == -1 ? 0 : height[left];
            // 对扩出的最大值的超出部分结算
            nums += (height[cur] - down) * num(n);
        }
        return nums;
    }

    public static int num(int n) {
        /**
         * 假设7位置值是6，左边找比它小且离它最近的位置是3值是2，右边找比它小且离它最近的位置是13值是1
         * 在整个大区域，有多少个矩阵，从3开始到3结束，3开始到4结束。。。3开始到13位置结束，4开始到4结束，。。。
         * 3有10个，4有9个，5有8个。。。12有1个
         */
        return (n * (n + 1)) >> 1;
    }
}
