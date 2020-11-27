package com.rukawa.algorithm.trainingcamp.trainingcamp4.class7;

import java.util.HashSet;
import java.util.TreeSet;

/**
 * Created with Intellij IDEA
 *
 * @Author：SuperHai
 * @Date：2020-10-22 7:50
 * @Version：1.0
 */
public class Code04_SubSequenceMaxModM {
    /**
     * 给定一个非负数组arr，和一个正数m
     * 返回arr的所有子序列中累加和%m之后的最大值
     */

    // 暴力递归
    public static int max1(int[] arr, int m) {
        HashSet<Integer> set = new HashSet<>();
        process(arr, 0, 0, set);
        int max = 0;
        for (Integer sum : set) {
            max = Math.max(max, sum % m);
        }
        return max;
    }

    public static void process(int[] arr, int index, int sum, HashSet<Integer> set) {
        if (index == arr.length) {
            set.add(sum);
        } else {
            process(arr, index + 1, sum, set);
            process(arr, index + 1, sum + arr[index], set);
        }
    }

    // O(n*sum) -> 如果sum很大，构建表的规模很大，不适合
    public static int max2(int[] arr, int m) {
        int sum = 0;
        int N = arr.length;
        for (int i = 0; i < N; i++) {
            sum += arr[i];
        }
        boolean[][] dp = new boolean[N][sum + 1];
        for (int i = 0; i < N; i++) {
            dp[i][0] = true;
        }
        dp[0][arr[0]] = true;
        for (int i = 1; i < N; i++) {
            for (int j = 1; j <= sum; j++) {
                dp[i][j] = dp[i - 1][j];
                if (j - arr[i] >= 0) {
                    dp[i][j] = dp[i][j] | dp[i - 1][j - arr[i]];
                }
            }
        }
        int ans = 0;
        for (int j = 0; j <= sum; j++) {
            if (dp[N - 1][j]) {
                ans = Math.max(ans, j % m);
            }
        }
        return ans;
    }

    // O(n*m) ->
    public static int max3(int[] arr, int m) {
        //
        int N = arr.length;
        boolean[][] dp = new boolean[N][m];
        // 第0列，0~i中数得到累加和最大值0，%m是0，所以什么都不选
        for (int i = 0; i < N; i++) {
            dp[i][0] = true;
        }
        // 第0行，第一个数%m得到的累加和为true
        dp[0][arr[0] % m] = true;
        /**
         * 思路：
         *  1、dp[5][7]，0~5范围上的数，不使用arr[5],则去判断0~4范围上的数能组成7  -> dp[4][7]
         *  2、dp[5][7]，arr[5]=3，一定使用arr[5]，那么0~4范围上要出4  -> dp[4][4]
         *  3、绕一圈回来，m=10，dp[5][2]，arr[5]=3，结合出来之后出来12,12%10=2，所以0~4范围上出9  -> dp[4][9]
         *
         */
        for (int i = 1; i < N; i++) {
            for (int j = 1; j < m; j++) {
                dp[i][j] = dp[i - 1][j];
                int cur = arr[i] % m;
                if (j - cur >= 0) {
                    dp[i][j] = dp[i][j] | dp[i - 1][j - cur];
                }
                // 转圈
                if (j - cur < 0) {
                    dp[i][j] = dp[i][j] | dp[i - 1][(m + j) - cur];
                }
            }
        }
        int res = 0;
        for (int i = m - 1; i >= 0; i--) {
            if (dp[N - 1][i]) {
                res = i;
                break;
            }
        }
        return res;
    }

    // 如果arr的累加和很大，m也很大
    // 但是arr的长度相对不大

    /**
     * 每一个值都巨大无比，10^8
     * m的值也很大，10^12
     * n的规模是20
     * 采用分治方法，
     * 2^10 + 2^10 ，单独整合2^10,不会超过10^8
     */
    public static int max4(int[] arr, int m) {
        if (arr.length == 1) {
            return arr[0] % m;
        }
        int mid = (arr.length - 1) / 2;
        TreeSet<Integer> sortSet1 = new TreeSet<>();
        process4(arr, 0, 0, mid, m, sortSet1);

        TreeSet<Integer> sortSet2 = new TreeSet<>();
        process4(arr, mid + 1, 0, arr.length - 1, m, sortSet2);

        int res = 0;
        for (Integer leftMod : sortSet1) {
            /**
             * 有序数组中必然包含0
             * 情况1：只在左侧，在右侧选择0个
             * 情况2：只在右侧，在左侧选择0个
             * 情况3：在两侧都有
             */
            res = Math.max(res, leftMod + sortSet2.floor(m - 1 - leftMod));
        }
        return res;
    }

    // 从index出发，最后有边界是end+1，arr[index...end]
    public static void process4(int[] arr, int index, int sum, int end, int m, TreeSet<Integer> sortSet) {
        if (index == end + 1) {
            sortSet.add(sum % m);
        } else {
            process4(arr, index + 1, sum, end, m, sortSet);
            process4(arr, index + 1, sum + arr[index], end, m, sortSet);
        }
    }

    public static int[] generateRandomArray(int len, int value) {
        int[] ans = new int[(int) (Math.random() * len) + 1];
        for (int i = 0; i < ans.length; i++) {
            ans[i] = (int) (Math.random() * value);
        }
        return ans;
    }

    public static void main(String[] args) {
        int len = 10;
        int value = 100;
        int m = 76;
        int testTime = 500000;
        System.out.println("test begin");
        for (int i = 0; i < testTime; i++) {
            int[] arr = generateRandomArray(len, value);
            int ans1 = max1(arr, m);
            int ans2 = max2(arr, m);
            int ans3 = max3(arr, m);
            int ans4 = max4(arr, m);
            if (ans1 != ans2 || ans2 != ans3 || ans3 != ans4) {
                System.out.print("Oops!");
            }
        }
        System.out.println("test finish!");

    }
}
