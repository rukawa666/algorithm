package com.rukawa.algorithm.trainingcamp.trainingcamp1.class1;

import java.util.LinkedList;

/**
 * Created with Intellij IDEA
 *
 * @Author：SuperHai
 * @Date：2020-07-30 23:41
 * @Version：1.0
 */
public class Code01_SlidingWindowMaxArray {

    /**
     * 题目一
     * 假设一个固定大小为W的窗口，依次划过arr
     * 返回每一次划出状况的最大值
     * 例如：arr={4,3,5,4,3,3,6,7}，W=3
     * 返回：[5,5,5,4,6,7]
     * @param arr
     * @param w
     * @return
     */
    public static int[] getMaxWindow(int[] arr, int w) {
        if (arr == null || arr.length == 0 || w < 1 || arr.length <= w) {
            return new int[0];
        }

        LinkedList<Integer> qMax = new LinkedList<>();
        int[] res = new int[arr.length - w + 1];
        int index = 0;
        for (int i = 0; i < arr.length; i++) {
            // 添加元素和末尾删除元素
            while (!qMax.isEmpty() && arr[qMax.peekLast()] <= arr[i]) {
                qMax.pollLast();
            }
            qMax.addLast(i);

            // 过期元素删除
            if (qMax.peekFirst() == i - w) {
                qMax.pollFirst();
            }

            if (i >= w - 1) {
                res[index++] = arr[qMax.peekFirst()];
            }
        }
        return res;
    }
}
