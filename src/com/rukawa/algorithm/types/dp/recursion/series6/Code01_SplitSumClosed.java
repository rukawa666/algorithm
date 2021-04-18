package com.rukawa.algorithm.types.dp.recursion.series6;

/**
 * Created with Intellij IDEA
 *
 * @Author：SuperHai
 * @Date：2021-01-19 20:05
 * @Version：1.0
 */
public class Code01_SplitSumClosed {

    /**
     * 给定一个正数数组arr，
     * 请把arr中所有的数分成两个集合，尽量让两个集合的累加和接近
     * 返回：
     *  最接近的情况下，较小集合的累加和
     */

    public static int right(int[] arr) {
        if (arr == null || arr.length < 2) {
            return 0;
        }
        int sum = 0;
        for (int num : arr) {
            sum += num;
        }
        // 如果是-1>>1,结果是-1，注意此点
//        return process(arr, 0, sum >> 1);
        return process(arr, 0, sum / 2);

    }

    // arr[i]可以自由选择，请返回累加和尽量接近rest，但是不能超过rest的情况下，最接近的累加和是多少
    public static int process(int[] arr, int i, int rest) {
        if (i == arr.length) {
            return 0;
        } else { // 还有数
            // 不使用arr[i]
            int p1 = process(arr, i + 1, rest);
            int p2 = 0;
            if (arr[i] <= rest) {
                p2 = arr[i] + process(arr, i + 1, rest - arr[i]);
            }
            return Math.max(p1, p2);
        }
    }

    public static int dp(int[] arr) {
        if (arr == null || arr.length < 2) {
            return 0;
        }
        int N = arr.length;
        int sum = 0;
        for (int num : arr) {
            sum += num;
        }
        sum >>= 1;
        int[][] dp = new int[N + 1][sum + 1];
//        dp[N][...] = 0
        for (int index = N - 1; index >= 0; index--) {
            for (int rest = 0; rest <= sum; rest++) {
                dp[index][rest] = dp[index + 1][rest];
                if (arr[index] <= rest) {
                    dp[index][rest] = Math.max(dp[index][rest], arr[index] + dp[index + 1][rest - arr[index]]);
                }
            }
        }
        return dp[0][sum];
    }

    public static int[] randomArray(int len, int value) {
        int[] arr = new int[len];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (int) (Math.random() * value);
        }
        return arr;
    }

    public static void printArray(int[] arr) {
        for (int num : arr) {
            System.out.print(num + " ");
        }
        System.out.println();
    }

    public static void main(String[] args) {
        int maxLen = 20;
        int maxValue = 50;
        int testTime = 10000;
        System.out.println("测试开始");
        for (int i = 0; i < testTime; i++) {
            int len = (int) (Math.random() * maxLen);
            int[] arr = randomArray(len, maxValue);
            int ans1 = right(arr);
            int ans2 = dp(arr);
            if (ans1 != ans2) {
                printArray(arr);
                System.out.println(ans1);
                System.out.println(ans2);
                System.out.println("Oops!");
                break;
            }
        }
        System.out.println("测试结束");
    }
}
