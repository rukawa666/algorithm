package com.rukawa.algorithm.leetcode.top100likedquestions;



/**
 * create by hqh on 2022/2/23
 */
public class Problem_0622_DesignCircularQueue {

    static class MyCircularQueue {

        private int[] arr;
        private int index;
        private int size;
        private int limit;

        public MyCircularQueue(int k) {
            arr = new int[k];
            index = 0;
            size = 0;
            limit = k;
        }

        // 向循环队列插入一个元素。如果成功插入则返回真。
        public boolean enQueue(int value) {
            if (size == limit) {
                return false;
            }
            arr[(index + size) % limit] = value;
            size++;
            return true;
        }

        // 从循环队列中删除一个元素。如果成功删除则返回真。
        public boolean deQueue() {
            if (size == 0) {
                return false;
            }
            index = (index + 1) % limit;
            size--;
            return true;
        }

        // 从队首获取元素。如果队列为空，返回 -1
        public int Front() {
            if (size == 0) {
                return -1;
            }
            return arr[index];
        }

        // 获取队尾元素。如果队列为空，返回 -1 。
        public int Rear() {
            if (size == 0) {
                return -1;
            }
            int tailIndex = (index + size - 1) % limit;
            return arr[tailIndex];
        }

        public boolean isEmpty() {
            return size == 0;
        }

        public boolean isFull() {
            return size == limit;
        }

        public int nextIndex(int index) {
            return index < limit - 1 ? index + 1 : 0;
        }
    }
}
