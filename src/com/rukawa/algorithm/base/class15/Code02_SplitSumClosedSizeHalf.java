package com.rukawa.algorithm.base.class15;


/**
 * Created with Intellij IDEA
 *
 * @Author：SuperHai
 * @Date：2021-01-19 20:43
 * @Version：1.0
 */
public class Code02_SplitSumClosedSizeHalf {
    /**
     * 字节跳动2020-07原题
     * 给定一个正序数组arr，请把arr中所有的数分成两个集合
     * 如果arr长度为偶数，两个集合包含数的个数要一样多
     * 如果arr长度为奇数，两个集合包含数的个数必须只差一个
     * 请尽量让两个集合的累加和接近
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
        // 偶数和奇数情况分析
        if ((arr.length & 1) == 0) {
            return process(arr, 0, arr.length >> 1, sum >> 1);
        } else {
            int p1 = process(arr, 0, arr.length >> 1, sum >> 1);
            int p2 = process(arr, 0, (arr.length >> 1) + 1, sum >> 1);
            return Math.max(p1, p2);
        }
    }

    // arr[i...]自由选择，挑选的个数一定要是picks个，累计和<=rest，离rest最近的返回
    public static int process(int[] arr, int index, int picks, int rest) {
        if (index == arr.length) {
            return picks == 0 ? 0 : -1;
        }
        int p1 = process(arr, index + 1, picks, rest);

        int next = -1;
        if (arr[index] <= rest) {
            next = process(arr, index + 1, picks - 1, rest - arr[index]);
        }
        int p2 = -1;
        if (next != -1) {
            p2 = arr[index] + next;
        }
        return Math.max(p1, p2);
    }

    public static int dp1(int[] arr) {
        if (arr == null || arr.length < 2) {
            return 0;
        }
        int sum = 0;
        for (int num : arr) {
            sum += num;
        }
        sum >>= 1;
        int n = arr.length;
        // 如果是奇数，取上中点
        int m = (n + 1) >> 1;
        int[][][] dp = new int[n + 1][m + 1][sum + 1];
        // 初始化为无效
        for (int i = 0; i <= n; i++) {
            for (int j = 0; j <= m; j++) {
                for (int k = 0; k <= sum; k++) {
                    dp[i][j][k] = -1;
                }
            }
        }

        for (int k = 0; k <= sum; k++) {
            dp[n][0][k] = 0;
        }

        for (int i = n - 1; i >= 0; i--) {
            for (int j = 0; j <= m; j++) {
                for (int k = 0; k <= sum; k++) {
                    dp[i][j][k] = dp[i + 1][j][k];
                    int next = -1;
                    if (j - 1 >= 0 && arr[i] <= k) {
                        next = dp[i + 1][j - 1][k - arr[i]];
                    }
                    if (next != -1) {
                        dp[i][j][k] = Math.max(dp[i][j][k], next + arr[i]);
                    }
                }
            }
        }

        if ((arr.length & 1) == 0) {
            return dp[0][n >> 1][sum];
        } else {
            return Math.max(dp[0][n >> 1][sum], dp[0][(n >> 1) + 1][sum]);
        }
    }

    public static int dp2(int[] arr) {
        if (arr == null || arr.length < 1) {
            return 0;
        }
        int sum = 0;
        for (int num : arr) {
            sum += num;
        }
        sum >>= 1;
        int N = arr.length;
        int M = ((arr.length + 1) >> 1);
        int[][][] dp = new int[N + 1][M + 1][sum + 1];
        for (int i = 0; i <= N; i++) {
            for (int j = 0; j <= M; j++) {
                for (int k = 0; k <= sum; k++) {
                    dp[i][j][k] = -1;
                }
            }
        }

        for (int rest = 0; rest <= sum; rest++) {
            dp[N][0][rest] = 0;
        }

        for (int i = N - 1; i >= 0; i--) {
            for (int picks = 0; picks <= M; picks++) {
                for (int rest = 0; rest <= sum; rest++) {
                    int p1 = dp[i + 1][picks][rest];
                    int p2 = -1;
                    // 判断是否是无效解
                    int next = -1;
                    if (picks - 1 >= 0 && arr[i] <= rest) {
                        next = dp[i + 1][picks - 1][rest - arr[i]];
                    }
                    // 如果不是无效解，才有可能性2
                    if (next != -1) {
                        p2 = arr[i] + next;
                    }
                    dp[i][picks][rest] = Math.max(p1, p2);
                }
            }
        }

        if ((arr.length & 1) == 0) {
            return dp[0][N >> 1][sum];
        } else {
            return Math.max(dp[0][N >> 1][sum], dp[0][(N >> 1) + 1][sum]);
        }
    }

    // for test
    public static int[] randomArray(int len, int value) {
        int[] arr = new int[len];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (int) (Math.random() * value);
        }
        return arr;
    }

    // for test
    public static void printArray(int[] arr) {
        for (int num : arr) {
            System.out.print(num + " ");
        }
        System.out.println();
    }

    // for test
    public static void main(String[] args) {
        int maxLen = 20;
        int maxValue = 50;
        int testTime = 10000;
        System.out.println("测试开始");
        for (int i = 0; i < testTime; i++) {
            int len = (int) (Math.random() * maxLen);
            int[] arr = randomArray(len, maxValue);
            int ans1 = right(arr);
            int ans2 = dp1(arr);
            int ans3 = dp2(arr);
            if (ans1 != ans2 && ans1 != ans3) {
                printArray(arr);
                System.out.println(ans1);
                System.out.println(ans2);
                System.out.println(ans3);
                System.out.println("Oops!");
                break;
            }
        }
        System.out.println("测试结束");
    }
}
