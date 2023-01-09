package com.rukawa.algorithm.base.class16_window;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @Author: Administrator
 * @Date: 2021/4/27 0027 9:19
 */
public class Code04_MinCoinsOnePaper {
    /**
     * arr是货币数组，其中的值都是正数，在给定一个正数aim
     * 每个值都认为是一张货币，
     * 返回组成aim的最少货币数
     * 注意：
     *  因为是求最少货币数，所以每一张货币认为是相同或者不相同就不重要了
     */

    public static int minCoins(int[] arr, int aim) {
        return process(arr, 0, aim);
    }

    public static int process(int[] arr, int index, int rest) {
        if (rest < 0) {
            return Integer.MAX_VALUE;
        }

        if (index == arr.length) {
            return rest == 0 ? 0 : Integer.MAX_VALUE;
        } else {
            int p1 = process(arr, index + 1, rest);
            int p2 = process(arr, index + 1, rest - arr[index]);
            if (p2 != Integer.MAX_VALUE) {
                p2++;
            }
            return Math.min(p1, p2);
        }
    }

    // dp优化
    public static int dp1(int[] arr, int aim) {
        if (aim == 0) {
            return 0;
        }
        int N = arr.length;
        int[][] dp = new int[N + 1][aim + 1];
        dp[N][0] = 0;
        for (int index = N - 1; index >= 0; index++) {
            for (int rest = 0; rest <= aim; rest++) {
                dp[index][rest] = dp[index + 1][rest];
                if (rest >= arr[index]) {
                    dp[index][rest] = Math.min(dp[index][rest], dp[index + 1][rest - arr[index]] + 1);
                }
            }
        }
        return dp[0][aim];
    }

    // 收集张数去重
    // O(M*aim M是张数去重的值)
    public static int dp2(int[] arr, int aim) {
        if (aim == 0) {
            return 0;
        }
        Info info = getInfo(arr);
        int[] coins = info.money;
        int[] zhangs = info.zhang;
        int N = coins.length;
        int[][] dp = new int[N + 1][aim + 1];
        dp[N][0] = 0;

        for (int j = 1; j <= aim; j++) {
            dp[N][j] = Integer.MAX_VALUE;
        }

        for (int i = N - 1; i >= 0; i--) {
            // 假设当前货币是7元，总钱数是29
            // 先从0 7 14 28
            // 再从1 8 15 这样分组来
            for (int mod = 0; mod < Math.min(aim + 1, coins[i]); mod++) {
                LinkedList<Integer> window = new LinkedList<>();
                window.add(mod);
                dp[i][mod] = dp[i + 1][mod];
                for (int r = mod + coins[i]; r <= aim; r += coins[i]) {
                    while (!window.isEmpty() && (dp[i + 1][window.peekLast()] == Integer.MAX_VALUE
                        || dp[i + 1][window.peekLast()] + compensate(window.peekLast(), r, coins[i]) > dp[i + 1][r])) {
                        window.pollLast();
                    }
                    window.addLast(r);
                    int overdue = r - coins[i] * (zhangs[i] + 1);
                    if (window.peekFirst() == overdue) {
                        window.pollFirst();
                    }
                    dp[i][r] = dp[i + 1][window.peekFirst()] + compensate(window.peekFirst(), r, coins[i]);
                }
            }
        }
        return dp[0][aim];
    }

    public static int compensate(int pre, int cur, int coin) {
        return (cur - pre) / coin;
    }

    public static Info getInfo(int[] arr) {
        HashMap<Integer, Integer> counts = new HashMap<>();
        for (int num : arr) {
            if (!counts.containsKey(num)) {
                counts.put(num, 1);
            } else {
                counts.put(num, counts.get(num) + 1);
            }
        }
        int N = counts.size();
        int[] money = new int[N];
        int[] zhang = new int[N];
        int index = 0;
        for (Map.Entry<Integer, Integer> entry : counts.entrySet()) {
            money[index] = entry.getKey();
            zhang[index++] = entry.getValue();
        }
        return new Info(money, zhang);
    }

    public static class Info {
        private int[] money;
        private int[] zhang;

        public Info(int[] m, int[] z) {
            this.money = m;
            this.zhang = m;
        }
    }
}
