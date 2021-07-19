package com.rukawa.algorithm.types.dp.quadrilateral;

/**
 * @className: Code01_BestSplitForAll
 * @description: TODO 类描述
 * @author: 鎏川疯
 * @date: 2021/7/18 0018 19:14
 **/
public class Code01_BestSplitForAll {

    /**
     * 给定一个非负数组arr，长度为N
     * 那么有N-1种方案可以把arr切分成左右两部分
     * 每一种方案都有，min{左部分的累加和，右部分的累加和}
     * 求这么多方案中，min{左部分的累加和，右部分的累加和}的最大值是多少？
     * 整个过程要求时间复杂度O(N)
     */

    // 暴力方法 O(N^2)
    public static int bestSplit1(int[] arr) {
        if (arr == null || arr.length < 2) {
            return 0;
        }
        int N = arr.length;
        int ans = 0;
        for (int start = 0; start < N - 1; start++) {
            int sumLeft = 0;
            for (int L = 0; L <= start; L++) {
                sumLeft += arr[L];
            }

            int sumRight = 0;
            for (int R = start + 1; R < N; R++) {
                sumRight += arr[R];
            }

            ans = Math.max(ans, Math.min(sumLeft, sumRight));
        }
        return ans;
    }

    // 最终解 O(N)
    public static int bestSplit2(int[] arr) {
        if (arr == null || arr.length < 2) {
            return 0;
        }
        int N = arr.length;
        int sumAll = 0;
        for (int num : arr) {
            sumAll += num;
        }
        int ans = 0;
        int sumL = 0;
        for (int start = 0; start < N - 1; start++) {
            sumL += arr[start];
            int sumR = sumAll - sumL;
            ans = Math.max(ans, Math.min(sumL, sumR));
        }
        return ans;
    }


    // for test
    public static int[] randomArray(int len, int max) {
        int[] ans = new int[len];
        for (int i = 0; i < len; i++) {
            ans[i] = (int) (Math.random() * max);
        }
        return ans;
    }

    public static void main(String[] args) {
        int N = 20;
        int max = 30;
        int testTime = 1000000;
        System.out.println("测试开始");
        for (int i = 0; i < testTime; i++) {
            int len = (int) (Math.random() * N);
            int[] arr = randomArray(len, max);
            int ans1 = bestSplit1(arr);
            int ans2 = bestSplit2(arr);
            if (ans1 != ans2) {
                System.out.println(ans1);
                System.out.println(ans2);
                System.out.println("Oops!");
            }
        }
        System.out.println("测试结束");
    }
}
