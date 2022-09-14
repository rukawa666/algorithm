package com.rukawa.algorithm.base.class11;

import java.util.Stack;

/**
 * Created with Intellij IDEA
 *
 * @Author：SuperHai
 * @Date：2020-07-26 13:41
 * @Version：1.0
 */
public class Code04_ReverseStackUsingRecursive {
    /**
     * 给你一个栈，请你逆序这个栈，不能申请额外的数据结构
     * 只能使用递归函数。如何实现？
     */

    public static void reverse(Stack<Integer> stack) {
        if (stack.isEmpty()) {
            return;
        }
        /**
         * 原始栈：1，2，3
         * r1递归：i=3，r2，此时栈：1，2
         * r2递归：i=2，r3，此时栈：1
         * r3递归：i=1，r4
         * r4递归：此时栈为空，则直接返回
         * 到达r3，把此时i压入栈
         * 到达r2，把此时i压入栈
         * 到达r1，把此时i压入栈
         */
        int i = f(stack);
        reverse(stack);
        stack.push(i);
    }

    // 移除栈底元素
    // 上面的元素盖下来
    // 返回移除掉的栈底元素

    /**
     * 栈：1，2，3
     * 弹出1(f1)：result=1  last=f2
     * 弹出2(f2)：result=2  last=f3
     * 弹出3(f3)：result=3  弹出3之后栈变成空，返回3
     *
     * 此时f2接住f3的返回：last=3，但是把result=2 入栈，返回f2的last=3
     * 此时f1接住f2的返回：last=3，但是把result=1 入栈，返回f1的last=3
     */
    public static int f(Stack<Integer> stack) {
        int result = stack.pop();
        if (stack.isEmpty()) {
            return result;
        } else {
            int last = f(stack);
            stack.push(result);
            return last;
        }
    }

    public static void main(String[] args) {
        Stack<Integer> test = new Stack<Integer>();
        test.push(1);
        test.push(2);
        test.push(3);
        test.push(4);
        test.push(5);
        reverse(test);
        while (!test.isEmpty()) {
            System.out.println(test.pop());
        }

    }
}
