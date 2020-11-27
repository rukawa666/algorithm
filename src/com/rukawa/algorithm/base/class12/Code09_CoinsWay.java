package com.rukawa.algorithm.base.class12;

/**
 * Created with Intellij IDEA
 *
 * @Author：SuperHai
 * @Date：2020-07-28 23:21
 * @Version：1.0
 */
public class Code09_CoinsWay {

    public static int ways01(int[] arr, int aim) {
        if (arr == null || arr.length == 0 || aim < 0) {
            return 0;
        }

        return process01(arr, 0, aim);
    }

    public static int process01(int[] arr, int index, int rest) {
//        if (rest < 0) {
//            return 0;
//        }

        if (index == arr.length) {
            return rest == 0 ? 1 : 0;
        }

        int ways = 0;
        for (int k = 0; k * arr[index] <= rest; k++) {
            ways += process01(arr, index + 1, rest - k * arr[index]);
        }
        return ways;
    }

    public static int ways02(int[] arr, int aim) {
        if (arr == null || arr.length == 0 || aim < 0) {
            return 0;
        }
        int[][] dp = new int[arr.length + 1][aim + 1];
        for (int i = 0; i < dp.length; i++) {
            for (int j = 0; j < dp[0].length; j++) {
                dp[i][j] = -1;
            }
        }
        return process02(arr, 0, aim, dp);
    }

    // 如果index和rest的参数组合，是没算过的，dp[index][rest] == -1
    // 如果index和rest的参数组合，是算过的，dp[index][rest] > 1
    public static int process02(int[] arr, int index, int rest, int[][] dp) {
        if (dp[index][rest] != -1) {
            return dp[index][rest];
        }

        if (index == arr.length) {
            dp[index][rest] = rest == 0 ? 1 : 0;
            return dp[index][rest];
        }

        int ways = 0;
        for (int k = 0; k * arr[index] <= rest; k++) {
            ways += process02(arr, index + 1, rest - k * arr[index], dp);
        }
        dp[index][rest] = ways;
        return dp[index][rest];
    }

    public static int ways03(int[] arr, int aim) {
        if (arr == null || arr.length == 0 || aim < 0) {
            return 0;
        }
        int N = arr.length;
        int[][] dp = new int[N + 1][aim + 1];

        dp[N][0] = 1;  // dp[N][1...aim] -> 0

        for (int index = N - 1; index >= 0; index--) {
            for (int rest = 0; rest <= aim; rest++) {
                int ways = 0;
                for (int Z = 0; Z * arr[index] <= rest; Z++) {
                    ways += dp[index + 1][rest - (Z * arr[index])];
                }
                dp[index][rest] = ways;
            }
        }

        return dp[0][aim];
    }

    public static int ways04(int amount, int[] coins) {
        if (coins == null || coins.length == 0 || amount < 0) {
            return 0;
        }
        int N = coins.length;
        int[][] dp = new int[N + 1][amount + 1];

        dp[N][0] = 1;  // dp[N][1...aim] -> 0

        for (int index = N - 1; index >= 0; index--) {
            for (int rest = 0; rest <= amount; rest++) {
                dp[index][rest] = dp[index + 1][rest];
                if (rest - coins[index] >= 0) {
                    dp[index][rest] += dp[index][rest - coins[index]];
                }
            }
        }

        return dp[0][amount];
    }

    public static void main(String[] args) {
        int[] arr = { 5, 10,50,100 };
        int sum = 1000;
        System.out.println(ways01(arr, sum));
        System.out.println(ways02(arr, sum));
        System.out.println(ways03(arr, sum));
        System.out.println(ways04(sum, arr));
    }


}
