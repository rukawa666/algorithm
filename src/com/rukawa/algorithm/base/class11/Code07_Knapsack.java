package com.rukawa.algorithm.base.class11;

/**
 * Created with Intellij IDEA
 *
 * @Author：SuperHai
 * @Date：2020-07-27 7:41
 * @Version：1.0
 */
public class Code07_Knapsack {

    /**
     * 给定两个长度为N的数组weights和values，
     * weights[i]和values[i]分别代表i号物品的重量和价值
     * 给定一个正数bag，表示一个载重bag的袋子
     * 你装的物品不能超过这个重量。
     * 返回你能装下最多的价值是多少？
     */
    // 暴力递归
    public static int getMaxValue2(int[] w, int[] v, int bag) {
        return process2(w, v, 0, bag);
    }

    public static int process2(int[] w, int[] v, int index, int bag) {
        if (bag < 0) {
            return -1;
        }
        if (index == w.length) {
            return 0;
        }
        int p1 = process2(w, v, index, bag);
        int p2 = 0;
        // bag - w[index] < 0 是无效解，所以上面base case返回-1
        int next = process2(w, v, index + 1, bag - w[index]);
        if (next != -1) {
            p2 = v[index] + next;
        }
        return Math.max(p1, p2);
    }

    // 暴力递归
    public static int getMaxValue(int[] w, int[] v, int bag) {
        return process(w, v, 0, 0, bag);
    }

    /**
     * @param w 货品重量
     * @param v 货品价值
     * @param index index...最大价值
     * @param alreadyW  0...index-1做出货物的选择，使得你已经达到的重量是多少
     * @param bag  背包总载重
     * @return
     */
    public static int process(int[] w, int[] v, int index, int alreadyW, int bag) {
        if (alreadyW > bag) {
            return -1;  // 返回-1，没有方案，否则就是正常价值
        }
        // 重量没超，没货了
        if (index == w.length) {
            return 0;
        }
        // 没要当前index的货
        int p1 = process(w, v, index + 1, alreadyW, bag);

        // 要了当前index的货
        int p2Next = process(w, v, index + 1, alreadyW + w[index], bag);

        int p2 = -1;
        if (p2Next != -1) {
            // 当前货的价值加后续的价值
            p2 = v[index] + p2Next;
        }

        return Math.max(p1, p2);
    }

    /**
     * 优化版本
     * @param w
     * @param v
     * @param bag
     * @return
     */
    public static int maxValue(int[] w, int[] v, int bag) {
        return process(w, v, 0, bag);
    }

    /**
     *
     * @param w 货物重量
     * @param v 货物价值
     * @param index index...之后的货物自由选择，但是不要超过rest的空间
     * @param rest  只剩下rest的空间了
     * @return
     */
    public static int process(int[] w, int[] v, int index, int rest) {
        // 如果背包没空间，返回0
        if (rest <= 0) {    // base case 1
            return 0;
        }

        // 如果没货了，返回0
        if (index == w.length) { // base case 2
            return 0;
        }
        // 有货也有空间
        // 没有选择当前货物
        int p1 = process(w, v, index + 1, rest);

        int p2 = Integer.MIN_VALUE;
        // 选择当前获取，背包剩余空间不能大于当前货物的重量
        if (rest >= w[index]) {
            p2 = v[index] + process(w, v, index + 1, rest - w[index]);
        }
        return Math.max(p1, p2);
    }

    // 傻缓存
    public static int dpWay(int[] w, int[] v, int bag) {
        int N = w.length;
        int[][] dp = new int[N + 1][bag + 1];
        // 暴力递归需要依赖下面的行的值和左边列的值
        // dp[index][rest] 依赖dp[index+1][rest] dp[index+1][rest-w[index]]
        // 最后一行为0，dp[n][...] = 0
        for (int index = N - 1; index >= 0; index--) {
            for (int rest = 0; rest <= bag; rest++) {   // rest < 0，为无效解，不需要管
                // dp
                int p1 = dp[index + 1][rest];

                int p2 = -1;
                if (rest >= w[index]) {
                    p2 = v[index] + dp[index + 1][rest - w[index]];
                }

                dp[index][rest] = Math.max(p1, p2);
            }
        }

        return dp[0][bag];
    }

    public static void main(String[] args) {
        int[] weights = { 3, 2, 4, 7 };
        int[] values = { 5, 6, 3, 19 };
        int bag = 11;
        System.out.println(maxValue(weights, values, bag));
        System.out.println(dpWay(weights, values, bag));
    }


}
