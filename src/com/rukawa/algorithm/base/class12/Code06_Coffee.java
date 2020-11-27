package com.rukawa.algorithm.base.class12;

/**
 * Created with Intellij IDEA
 *
 * @Author：SuperHai
 * @Date：2020-07-30 7:36
 * @Version：1.0
 */
public class Code06_Coffee {
    /**
     * 题目：
     * 数组arr代表每一个咖啡机冲一杯咖啡的时间，每个咖啡机只能串行的制造咖啡。
     * 现在有n个人需要喝咖啡，只能用咖啡机来制造咖啡。
     * 认为每个人喝咖啡的时间非常短，冲好的时间既是喝完的时间。
     * 每个人喝完之后咖啡杯可以选择洗或者自然挥发干净，只有一台洗咖啡杯的机器，只能串行的洗咖啡杯
     * 洗杯子的机器洗完一个被子的时间为a，任何一个杯子自然挥发干净的时间为b。
     * 四个参数：arr，n，a，b
     * 假设时间点从0开始，返回所有人喝完咖啡并洗完咖啡杯的全部过程后，至少来到什么时间点
     */

    public static int minTime2(int[] arr, int n, int a, int b) {
        return 0;
    }

    /**
     * 方法二：洗咖啡杯的方式和原来的一样，只是这个暴力版本减少了一个可变参数
     *
     * @param drinks   每一个员工喝完的时间 固定变量
     * @param a        洗一杯的时间，固定变量
     * @param b        杯子自己挥发干净的时间，固定变量
     * @param index    drinks[0...index-1]都已经干净了，不用考虑，drinks[i...]都想要变干净，这是我操心的
     * @param washLine 表示洗的机器何时可以用
     * @return drinks[i...]变干净，最少的时间返回
     */
    public static int process(int[] drinks, int a, int b, int index, int washLine) {
        // 剩余一杯咖啡
        if (index == drinks.length - 1) {
            return Math.min(
                    Math.max(washLine,
                            drinks[index]) + a,    // 洗干净的时间和喝完一杯咖啡并且洗干净的最大值
                    drinks[index] + b); // 挥发干净的时间
        }

        // 剩余不止一杯咖啡
        int wash = Math.max(washLine, drinks[index]) + a;  // 洗一杯咖啡，结束的时间点
        // 让index+1...变干净的最早时间
        int next1 = process(drinks, a, b, index + 1, wash); //
        // index...表示自己洗完，后面的都变干净，最早变干净的时间
        int p1 = Math.max(wash, next1);

        int dry = drinks[index] + b;    // 挥发 index咖啡杯，结束的时间
        // 后面的杯子可以洗杯子，机器washLine的时间可以洗杯子
        int next2 = process(drinks, a, b, index + 1, washLine);
        int p2 = Math.max(dry, next2);

        return Math.min(p1, p2);
    }

    public static int dp(int[] drinks, int a, int b) {
        // 挥发时间比洗的时间短
        if (a >= b) {
            return drinks[drinks.length - 1] + b;
        }

        int limit = 0;  // 咖啡机什么时候可用
        for (int i = 0; i < drinks.length; i++) {
            limit = Math.max(limit, drinks[i]) + a;
        }
        int N = drinks.length;
        int[][] dp = new int[N][limit + 1];
        for (int washLine = 0; washLine <= limit; washLine++) {
            dp[N - 1][washLine] = Math.min(
                    Math.max(washLine,
                            drinks[N - 1]) + a,
                    drinks[N - 1] + b);
        }

        for (int index = N - 2; index >= 0; index--) {
            for (int washLine = 0; washLine <= limit; washLine++) {
                int p1 = Integer.MAX_VALUE;
                int wash = Math.max(washLine, drinks[index]) + a;
                if (wash <= limit) {
                    p1 = Math.max(wash, dp[index + 1][wash]);
                }
                int p2 = Math.max(drinks[index] + b, dp[index + 1][washLine]);

                dp[index][washLine] = Math.min(p1, p2);
            }
        }

        return dp[0][0];
    }

    public static void main(String[] args) {
        int[] drinks = {1, 1, 5, 5, 7, 10, 12, 12, 12, 12, 12, 12, 15};
        int a = 3;
        int b = 10;

        System.out.println(process(drinks, a, b, 0, 0));
        System.out.println(dp(drinks, a, b));
    }
}
