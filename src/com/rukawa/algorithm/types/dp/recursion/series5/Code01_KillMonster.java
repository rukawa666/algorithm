package com.rukawa.algorithm.types.dp.recursion.series5;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @Author: Administrator
 * @Date: 2021/4/13 0013 22:15
 */
public class Code01_KillMonster {
    /**
     * 给定3个参数，N、M、K
     * 怪兽有N滴血，等着英雄来砍自己
     * 英雄每一次打击，都会让怪兽流失[0-M]的血量
     * 到底流多少？每一次在[0,M]上等概率获得一个值
     * 求K次打击之后，英雄把怪兽砍死的概率
     */

    public static double right(int N, int M, int K) {
        /**
         * 思路：第一回砍是一个M+1的可能，第二回砍也是M+1的可能，最终是(M+1)^K的总可能性
         * 怪兽砍死之后，打击次数不够，继续砍
         */
        if (N < 1 || M < 1 || K < 1) {
            return 0;
        }
        long all = (long) Math.pow(M + 1, K);
        long kill = process(K, M, N);
        return (double) kill / (double) all;
    }

    /**
     * @param times 还剩下K次可以砍
     * @param M 每次的伤害在[0,M]范围上
     * @param hp 怪兽还剩余N点血
     * @return 返回砍死的情况数
     */
    public static long process(int times, int M, int hp) {
        /**
         * 如果还剩余三刀(times=3)，此时怪兽血量已经小于等于0(hp<=0)
         * 能获得生存点，(M+1)^3
         */
        if (hp <= 0) {
            return (long) Math.pow(M + 1, times);
        }
        // 击打次数为0
        if (times == 0) {
            // 怪兽剩余血量<=0 ，则砍死一次怪兽
            return hp <= 0 ? 1 : 0;
        }
        long ways = 0;
        for (int i = 0; i <= M; i++) {
            ways += process(times - 1, M, hp - i);
        }
        return ways;
    }

    public static double dp1(int N, int M, int K) {
        if (N < 1 || M < 1 || K < 1) {
            return 0;
        }
        long all = (long) Math.pow(M + 1, K);

        long[][] dp = new long[K + 1][N + 1];
        dp[0][0] = 1; // dp[0][...] = 0
        for (int times = 1; times <= K; times++) {
            // 先求出第0列的值
            // 第0列值依赖以下公式
            dp[times][0] = (long) Math.pow(M + 1, times);
            // 从第1列开始执行
            for (int hp = 1; hp <= N; hp++) {
                long ways = 0;
                for (int i = 0; i <= M; i++) {
                    // hp - i 可能越界
                    // 如果不越界就是真实格子的值
                    if (hp - i > 0) {
                        ways += dp[times - 1][hp - i];
                    } else {    // 越界，<0, 拿不到dp的值，还剩余(times - 1)次
                        ways += (long) Math.pow(M + 1, times - 1);
                    }
                }
                dp[times][hp] = ways;
            }
        }
        long kill = dp[K][N];
        return ((double) kill / (double) all);
    }

    // 样本对应模型
    // 优化：省去枚举行为
    public static double dp2(int N, int M, int K) {
        if (N < 1 || M < 1 || K < 1) {
            return 0;
        }
        long all = (long) Math.pow(M + 1, K);

        long[][] dp = new long[K + 1][N + 1];
        dp[0][0] = 1; // dp[0][...] = 0
        for (int times = 1; times <= K; times++) {
            // 先求出第0列的值
            // 第0列值依赖以下公式
            dp[times][0] = (long) Math.pow(M + 1, times);
            // 从第1列开始执行
            for (int hp = 1; hp <= N; hp++) {
                dp[times][hp] = dp[times - 1][hp] + dp[times][hp - 1];
                if (hp - 1 - M >= 0) {
                    dp[times][hp] -= dp[times - 1][hp - 1 - M];
                } else {
                    dp[times][hp] -= Math.pow(M + 1, times - 1);
                }
            }
        }
        long kill = dp[K][N];
        return ((double) kill / (double) all);
    }

    public static void main(String[] args) {
        int NMax = 10;
        int MMax = 10;
        int KMax = 10;
        int testTime = 200;
        System.out.println("测试开始");
        for (int i = 0; i < testTime; i++) {
            int N = (int) (Math.random() * NMax);
            int M = (int) (Math.random() * MMax);
            int K = (int) (Math.random() * KMax);
            double ans1 = right(N, M, K);
            double ans2 = dp1(N, M, K);
            double ans3 = dp2(N, M, K);
            if (ans1 != ans2 && ans2 != ans3) {
                System.out.println("Oops!");
                break;
            }
        }
        System.out.println("测试结束");
    }
}
