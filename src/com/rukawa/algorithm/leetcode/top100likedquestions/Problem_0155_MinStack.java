package com.rukawa.algorithm.leetcode.top100likedquestions;

import java.util.Stack;

/**
 * Created with Intellij IDEA
 *
 * @Author：SuperHai
 * @Date：2021-01-10 21:37
 * @Version：1.0
 */
public class Problem_0155_MinStack {
    /**
     * 设计一个支持 push ，pop ，top 操作，并能在常数时间内检索到最小元素的栈。
     *
     * push(x) —— 将元素 x 推入栈中。
     * pop() —— 删除栈顶的元素。
     * top() —— 获取栈顶元素。
     * getMin() —— 检索栈中的最小元素。
     *  
     * 提示：
     * pop、top 和 getMin 操作总是在 非空栈 上调用。
     */

    class MinStack {

        private Stack<Integer> stackData;
        private Stack<Integer> stackMin;

        /** initialize your data structure here. */
        public MinStack() {
            stackData = new Stack<>();
            stackMin = new Stack<>();
        }

        public void push(int x) {
            if (this.stackMin.isEmpty() || x <= stackMin.peek()) {
                stackMin.push(x);
            }

            this.stackData.push(x);
        }

        public void pop() {
            if (this.stackData.isEmpty()) {
                throw new RuntimeException("Your Stack is Empty!");
            }
            int data = this.stackData.pop();
            if (data == getMin()) {
                this.stackMin.pop();
            }
        }

        public int top() {
            return stackData.peek();
        }

        public int getMin() {
            return stackMin.peek();
        }
    }

    class MinStack02 {

        private Stack<Integer> stackData;
        private Stack<Integer> stackMin;

        /** initialize your data structure here. */
        public MinStack02() {
            stackData = new Stack<>();
            stackMin = new Stack<>();
        }

        public void push(int x) {
            if (this.stackMin.isEmpty()) {
                stackMin.push(x);
            } else if (x < stackMin.peek()) {
                stackMin.push(x);
            } else {
                int minData = this.stackMin.peek();
                stackMin.push(minData);
            }

            this.stackData.push(x);
        }

        public void pop() {
            if (this.stackData.isEmpty()) {
                throw new RuntimeException("Your Stack is Empty!");
            }
            this.stackData.pop();
            this.stackMin.pop();
        }

        public int top() {
            return stackData.peek();
        }

        public int getMin() {
            return stackMin.peek();
        }
    }
}
