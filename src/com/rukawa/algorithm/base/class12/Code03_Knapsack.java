package com.rukawa.algorithm.base.class12;

/**
 * Created with Intellij IDEA
 *
 * @Author：SuperHai
 * @Date：2020-07-29 7:48
 * @Version：1.0
 */
public class Code03_Knapsack {

    /**
     * 给定两个长度为N的数组weights和values，
     * weights[i]和values[i]分别代表i号物品的重量和价值
     * 给定一个正数bag，表示一个载重bag的袋子
     * 你装的物品不能超过这个重量。
     * 返回你能装下最多的价值是多少？
     * @param w
     * @param v
     * @param bag
     * @return
     */
    public static int getMaxValue(int[] w, int[] v, int bag) {
        return process(w, v, 0, 0, bag);
    }

    // index... 最大价值
    public static int process(int[] w, int[] v, int index, int alreadyW, int bag) {
        if (alreadyW > bag) {
            return -1;
        }
        // 重量没超
        if (index == w.length) {
            return 0;
        }
        int p1 = process(w, v, index + 1, alreadyW, bag);
        int p2next = process(w, v, index + 1, alreadyW + w[index], bag);
        int p2 = -1;
        if (p2next != -1) {
            p2 = v[index] + p2next;
        }
        return Math.max(p1, p2);

    }

    public static int maxValue(int[] w, int[] v, int bag) {
        return process(w, v, 0, bag);
    }

    // 只剩下rest的空间了，
    // index...货物自由选择，但是不要超过rest的空间
    // 返回能够获得的最大价值
    public static int process(int[] w, int[] v, int index, int rest) {
        if (rest < 0) { // base case 1
            return -1;
        }
        // rest >=0
        if (index == w.length) { // base case 2
            return 0;
        }
        // 有货也有空间
        int p1 = process(w, v, index + 1, rest);
        int p2 = -1;
        int p2Next = process(w, v, index + 1, rest - w[index]);
        if(p2Next!=-1) {
            p2 = v[index] + p2Next;
        }
        return Math.max(p1, p2);
    }

    public static int dpWay(int[] w, int[] v, int bag) {
        int N = w.length;
        int[][] dp = new int[N + 1][bag + 1];
        // dp[N][...] = 0   N行要为0
        for (int index = N - 1; index >= 0; index--) {  // N-1行开始填写
            for (int rest = 0; rest <= bag; rest++) { // rest < 0
                int p1 = dp[index + 1][rest];
                int p2 = -1;
                if(rest - w[index] >= 0) {
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
