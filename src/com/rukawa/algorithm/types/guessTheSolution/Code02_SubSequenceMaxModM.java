package com.rukawa.algorithm.types.guessTheSolution;

import java.util.TreeSet;

/**
 * @className: Code02_SubSquenceMaxModM
 * @description: TODO 类描述
 * @author: 鎏川疯
 * @date: 2021/7/12 0012 23:25
 **/
public class Code02_SubSequenceMaxModM {
    /**
     * 给定一个非负数组arr，和一个正数m。返回arr的所有子序列中累加和%m之后的最大值
     */
    /**
     * 根据数据量猜解法：
     *  1、假设arr中的数字的值都不大(100以内)，长度也不大(1000~2000)，但是m很大(10^12)，可以使用最经典的背包解法
     *  dp含义：自由使用0~i的这些数字，能不能把某一个累加和j搞出来
     *  情况1：不要i的数字，去判断0~i-1之间的数字随便选择，能不能搞出来累加和j，如果它能，自己也能
     *  情况2：要i的数字，去判断0~i-1之间的数字随便选择，把i位置的数字去掉，看不能搞出j-arr[i]的累加和，如果能，自己也能
     *
     *  2、如果arr中的数字很大，但是m不大，上面的情况会失效，列可能会超出10^8。
     *  dp[i][j]含义：arr中自由选择0~i的数字，搞出的所有累加和%m之后有没有余数j
     *  假设 dp[7][5] m=8, 自由使用arr[0...7]的所有数字，搞出的累加和%8之后有没有余数5
     *  情况1：不要i的数字，判断0~6之间的所有数字，累加和%m等于5，如果它能，自己也能
     *  情况2：要i的数字，假设arr[7]=3, 3%8=3，所以在0~6的范围上能凑出余数2，自己能凑出余数5
     *                 假设arr[7]=30,30%8=6，所以在0~6的范围上能凑出余数7(8+5-6)，自己能凑出余数5
     *                 假设arr[7]=12,12%8=4, 所以在0~6的范围上能凑出余数1(能不能凑出余数17 17%8=1)，因为j<m
     *
     *  3、如果arr中的数字很大，但是m也很大，但是arr的长度在30以内，此时可以用分治算法
     *  用分治，可得到2^30，大概10^9，此时超出10^8，可以拆分成两块，各为2^15(单独搞定一边的有多少余数)
     *
     */

    // 适用于arr数组的数规模比较小，且长度比较小的情况，但是m特别大的情况
    // 复杂度O(10^3 * 10^5) 能在10^8内完成
    public static int max1(int[] arr, int m) {
        int sum = 0;
        for (int num : arr) {
            sum += num;
        }
        int N = arr.length;
        // 自由使用0~i的这些数字，能不能把某一个累加和j搞出来
        boolean[][] dp = new boolean[N][sum + 1];
        for (int i = 0; i < N; i++) {
            dp[i][0] = true;
        }
        dp[0][arr[0]] = true;
        for (int i = 1; i < N; i++) {
            for (int j = 1; j <= sum; j++) {
                dp[i][j] = dp[i - 1][j];
                if (j - arr[i] >= 0) {
                    dp[i][j] |= dp[i - 1][j - arr[i]];
                }
            }
        }
        int max = 0;
        // 0~i所有的数字自由选择，能不能把累加和搞出来
        for (int j = 0; j <= sum; j++) {
            if (dp[N - 1][j]) {
                max = Math.max(max, j % m);
            }
        }
        return max;
    }

    public static int max2(int[] arr, int m) {
        int N = arr.length;
        boolean[][] dp = new boolean[N][m];
        // 一个数都选的时候搞出余数0
        for (int i = 0; i < N; i++) {
            dp[i][0] = true;
        }
        dp[0][arr[0] % m] = true;
        for (int i = 1; i < N; i++) {
            for (int j = 1; j < m; j++) {
                // 0~i-1搞出余数j，自己也能搞出余数j
                dp[i][j] = dp[i - 1][j];
                int cur = arr[i] % m;
                // 假设j=5，m=8
                // cur = arr[i]%8 = 7  j - cur < 0
                // cur = arr[i]%8 = 3  j - cur > 0
                if (j - cur >= 0) {
                    dp[i][j] |= dp[i - 1][j - cur];
                } else {
                    dp[i][j] |= dp[i - 1][j + m - cur];
                }
            }
        }
        // 从右往左遍历，遇到第一个为true返回
        int ans = 0;
        for (int j = m - 1; j >= 0; j--) {
            if (dp[N - 1][j]) {
                ans = j;
                break;
            }
        }
        return ans;
    }

    // 分治算法
    // 如果arr的累加和很大，m也很大
    // 但是arr的长度相对不大
    public static int max3(int[] arr, int m) {
        if (arr.length == 1) {
            return arr[0] % m;
        }
        int mid = (arr.length - 1) / 2;
        // 左部分
        TreeSet<Integer> sortSet1 = new TreeSet<>();
        process3(arr, 0, 0, mid, m, sortSet1);

        // 右部门
        TreeSet<Integer> sortSet2 = new TreeSet<>();
        process3(arr, mid + 1, 0, arr.length - 1, m, sortSet2);

        int ans = 0;
        for (Integer leftMod : sortSet1) {
            ans = Math.max(ans, leftMod + sortSet2.floor(m - 1 - leftMod));
        }
        return ans;
    }

    public static void process3(int[] arr, int index, int sum, int end, int m, TreeSet<Integer> sortSet) {
        if (index == end + 1) {
            sortSet.add(sum % m);
        } else {
            process3(arr, index + 1, sum, end, m, sortSet);
            process3(arr, index + 1, sum + arr[index], end, m, sortSet);
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
            if (ans1 != ans2 || ans2 != ans3) {
                System.out.println("Oops!");
            }
        }
        System.out.println("test finish!");

    }
}
