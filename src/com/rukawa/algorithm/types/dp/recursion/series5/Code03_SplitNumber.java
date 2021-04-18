package com.rukawa.algorithm.types.dp.recursion.series5;

import javax.swing.*;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @Author: Administrator
 * @Date: 2021/4/14 0014 0:35
 */
public class Code03_SplitNumber {
    /**
     * 一个数字裂开，有几种裂开的方法，但是要求裂开的数后面的数不能小与前面的数
     * 比如3：1+1+1，1+2+3，3，但是不能出现2+1,后面的数字比前面的小
     * 给定一个正数n，求裂开的方法数。动态规划
     * 优化状态依赖的技巧
     */

    public static int ways(int n) {
        if (n < 0) {
            return 0;
        }
        if (n == 1) {
            return 1;
        }
        return process(1, n);
    }

    /**
     * @param pre 上一个拆出来的数
     * @param rest 还剩rest需要去拆
     * @return 返回拆解的方法数
     */
    public static int process(int pre, int rest) {
        // 如果rest==pre只能有一种方法，后续的pre一定大于rest
        if (rest == 0) {
            return 1;
        }
        if (pre > rest) {
            return 0;
        }
        // pre < rest
        // 尝试的数一定不能比pre小，所以只能从pre开始尝试
        int ways = 0;
        for (int first = pre; first <= rest; first++) {
            ways += process(first, rest - first);
        }
        // rest == first,此时需要+1
//        ways++;
        return ways;
    }

    public static int dp1(int n) {
        if (n < 0) {
            return 0;
        }
        if (n == 1) {
            return 1;
        }
        int[][] dp = new int[n + 1][n + 1];
//        dp[0][...] 第一行无效
        /**
         * 思路：假设是个8*8的格子
         *    1、对角线位置分析，dp[8][8] pre=8,rest=8,此时依赖pre=8,rest=0的格子
         *                    dp[7][7] pre=7,rest=7,此时依赖pre=7，rest=0的格子
         *                    。。。
         *                    所以得出对象线位置依赖本行0列的位置
         *    2、普遍位置分析
         *      dp[3][6]的格子依赖dp[3][3],dp[4][2],dp[5][1],dp[6][0]
         */
        for (int pre = 1; pre <= n; pre++) {
            dp[pre][0] = 1;
            dp[pre][pre] = 1;
        }

        for (int pre = n - 1; pre > 0; pre--) {
            for (int rest = pre + 1; rest <= n; rest++) {
                int ways = 0;
                for (int first = pre; first <= rest; first++) {
                    ways += dp[first][rest - first];
                }
                dp[pre][rest] = ways;
            }
        }
        return dp[1][n];
    }

    public static int dp2(int n) {
        if (n < 0) {
            return 0;
        }
        if (n == 1) {
            return 1;
        }
        int[][] dp = new int[n + 1][n + 1];
//        dp[0][...] 第一行无效
        for (int pre = 1; pre <= n; pre++) {
            dp[pre][0] = 1;
            dp[pre][pre] = 1;
        }

        for (int pre = n - 1; pre > 0; pre--) {
            for (int rest = pre + 1; rest <= n; rest++) {
                /**
                 * 斜率优化：
                 *  dp[3][6]的格子依赖dp[3][3],dp[4][2],dp[5][1],dp[6][0]
                 *  观察dp[4][6]的格子依赖dp[4][2],dp[5][1],dp[6][0]
                 *  所以dp[3][6]的格子依赖dp[3][3]和dp[4][6]的格子
                 *  dp[pre][rest]依赖的格子是dp[pre+1][rest]和dp[pre][rest-pre]
                 */
                dp[pre][rest] = dp[pre + 1][rest] + dp[pre][rest - pre];
            }
        }
        return dp[1][n];
    }

    public static void main(String[] args) {
        int test = 13;
        System.out.println(ways(test));
        System.out.println(dp1(test));
        System.out.println(dp2(test));
    }
}
