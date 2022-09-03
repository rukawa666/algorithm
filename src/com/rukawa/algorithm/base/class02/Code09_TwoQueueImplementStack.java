package com.rukawa.algorithm.base.class02;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

/**
 * Created with Intellij IDEA
 *
 * @Author：SuperHai
 * @Date：2021-01-10 22:35
 * @Version：1.0
 */
public class Code09_TwoQueueImplementStack {

    /**
     * 如果使用队列结构实现栈
     *
     * 问题：实现图的深度优先遍历，但是用队列实现
     */

    public static class TwoQueueStack<T> {
        public Queue<T> queue;
        public Queue<T> help;

        public TwoQueueStack() {
            queue = new LinkedList<>();
            help = new LinkedList<>();
        }

        public void push(T value) {
            queue.offer(value);
        }

        public T poll() {
            // 把queue的最后一个留下，其余全部进入help
            while (queue.size() > 1) {
                help.offer(queue.poll());
            }
            T popValue = queue.poll();
            Queue<T> tmp = queue;
            queue = help;
            help = tmp;
            return popValue;
        }

        public T peek() {
            while (queue.size() > 1) {
                help.offer(queue.poll());
            }
            T peekValue = queue.poll();
            help.offer(peekValue);
            Queue<T> tmp = queue;
            queue = help;
            help = tmp;
            return peekValue;
        }

        public boolean isEmpty() {
            return queue.isEmpty();
        }
    }

    public static void main(String[] args) {
        System.out.println("test begin");
        TwoQueueStack<Integer> myStack = new TwoQueueStack<>();
        Stack<Integer> test = new Stack<>();
        int testTime = 1000000;
        int max = 1000000;
        for (int i = 0; i < testTime; i++) {
            if (myStack.isEmpty()) {
                if (!test.isEmpty()) {
                    System.out.println("Oops");
                }
                int num = (int) (Math.random() * max);
                myStack.push(num);
                test.push(num);
            } else {
                if (Math.random() < 0.25) {
                    int num = (int) (Math.random() * max);
                    myStack.push(num);
                    test.push(num);
                } else if (Math.random() < 0.5) {
                    if (!myStack.peek().equals(test.peek())) {
                        System.out.println("Oops");
                    }
                } else if (Math.random() < 0.75) {
                    if (!myStack.poll().equals(test.pop())) {
                        System.out.println("Oops");
                    }
                } else {
                    if (myStack.isEmpty() != test.isEmpty()) {
                        System.out.println("Oops");
                    }
                }
            }
        }

        System.out.println("test finish!");

    }
}
