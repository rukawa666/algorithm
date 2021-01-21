package com.rukawa.algorithm.leetcode.top100likedquestions;

import java.util.Stack;

/**
 * Created with Intellij IDEA
 *
 * @Author：SuperHai
 * @Date：2021-01-10 22:46
 * @Version：1.0
 */
public class Problem_0225_ImplementQueueUsingStacks {

    /**
     * 用栈实现队列
     * 请你仅使用两个栈实现先入先出队列。队列应当支持一般队列的支持的所有操作（push、pop、peek、empty）：
     *
     * 实现 MyQueue 类：
     * void push(int x) 将元素 x 推到队列的末尾
     * int pop() 从队列的开头移除并返回元素
     * int peek() 返回队列开头的元素
     * boolean empty() 如果队列为空，返回 true ；否则，返回 false
     *
     * 说明：
     * 你只能使用标准的栈操作 —— 也就是只有 push to top, peek/pop from top, size, 和 is empty 操作是合法的。
     * 你所使用的语言也许不支持栈。你可以使用 list 或者 deque（双端队列）来模拟一个栈，只要是标准的栈操作即可。
     *
     * 进阶：
     * 你能否实现每个操作均摊时间复杂度为O(1)的队列？换句话说，执行n个操作的总时间复杂度为O(n)，即使其中一个操作可能花费较长时间
     */

    class MyQueue {
        public Stack<Integer> stackPush;
        public Stack<Integer> stackPop;


        /** Initialize your data structure here. */
        public MyQueue() {
            stackPush = new Stack<>();
            stackPop = new Stack<>();
        }

        public void pushToPop() {
            if (stackPop.empty()) {
                while (!stackPush.empty()) {
                    stackPop.push(stackPush.pop());
                }
            }
        }

        /** Push element x to the back of queue. */
        public void push(int x) {
            stackPush.push(x);
            pushToPop();
        }

        /** Removes the element from in front of queue and returns that element. */
        public int pop() {
            if (stackPush.empty() && stackPop.empty()) {
                throw new RuntimeException("Queue is empty!");
            }
            pushToPop();
            return stackPop.pop();
        }

        /** Get the front element. */
        public int peek() {
            if (stackPush.empty() && stackPop.empty()) {
                throw new RuntimeException("Queue is empty!");
            }
            pushToPop();
            return stackPop.peek();
        }

        /** Returns whether the queue is empty. */
        public boolean empty() {
            return stackPush.empty() && stackPop.empty();
        }
    }
}
