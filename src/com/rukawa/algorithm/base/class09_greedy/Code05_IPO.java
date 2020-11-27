package com.rukawa.algorithm.base.class09_greedy;

import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * Created with Intellij IDEA
 *
 * @Author：SuperHai
 * @Date：2020-07-24 0:29
 * @Version：1.0
 */
public class Code05_IPO {
    /**
     * 输入：正整数数组costs、正整数数组profits、正数K、正数M
     * costs[i]表示i号项目的花费
     * profits[i]表示i号项目在扣除花费之后还能挣到的钱(利润)
     * K表示你只能串行的最多做K个项目
     * M表示你初始的资金
     * 说明：每做完一个项目，马上获得利益，可以支持你去做下一个项目。不能并行的做项目、
     * 输出：你最后获得的最大钱数
     */

    public static int findMaximizedCapital(int K, int M, int[] costs, int[] profits) {
        PriorityQueue<Program> minCostQ = new PriorityQueue<>(new MinCostComparator());

        PriorityQueue<Program> maxProfitQ = new PriorityQueue<>(new MaxProfitComparator());

        for (int i = 0; i < profits.length; i++) {
            minCostQ.add(new Program(profits[i], costs[i]));
        }

        for (int i = 0; i < K; i++) {
            while (!minCostQ.isEmpty() && minCostQ.peek().c <= M) {
                maxProfitQ.add(minCostQ.poll());
            }

            if (maxProfitQ.isEmpty()) {
                return M;
            }
            M += maxProfitQ.poll().p;
        }
        return M;
    }

    public static class Program {
        public int p;
        public int c;

        public Program(int p, int c) {
            this.p = p;
            this.c = c;
        }
    }

    public static class MinCostComparator implements Comparator<Program> {

        @Override
        public int compare(Program o1, Program o2) {
            return o1.c - o2.c;
        }
    }

    public static class MaxProfitComparator implements Comparator<Program> {

        @Override
        public int compare(Program o1, Program o2) {
            return o2.p - o1.p;
        }
    }
}
