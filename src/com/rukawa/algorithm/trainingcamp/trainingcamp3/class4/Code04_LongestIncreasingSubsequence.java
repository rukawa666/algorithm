package com.rukawa.algorithm.trainingcamp.trainingcamp3.class4;

/**
 * Created with Intellij IDEA
 *
 * @Author：SuperHai
 * @Date：2020-08-26 8:29
 * @Version：1.0
 */
public class Code04_LongestIncreasingSubsequence {
    /**
     * 最长递增子序列问题的O(N * logN)的解法
     */

    // 最原始的解法，O(N^2)
    public static int[] lis1(int[] arr) {
        if (arr == null || arr.length == 0) {
            return null;
        }
        int[] dp = getDp1(arr);
        return generateLIS(arr, dp);
    }

    public static int[] getDp1(int[] arr) {
        int[] dp = new int[arr.length];
        for (int i = 0; i < arr.length; i++) {
            dp[i] = 1;
            for (int j = 0; j < i; j++) {
                if (arr[i] > arr[j]) {
                    dp[i] = Math.max(dp[i], dp[j] + 1);
                }
            }
        }
        return dp;
    }
    public static int[] generateLIS(int[] arr, int[] dp) {
        int len = 0;
        int index = 0;
        for (int i = 0; i < dp.length; i++) {
            if (dp[i] > len) {
                len = dp[i];
                index = i;
            }
        }

        int[] lis = new int[len];
        lis[--len] = arr[index];
        for (int i = index; i >= 0; i--) {
            if (arr[i] < arr[index] && dp[i] == dp[index] - 1) {
                lis[--len] = arr[i];
                index = i;
            }
        }
        return lis;
    }

    public static int[] lis2(int[] arr) {
        if (arr == null || arr.length == 0) {
            return null;
        }
        int[] dp = getDp2(arr);
        return generateLIS(arr, dp);
    }

    /**
     * eg:
     *   arr [3 2 4 5 1 3]
     *   dp  [1 1 2 3 1 2]
     *   ends[1 3 5]
     *   1、有效区0~0，i=3，在ends找到最左的大于等于3的数，找不到则扩充ends，
     *      此时ends[0]=3,dp[0]=1
     *   2、有效区0~1，i=2，在ends找到最左的大于等于2的数，此时找到为3，则更新ends[0]=2,
     *      此时ends中只有一个数，dp[1]=1
     *   3、有效区0~2，i=4，在ends找到最左的大于等于4的数，此时找不到，扩充有效区，ends[1]=4,
     *      此时ends包含自己在内左边有2个数，dp[2]=2
     *   4、有效区0~3，i=5，在ends找到最左的大于等于5的数，此时找不到，扩充有效区，ends[2]=5,
     *      此时ends包含自己在内左边有3个数，dp[3]=3
     *   5、有效区0~4，i=1，在ends找到最左的大于等于1的数，此时找到为2，更新ends[0]=1，
     *      此时ends包含自己在内左边有1个数，dp[4]=1
     *   6、有效区0~5，i=3，在ends找到最左的大于等于3的数，此时找到为4，更新ends[1]=3，
     *      此时ends包含自己在内左边有2个数，dp[5]=2
     */
    public static int[] getDp2(int[] arr) {
        int[] dp = new int[arr.length];
        int[] ends = new int[arr.length];   // 找到的所有长度为i+1的递增子序列最小结尾是什么,ends的有效区必须有序
        ends[0] = arr[0];
        dp[0] = 1;
        int right = 0;  // 0...right是有效期  right往右无效
        int l = 0;
        int r = 0;
        int m = 0;
        for (int i = 1; i < arr.length; i++) {
            l = 0;
            r = right;
            // 找到大于等于当前这个数(arr[i])最左的位置
            while (l <= r) {
                m = l + ((r - l) >>> 1);
                if (arr[i] > ends[m]) {
                    l = m + 1;
                } else {
                    r = m - 1;
                }
            }
            // 如果没有找到，则l来到right+1位置，如果找到了来到right位置
            right = Math.max(right, l);
            ends[l] = arr[i];
            dp[i] = l + 1;
        }
        return dp;
    }
}
