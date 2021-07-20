package com.rukawa.algorithm.types.dp.quadrilateral;

import java.util.Arrays;

/**
 * @className: Code05_PostOfficeProblem
 * @description: TODO 类描述
 * @author: 鎏川疯
 * @date: 2021/7/20 0020 17:18
 **/
public class Code05_PostOfficeProblem {

    /**
     * 一条直线上有居民点，邮局只能建在居民点上。给定一个有序正数数组arr，
     * 每个值表示居民点的一维坐标，再给定一个正数num，表示邮局数量。
     * 选择num个居民点建立num个邮局，使所有的居民点到最近邮局的总距离最短，返回最短的总距离
     * 【举例】
     * arr=[1,2,3,4,5,1000]，num=2。
     * 第一个邮局建立在 3 位置，第二个邮局建立在 1000 位置。那么
     * 1位置到邮局的距离为2，
     * 2位置到邮局距离为1，
     * 3位置到邮局的距离为0，
     * 4位置到邮局的距离为1，
     * 5位置到邮局的距离为2，
     * 1000位置到邮局的距离为0。
     * 这种方案下的总距离为 6， 其他任何方案的总距离都不会 比该方案的总距离更短，所以返回6
     */

    // 暴力解
    public static int min1(int[] arr, int num) {
        /**
         * 思路：
         *  在arr[L...R]位置上，建立一个邮局，最小代价是多少？
         *  如果个数有奇数个，则建在中位数一定是最优的。
         *  如果个数是偶数个，建在上中位数和下中位数都是最优的。
         */
        if (arr == null || num < 1 || arr.length < num) {
            return 0;
        }
        int N = arr.length;
        /**
         * 为什么是(N + 1) * (N + 1)？
         * 整个数组现在是0~7范围，有一个划分0~7都由之前的邮局负责，最后一个邮局负责无，也可以认为最后一个邮局负责8~7这个无效位置
         * 假设arr的长度是10，下标是0~9，总共有4个邮局，之前0~9是3个邮局负责，10~9由第4个邮局负责，如果w只准备了10的长度，需要边界讨论
         */
        // 在arr[L...R]位置上，建立一个邮局，最小代价
        int[][] w = new int[N + 1][N + 1];
        for (int L = 0; L < N; L++) {
            // L==R，只有一个居民点，距离为0，直接跳过
            for (int R = L + 1; R < N; R++) {
                // 之前的答案+当前居民点到最佳邮局的位置
                w[L][R] = w[L][R - 1] + (arr[R] - arr[(L + R) >> 1]);
            }
        }
        int[][] dp = new int[N][num + 1];
        // 第0列代表，0个邮局没意思，舍弃
        // 第0行，在1个居民点建立多少个邮局，最小距离都是0
//        for (int j = 1; j <= num; j++) {
//            dp[0][j] = 0;
//        }
        // 第1列，代表在0~i建一个邮局，直接从w中获取
        for (int i = 1; i < N; i++) {
            dp[i][1] = w[0][i];
        }

        // 普遍位置
        for (int i = 1; i < N; i++) {
            // 邮局个数超过居民点，多出的邮局放置在哪都无效
            for (int j = 2; j <= Math.min(i, num); j++) {
                dp[i][j] = Integer.MAX_VALUE;
                // 枚举0~k放j-1个邮局，k+1~i放置最后一个邮局
                for (int k = 0; k <= i; k++) {
                    dp[i][j] = Math.min(dp[i][j], dp[k][j - 1] + w[k + 1][i]);
                }
            }
        }
        return dp[N - 1][num];
    }

    // 四边形不等式的优化
    public static int min2(int[] arr, int num) {
        if (arr == null || num < 1 || arr.length < num) {
            return 0;
        }
        int N = arr.length;
        /**
         * 为什么是(N + 1) * (N + 1)？
         * 整个数组现在是0~7范围，有一个划分0~7都由之前的邮局负责，最后一个邮局负责无，也可以认为最后一个邮局负责8~7这个无效位置
         * 假设arr的长度是10，下标是0~9，总共有4个邮局，之前0~9是3个邮局负责，10~9由第4个邮局负责，如果w只准备了10的长度，需要边界讨论
         */
        // 在arr[L...R]位置上，建立一个邮局，最小代价
        int[][] w = new int[N + 1][N + 1];
        for (int L = 0; L < N; L++) {
            // L==R，只有一个居民点，距离为0，直接跳过
            for (int R = L + 1; R < N; R++) {
                // 之前的答案+当前居民点到最佳邮局的位置
                w[L][R] = w[L][R - 1] + (arr[R] - arr[(L + R) >> 1]);
            }
        }
        int[][] dp = new int[N][num + 1];
        int[][] best = new int[N][num + 1];
        // 第0列代表，0个邮局没意思，舍弃
        // 第0行，在1个居民点建立多少个邮局，最小距离都是0
//        for (int j = 1; j <= num; j++) {
//            dp[0][j] = 0;
//        }
        // 第1列，代表在0~i建一个邮局，直接从w中获取
        for (int i = 1; i < N; i++) {
            dp[i][1] = w[0][i];
            best[i][1] = -1;
        }

        for (int j = 2; j <= num; j++) {
            for (int i = N - 1; i >= j; i--) {
                // 左边给予的下限
                int down = best[i][j - 1];
                // 下边给予的上限
                int up = i == N - 1 ? N - 1 : best[i + 1][j];
                int ans = Integer.MAX_VALUE;
                int choose = -1;
                for (int leftEnd = down; leftEnd <= up; leftEnd++) {
                    int leftCost = leftEnd == -1 ? 0 : dp[leftEnd][j - 1];
                    int rightCost = leftEnd == i ? 0 : w[leftEnd + 1][i];
                    int cur = leftCost + rightCost;
                    if (cur <= ans) {
                        ans = cur;
                        choose = leftEnd;
                    }
                }
                dp[i][j] = ans;
                best[i][j] = choose;
            }
        }
        return dp[N - 1][num];
    }

    // for test
    public static int[] randomSortedArray(int len, int range) {
        int[] arr = new int[len];
        for (int i = 0; i != len; i++) {
            arr[i] = (int) (Math.random() * range);
        }
        Arrays.sort(arr);
        return arr;
    }

    // for test
    public static void printArray(int[] arr) {
        for (int i = 0; i != arr.length; i++) {
            System.out.print(arr[i] + " ");
        }
        System.out.println();
    }

    // for test
    public static void main(String[] args) {
//        int N = 30;
//        int maxValue = 100;
//        int testTime = 10000;
//        System.out.println("测试开始");
//        for (int i = 0; i < testTime; i++) {
//            int len = (int) (Math.random() * N) + 1;
//            int[] arr = randomSortedArray(len, maxValue);
//            int num = (int) (Math.random() * N) + 1;
//            int ans1 = min1(arr, num);
//            int ans2 = min2(arr, num);
//            if (ans1 != ans2) {
//                printArray(arr);
//                System.out.println(num);
//                System.out.println(ans1);
//                System.out.println(ans2);
//                System.out.println("Oops!");
//            }
//        }
//        System.out.println("测试结束");
        int[] arr = {7, 4, 6, 1};
        int k = 1;
        System.out.println(min2(arr, k));
    }


}
