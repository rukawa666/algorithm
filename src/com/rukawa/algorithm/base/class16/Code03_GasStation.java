package com.rukawa.algorithm.base.class16;

import java.util.LinkedList;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @Author: Administrator
 * @Date: 2021/4/27 0027 9:19
 */
public class Code03_GasStation {
    /**
     * 加油站的良好出发点
     * N个加油站组成一个环形，给定两个长度都是N的非负数组gas和cost(N>1),gas[i]代表
     * 第i个加油站存的油可以跑多少千米，cost[i]表示第i个加油站到环中下一个加油站相隔
     * 多少千米。假设你有一辆邮箱足够大的车，初始时车里没有油。如果车从第i个加油站出发，
     * 最终可以回到这个加油站，那么第i个加油站就算良好出发点，否则就不算。请返回长度为
     * N的boolean类型数组res，res[i]代表第i个加油站是不是良好出发点。
     */

    // 这个方法的时间复杂度O(N)，额外空间复杂度O(N)

    public static int canCompleteCircuit(int[] gas, int[] cost) {
        boolean[] good = goodArray(gas, cost);
        for (int i = 0; i < gas.length; i++) {
            if (good[i]) {
                return i;
            }
        }
        return -1;
    }

    /**
     * 返回所有的良好出发点
     * @param gas
     * @param cost
     * @return
     */
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
        LinkedList<Integer> window = new LinkedList<>();
        for (int i = 0; i < N; i++) {
            while (!window.isEmpty() && arr[window.peekLast()] >= arr[i]) {
                window.pollLast();
            }
            window.addLast(i);
        }
        boolean[] res = new boolean[N];
        for (int offset = 0, i = 0, j = N; j < M; offset = arr[i++], j++) {
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

    public static void main(String[] args) {
        int[] gas = {2, 3, 4};
        int[] cost = {3, 4, 3};
        System.out.println(canCompleteCircuit(gas, cost));
    }
}
