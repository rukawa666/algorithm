package com.rukawa.algorithm.base.class04;

import java.util.PriorityQueue;

/**
 * Created with Intellij IDEA
 *
 * @Author：SuperHai
 * @Date：2020-07-15 0:26
 * @Version：1.0
 */
public class Code05_SortArrayDistanceLessK {

    /**
     * 已知一个几乎有序的数组。几乎是指，如果把数据排好顺序的话，每个元素移动的距离一定不超过k，
     * 并且k相对于数组长度来说是比较小的
     *
     * 请选择一个合适的排序策略，对这个数组进行排序
     * @param arr
     * @param k
     */
    public void sortedArrDistanceLessK(int[] arr, int k) {
        // 默认小根堆
        PriorityQueue<Integer> heap = new PriorityQueue<>();
        int index = 0;
        for (; index <= Math.min(arr.length - 1, k); index++) {
            heap.add(arr[index]);
        }

        int i = 0;
        for (; index < arr.length; i++, index++) {
            arr[i] = heap.poll();
            heap.add(arr[index]);
        }

        while (!heap.isEmpty()) {
            arr[i++] = heap.poll();
        }
    }

    public static void main(String[] args) {

    }
}
