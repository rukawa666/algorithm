package com.rukawa.algorithm.trainingcamp.trainingcamp1.class1;

import java.util.LinkedList;

/**
 * Created with Intellij IDEA
 *
 * @Author：SuperHai
 * @Date：2020-07-30 23:53
 * @Version：1.0
 */
public class Code02_AllLessNumSubArray {

    /**
     * 给定一个整形数组arr，和一个整数num
     * 某个arr中子数组sub，如果想要达标，必须满足：
     * sub中最大值-sub中最小值<=num,
     * 返回arr中达标子数组的数量
     */

    public static int getNum(int[] arr, int num) {
        if (arr == null || arr.length == 0) {
            return 0;
        }

        LinkedList<Integer> qMax = new LinkedList<>();
        LinkedList<Integer> qMin = new LinkedList<>();

        int L = 0;
        int R = 0;
        // [L...R) -> [0,0) 窗口内无数,所以L==R，则窗口内无数
        // [0,1) -> [0,0](真实范围), L < R,窗口内有数
        int res = 0;
        while (L < arr.length) {    // L是开头位置，尝试每一个开头

            // 如果此时窗口的开头是L，下面while -> R向右扩到违规为止
            while(R < arr.length) { // R是最后一个达标位置的下一个
                // 最小值的更新
                while (!qMin.isEmpty() && arr[qMin.peekLast()] >= arr[R]) {
                    qMin.pollLast();
                }
                qMin.addLast(R);
                // 最大值的更新
                while (!qMax.isEmpty() && arr[qMax.peekLast()] <= arr[R]) {
                    qMax.pollLast();
                }
                qMax.addLast(R);

                // 最大值的结果中取出最大值-最小值的结果中取出最小值 > num ,说明不达标
                if (arr[qMax.getFirst()] - arr[qMin.getFirst()] > num) {
                    break;
                }
                R++;
            }
            // R是最后一个达标位置的再下一个，第一个违规的位置
            res += R - L;

            // L的位置过期
            if (qMin.peekFirst() == L){
                qMin.pollFirst();
            }
            if (qMax.peekFirst() == L) {
                qMax.pollFirst();
            }

            L++;
        }

        return res;
    }
}
