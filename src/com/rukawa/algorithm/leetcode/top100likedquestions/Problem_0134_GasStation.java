package com.rukawa.algorithm.leetcode.top100likedquestions;

import javafx.concurrent.Worker;

import java.util.LinkedList;

/**
 * Created with Intellij IDEA
 *
 * @Author：SuperHai
 * @Date：2020-09-19 11:05
 * @Version：1.0
 */
public class Problem_0134_GasStation {
    /**
     * 加油站
     * 在一条环路上有 N 个加油站，其中第 i 个加油站有汽油 gas[i] 升。
     * 你有一辆油箱容量无限的的汽车，从第 i 个加油站开往第 i+1 个加油站需要消耗汽油 cost[i] 升。你从其中的一个加油站出发，开始时油箱为空。
     * 如果你可以绕环路行驶一周，则返回出发时加油站的编号，否则返回 -1。
     * 说明: 
     * 如果题目有解，该答案即为唯一答案。
     * 输入数组均为非空数组，且长度相同。
     * 输入数组中的元素均为非负数。
     *
     * 示例 1:
     * 输入:
     * gas  = [1,2,3,4,5]
     * cost = [3,4,5,1,2]
     * 输出: 3
     * 解释:
     * 从 3 号加油站(索引为 3 处)出发，可获得 4 升汽油。此时油箱有 = 0 + 4 = 4 升汽油
     * 开往 4 号加油站，此时油箱有 4 - 1 + 5 = 8 升汽油
     * 开往 0 号加油站，此时油箱有 8 - 2 + 1 = 7 升汽油
     * 开往 1 号加油站，此时油箱有 7 - 3 + 2 = 6 升汽油
     * 开往 2 号加油站，此时油箱有 6 - 4 + 3 = 5 升汽油
     * 开往 3 号加油站，你需要消耗 5 升汽油，正好足够你返回到 3 号加油站。
     * 因此，3 可为起始索引。
     *
     * 示例 2:
     * 输入:
     * gas  = [2,3,4]
     * cost = [3,4,3]
     * 输出: -1
     * 解释:
     * 你不能从 0 号或 1 号加油站出发，因为没有足够的汽油可以让你行驶到下一个加油站。
     * 我们从 2 号加油站出发，可以获得 4 升汽油。 此时油箱有 = 0 + 4 = 4 升汽油
     * 开往 0 号加油站，此时油箱有 4 - 3 + 2 = 3 升汽油
     * 开往 1 号加油站，此时油箱有 3 - 3 + 3 = 3 升汽油
     * 你无法返回 2 号加油站，因为返程需要消耗 4 升汽油，但是你的油箱只有 3 升汽油。
     * 因此，无论怎样，你都不可能绕环路行驶一周。
     */

    public int canCompleteCircuit(int[] gas, int[] cost) {
        return 0;
    }

    public static boolean[] goodArray(int[] gas, int[] cost) {
        /**
         * final由gas-cost得到
         * final: {-2, -3,  4, -2, 6, -1}
         * sp由final的后一个减前一个，到最后一个位置再去减final0位置的值
         * sp:    {-2, -5, -1, -3, 3,  2, 0, -3, 1, -1, 5, 4}
         * 通过sp得到final
         *
         * 0位置出发：  -2, -3,  4, -2, 6, -1                                  F
         * 1位置出发：  -3,  1, -1,  5, 4,  2   -> sp[1...6] - sp[0]得到的结果   F
         * 2位置出发：   4,  2,  8,  7, 5,  2   -> sp[2...7] - sp[1]得到的结果   T
         * 3位置出发：  -2,  2,  3, -2, -2, 0   -> sp[3...8] - sp[2]得到的结果   F
         * 4位置出发：   6,  5,  3,  0,  4, 2   -> sp[4...9] - sp[1]得到的结果   T
         * 5位置出发：  -1, -3, -6, -2, -4, 2   -> sp[5...10] - sp[1]得到的结果  F
         * 6位置出发：  -2, -5, -1, -3,  3, 2   -> sp[6...11] - sp[1]得到的结果  F
         */
        int N = gas.length;
        int M = N << 1;
        int[] arr = new int[M];
        // {-2, -3,  4, -2, 6, -1, -2, -3,  4, -2, 6, -1}
        for (int i = 0; i < N; i++) {
            arr[i] = gas[i] - cost[i];
            arr[i + N] = gas[i] - cost[i];
        }
        // 得到sp数组
        // {-2, -5, -1, -3, 3, 2, 0, -3,  1, -1, 5, 4}
        for (int i = 1; i < M; i++) {
            arr[i] += arr[i - 1];
        }

        // 在长度为N的窗口内获取最小值
        // 从小 -> 大的顺序
        LinkedList<Integer> window = new LinkedList<>();
        for (int i = 0; i < N; i++) {
            while (!window.isEmpty() && arr[window.peekLast()] >= arr[i]) {
                window.pollLast();
            }
            window.addLast(i);
        }
        boolean[] res = new boolean[N];
        for (int offset = 0, i = 0, j = N; j < M; offset = arr[i++], j++) {
            // 减去之前的数得到当前位置出发的情况
            if (arr[window.peekFirst()] - offset >= 0) {
                res[i] = true;
            }
            // 范围越界
            if (window.peekFirst() == i) {
                window.pollFirst();
            }

            while (!window.isEmpty() && arr[window.peekLast()] >= arr[j]) {
                window.pollLast();
            }
            window.addLast(j);
        }
        return res;
    }
}
