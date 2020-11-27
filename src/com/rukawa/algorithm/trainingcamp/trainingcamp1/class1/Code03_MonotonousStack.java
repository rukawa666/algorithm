package com.rukawa.algorithm.trainingcamp.trainingcamp1.class1;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * Created with Intellij IDEA
 *
 * @Author：SuperHai
 * @Date：2020-07-31 8:40
 * @Version：1.0
 */
public class Code03_MonotonousStack {

    public static int[][] getNearLessNoRepeat(int[] arr) {
        int[][] res = new int[arr.length][2];
        Stack<Integer> stack = new Stack<>();
        for (int i = 0; i < arr.length; i++) {
            while (!stack.isEmpty() && arr[stack.peek()] > arr[i]) {
                int popIndex = stack.pop();
                int leftLessIndex = stack.isEmpty() ? -1 : stack.peek();
                res[popIndex][0] = leftLessIndex;
                res[popIndex][1] = i;
            }
            stack.push(i);
        }

        while (!stack.isEmpty()) {
            int popIndex = stack.pop();
            int leftLessIndex = stack.isEmpty() ? -1 : stack.peek();
            res[popIndex][0] = leftLessIndex;
            res[popIndex][1] = -1;
        }

        return res;
    }

    public static int[][] getNearLess(int[] arr) {
        int[][] res = new int[arr.length][2];
        // List<Integer> -> 放的是位置，相同值的位置压在一起
        Stack<List<Integer>> stack = new Stack<>();
        for (int i = 0; i < arr.length; i++) {
            // 底 -> 顶，小 -> 大
            while (!stack.isEmpty() && arr[stack.peek().get(0)] > arr[i]) {
                List<Integer> popIs = stack.pop();
                // 取决于下面位置的列表中，最晚加入的位置
                int leftLessIndex = stack.isEmpty() ?
                        -1 :
                        stack.peek().get(stack.peek().size() - 1);
                for (Integer popI : popIs) {
                    res[popI][0] = leftLessIndex;
                    res[popI][1] = i;
                }
            }
            // 相等的或者比你小的，入栈，相同元素位置压在一起
            if (!stack.isEmpty() && arr[stack.peek().get(0)] == arr[i]) {
                stack.peek().add(Integer.valueOf(i));
            } else {
                ArrayList<Integer> list = new ArrayList<>();
                list.add(i);
                stack.push(list);
            }
        }
        // 遍历完，栈内还有数
        while (!stack.isEmpty()) {
            List<Integer> popIs = stack.pop();
            int leftLessIndex = stack.peek().get(stack.peek().size() - 1);
            for (Integer popI : popIs) {
                res[popI][0] = leftLessIndex;
                res[popI][1] = -1;
            }
        }

        return res;
    }
}
