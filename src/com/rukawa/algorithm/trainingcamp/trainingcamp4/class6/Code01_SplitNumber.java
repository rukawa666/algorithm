package com.rukawa.algorithm.trainingcamp.trainingcamp4.class6;

/**
 * Created with Intellij IDEA
 *
 * @Author：SuperHai
 * @Date：2020-10-20 7:35
 * @Version：1.0
 */
public class Code01_SplitNumber {
    /**
     * 给定一个正数1，裂开的方法有1种，(1)
     * 给定一个正数2，裂开的方法有2种，(1和1)、(2)
     * 给定一个正数3，裂开的方法有3种，(1、1、1)、(1、2)、(3)
     * 给定一个正数4，裂开的方法有5种，(1、1、1、1)、(1、1、2)、(1、3)、(2、2)、(4)
     * 给定一个正数n，求裂开的方法数。
     * 动态规划优化状态依赖的技巧
     *
     * DP从左到右的尝试模型
     */
    public static int way1(int n) {
        if (n < 1) {
            return 0;
        }
        return process(1, n);
    }

    // 第一块裂开为1
    // 保证pre 不能小于 rest，自然屏蔽降序的可能性，不能出现1,2,1,(2,1)不被允许
    // pre 要裂开rest的，前一个约束(rest裂开的第一个部分不能<pre)
    // rest 还剩下多少值，需要去裂开
    // 返回裂开的方法数
    public static int process(int pre, int rest) {
        if (rest == 0) {
            return 1;   // 之前的裂开方案，构成了一种有效方案
        }
        // rest小于之前的块，不能在往下分解，不然就降序，不被允许
        if (pre > rest) {
            return 0;
        }

        int ways = 0;
        for (int i = pre; i <= rest; i++) { // i: rest第一个裂开的部分，值是多少
            ways += process(i, rest - i);
        }
        return ways;
    }
    // O(N^3)
    public static int wayDp(int n) {
        if (n < 1) {
            return 0;
        }
        // pre -> 0~n (0不可用)
        // rest -> 0~n
        int[][] dp = new int[n + 1][n + 1];
        // dp[0][...]  0行不需要
        for (int pre = 1; pre <= n; pre++) {
            dp[pre][0] = 1;
        }

        // pre > rest  都等于0，所以左下半区都是0，但是0列都是1

        for (int pre = n; pre >= 1; pre--) {
            for (int rest = pre; rest <= n; rest++) {
                int ways = 0;
                // 枚举行为
                for (int i = pre; i <= rest; i++) { // i : rest第一个裂开的部分，值是多少
                    ways += dp[i][rest - i];
                }
                dp[pre][rest] = ways;
            }
        }
        return dp[1][n];
    }

    // 省略枚举行为的优化
    public static int way2(int n) {
        if (n < 1) {
            return 0;
        }
        int[][] dp = new int[n + 1][n + 1];
        // dp[0][...]  0行不需要
        for (int pre = 1; pre <= n; pre++) {
            dp[pre][0] = 1;
        }
        /**
         * 枚举优化：
         * ?格子(2,6) -> 第一块是2，还剩余6需要裂开，依赖下面
         * -> (2,4)
         * -> (3,3)
         * -> (4,2)
         * -> (5,1)
         * -> (6,0)
         *
         * Y下方格子(3,6) -> 第一块是3，还剩余6需要裂开，依赖下面
         * -> (3,3)
         * -> (4,2)
         * -> (5,1)
         * -> (6,0)
         * 由此可以看出？ = (2,4) + Y(此时Y已经求好了)
         */
        for (int pre = 1; pre <= n; pre++) {
            dp[pre][pre] = 1;
        }

        for (int pre = n - 1; pre >= 1; pre--) {
            for (int rest = pre + 1; rest <= n; rest++) {
                dp[pre][rest] = dp[pre + 1][rest] + dp[pre][rest - pre];
            }
        }
        return dp[1][n];
    }

    public static void main(String[] args) {
        int n = 20;
        System.out.println(way1(n));
        System.out.println(wayDp(n));
        System.out.println(way2(n));
    }

}
