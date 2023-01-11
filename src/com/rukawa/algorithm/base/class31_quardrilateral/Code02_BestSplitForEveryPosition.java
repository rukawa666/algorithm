package com.rukawa.algorithm.base.class31_quardrilateral;

/**
 * create by hqh on 2023/1/10
 */
public class Code02_BestSplitForEveryPosition {
    /**
     * 给定一个非负数组arr，长度为N
     * 返回一个长度为N的数组，求在每个位置：min{左部分累加和,右部分累加和}的所有方案中，得到min{左部分累加和,右部分累加和}的最大值是多少
     * 整个过程要求时间复杂度O(N)
     */
    // 暴力解
    public static int[] bestSplit1(int[] arr) {
        if (arr == null || arr.length == 0) {
            return new int[0];
        }
        int N = arr.length;
        int[] ans = new int[N];
        ans[0] = 0;
        for (int range = 1; range < N; range++) {
            for (int s = 0; s < range; s++) {
                int sumL = 0;
                for (int L = 0; L <= s; L++) {
                    sumL += arr[L];
                }
                int sumR = 0;
                for (int R = s + 1; R <= range; R++) {
                    sumR += arr[R];
                }
                ans[range] = Math.max(ans[range], Math.min(sumL, sumR));
            }
        }
        return ans;
    }

    // 稍微优化
    // O(N^2)
    public static int[] bestSplit2(int[] arr) {
        if (arr == null || arr.length == 0) {
            return new int[0];
        }
        int N = arr.length;
        int[] ans = new int[N];
        ans[0] = 0;
        int[] sum = new int[N + 1];
        for (int i = 0; i < N; i++) {
            sum[i + 1] = sum[i] + arr[i];
        }
        for (int range = 1; range < N; range++) {
            for (int s = 0; s < range; s++) {
                int sumL = sum(sum, 0, s);
                int sumR = sum(sum, s + 1, range);
                ans[range] = Math.max(ans[range], Math.min(sumL, sumR));
            }
        }
        return ans;
    }
    // 原数组arr中，arr[L...R]的累加和
    private static int sum(int[] sum, int L, int R) {
        return sum[R + 1] - sum[L];
    }

    // 最终优化版本
    // O(N)
    public static int[] bestSplit3(int[] arr) {
        if (arr == null || arr.length == 0) {
            return new int[0];
        }
        int N = arr.length;
        int[] ans = new int[N];
        // 0~0划分，答案一定是0
        ans[0] = 0;
        // 前缀和
        // arr   =  {5, 3, 1, 3}
        //           0  1  2  3
        // sum  ={0, 5, 8, 9, 12}
        //        0  1  2  3  4
        // 0~2 -> sum[3] - sum[0]
        // 1~3 -> sum[4] - sum[1]
        int[] sum = new int[N + 1];
        for (int i = 0; i < N; i++) {
            sum[i + 1] = sum[i] + arr[i];
        }
        // 最优划分
        // 0～range-1 上最优划分左部分[0~best]，右部分[best+1~range-1]，best是下标。
        int best = 0;
        for (int range = 1; range < N; range++) {
            while (best + 1 < range) { // 后面还有位置可以尝试,要保证右半部分有数
                // 跳之前的答案
                int before = Math.min(sum(sum, 0, best), sum(sum, best + 1, range));
                // 跳之后的答案
                int after = Math.min(sum(sum, 0, best + 1), sum(sum, best + 2, range));
                // 为什么=0，5,5,0,0,5,5,... 的情况下，遇到0位置要继续往右尝试
                if (after >= before) {
                    best++;
                } else {
                    break;
                }
            }
            ans[range] = Math.min(sum(sum, 0, best), sum(sum, best + 1, range));
        }
        return ans;
    }

    public static int[] randomArray(int len, int max) {
        int[] ans = new int[len];
        for (int i = 0; i < len; i++) {
            ans[i] = (int) (Math.random() * max);
        }
        return ans;
    }

    public static boolean isSameArray(int[] arr1, int[] arr2) {
        if (arr1.length != arr2.length) {
            return false;
        }
        int N = arr1.length;
        for (int i = 0; i < N; i++) {
            if (arr1[i] != arr2[i]) {
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        int N = 20;
        int max = 30;
        int testTime = 1000000;
        System.out.println("测试开始");
        for (int i = 0; i < testTime; i++) {
            int len = (int) (Math.random() * N);
            int[] arr = randomArray(len, max);
            int[] ans1 = bestSplit1(arr);
            int[] ans2 = bestSplit2(arr);
            int[] ans3 = bestSplit3(arr);
            if (!isSameArray(ans1, ans2) || !isSameArray(ans1, ans3)) {
                System.out.println("Oops!");
            }
        }
        System.out.println("测试结束");
    }
}
