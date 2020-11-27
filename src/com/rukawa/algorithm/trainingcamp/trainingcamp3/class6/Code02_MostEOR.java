package com.rukawa.algorithm.trainingcamp.trainingcamp3.class6;

import java.util.HashMap;

/**
 * Created with Intellij IDEA
 *
 * @Author：SuperHai
 * @Date：2020-10-01 7:48
 * @Version：1.0
 */
public class Code02_MostEOR {
    /**
     * 一个数组异或和为0最多的部分有多少个
     */
    public static int mostEOR(int[] arr) {
        if (arr == null || arr.length == 0) {
            return 0;
        }

        int sum = 0;
        // dp[i] -> arr[i]在最优划分的情况下，异或和为0最多的部分有多少个
        int[] dp = new int[arr.length];

        // key : 某个前缀异或和
        // value : 这个前缀异或和出现的最晚的结束位置(0~value)
        HashMap<Integer, Integer> map = new HashMap<>();
        map.put(0, -1); // 一个数也没有的情况，出现在-1

        for (int i = 0; i < arr.length; i++) {
            // i所在的一定是在最后一块

            sum ^= arr[i]; // sum -> [0...i]所有数的异或和

            // 假设sum上次出现的结尾是k，0~k的异或和是sum，0~i的异或和也是sum，k+1~i是异或和等于0的区域
            // 前缀异或和出现的最晚的结束为止，k+1是离i最近的
            if (map.containsKey(sum)) { // 上一次，这个异或和出现的位置
                // pre -> pre + 1 -> i，最优划分，这个异或和出现的位置
                // (pre + 1, i) 最后一个部分
                int pre = map.get(sum); // sum 0...pre (pre+1 ... i)
                // pre == -1 代表0~i整体就是一块
                dp[i] = pre == -1 ? 1 : (dp[pre] + 1);
            }

            // dp[i] = Max{dp[i - 1], dp[k - 1] + 1}
            if (i > 0) {
                dp[i] = Math.max(dp[i - 1], dp[i]);
            }
            map.put(sum, i);
        }
        return dp[dp.length - 1];
    }

    // for test
    public static int comparator(int[] arr) {
        if (arr == null || arr.length == 0) {
            return 0;
        }
        int[] eors = new int[arr.length];
        int eor = 0;
        for (int i = 0; i < arr.length; i++) {
            eor ^= arr[i];
            eors[i] = eor;
        }
        int[] mosts = new int[arr.length];
        mosts[0] = arr[0] == 0 ? 1 : 0;
        for (int i = 1; i < arr.length; i++) {
            mosts[i] = eors[i] == 0 ? 1 : 0;
            for (int j = 0; j < i; j++) {
                if ((eors[i] ^ eors[j]) == 0) {
                    mosts[i] = Math.max(mosts[i], mosts[j] + 1);
                }
            }
            mosts[i] = Math.max(mosts[i], mosts[i - 1]);
        }
        return mosts[mosts.length - 1];
    }

    // for test
    public static int[] generateRandomArray(int maxSize, int maxValue) {
        int[] arr = new int[(int) ((maxSize + 1) * Math.random())];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (int) ((maxValue + 1) * Math.random());
        }
        return arr;
    }

    // for test
    public static void printArray(int[] arr) {
        if (arr == null) {
            return;
        }
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i] + " ");
        }
        System.out.println();
    }

    // for test
    public static void main(String[] args) {
        int testTime = 500000;
        int maxSize = 300;
        int maxValue = 100;
        boolean succeed = true;
        for (int i = 0; i < testTime; i++) {
            int[] arr = generateRandomArray(maxSize, maxValue);
            int res = mostEOR(arr);
            int comp = comparator(arr);
            if (res != comp) {
                succeed = false;
                printArray(arr);
                System.out.println(res);
                System.out.println(comp);
                break;
            }
        }
        System.out.println(succeed ? "Nice!" : "Fucking fucked!");
    }
}
