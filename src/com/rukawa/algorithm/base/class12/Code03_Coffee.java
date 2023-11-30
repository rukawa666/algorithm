package com.rukawa.algorithm.base.class12;


import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @Author: Administrator
 * @Date: 2021/4/10 0010 21:33
 */
public class Code03_Coffee {
    /**
     * 给定一个数组arr，arr[i]代表第i号咖啡机泡一杯咖啡的时间
     * 给定一个正数N，表示N个人等着咖啡机泡咖啡，每台咖啡机只能轮流泡咖啡
     * 只有一台咖啡机，一次只能洗一个杯子，时间耗费a，洗完才能洗下一个杯子
     * 每个咖啡杯也可以自己挥发干净，时间耗费b，咖啡杯可以并行挥发
     * 假设所有人拿到咖啡后立刻喝干净，
     * 返回从开始等到所有咖啡机变干净的最短时间
     * 三个参数：int[] arr、int N、int a、int b
     */

//    // 验证的方法
//    // 彻底的暴力
//    // 很慢但是绝对正确
//    public static int right(int[] arr, int n, int a, int b) {
//        return 0;
//    }
//
//    // 优良一点的暴力尝试的方法
//    public static int minTime1(int[] arr, int n, int a, int b) {
//        return 0;
//    }

    // 方法2，每个人暴力尝试用每一个咖啡机给自己做咖啡，优化成贪心
    public static int minTime2(int[] arr, int n, int a, int b) {
        PriorityQueue<Machine> minHeap = new PriorityQueue<>(new MachineComparator());
        for (int num : arr) {
            minHeap.add(new Machine(0, num));
        }
        // n个人的喝完咖啡的时间
        int[] drinks = new int[n];
        for (int i = 0; i < n; i++) {
            Machine cur = minHeap.poll();
            cur.timePoint += cur.workTime;
            drinks[i] = cur.timePoint;
            minHeap.add(cur);
        }
        return process2(drinks, a, b, 0, 0);
    }

    // drinks 每一个人喝完咖啡的时间  也是咖啡杯需要洗的时间
    // a 洗一个杯子的时间
    // b 杯子自己挥发干净的时间
    // drinks[0...index-1]都已经洗干净了，不用再考虑
    // drinks[index...] 想要变干净，需要的最少时间返回
    // washLine 表示洗的机器什么时候可用
    public static int process2(int[] drinks, int a, int b, int index, int washLine) {
        if (index == drinks.length) {
            return 0;
        }

        // index杯子决定洗
        // 喝完的时间和机器能用的时间取最大
        // 比如，3号时间点可以洗，但是机器在7号时间才能洗
        int selfWash = Math.max(drinks[index], washLine) + a;
        // 剩余杯子洗完的时间
        int restWash = process2(drinks, a, b, index + 1, selfWash);
        // 第一种情况：自己洗完的时间和剩余洗完的时间取最大
        int p1 = Math.max(selfWash, restWash);

        // index杯子决定挥发
        int selfEvaporate = drinks[index] + b;
        int restEvaporate = process2(drinks, a, b, index + 1, washLine);
        // 第二种情况：自己挥发完的时间和剩余挥发完的时间取最大
        int p2 = Math.max(selfEvaporate, restEvaporate);
        return Math.min(p1, p2);
    }

    public static class Machine {
        // 咖啡机什么时间点能提供服务
        public int timePoint;
        // 泡一杯咖啡需要多久
        public int workTime;

        public Machine(int tp, int wt) {
            this.timePoint = tp;
            this.workTime = wt;
        }
    }

    public static class MachineComparator implements Comparator<Machine> {

        @Override
        public int compare(Machine o1, Machine o2) {
            return (o1.timePoint + o1.workTime) - (o2.timePoint + o2.workTime);
        }
    }

    // dp优化
    public static int minTime3(int[] arr, int n, int a, int b) {
        PriorityQueue<Machine> minHeap = new PriorityQueue<>(new MachineComparator());
        for (int num : arr) {
            minHeap.add(new Machine(0, num));
        }
        // n个人的喝完咖啡的时间
        int[] drinks = new int[n];
        for (int i = 0; i < n; i++) {
            Machine cur = minHeap.poll();
            cur.timePoint += cur.workTime;
            drinks[i] = cur.timePoint;
            minHeap.add(cur);
        }
        return dp(drinks, a, b);
    }

    // 方法2有两个可变参数，index washLine
    // washLine的范围难估计
    // 属于业务限制模型
    // 人为的想限制把free的大小估算出来
    // washLine的最大范围：所有杯子都洗，得到的最大值
    public static int dp(int[] drinks, int wash, int air) {
        /**
         * wash = 3
         * 洗杯子的时间
         * 1 50 51 70 72
         * 洗干净时间
         * 4 53 56 73 76
         */
        int maxWash = 0;
        for (int i = 0; i < drinks.length; i++) {
            maxWash = Math.max(drinks[i], maxWash) + wash;
        }
        int n = drinks.length;
        int[][] dp = new int[n + 1][maxWash + 1];
        // 依赖index+1的位置
        // dp[n][...] = 0
        for (int index = n - 1; index >= 0; index--) {
            for (int free = 0; free <= maxWash; free++) {

                // 所有咖啡杯100的时间点能结束，不需要讨论第5杯咖啡在100时才空闲的情况  真实情况不会存在
                if (Math.max(drinks[index], free) + wash > maxWash) {
                    continue;
                }

                // index杯子决定洗
                // 喝完的时间和机器能用的时间取最大
                // 比如，3号时间点可以洗，但是机器在7号时间才能洗
                int selfWash = Math.max(drinks[index], free) + wash;
                // 剩余杯子洗完的时间
                int restWash = dp[index + 1][selfWash];
                // 第一种情况：自己洗完的时间和剩余洗完的时间取最大
                int p1 = Math.max(selfWash, restWash);

                // index杯子决定挥发
                int selfEvaporate = drinks[index] + air;
                int restEvaporate = dp[index + 1][free];
                // 第二种情况：自己挥发完的时间和剩余挥发完的时间取最大
                int p2 = Math.max(selfEvaporate, restEvaporate);
                dp[index][free] =  Math.min(p1, p2);
            }
        }
        return dp[0][0];
    }


    // for test
    public static int[] randomArray(int len, int max) {
        int[] arr = new int[len];
        for (int i = 0; i < len; i++) {
            arr[i] = (int) (Math.random() * max) + 1;
        }
        return arr;
    }

    // for test
    public static void printArray(int[] arr) {
        System.out.print("arr : ");
        for (int j = 0; j < arr.length; j++) {
            System.out.print(arr[j] + ", ");
        }
        System.out.println();
    }

    public static void main(String[] args) {
        int len = 10;
        int max = 10;
        int testTime = 10;
        System.out.println("测试开始");
        for (int i = 0; i < testTime; i++) {
            int[] arr = randomArray(len, max);
            int n = (int) (Math.random() * 7) + 1;
            int a = (int) (Math.random() * 7) + 1;
            int b = (int) (Math.random() * 10) + 1;
//            int ans1 = right(arr, n, a, b);
//            int ans2 = minTime1(arr, n, a, b);
            int ans3 = minTime2(arr, n, a, b);
            int ans4 = minTime3(arr, n, a, b);
            if (ans3 != ans4) {
                printArray(arr);
                System.out.println("n : " + n);
                System.out.println("a : " + a);
                System.out.println("b : " + b);
                System.out.println(ans3 + " , " + ans4);
                System.out.println("===============");
                break;
            }
        }
        System.out.println("测试结束");
    }
}
