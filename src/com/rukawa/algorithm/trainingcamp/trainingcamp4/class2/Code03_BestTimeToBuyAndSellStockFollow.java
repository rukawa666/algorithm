package com.rukawa.algorithm.trainingcamp.trainingcamp4.class2;

/**
 * Created with Intellij IDEA
 *
 * @Author：SuperHai
 * @Date：2020-10-10 7:36
 * @Version：1.0
 */
public class Code03_BestTimeToBuyAndSellStockFollow {
    /**
     * 给定一个数组arr，从左到右表示昨天从早到晚股票的价格
     * 作为一个事后诸葛亮，你想知道如果交易次数不超过K次，
     * 且每次交易只买卖一股，返回能挣到的最大钱数
     */

    public static int maxProfitByDP(int k, int[] prices) {
        if (prices == null || prices.length == 0) {
            return 0;
        }

        int N = prices.length;
        if (k >= N / 2) {
            return allTrans(prices);
        }
        // arr[0...j]的股票随意做交易，但是交易次数 <= j，获得的最大钱数
        int[][] dp = new int[N][k + 1];
        // 交易0次获得钱数为0
        // dp[0][...] = 0
        // 只选择一个股票，只能当前时间点买，当前时间点卖，获得钱数为0
        // dp[...][0] = 0
        /**
         * 可能性：
         * arr[0...i] <= j次
         * 1、最后一个点(i位置)的股票不参与交易，dp[i-1][j]
         * 2、最后一个点(i位置)的股票参与交易,此时的贪心(只用参与最后一次交易的卖出时机)，其他情况都不会得到最好的解
         *    等同于 -> i位置的股票参与的是最后一次的卖出时机
         *    i、arr[0...i]做k-1次交易，在i位置买入再卖出 -> dp[i][j-1] + arr[i] - arr[i]
         *    ii、arr[0...i-1]做k-1次交易，然后再i-1位置买入，在i位置卖出 -> dp[i-1][j-1] + arr[i] - arr[i-1]
         *
         * 总结：
         *    1、dp[i-1][j]
         *    2、dp[每一个k][j-1] + arr[i](卖出) - arr[每一个k](卖出) 求出一个最大值
         *    然后1、2两项求max
         *
         *  枚举行为：
         *    可能性2.ii、在arr[0...i-1]交易了j-1次，然后再i位置卖出，再去0...i-1买入
         *    例如：dp[10][3]，依赖于
         *    1、   dp[9][3]
         *    2.i、 dp[10][2] + arr[10] - arr[10]
         *    2.ii、dp[9][2] + arr[10] - arr[9]
         *         dp[8][2] + arr[10] - arr[8]
         *         .
         *         .
         *         .
         *         dp[0][2] + arr[10] - arr[0] 说明，在0位置买入卖出两次，然后在0位置买入，去10位置卖出
         *    情况2.ii 所有的细节就是枚举行为
         *
         *    dp[11][3],依赖于
         *    1、   dp[10][3]
         *    2.i、 dp[11][2] + arr[11] - arr[11]
         *    2.ii、dp[10][2] + arr[11] - arr[10]
         *         dp[9][2] + arr[11] - arr[9]
         *         .
         *         .
         *         .
         *         dp[0][2] + arr[11] - arr[0]
         *    所以可以提取枚举行为dp[每一个k][j-1] - arr[每一个k]获取一个最大max，然后+arr[i]即可
         *
         */
        int res = 0;
        for (int j = 1; j <= k; j++) {
            int preMax = dp[0][j - 1] - prices[0];

            for (int i = 1; i < N; i++) {
                preMax = Math.max(dp[i][j - 1] - prices[i], preMax);
                dp[i][j] = Math.max(dp[i - 1][j], preMax + prices[i]);
                res = Math.max(res, dp[i][j]);
            }
        }
        return res;
    }

    // 使用空间压缩技巧
    public static int maxProfit(int k, int[] prices) {
        if (prices == null || prices.length == 0) {
            return 0;
        }
        int N = prices.length;
        if (k >= N / 2) {
            return allTrans(prices);
        }
        // dp一维表，做了空间压缩
        int[] dp = new int[N];
        int ans = 0;
        for (int tran = 1; tran <= k; tran++) {
            int pre = dp[0];
            int best = pre - prices[0];
            for (int index = 1; index < N; index++) {
                pre = dp[index];
                dp[index] = Math.max(dp[index - 1], prices[index] + best);
                best = Math.max(best, pre - prices[index]);
                ans = Math.max(dp[index], ans);
            }
        }
        return ans;
    }

    public static int allTrans(int[] prices) {
        int res = 0;
        for (int i = 1; i < prices.length; i++) {
            res += Math.max(0, prices[i] - prices[i - 1]);
        }
        return res;
    }

    public static void main(String[] args) {
        int[] test = {3,2,6,5,0,3};
        int K = 2;
        System.out.println(maxProfitByDP(K, test));
        System.out.println(maxProfit(K, test));

    }
}
