package com.rukawa.algorithm.base.class02;

import java.util.Stack;

/**
 * Created with Intellij IDEA
 *
 * @Author：SuperHai
 * @Date：2020-07-12 9:24
 * @Version：1.0
 */
public class Code05_GetMinStack {

    public static class MyStack01 {
        private Stack<Integer> stackData;
        private Stack<Integer> stackMin;

        public MyStack01() {
            this.stackData = new Stack<>();
            this.stackMin = new Stack<>();
        }

        public void push(int newNum) {
            if (this.stackMin.isEmpty()) {
                this.stackMin.push(newNum);
            } else if (newNum <= this.getMin()) {
                this.stackMin.push(newNum);
            }
            this.stackData.push(newNum);
        }

        public int pop() {
            if (this.stackData.isEmpty()) {
                throw new RuntimeException("Your Stack is Empty!");
            }
            int value = this.stackData.pop();
            if (value == this.getMin()) {
                this.stackMin.pop();
            }
            return value;
        }

        public Integer getMin() {
            if (this.stackMin.isEmpty()) {
                throw new RuntimeException("Your Stack is Empty!");
            }
            return stackMin.peek();
        }
    }

    public static class MyStack02 {
        private Stack<Integer> stackData;
        private Stack<Integer> stackMin;

        public MyStack02() {
            this.stackData = new Stack<>();
            this.stackMin = new Stack<>();
        }

        public void push(Integer newNum) {
            if (this.stackMin.isEmpty()) {
                this.stackMin.push(newNum);
            } else if (newNum < this.getMin()) {
                this.stackMin.push(newNum);
            } else {
                int newMin = this.stackMin.peek();
                this.stackMin.push(newMin);
            }
            this.stackData.push(newNum);
        }

        public int pop() {
            if (this.stackData.isEmpty()) {
                throw new RuntimeException("Your Stack is Empty!");
            }
            this.stackMin.pop();
            return this.stackData.pop();
        }

        public Integer getMin() {
            if (this.stackMin.isEmpty()) {
                throw new RuntimeException("Your Stack is Empty!");
            }
            return stackMin.peek();
        }
    }
}
