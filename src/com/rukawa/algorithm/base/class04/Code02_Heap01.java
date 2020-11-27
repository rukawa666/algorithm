package com.rukawa.algorithm.base.class04;

/**
 * Created with Intellij IDEA
 *
 * @Author：SuperHai
 * @Date：2020-07-14 20:17
 * @Version：1.0
 */
public class Code02_Heap01 {

    public static class MyMaxHeap {

        private int[] heap;
        private int heapSize;
        private final int limit;

        public MyMaxHeap(int limit) {
            heap = new int[limit];
            this.limit = limit;
            heapSize = 0;
        }

        public boolean isEmpty() {
            return heapSize == 0;
        }

        public boolean isFull() {
            return heapSize == limit;
        }

        public void push(int value) {
            if (heapSize == limit) {
                throw new RuntimeException("Heap is full");
            }
            heap[heapSize] = value;
            heapInsert(heap, heapSize++);
        }

        // 用户此时让你返回最大值，在堆中把这个最大值删除
        public int pop() {
            int ans = heap[0];
            swap(heap, 0, --heapSize);
            heapify(heap, 0, heapSize);
            return ans;
        }

        public static void heapInsert(int[] heap, int index) {
            while (heap[index] > heap[(index - 1) / 2]) {
                swap(heap, index, (index - 1) / 2);
                index = (index - 1) / 2;
            }
        }

        // 从index位置往下看，不断的下沉
        // 停：我的孩子都不比我大，或者我已经没孩子了
        public static void heapify(int[] heap, int index, int heapSize) {
            int left = index * 2 + 1;
            while (left < heapSize) {
                // 左右两个孩子中，谁大，谁把自己的下标给largest
                // 右 -> 1)、有右孩子 && 2)、右孩子的值比左孩子大
                // 否则为左孩子
                int largest = left + 1 < heapSize && heap[left + 1] > heap[left] ? left + 1 : left;
                largest = heap[largest] > heap[index] ? largest : index;

                if (largest == index) {
                    break;
                }
                swap(heap, largest, index);
                index = largest;
                left = index * 2 + 1;
            }
        }

        public static void swap(int[] arr, int i, int j) {
            int tmp = arr[i];
            arr[i] = arr[j];
            arr[j] = tmp;
        }
    }

    public static class RightMaxHeap {
        private int[] arr;
        private final int limit;
        private int size;

        public RightMaxHeap(int limit) {
            arr = new int[limit];
            this.limit = limit;
            size = 0;
        }

        public boolean isEmpty() {
            return size == 0;
        }

        public boolean isFull() {
            return size == limit;
        }

        public void push(int value) {
            if (size == limit) {
                throw new RuntimeException("Heap is full");
            }
            arr[size++] = value;
        }

        public int pop() {
            int maxIndex = 0;
            for (int i = 1; i < size; i++) {
                if (arr[i] > arr[maxIndex]) {
                    maxIndex = i;
                }
            }

            int ans = arr[maxIndex];
            arr[maxIndex] = arr[--size];
            return ans;
        }
    }

    public static void main(String[] args) {
        int value = 1000;
        int limit = 100;
        int testTimes = 1000000;
        for (int i = 0; i < testTimes; i++) {
            int cutLimit = (int) (Math.random() * limit) + 1;
            MyMaxHeap my = new MyMaxHeap(cutLimit);
            RightMaxHeap test = new RightMaxHeap(cutLimit);
            int curOpTimes = (int) (Math.random() * limit);
            for (int j = 0; j < curOpTimes; j++) {
                if (my.isEmpty() != test.isEmpty()) {
                    System.out.println("Oops!");
                }

                if (my.isFull() != test.isFull()) {
                    System.out.println("Oops!");
                }

                if (my.isEmpty()) {
                    int curValue = (int) (Math.random() * value);
                    my.push(curValue);
                    test.push(curValue);
                } else if (my.isFull()) {
                    if (my.pop() != test.pop()) {
                        System.out.println("Oops!");
                    }
                } else {
                    if (Math.random() < 0.5) {
                        int curValue = (int) (Math.random() * value);
                        my.push(curValue);
                        test.push(curValue);
                    } else {
                        if (my.pop() != test.pop()) {
                            System.out.println("Oops!");
                        }
                    }
                }
            }
        }
        System.out.println("Finish!");
    }
}
