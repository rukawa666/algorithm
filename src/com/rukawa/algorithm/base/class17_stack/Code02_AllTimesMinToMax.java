package com.rukawa.algorithm.base.class17_stack;

import java.util.Stack;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @Author: Administrator
 * @Date: 2021/5/18 0018 8:13
 */
public class Code02_AllTimesMinToMax {

    /**
     * 给定一个只包含正数的数组arr，arr中任何一个子数组sub，一定可以算出(sub累加和)*(sub中的最小值)是什么，
     * 那么所有子数组中，这个值最大是多少？
     */

    public static int max1(int[] arr) {
        int max = Integer.MIN_VALUE;
        for (int i = 0; i < arr.length; i++) {
            for (int j = i; j < arr.length; j++) {
                int minNum = Integer.MAX_VALUE;
                int sum = 0;
                for (int k = i; k <= j; k++) {
                    sum += arr[k];
                    minNum = Math.min(minNum, arr[k]);
                }
                max = Math.max(max, minNum * sum);
            }
        }
        return max;
    }

    /**
     * 思路：
     *   以i位置的数作为最小值，哪个子数组最大？往左右两边扩，扩到比i位置的小的前一个位置的子数组
     *      左边离i最近比它小，右边离i最近比它小的子数组
     */
    public static int max2(int[] arr) {
        int n = arr.length;
        int[] sum = new int[n];
        sum[0] = arr[0];
        for (int i = 1; i < n; i++) {
            sum[i] = arr[i] + sum[i - 1];
        }
        int max = Integer.MIN_VALUE;
        Stack<Integer> stack = new Stack<>();
        for (int i = 0; i < n; i++) {
            // 不严格纠结每个位置就正确，后面相同的数会更新
            while (!stack.isEmpty() && arr[stack.peek()] >= arr[i]) {
                // sum[i - 1] : 往右边扩，不能扩的前一个位置
                // sum[stack.peek()]：往左边扩，不能继续扩的位置
                int j = stack.pop();
                max = Math.max(max, (stack.isEmpty() ? sum[i - 1] : (sum[i - 1] - sum[stack.peek()])) * arr[j]);
            }
            stack.push(i);
        }

        while (!stack.isEmpty()) {
            int j = stack.pop();
            max = Math.max(max, (stack.isEmpty() ? sum[n - 1] : sum[n - 1] - sum[stack.peek()]) * arr[j]);
        }

        return max;
    }

    public static int[] generateRandomArray() {
        int[] arr = new int[(int) (Math.random() * 20) + 10];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (int) (Math.random() * 101);
        }
        return arr;
    }

    public static void main(String[] args) {
        int testTimes = 2000000;
        System.out.println("test begin");
        for (int i = 0; i < testTimes; i++) {
            int[] arr = generateRandomArray();
            if (max1(arr) != max2(arr)) {
                System.out.println("FUCK!");
                break;
            }
        }
        System.out.println("test finish");
    }

}
