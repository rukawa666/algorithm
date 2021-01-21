package com.rukawa.algorithm.leetcode.top100likedquestions;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Created with Intellij IDEA
 *
 * @Author：SuperHai
 * @Date：2021-01-10 20:57
 * @Version：1.0
 */
public class Problem_0225_ImplementStackUsingQueues {

    class MyStack {

        public Queue<Integer> queue;
        public Queue<Integer> help;

        /** Initialize your data structure here. */
        public MyStack() {
            queue = new LinkedList<>();
            help = new LinkedList<>();
        }

        /** Push element x onto stack. */
        public void push(int x) {
            queue.offer(x);
        }

        /** Removes the element on top of the stack and returns that element. */
        public int pop() {
            while (queue.size() > 1) {
                help.offer(queue.poll());
            }
            Integer value = queue.poll();
            Queue<Integer> tmp = queue;
            queue = help;
            help = tmp;
            return value;
        }

        /** Get the top element. */
        public int top() {
            while (queue.size() > 1) {
                help.offer(queue.poll());
            }
            Integer value = queue.poll();
            help.offer(value);
            Queue<Integer> tmp = queue;
            queue = help;
            help = tmp;
            return value;
        }

        /** Returns whether the stack is empty. */
        public boolean empty() {
            return queue.isEmpty();
        }
    }

}
