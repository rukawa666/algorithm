package com.rukawa.algorithm.base.class13;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @Author: Administrator
 * @Date: 2021/4/13 0013 22:10
 */
public class Code03_CoinsWaysNoLimit {
    /**
     * arr是面值数组，其中的值都是正数且没有重复，再给定一个正数aim
     * 每个值都认为是一种面值，且认为张数是无限的
     * 返回组成aim的方法数
     * 例如：arr={1,2},aim=4
     * 方法如下：1+1+1+1,1+1+2,2+2
     * 一共就3种方法，所以返回3
     */
    public static int coinsWay(int[] arr, int aim) {
        if (arr == null || arr.length == 0 || aim < 0) {
            return 0;
        }
        return process(arr, 0, aim);
    }

    // arr[index...]所有的面值，每一个面值都可以选择任意张数，组成正好rest的钱，方法数多少
    public static int process(int[] arr, int index, int rest) {
        if (index == arr.length) {
            return rest == 0 ? 1 : 0;
        }
        int ways = 0;
        // 每一张面值去尝试，从0张开始
        for (int zhang = 0; zhang * arr[index] <= rest; zhang++) {
            ways += process(arr, index + 1, rest - (zhang * arr[index]));
        }
        return ways;
    }

    public static int dp1(int[] arr, int aim) {
        if (arr == null || arr.length == 0 || aim < 0) {
            return 0;
        }
        int n = arr.length;
        int[][] dp = new int[n + 1][aim + 1];
        dp[n][0] = 1;
        // dp[n][...] = 0
        for (int i = n - 1; i >= 0; i--) {
            for (int j = 0; j <= aim; j++) {
                int ways = 0;
                for (int zhang = 0; zhang * arr[i] <= j; zhang++) {
                    ways += dp[i + 1][j - (zhang * arr[i])];
                }
                dp[i][j] = ways;
            }
        }
        return dp[0][aim];
    }

    public static int dp2(int[] arr, int aim) {
        if (arr == null || arr.length == 0 || aim < 0) {
            return 0;
        }
        int n = arr.length;
        int[][] dp = new int[n + 1][aim + 1];
        dp[n][0] = 1;
        // dp[n][...] = 0
        for (int i = n - 1; i >= 0; i--) {
            for (int j = 0; j <= aim; j++) {

                /**
                 * 分析情况：当前面值是arr[i]=3
                 * 如果i行14列的位置，得到的答案需要依赖以下位置
                 * [i+1,14] [i+1,11] [i+1,8] [i+1,5] [i+1,2]
                 *
                 * 如果i行11列的位置，得到的答案需要依赖以下位置
                 * [i+1,11] [i+1,8] [i+1,5] [i+1,2]
                 *
                 * 所以i行14列的位置依赖位置如下
                 * [i+1,14] [i,11]
                 */

                dp[i][j] = dp[i + 1][j];
                if (j - arr[i] >= 0) {
                    dp[i][j] += dp[i][j - arr[i]];
                }
            }
        }
        return dp[0][aim];
    }

    // 为了测试
    public static int[] randomArray(int maxLen, int maxValue) {
        int N = (int) (Math.random() * maxLen);
        int[] arr = new int[N];
        boolean[] has = new boolean[maxValue + 1];
        for (int i = 0; i < N; i++) {
            do {
                arr[i] = (int) (Math.random() * maxValue) + 1;
            } while (has[arr[i]]);
            has[arr[i]] = true;
        }
        return arr;
    }

    // 为了测试
    public static void printArray(int[] arr) {
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i] + " ");
        }
        System.out.println();
    }

    // 为了测试
    public static void main(String[] args) {
        int maxLen = 10;
        int maxValue = 30;
        int testTime = 1000000;
        System.out.println("测试开始");
        for (int i = 0; i < testTime; i++) {
            int[] arr = randomArray(maxLen, maxValue);
            int aim = (int) (Math.random() * maxValue);
            int ans1 = coinsWay(arr, aim);
            int ans2 = dp1(arr, aim);
            int ans3 = dp2(arr, aim);
            if (ans1 != ans2 || ans1 != ans3) {
                System.out.println("Oops!");
                printArray(arr);
                System.out.println(aim);
                System.out.println(ans1);
                System.out.println(ans2);
                System.out.println(ans3);
                break;
            }
        }
        System.out.println("测试结束");
    }
}
