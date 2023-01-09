package com.rukawa.algorithm.base.class17_stack;

import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @Author: Administrator
 * @Date: 2021/5/18 0018 7:48
 */
public class Code01_MonotonousStack {
    /**
     * 一个数组，需要生成每一个位置左边离它最近比它小，每一个位置右边离它最近比它小的位置在哪
     */

    // 不支持重复值的单调栈
    public static int[][] getNearLessNoRepeat(int[] arr) {
        if (arr == null || arr.length < 0) {
            return null;
        }
        int[][] res = new int[arr.length][2];
        Stack<Integer> stack = new Stack<>();
        for (int i = 0; i < arr.length; i++) {
            while (!stack.isEmpty() && arr[stack.peek()] > arr[i]) {
                int popIndex = stack.pop();
                res[popIndex][0] = !stack.isEmpty() ? stack.peek() : -1;
                res[popIndex][1] = i;
            }
            stack.push(i);
        }

        while (!stack.isEmpty()) {
            int popIndex = stack.pop();
            res[popIndex][0] = !stack.isEmpty() ? stack.peek() : -1;
            res[popIndex][1] = -1;
        }
        return res;
    }

    // 支持重复值的单调栈
    public static int[][] getNearLess(int[] arr) {
        if (arr == null || arr.length < 0) {
            return null;
        }
        int[][] res = new int[arr.length][2];
        Stack<LinkedList<Integer>> stack = new Stack<>();
        for (int i = 0; i < arr.length; i++) {
            while (!stack.isEmpty() && arr[stack.peek().peekLast()] > arr[i]) {
                LinkedList<Integer> popList = stack.pop();
                for (int index : popList) {
                    res[index][0] = !stack.isEmpty() ? stack.peek().peekLast() : -1;
                    res[index][1] = i;
                }
            }
            if (!stack.isEmpty() && stack.peek().peekLast() == arr[i]) {
                stack.peek().addLast(i);
            } else {
                LinkedList<Integer> list = new LinkedList<>();
                list.addLast(i);
                stack.push(list);
            }
        }

        while (!stack.isEmpty()) {
            LinkedList<Integer> popList = stack.pop();
            for (int index : popList) {
                res[index][0] = !stack.isEmpty() ? stack.peek().peekLast() : -1;
                res[index][1] = -1;
            }
        }
        return res;
    }
}
