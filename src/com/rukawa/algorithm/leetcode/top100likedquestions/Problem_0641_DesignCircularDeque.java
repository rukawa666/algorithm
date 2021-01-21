package com.rukawa.algorithm.leetcode.top100likedquestions;

/**
 * Created with Intellij IDEA
 *
 * @Author：SuperHai
 * @Date：2021-01-10 21:20
 * @Version：1.0
 */
public class Problem_0641_DesignCircularDeque {
    /**
     * 设计循环双端队列
     *
     * 你的实现需要支持以下操作：
     * MyCircularDeque(k)：构造函数,双端队列的大小为k。
     * insertFront()：将一个元素添加到双端队列头部。 如果操作成功返回 true。
     * insertLast()：将一个元素添加到双端队列尾部。如果操作成功返回 true。
     * deleteFront()：从双端队列头部删除一个元素。 如果操作成功返回 true。
     * deleteLast()：从双端队列尾部删除一个元素。如果操作成功返回 true。
     * getFront()：从双端队列头部获得一个元素。如果双端队列为空，返回 -1。
     * getRear()：获得双端队列的最后一个元素。 如果双端队列为空，返回 -1。
     * isEmpty()：检查双端队列是否为空。
     * isFull()：检查双端队列是否满了。
     *
     * 提示：
     * 所有值的范围为 [1, 1000]
     * 操作次数的范围为 [1, 1000]
     * 请不要使用内置的双端队列库。
     */

    class MyCircularDeque {

        private MyQueue queue;
        /** Initialize your data structure here. Set the size of the deque to be k. */
        public MyCircularDeque(int k) {
            queue = new MyQueue(k);
        }

        /** Adds an item at the front of Deque. Return true if the operation is successful. */
        public boolean insertFront(int value) {
            return false;
        }

        /** Adds an item at the rear of Deque. Return true if the operation is successful. */
        public boolean insertLast(int value) {
            return false;
        }

        /** Deletes an item from the front of Deque. Return true if the operation is successful. */
        public boolean deleteFront() {
            return false;
        }

        /** Deletes an item from the rear of Deque. Return true if the operation is successful. */
        public boolean deleteLast() {
            return false;
        }

        /** Get the front item from the deque. */
        public int getFront() {
            return 0;
        }

        /** Get the last item from the deque. */
        public int getRear() {
            return 0;
        }

        /** Checks whether the circular deque is empty or not. */
        public boolean isEmpty() {
            return false;
        }

        /** Checks whether the circular deque is full or not. */
        public boolean isFull() {
            return false;
        }
    }

    public static class MyQueue {
        private int[] arr;
        private int pushI;
        private int pullI;
        private int size;
        private final int limit;

        public MyQueue(int l) {
            arr = new int[l];
            pushI = 0;
            pullI = 0;
            size = 0;
            limit = l;
        }

        public void push(int data) {
            if (size == limit) {
                throw new RuntimeException("队列满了，停止添加");
            }
            size++;
            arr[pushI] = data;
            pushI = nextIndex(pushI);
        }

        public int pop() {
            if (size == 0) {
                throw new RuntimeException("队列空了，停止拿取");
            }
            size--;
            int data = arr[pullI];
            pullI = nextIndex(pullI);
            return data;
        }
        public boolean isEmpty() {
            return size == 0;
        }

        private int nextIndex(int i) {
            return i < limit - 1 ? i + 1 : 0;
        }
    }
}
