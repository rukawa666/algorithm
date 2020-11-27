package com.rukawa.algorithm.base.class02;

/**
 * Created with Intellij IDEA
 *
 * @Author：SuperHai
 * @Date：2020-07-12 1:19
 * @Version：1.0
 */
public class Code04_RingArray {

    public static class MyQueue {
        private int[] arr;
        private int push_i;
        private int poll_i;
        private int size;
        private final int limit;

        public MyQueue(int l) {
            arr = new int[l];
            push_i = 0;
            poll_i = 0;
            size = 0;
            limit = l;
        }

        public void push(int data) {
            if (size == limit) {
                throw new RuntimeException("栈满了，不能再添加了！");
            }
            size++;
            arr[push_i] = data;
            push_i = nextIndex(push_i);
        }

        public int pop() {
            if (size == 0) {
                throw new RuntimeException("栈空了，不能再拿了！");
            }
            size--;
            int ans = arr[poll_i];
            poll_i = nextIndex(poll_i);
            return ans;
        }

        public boolean isEmpty() {
            return size == 0;
        }

        // 如果现在的下标是i，返回下一个位置
        private int nextIndex(int i) {
            return i < limit - 1 ? i + 1 : 0;
        }
    }
}
