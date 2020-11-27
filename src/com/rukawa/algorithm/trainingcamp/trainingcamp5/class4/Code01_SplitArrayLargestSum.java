package com.rukawa.algorithm.trainingcamp.trainingcamp5.class4;

/**
 * Created with Intellij IDEA
 *
 * @Author：SuperHai
 * @Date：2020-11-08 15:28
 * @Version：1.0
 */
public class Code01_SplitArrayLargestSum {
    /**
     * 给定一个整型数组arr，数组中的每个值都为正数，表示完成一幅画作需要的时间，再给定一个整数num，
     * 表示画匠的数量，每个画匠只能画连在一起的画作。所有的画家并行工作，请返回完成所有的画作需要
     * 的最少时间。
     * 【举例】
     * arr=[3,1,4]，num=2。
     * 最好的分配方式为第一个画匠画3和 1，所需时间为4。第二个画匠画4，所需时间为4。 因为并行工作，
     * 所以最少时间为4。如果分配方式为第一个画匠画3，所需时间为3。第二个画匠画1和4，所需的时间为5。
     * 那么最少时间为5，显然没有第一种分配方式好。所以返回4。
     * arr=[1,1,1,4,3]，num=3。
     * 最好的分配方式为第一个画匠画前三个1，所需时间为3。第二个画匠画4，所需时间为4。 第三个画匠画3，
     * 所需时间为3。返回4。
     */

    // O(N*log(2,sum))
    public static int splitArray4(int[] nums, int M) {
        long sum = 0;
        for (int i = 0; i < nums.length; i++) {
            sum += nums[i];
        }

        long l = 0;
        long r = sum;
        long ans = 0;
        while (l <= r) {
            long mid = (l + r) / 2;
            long cur = getNeedParts(nums, mid);
            if (cur <= M) {
                ans = mid;
                r = mid - 1;
            } else {
                l = mid + 1;
            }
        }
        return (int) ans;
    }

    public static int getNeedParts(int[] nums, long aim) {
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] > aim) {
                return Integer.MAX_VALUE;
            }
        }
        int parts = 1;
        int all = nums[0];
        for (int i = 1; i < nums.length; i++) {
            if (all + nums[i] > aim) {
                parts++;
                all = nums[i];
            } else {
                all += nums[i];
            }
        }
        return parts;
    }


    public static int splitArray1(int[] nums, int m) {
        return process(nums, 0, m);
    }

    public static int process(int[] arr, int index, int part) {
        if (index == arr.length) {
            return 0;
        }
        if (part == 0) {
            return -1;
        }

        int first = 0;
        int min = Integer.MAX_VALUE;
        for (int end = index; arr.length - end >= part; end++) {
            first += arr[end];
            int next = process(arr, end + 1, part - 1);
            if (next != -1) {
                min = Math.min(min, Math.max(first, next));
            }
        }
        return min;
    }

    public static int splitArray2(int[] nums, int m) {
        int N = nums.length;
        int[] help = new int[nums.length + 1];
        for (int i = 0; i < N; i++) {
            help[i + 1] = help[i] + nums[i];
        }
        int[][] dp = new int[N][m + 1];
        for (int i = 0; i < N; i++) {
            dp[i][1] = help[i + 1] - help[0];
        }
        for (int i = 1; i < Math.min(N, m); i++) {
            dp[i][i + 1] = Math.max(dp[i - 1][i], nums[i]);
        }
        for (int i = 2; i < N; i++) {
            for (int j = 2; j <= Math.min(i, m); j++) {
                dp[i][j] = Integer.MAX_VALUE;
                for (int k = i; k >= j - 1; k--) {
                    dp[i][j] = Math.min(dp[i][j], Math.max(dp[k - 1][j - 1], help[i + 1] - help[k]));
                }
            }
        }
        return dp[N - 1][m];
    }

    public static int splitArray3(int[] nums, int M) {
        int N = nums.length;
        int[] help = new int[nums.length + 1];
        for (int i = 0; i < N; i++) {
            help[i + 1] = help[i] + nums[i];
        }
        int[][] dp = new int[N][M + 1];
        int[][] best = new int[N][M + 1];
        for (int i = 0; i < N; i++) {
            dp[i][1] = help[i + 1] - help[0];
        }
        for (int i = 1; i < Math.min(N, M); i++) {
            dp[i][i + 1] = Math.max(dp[i - 1][i], nums[i]);
            best[i][i + 1] = i;
        }
        for (int i = 2; i < N; i++) {
            for (int j = Math.min(i, M); j >= 2; j--) {
                dp[i][j] = Integer.MAX_VALUE;
                int left = best[i - 1][j];
                int right = j + 1 > M ? i : best[i][j + 1];
                for (int k = left; k <= right; k++) {
                    int curAns = Math.max(dp[k - 1][j - 1], help[i + 1] - help[k]);
                    if (dp[i][j] > curAns) {
                        dp[i][j] = curAns;
                        best[i][j] = k;
                    }
                }
            }
        }
        return dp[N - 1][M];
    }
}
