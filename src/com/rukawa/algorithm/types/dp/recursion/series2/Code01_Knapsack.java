package com.rukawa.algorithm.types.dp.recursion.series2;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @Author: Administrator
 * @Date: 2021/4/1 0001 7:40
 */
public class Code01_Knapsack {


    /**
     * 所有的货，重量和价值，都在w和v数组里面
     * 为了方便，其中没有负数
     * bag背包容量，不能超过这个载重
     * 返回：不超重的情况下，能得到的最大值
     * @param w
     * @param v
     * @param bag
     * @return
     */
    public static int maxValue(int[] w, int[] v, int bag) {
        if (w == null || v == null || w.length != v.length || w.length == 0) {
            return 0;
        }
        return process(w, v, 0, bag);

    }

    // 当前考虑到index号货物，index...所有的货物可以自由选择
    // 做的选择不能超过背包容量
    // 返回最大价值
    public static int process(int[] w, int[] v, int index, int rest) {
        if (rest < 0) {
            return -1;
        }

        if (index == w.length) {
            return 0;
        }
        int p1 = process(w, v, index + 1, rest);

        // p2 可能返回无效解，所以+v[index]答案不对
        int p2 = process(w, v, index + 1, rest - w[index]);
        if (p2 != -1) {
            p2 += v[index];
        }
        return Math.max(p1, p2);
    }

    // 傻缓存：解决重复调用
    public static int maxValueByDP(int[] w, int[] v, int bag) {
        if (w == null || v == null || w.length != v.length || w.length == 0) {
            return 0;
        }
        int N = w.length;
        int[][] dp = new int[N + 1][bag + 1];
        for (int index = N - 1; index >= 0; index--) {
            for (int rest = 0; rest <= bag; rest++) {
                int p1 = dp[index + 1][rest];
                int p2 = rest - w[index] < 0 ? -1 : dp[index + 1][rest - w[index]];
                if (p2 != -1) {
                    p2 += v[index];
                }
                dp[index][rest] = Math.max(p1, p2);
            }
        }
        return dp[0][bag];
    }

    public static void main(String[] args) {
        int[] weights = {3, 2, 4, 7};
        int[] values = {5, 6, 3, 19};
        int bag = 11;
        System.out.println(maxValue(weights, values, bag));
        System.out.println(maxValueByDP(weights, values, bag));
    }
}
