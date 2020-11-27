package com.rukawa.algorithm.trainingcamp.trainingcamp3.class8;

/**
 * Created with Intellij IDEA
 *
 * @Author：SuperHai
 * @Date：2020-10-09 7:13
 * @Version：1.0
 */
public class Code02_MoneyWays {
    /**
     * 现在有n1+n2种面值的硬币，其中前n1种为普通币，可以取任意枚，后n2种为纪念币，每种只能取一枚，
     * 每种硬币都有一个面值，问能用多少种方法拼出m的面值。
     */

    public static int moneyWays(int[] arbitrary, int[] onlyOne, int money) {
        if (money < 0) {
            return 0;
        }

        if ((arbitrary == null || arbitrary.length == 0) && (onlyOne == null || onlyOne.length == 0)) {
            return money == 0 ? 1 : 0;
        }
        // 任意张的数组，一张的数组，不可能都没有
        int[][] dpArbitrary = getDpArb(arbitrary, money);
        int[][] dpOnlyOne = getDpOne(onlyOne, money);

        if (dpArbitrary == null) {  // 普通币的数组没有，纪念币的数组有
            return dpOnlyOne[dpOnlyOne.length - 1][money];
        }

        if (dpOnlyOne == null) {    // 纪念币的数组没有，普通币的数组有
            return dpArbitrary[dpArbitrary.length - 1][money];
        }
        // 普通币的数组和纪念币的数组都存在，组合选取
        int res = 0;
        for (int i = 0; i <= money; i++) {
            res += dpArbitrary[dpArbitrary.length - 1][i] * dpOnlyOne[dpOnlyOne.length - 1][money - i];
        }
        return res;
    }


    public static int[][] getDpArb(int[] arr, int money) {
        if (arr == null || arr.length == 0) {
            return null;
        }
        int[][] dp = new int[arr.length][money + 1];

        // 0元的时候，不管选择什么都有一种方法
        for (int i = 0; i < arr.length; i++) {
            dp[i][0] = 1;
        }

        // 如果arr[0]=5, 那么能搞定的钱数是5,10,15,20,...
        for (int j = 1; j * arr[0] <= money; j++) {
            dp[0][arr[0] * j] = 1;
        }

        for (int i = 1; i < arr.length; i++) {
            for (int j = 1; j <= money; j++) {
                // i-1位置已经凑齐了j元
                dp[i][j] = dp[i - 1][j];
                dp[i][j] += j - arr[i] >= 0 ? dp[i][j - arr[i]] : 0;
            }
        }
        return dp;
    }

    public static int[][] getDpOne(int[] arr, int money) {
        if (arr == null || arr.length == 0) {
            return null;
        }
        int[][] dp = new int[arr.length][money + 1];
        for (int i = 0; i < arr.length; i++) {
            dp[i][0] = 1;
        }

        if (arr[0] <= money) {
            dp[0][arr[0]] = 1;
        }

        for (int i = 1;i < arr.length; i++) {
            for (int j = 1; j <= money; j++) {
                dp[i][j] = dp[i - 1][j];
                dp[i][j] += j - arr[i] >= 0 ? dp[i - 1][j - arr[i]] : 0;
            }
        }
        return dp;
    }
}
